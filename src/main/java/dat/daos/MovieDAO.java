package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Movie;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDAO implements GenericDAO<MovieDTO, Long> {

	// Singleton instance
	private static MovieDAO instance;

	private final EntityManagerFactory emf;

	// Private constructor
	private MovieDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Singleton pattern
	public static synchronized MovieDAO getInstance(EntityManagerFactory emf) {
		if (instance == null) {
			instance = new MovieDAO(emf);
		}
		return instance;
	}

	// Method to find all movies in the database
	@Override
	public Collection<MovieDTO> findAll() {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
			List<Movie> movies = query.getResultList();
			return movies.stream().map(MovieDTO::new).collect(Collectors.toList());
		} catch (Exception e) {
			throw new JpaException("Could not find movies.", e);
		} finally {
			if (em != null) em.close();
		}
	}

	// Method to persist a movie to the database
	@Override
	public void persistEntity(MovieDTO movieDTO) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Movie movie = new Movie(movieDTO);
			em.persist(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new JpaException("Failed to persist movie.", e);
		} finally {
			if (em != null) em.close();
		}
	}

	// Method to delete a movie from the database
	@Override
	public void removeEntity(Long id) {
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
			throw new JpaException("Failed to remove movie.", e);
		} finally {
			if (em != null) em.close();
		}
	}

	// Method to find a movie by id
	@Override
	public MovieDTO findEntity(Long id) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found with id: " + id);
			}
			return new MovieDTO(movie);
		} catch (Exception e) {
			throw new JpaException("Failed to find movie.", e);
		} finally {
			if (em != null) em.close();
		}
	}

	// Method to update a movie in the database by id
	@Override
	public void updateEntity(MovieDTO movieDTO, Long id) {
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
			throw new JpaException("Failed to update movie.", e);
		} finally {
			if (em != null) em.close();
		}
	}
}