package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.GenreDTO;
import dat.entities.Genre;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GenreDAOTest {

	private EntityManagerFactory emf;
	private GenreDAO genreDAO;

	@BeforeAll
	public void setUp () {
		emf = HibernateConfig.getEntityManagerFactoryForTest();
		genreDAO = GenreDAO.getInstance(emf);
	}

	@AfterAll
	public void tearDown () {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersistEntity () {
		// Create a sample GenreDTO
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(1L);
		genreDTO.setName("Action");

		// Persist the genre and assert
		GenreDTO persistedGenre = GenreDAO.persistEntity(genreDTO);
		assertNotNull(persistedGenre);
		assertEquals("Action", persistedGenre.getName());
	}

	@Test
	public void testFindAll () {
		// Assuming some genres have already been persisted
		List<GenreDTO> genres = GenreDAO.findAll();
		assertNotNull(genres);
		assertTrue(genres.size() > 0);
	}

	@Test
	public void testFindEntity () {
		// Persist a genre to test retrieval
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(2L);
		genreDTO.setName("Adventure");

		GenreDAO.persistEntity(genreDTO);

		// Find the genre and assert
		GenreDTO foundGenre = GenreDAO.findEntity(2L);
		assertNotNull(foundGenre);
		assertEquals("Adventure", foundGenre.getName());
	}

	@Test
	public void testRemoveEntity () {
		// Persist a genre and then remove it
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(3L);
		genreDTO.setName("Comedy");

		GenreDAO.persistEntity(genreDTO);

		// Remove the genre
		GenreDAO.removeEntity(3L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> GenreDAO.findEntity(3L));
	}

	@Test
	public void testUpdateEntity () {
		// Persist a genre
		GenreDTO genreDTO = new GenreDTO();
		genreDTO.setId(4L);
		genreDTO.setName("Horror");

		GenreDAO.persistEntity(genreDTO);

		// Update the genre
		genreDTO.setName("Thriller");

		GenreDTO updatedGenre = GenreDAO.updateEntity(genreDTO, 4L);

		assertNotNull(updatedGenre);
		assertEquals("Thriller", updatedGenre.getName());
	}

	@Test
	public void testPersistListOfGenres () {
		// Create a list of GenreDTOs without IDs (assuming IDs are generated by the database)
		GenreDTO actionGenre = new GenreDTO();
		actionGenre.setName("Action");

		GenreDTO dramaGenre = new GenreDTO();
		dramaGenre.setName("Drama");

		List<GenreDTO> genreList = List.of(actionGenre, dramaGenre);

		// Persist the list of genres and assert
		List<GenreDTO> persistedGenres = genreDAO.persistListOfGenres(genreList);

		// Assert that the persistedGenres list is not null and contains the expected number of genres
		assertNotNull(persistedGenres);
		assertEquals(2, persistedGenres.size());

		// Assert that the names of the persisted genres are correctly assigned
		assertEquals("Action", persistedGenres.get(0).getName());
		assertEquals("Drama", persistedGenres.get(1).getName());

		// Assert that IDs have been assigned (since they should be auto-generated by the database)
		assertNotNull(persistedGenres.get(0).getId());
		assertNotNull(persistedGenres.get(1).getId());
	}
}