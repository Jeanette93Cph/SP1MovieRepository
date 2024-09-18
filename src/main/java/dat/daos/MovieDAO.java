package dat.daos;

import com.fasterxml.jackson.annotation.JsonProperty;
import dat.config.HibernateConfig;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDAO implements IDAO<MovieDTO, Long> {

	private static MovieDAO instance;
	private static EntityManagerFactory emf;

	private MovieDAO () {
		emf = HibernateConfig.getEntityManagerFactory("tmdb");
	}

	public static MovieDAO getInstance () {
		if (instance == null) {
			instance = new MovieDAO();
		}
		return instance;
	}

	@Override
	public boolean create (MovieDTO movieDTO) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			Movie movie = convertDtoToEntity(movieDTO);
			em.persist(movie);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete (MovieDTO movieDTO) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			Movie movie = convertDTOToEntity(movieDTO);
			em.remove(movie);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public MovieDTO getById (Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			return em.find(MovieDTO.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Set<MovieDTO> getAll () {
		try (EntityManager em = emf.createEntityManager()) {
			return Set.copyOf(em.createQuery("SELECT m FROM Movie m", MovieDTO.class).getResultList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update (MovieDTO movieDTO) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			Movie movie = convertDTOToEntity(movieDTO);
			em.merge(movie);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Movie convertDtoToEntity (MovieDTO movieDTO) {
		Movie movie = new Movie();

		// Set simple properties
		movie.setId(movieDTO.getId());
		movie.setTitle(movieDTO.getTitle());
		movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
		movie.setReleaseDate(LocalDate.parse(movieDTO.getReleaseDate()));
		movie.setVoteAverage(movieDTO.getVoteAverage());

		// Fetch and set Director entity by its ID
		Director director = directorService.getDirectorById(movieDTO.getDirectorId());
		movie.setDirector(director);

		// Fetch and set Genre entities by their IDs
		List<Genre> genres = movieDTO.getGenres().stream()
				.map(genreService::getGenreById) // Fetch each Genre by ID
				.collect(Collectors.toList());
		movie.setGenres(genres);

		// Fetch and set Actor entities by their IDs
		List<Actor> actors = movieDTO.getActorIds().stream()
				.map(actorService::getActorById) // Fetch each Actor by ID
				.collect(Collectors.toList());
		movie.setActors(actors);

		return movie;
	}

}