package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.DirectorDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DirectorDAOTest {

	private EntityManagerFactory emf;
	private DirectorDAO directorDAO;

	@BeforeAll
	public void setUp() {
		// Initialize EntityManagerFactory using the HibernateConfig for tests
		emf = HibernateConfig.getEntityManagerFactoryForTest();
		directorDAO = DirectorDAO.getInstance(emf);
	}

	@AfterAll
	public void tearDown() {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersistEntity() {
		// Create a sample DirectorDTO
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(19684L);
		directorDTO.setName("Bille August");

		// Persist the director and assert
		DirectorDTO persistedDirector = directorDAO.persistEntity(directorDTO);
		assertNotNull(persistedDirector);
		assertEquals("Bille August", persistedDirector.getName());
	}

	@Test
	public void testPersistListOfDirectors() {
		// Create a list of DirectorDTOs
		DirectorDTO director1 = new DirectorDTO();
		director1.setId(1183636L);
		director1.setName("Fenar Ahmad");

		DirectorDTO director2 = new DirectorDTO();
		director2.setId(4453L);
		director2.setName("Thomas Vinterberg");

		List<DirectorDTO> directorList = List.of(director1, director2);

		// Persist the list of directors and assert
		List<DirectorDTO> persistedDirectors = directorDAO.persistListOfDirectors(directorList);
		assertNotNull(persistedDirectors);
		assertEquals(2, persistedDirectors.size());
		assertEquals("Fenar Ahmad", persistedDirectors.get(0).getName());
		assertEquals("Thomas Vinterberg", persistedDirectors.get(1).getName());
	}

	@Test
	public void testFindAll() {
		// Persist a few directors
		DirectorDTO director1 = new DirectorDTO();
		director1.setId(1183636L);
		director1.setName("Fenar Ahmad");

		DirectorDTO director2 = new DirectorDTO();
		director2.setId(4453L);
		director2.setName("Thomas Vinterberg");

		directorDAO.persistEntity(director1);
		directorDAO.persistEntity(director2);

		// Retrieve all persisted directors and assert
		List<DirectorDTO> directors = DirectorDAO.findAll();
		assertNotNull(directors);
		assertTrue(directors.size() >= 2);  // Ensure that at least 2 directors are persisted
	}

	@Test
	public void testFindEntity() {
		// Persist a director to test retrieval
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(2168341L);
		directorDTO.setName("Kari Vidø");

		directorDAO.persistEntity(directorDTO);

		// Find the director and assert
		DirectorDTO foundDirector = DirectorDAO.findEntity(2168341L);
		assertNotNull(foundDirector);
		assertEquals("Kari Vidø", foundDirector.getName());
	}

	@Test
	public void testUpdateEntity() {
		// Persist a director
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(2168341L);
		directorDTO.setName("Kari Vidø");

		directorDAO.persistEntity(directorDTO);

		// Update the director
		directorDTO.setName("Updated");

		DirectorDTO updatedDirector = DirectorDAO.updateEntity(directorDTO, 2168341L);

		assertNotNull(updatedDirector);
		assertEquals("Updated", updatedDirector.getName());
	}

	@Test
	public void testRemoveEntity() {
		// Persist a director and then remove it
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(2168341L);
		directorDTO.setName("Kari Vidø");

		directorDAO.persistEntity(directorDTO);

		// Remove the director
		DirectorDAO.removeEntity(2168341L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> DirectorDAO.findEntity(2168341L));
	}
}