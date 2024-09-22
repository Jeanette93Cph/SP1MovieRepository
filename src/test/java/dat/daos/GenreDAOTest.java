package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.GenreDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenreDAOTest {

	private EntityManagerFactory emf;
	private GenreDAO genreDAO;

	@BeforeAll
	public void setUp() {
		// Initialize EntityManagerFactory using HibernateConfig for tests
		emf = HibernateConfig.getEntityManagerFactoryForTest();
		genreDAO = GenreDAO.getInstance(emf);
	}

	@AfterAll
	public void tearDown() {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersistEntity() {
		// Create a sample GenreDTO
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(28L);
		genreDTO.setName("Action");

		// Persist the genre and assert
		GenreDTO persistedGenre = genreDAO.persistEntity(genreDTO);
		assertNotNull(persistedGenre);
		assertEquals("Action", persistedGenre.getName());
	}

	@Test
	public void testPersistListOfGenres() {
		// Create a list of GenreDTOs
		GenreDTO genre1 = new GenreDTO();
		genre1.setId(35L);
		genre1.setName("Comedy");

		GenreDTO genre2 = new GenreDTO();
		genre2.setId(18L);
		genre2.setName("Drama");

		List<GenreDTO> genreList = List.of(genre1, genre2);

		// Persist the list of genres and assert
		List<GenreDTO> persistedGenres = genreDAO.persistListOfGenres(genreList);
		assertNotNull(persistedGenres);
		assertEquals(2, persistedGenres.size());
		assertEquals("Comedy", persistedGenres.get(0).getName());
		assertEquals("Drama", persistedGenres.get(1).getName());
	}

	@Test
	public void testFindAll() {
		// Persist a few genres
		GenreDTO genre1 = new GenreDTO();
		genre1.setId(878L);
		genre1.setName("Science Fiction");

		GenreDTO genre2 = new GenreDTO();
		genre2.setId(27L);
		genre2.setName("Horror");

		genreDAO.persistEntity(genre1);
		genreDAO.persistEntity(genre2);

		// Retrieve all persisted genres and assert
		List<GenreDTO> genres = GenreDAO.findAll();
		assertNotNull(genres);
		assertTrue(genres.size() >= 2);  // Ensure at least 2 genres are persisted
	}

	@Test
	public void testFindEntity() {
		// Persist a genre to test retrieval
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(53L);
		genreDTO.setName("Thriller");

		genreDAO.persistEntity(genreDTO);

		// Find the genre and assert
		GenreDTO foundGenre = GenreDAO.findEntity(53L);
		assertNotNull(foundGenre);
		assertEquals("Thriller", foundGenre.getName());
	}

	@Test
	public void testUpdateEntity() {
		// Persist a genre
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(12L);
		genreDTO.setName("Adventure");

		genreDAO.persistEntity(genreDTO);

		// Update the genre
		genreDTO.setName("Adventure - Updated");

		GenreDTO updatedGenre = GenreDAO.updateEntity(genreDTO, 12L);

		assertNotNull(updatedGenre);
		assertEquals("Adventure - Updated", updatedGenre.getName());
	}

	@Test
	public void testRemoveEntity() {
		// Persist a genre and then remove it
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(14L);
		genreDTO.setName("Fantasy");

		genreDAO.persistEntity(genreDTO);

		// Remove the genre
		GenreDAO.removeEntity(14L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> GenreDAO.findEntity(14L));
	}
}