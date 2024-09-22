package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.DirectorDTO;
import dat.entities.Director;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DirectorDAOTest {

	private EntityManagerFactory emf;
	private DirectorDAO directorDAO;

	@BeforeAll
	public void setUp() {
		// Initialize EntityManagerFactory for test environment
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
		directorDTO.setId(1L);
		directorDTO.setName("Christopher Nolan");

		// Persist the director and assert
		DirectorDTO persistedDirector = directorDAO.persistEntity(directorDTO);
		assertNotNull(persistedDirector);
		assertEquals("Christopher Nolan", persistedDirector.getName());
	}

	@Test
	public void testFindAll() {
		// Assuming some directors have already been persisted
		List<DirectorDTO> directors = DirectorDAO.findAll();
		assertNotNull(directors);
		assertFalse(directors.isEmpty());
	}

	@Test
	public void testFindEntity() {
		// Persist a director to test retrieval
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(2L);
		directorDTO.setName("Steven Spielberg");

		directorDAO.persistEntity(directorDTO);

		// Find the director and assert
		DirectorDTO foundDirector = DirectorDAO.findEntity(2L);
		assertNotNull(foundDirector);
		assertEquals("Steven Spielberg", foundDirector.getName());
	}

	@Test
	public void testRemoveEntity() {
		// Persist a director and then remove it
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(3L);
		directorDTO.setName("Quentin Tarantino");

		directorDAO.persistEntity(directorDTO);

		// Remove the director
		DirectorDAO.removeEntity(3L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> DirectorDAO.findEntity(3L));
	}

	@Test
	public void testUpdateEntity() {
		// Persist a director
		DirectorDTO directorDTO = new DirectorDTO();
		directorDTO.setId(4L);
		directorDTO.setName("James Cameron");

		directorDAO.persistEntity(directorDTO);

		// Update the director
		directorDTO.setName("James Cameron - Updated");

		DirectorDTO updatedDirector = DirectorDAO.updateEntity(directorDTO, 4L);

		assertNotNull(updatedDirector);
		assertEquals("James Cameron - Updated", updatedDirector.getName());
	}

	@Test
	public void testPersistListOfDirectors() {

		// Create a list of DirectorDTOs
		DirectorDTO director1 = new DirectorDTO(5L, "Ridley Scott");
		DirectorDTO director2 = new DirectorDTO(6L, "Martin Scorsese");

		List<DirectorDTO> directorList = List.of(director1, director2);

		// Persist the list of directors and assert
		List<DirectorDTO> persistedDirectors = directorDAO.persistListOfDirectors(directorList);
		assertNotNull(persistedDirectors);
		assertEquals(2, persistedDirectors.size());
		assertEquals("Ridley Scott", persistedDirectors.get(0).getName());
		assertEquals("Martin Scorsese", persistedDirectors.get(1).getName());
	}
}