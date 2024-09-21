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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class MovieDAO
{

	// Singleton instance
	private static MovieDAO instance;

	private static EntityManagerFactory emf;

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
	public static MovieDTO persistEntity(MovieDTO movieDTO)
	{
		Movie movie;

		try(EntityManager em = emf.createEntityManager())
		{
			em.getTransaction().begin();


			// Find existing movie, or create a new one if it doesn't exist
			movie = em.find(Movie.class, movieDTO.getId());
			if (movie == null) {
				// Use constructor to map basic fields
				movie = new Movie(movieDTO);
			} else {
				// Update movie fields from DTO if already exists
				movie = new Movie(movieDTO);
			}

			// Set the director, actors, and genres
			setRelationships(em, movie, movieDTO);


			if(movie.getId() == null)
			{
				em.persist(movie);
			} else{
				//merge if it already exists
				movie = em.merge(movie);
			}

			em.getTransaction().commit();
		}
		catch(Exception e)
		{
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
			System.out.println("Failed to persist list of movies: " + e.getMessage());
			e.printStackTrace();
		}
		return persistedlist;
	}


	public static List<MovieDTO> findAll()
	{
		try(EntityManager em = emf.createEntityManager())
		{
			return em.createQuery("SELECT new dat.dtos.MovieDTO(m) FROM Movie m", MovieDTO.class).getResultList();
		}
		catch(Exception e){
			throw new JpaException("Failed to find all movies." + e.getMessage());
		}
	}

	public static MovieDTO findEntity(Long id)
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

	public static MovieDTO updateEntity(MovieDTO movieDTO, Long id)
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

			setRelationships(em, movie, movieDTO);

			em.merge(movie);
			em.getTransaction().commit();

			return new MovieDTO(movie);

		}
		catch (Exception e)
		{
			System.out.println("Failed to update movie: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}



	public static void removeEntity(Long id)
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
		}
		catch (Exception e)
		{
			System.out.println("Failed to delete movie: ");
			e.printStackTrace();
		}

	}

	public MovieDTO findGenreInASpecificMovie(String title)
	{
		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery(
							"SELECT new dat.dtos.MovieDTO(m) " +
									"FROM Movie m " +
									"JOIN m.genre g " +  // Assuming there's a field 'genre' in the Movie entity for the relationship
									"WHERE m.title = :title", MovieDTO.class)
					.setParameter("title", title) // Use setParameter for safely passing the title
					.getSingleResult(); // Assuming you want one result since you're filtering by title
		} catch (Exception e) {
			throw new JpaException("Failed to find genre in movie '" + title + "': " + e.getMessage());
		}
	}




	//Help method to set the director, actors and genres. help from chatgpt
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

//		// Set Actors
//		if (dto.getActors() != null) {
//			List<Actor> actors = new ArrayList<>();
//			for (ActorDTO actorDTO : dto.getActors()) {
//				Actor actor = em.find(Actor.class, actorDTO.getId());
//				if (actor == null) {
//					actor = new Actor(actorDTO);
//					em.persist(actor);
//				}
//				actors.add(actor);
//			}
//			movie.setActors(actors);
//		}

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