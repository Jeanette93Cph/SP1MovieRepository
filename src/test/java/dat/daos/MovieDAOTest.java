package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.MovieDTO;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieDAOTest {

	private EntityManagerFactory emf;
	private MovieDAO movieDAO;

	@BeforeAll
	public void setUp() {
		emf = HibernateConfig.getEntityManagerFactoryForTest();
		movieDAO = MovieDAO.getInstance(emf);
	}

	@AfterAll
	public void tearDown() {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersistEntity() {
		// Create a sample MovieDTO
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(1L);
		movieDTO.setTitle("Inception");
		movieDTO.setOriginalLanguage("en");
		movieDTO.setReleaseDate("2010-07-16");
		movieDTO.setPopularity(100.0);
		movieDTO.setVoteAverage(8.8);
		movieDTO.setGenreIDs(List.of(28L, 12L)); // Action, Adventure

		// Persist the movie and assert
		MovieDTO persistedMovie = movieDAO.persistEntity(movieDTO);
		assertNotNull(persistedMovie);
		assertEquals("Inception", persistedMovie.getTitle());
		assertEquals(2, persistedMovie.getGenreIDs().size());
	}

	@Test
	public void testFindAll() {
		// Assuming some movies have already been persisted
		List<MovieDTO> movies = movieDAO.findAll();
		assertNotNull(movies);
		assertTrue(movies.size() > 0);
	}

	@Test
	public void testFindEntity() {
		// Persist a movie to test retrieval
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(2L);
		movieDTO.setTitle("Interstellar");
		movieDTO.setOriginalLanguage("en");
		movieDTO.setReleaseDate("2014-11-07");
		movieDTO.setPopularity(90.0);
		movieDTO.setVoteAverage(8.6);
		movieDTO.setGenreIDs(List.of(18L, 878L)); // Drama, Sci-Fi

		movieDAO.persistEntity(movieDTO);

		// Find the movie and assert
		MovieDTO foundMovie = movieDAO.findEntity(2L);
		assertNotNull(foundMovie);
		assertEquals("Interstellar", foundMovie.getTitle());
	}

	@Test
	public void testRemoveEntity() {
		// Persist a movie and then remove it
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(3L);
		movieDTO.setTitle("The Matrix");
		movieDTO.setOriginalLanguage("en");
		movieDTO.setReleaseDate("1999-03-31");
		movieDTO.setPopularity(85.0);
		movieDTO.setVoteAverage(8.7);
		movieDTO.setGenreIDs(List.of(28L, 878L)); // Action, Sci-Fi

		movieDAO.persistEntity(movieDTO);

		// Remove the movie
		movieDAO.removeEntity(3L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> movieDAO.findEntity(3L));
	}

	@Test
	public void testUpdateEntity() {
		// Persist a movie
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(4L);
		movieDTO.setTitle("The Dark Knight");
		movieDTO.setOriginalLanguage("en");
		movieDTO.setReleaseDate("2008-07-18");
		movieDTO.setPopularity(98.0);
		movieDTO.setVoteAverage(9.0);
		movieDTO.setGenreIDs(List.of(28L, 18L)); // Action, Drama

		movieDAO.persistEntity(movieDTO);

		// Update the movie
		movieDTO.setTitle("The Dark Knight Rises");
		movieDTO.setVoteAverage(8.4);

		MovieDTO updatedMovie = movieDAO.updateEntity(movieDTO, 4L);

		assertNotNull(updatedMovie);
		assertEquals("The Dark Knight Rises", updatedMovie.getTitle());
		assertEquals(8.4, updatedMovie.getVoteAverage());
	}

	@Test
	public void testFindAllMoviesInASpecificGenre() {
		// Persist movies in different genres
		MovieDTO actionMovie = new MovieDTO();
		actionMovie.setId(5L);
		actionMovie.setTitle("Mad Max: Fury Road");
		actionMovie.setOriginalLanguage("en");
		actionMovie.setReleaseDate("2015-05-15");
		actionMovie.setPopularity(88.0);
		actionMovie.setVoteAverage(8.1);
		actionMovie.setGenreIDs(List.of(28L)); // Action

		MovieDTO dramaMovie = new MovieDTO();
		dramaMovie.setId(6L);
		dramaMovie.setTitle("The Shawshank Redemption");
		dramaMovie.setOriginalLanguage("en");
		dramaMovie.setReleaseDate("1994-09-23");
		dramaMovie.setPopularity(92.0);
		dramaMovie.setVoteAverage(9.3);
		dramaMovie.setGenreIDs(List.of(18L)); // Drama

		movieDAO.persistEntity(actionMovie);
		movieDAO.persistEntity(dramaMovie);

		// Fetch all action movies
		List<MovieDTO> actionMovies = movieDAO.findAllMoviesInASpecificGenre("Action");
		assertNotNull(actionMovies);
		assertTrue(actionMovies.size() > 0);
		assertEquals("Mad Max: Fury Road", actionMovies.get(0).getTitle());
	}
}