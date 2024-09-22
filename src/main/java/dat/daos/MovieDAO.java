package dat.daos;

import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.exceptions.JpaException;
import dat.services.ActorService;
import dat.services.DirectorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDAO
{

	// Singleton instance
	private static MovieDAO instance;

	private static EntityManagerFactory emf;

	//used in method findDirectorInASpecificMovie() and findActorsInASpecificMovie()
	private static final String API_KEY = System.getenv("api_key");

	// Public constructor
	public MovieDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Singleton pattern
	public static MovieDAO getInstance(EntityManagerFactory emf) {
		if (instance == null) {
			instance = new MovieDAO(emf);
		}
		return instance;
	}


	//Persist one movie
	public MovieDTO persistEntity(MovieDTO movieDTO)
	{
		Movie movie;

		try(EntityManager em = emf.createEntityManager())
		{
			em.getTransaction().begin();

			// Find existing movie
			movie = em.find(Movie.class, movieDTO.getId());
			if (movie == null) {
				//Create a new Movie if it doesn't exist
				movie = new Movie(movieDTO);
				em.persist(movie);
			} else {
				// Update movie fields from DTO if already exists
				movie.setPopularity(movieDTO.getPopularity());
				movie.setTitle(movieDTO.getTitle());
				movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
				movie.setReleaseDate(movieDTO.getReleaseDate());
				movie.setVoteAverage(movieDTO.getVoteAverage());
				movie.setGenreIDs(movieDTO.getGenreIDs());
			}

			//called to handle the updating of the movies associated: director, actor and genres.
			setRelationships(em, movie, movieDTO);

			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to persist actor:" + e.getMessage());
		}
		return new MovieDTO(movie);
	}


	//Persist a list of movies
	public List<MovieDTO> persistListOfMovies(List<MovieDTO> movieDTOList)
	{
		List<MovieDTO> persistedlist = new ArrayList<>();

		try(EntityManager em = emf.createEntityManager())
		{
			em.getTransaction().begin();

			for(MovieDTO dto : movieDTOList)
			{
				Movie movie = em.find(Movie.class, dto.getId());

				if(movie == null)
				{
					movie = new Movie(dto);
					setRelationships(em, movie, dto);
					em.persist(movie);
				} else{
					movie = new Movie(dto);
					setRelationships(em, movie, dto);
					movie = em.merge(movie);
				}

				//add the persisted DTO to the result list
				persistedlist.add(new MovieDTO(movie));
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			throw new JpaException("Failed to persist list of movies: " + e.getMessage());
		}
		return persistedlist;
	}


	public static List<MovieDTO> findAll () {
		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT new dat.dtos.MovieDTO(m) FROM Movie m", MovieDTO.class).getResultList();
		} catch (Exception e) {
			throw new JpaException("Failed to find all movies." + e.getMessage());
		}
	}

	public MovieDTO findEntity(Long id)
	{
		try(EntityManager em = emf.createEntityManager())
		{
			Movie movie = em.find(Movie.class, id);
			if(movie == null)
			{
				throw new JpaException("No movie found with id: " + id);
			}
			return new MovieDTO(em.find(Movie.class, id));
		}
		catch (Exception e) {
			throw new JpaException("Failed to find movie.");
		}
	}

	public MovieDTO updateEntity(MovieDTO movieDTO, Long id)
	{
		try(EntityManager em = emf.createEntityManager())
		{
			em.getTransaction().begin();
			Movie movie = em.find(Movie.class, id);

			if(movie == null)
			{
				throw new JpaException("No movie found with id: " + id);
			}

			movie.setPopularity(movieDTO.getPopularity());
			movie.setTitle(movieDTO.getTitle());
			movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
			movie.setReleaseDate(movieDTO.getReleaseDate());
			movie.setVoteAverage(movieDTO.getVoteAverage());

			//called to handle the updating of the movies associated: director, actor and genres.
			setRelationships(em, movie, movieDTO);

			em.merge(movie);
			em.getTransaction().commit();

			return new MovieDTO(movie);

		} catch (Exception e) {
			System.out.println("Failed to update movie: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



	public void removeEntity(Long id)
	{
		try(EntityManager em = emf.createEntityManager())
		{
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found with id: " + id);
			}
			em.getTransaction().begin();
			em.remove(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to delete movie: " + e.getMessage());
		}

	}

	public MovieDTO findGenreInASpecificMovie(String title)
	{
		try (EntityManager em = emf.createEntityManager()) {
			// Hent filmen
			Movie movie = em.createQuery(
							"SELECT m FROM Movie m WHERE m.title = :title", Movie.class)
					.setParameter("title", title)
					.getSingleResult();

			// Hent genreID'erne fra filmen
			List<Long> genreIDs = movie.getGenreIDs();

			// Hent genrerne baseret på genreIDs
			List<Genre> genres = em.createQuery(
							"SELECT g FROM Genre g WHERE g.genre_id IN :genreIDs", Genre.class)
					.setParameter("genreIDs", genreIDs)
					.getResultList();

			// Opret en MovieDTO med genre
			MovieDTO movieDTO = new MovieDTO(movie);
			movieDTO.setGenres(genres.stream().map(GenreDTO::new).collect(Collectors.toList()));

			return movieDTO;
		} catch (Exception e) {
			throw new JpaException("Failed to find genre in movie '" + title + "': " + e.getMessage());
		}
	}

	public MovieDTO findDirectorInASpecificMovie(String title)
	{
		try (EntityManager em = emf.createEntityManager()) {

			TypedQuery<Movie> query = em.createQuery(
					"SELECT m FROM Movie m WHERE m.title = :title", Movie.class);
			query.setParameter("title", title);
			Movie movie = query.getSingleResult();

			MovieDTO movieDTO = new MovieDTO(movie);

			// Use the DirectorService to get the director details
			DirectorDTO directorDTO = DirectorService.extractDirectorFromCredits(
					DirectorService.getJSONResponse("https://api.themoviedb.org/3/movie/" + movieDTO.getId() + "/credits?api_key=" + API_KEY));

			if (directorDTO != null) {
				movieDTO.setDirector(directorDTO);
			}

			return movieDTO;
		} catch (Exception e) {
			throw new JpaException("Failed to find director in movie '" + title + "': " + e.getMessage());
		}
	}

	public MovieDTO findActorsInASpecificMovie(String title)
	{
		try (EntityManager em = emf.createEntityManager()) {

			TypedQuery<Movie> query = em.createQuery(
					"SELECT m FROM Movie m WHERE m.title = :title", Movie.class);
			query.setParameter("title", title);
			Movie movie = query.getSingleResult();

			MovieDTO movieDTO = new MovieDTO(movie);

			// Use the ActorService to get the actors details
			List<ActorDTO> actorDTOs = ActorService.extractActorsFromCredits(
					ActorService.getJSONResponse("https://api.themoviedb.org/3/movie/" + movieDTO.getId() + "/credits?api_key=" + API_KEY));

			if (actorDTOs != null) {
				movieDTO.setActors(actorDTOs);
			}

			return movieDTO;
		} catch (Exception e) {
			throw new JpaException("Failed to find director in movie '" + title + "': " + e.getMessage());
		}
	}

	//list of all movies within a particular genre. help from chatgpt
	public List<MovieDTO> findAllMoviesInASpecificGenre(String genreName)
	{
		try (EntityManager em = emf.createEntityManager()) {
			// fetch the genre
			Genre genre = em.createQuery(
							"SELECT g FROM Genre g WHERE g.name = :genreName", Genre.class)
					.setParameter("genreName", genreName)
					.getSingleResult();

			// fetch all movies that belong to the fetched genre
			List<Movie> movies = em.createQuery(
							"SELECT m FROM Movie m WHERE m.genreIDs LIKE :genreIdPattern", Movie.class)
					.setParameter("genreIdPattern", "%" + genre.getGenre_id() + "%")
					.getResultList();


			// convert Movie entities to MovieDTOs
			List<MovieDTO> movieDTOs = movies.stream().map(MovieDTO::new).collect(Collectors.toList());

			return movieDTOs;
		} catch (Exception e) {
			throw new JpaException("Failed to find movies in genre '" + genreName + "': " + e.getMessage());
		}
	}





	//Help method establishes the relationships between a Movie entity and its associated Director, Actors and Genres. help from chatgpt
	private static void setRelationships(EntityManager em, Movie movie, MovieDTO dto)
	{
		// Set Director
		DirectorDTO directorDTO = dto.getDirector();
		if (directorDTO != null) {
			Director director = em.find(Director.class, directorDTO.getId());
			if (director == null) {
				director = new Director(directorDTO);
				em.persist(director);
			}
			movie.setDirector(director);
		}

		// Set Actors
		if (dto.getActors() != null) {
			for (ActorDTO actorDTO : dto.getActors()) {
				Actor actor = em.find(Actor.class, actorDTO.getId());
				if (actor == null) {
					actor = new Actor(actorDTO);
					em.persist(actor);
				}
				movie.addActor(actor);
				actor.addMovie(movie);

			}
		}

		// Set Genres
		if (dto.getGenres() != null) {
			List<Genre> genres = new ArrayList<>();
			for (GenreDTO genreDTO : dto.getGenres()) {
				Genre genre = em.find(Genre.class, genreDTO.getId());
				if (genre == null) {
					genre = new Genre(genreDTO);
					em.persist(genre);
				}
				genres.add(genre);
			}
			movie.setGenres(genres);
		}
	}

}