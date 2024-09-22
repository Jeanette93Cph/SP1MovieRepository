package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MovieDAOTest {

	private EntityManagerFactory emf;
	private MovieDAO movieDAO;

	@BeforeAll
	public void setUp() {
		// Initialize EntityManagerFactory using the HibernateConfig for test purposes
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

	}

	@Test
	public void testPersistListOfMovies() {
		// Create a list of MovieDTOs

	}

	@Test
	public void testFindEntity() {
		// Create and persist a movie

	}

	@Test
	public void testUpdateEntity() {
		// Create and persist a movie

	}

	@Test
	public void testRemoveEntity() {
		// Create and persist a movie

	}

	@Test
	public void testFindGenreInASpecificMovie() {
		// Create and persist a movie with a genre

	}

	@Test
	public void testFindDirectorInASpecificMovie() {
		// Create and persist a movie with a director

	}
}