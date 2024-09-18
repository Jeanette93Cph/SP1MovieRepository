package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Movie;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDAO implements GenericDAO<MovieDTO, Long> {

	//singleton instance
	private static MovieDAO instance;
	private static EntityManagerFactory emf;

	//private constructor
	private MovieDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	//singleton pattern
	public static MovieDAO getInstance(EntityManagerFactory emf) {
		if (instance == null) {
			instance = new MovieDAO(emf);
		}
		return instance;
	}

	//method to find all movies in the database
	@Override
	public Collection<MovieDTO> findAll () {
		try (var em = emf.createEntityManager()) {
			TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);
			List<Movie> movies = query.getResultList();
			var collect = movies.stream().map(MovieDTO::new).collect(Collectors.toList());
			return collect;
		} catch (Exception e) {
			throw new JpaException("Could not find movies.");
		}
	}

	//method to persist a movie to the database
	@Override
	public void persistEntity (MovieDTO movieDTO) {
		Movie movie = new Movie(movieDTO);
		try (var em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to persist movie.");
		}
	}

	//method to delete a movie from the database
	@Override
	public void removeEntity (Long id) {
		try (var em = emf.createEntityManager()) {
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found.");
			}
			em.getTransaction().begin();
			em.remove(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to remove movie.");
		}
	}

	//method to find a movie by id
	@Override
	public MovieDTO findEntity (Long id) {
		try (var em = emf.createEntityManager()) {
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found.");
			}
			return new MovieDTO(movie);
		} catch (Exception e) {
			throw new JpaException("Failed to find movie.");
		}
	}

	//method to update a movie in the database by id
	//FIXME: This method is not used in the application
	@Override
	public void updateEntity (MovieDTO movieDTO, Long id) {
		/* try (var em = emf.createEntityManager()) {
			Movie movie = em.find(Movie.class, id);
			if (movie == null) {
				throw new JpaException("No movie found.");
			}
			movie.update(movieDTO);
			em.getTransaction().begin();
			em.merge(movie);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to update movie.");
		} */
	}
}