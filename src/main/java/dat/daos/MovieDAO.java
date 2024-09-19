package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDAO {

	//All methods here interact with the database

	private static MovieDAO instance;
	private final EntityManagerFactory emf;

	private MovieDAO (EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static synchronized MovieDAO getInstance (EntityManagerFactory emf) {
		if (instance == null) {
			instance = new MovieDAO(emf);
		}
		return instance;
	}

	// CRUD METHODS

	// Method to find all movies in the database
	public void findAll () {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
			List<Movie> movies = query.getResultList();
			movies.stream().map(MovieDTO::new).toList();
		} catch (Exception e) {
			throw new JpaException("Failed to find movies.");
		}
	}

	// Method to persist a movie to the database
	public void persistEntity (MovieDTO movieDTO) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			final EntityManager finalEm = em;

			em.getTransaction().begin();
			Movie movie = new Movie(movieDTO);

			if (movieDTO.getDirectors() != null && !movieDTO.getDirectors().isEmpty()) {
				Director director = finalEm.find(Director.class, movieDTO.getDirectors().get(0).getId());
			}

			if (movieDTO.getGenres() != null) {
				List<Genre> genres = movieDTO.getGenres().stream().map(genreDTO -> finalEm.find(Genre.class, genreDTO.getId())).collect(Collectors.toList());
				movie.setGenres(genres);
			}

			if (movieDTO.getActors() != null) {
				List<Actor> actors = movieDTO.getActors().stream().map(actorDTO -> finalEm.find(Actor.class, actorDTO.getId())).collect(Collectors.toList());
				movie.setActors(actors);
			}

			em.persist(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new JpaException("Failed to persist movie:" + e.getMessage());
		} finally {
			if (em != null) em.close();
		}
	}

	// Method to delete a movie from the database
	public void removeEntity (Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found with id: " + id);
			}
			em.getTransaction().begin();
			em.remove(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new JpaException("Failed to remove movie.");
		} finally {
			if (em != null) em.close();
		}
	}

	// Method to find a movie by id
	public MovieDTO findEntity (Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found with id: " + id);
			}
			return new MovieDTO(movie);
		} catch (Exception e) {
			throw new JpaException("Failed to find movie.");
		}
	}

	// Method to update a movie in the database by id
	public void updateEntity (MovieDTO movieDTO, Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found with id: " + id);
			}
			em.getTransaction().begin();
			// Update the movie with the new movieDTO
			movie = new Movie(movieDTO);
			em.merge(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new JpaException("Failed to update movie.");
		} finally {
			if (em != null) em.close();
		}
	}

	// OTHER METHODS

	// Method to find a movie by genre
	public void findMovieByGenre (String genre) {
	}

	// Method to find a movie by actor
	public void findByActor (String actor) {
	}

	// Method to find a movie by director
	public void findByDirector (String director) {
	}

	// Method to update a movie by title
	public void updateByTitle (String title) {
	}

	// Method to update a movie by release date
	public void updateByReleaseDate (String releaseDate) {
	}

	// Method to delete a movie by title
	public void deleteByTitle (String title) {
	}

	// Method to delete a movie by release date
	public void deleteByReleaseDate (String releaseDate) {
	}

	// Method to search for a movie by title - should be case sensitive
	public void searchByTitle (String title) {
	}

	// Method to get the average rating of a movie
	public void getAverageRating (String title) {
	}

	// Method to get the average rating of all movies
	public void getAverageRatingOfAllMovies () {
	}

	// Method to get the top 10 lowest rated movies
	public void getTop10LowestRatedMovies () {
	}

	// Method to get the top 10 highest rated movies
	public void getTop10HighestRatedMovies () {
	}

	// Method to get the top 10 most popular movies
	public void getTop10MostPopularMovies () {
	}
}