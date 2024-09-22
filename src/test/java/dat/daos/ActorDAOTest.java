package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.ActorDTO;
import dat.entities.Actor;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActorDAOTest {

	private EntityManagerFactory emf;
	private ActorDAO actorDAO;

	@BeforeAll
	public void setUp() {
		// Initialize EntityManagerFactory using the HibernateConfig for tests
		emf = HibernateConfig.getEntityManagerFactoryForTest();
		actorDAO = ActorDAO.getInstance(emf);
	}

	@AfterAll
	public void tearDown() {
		if (emf != null) {
			emf.close();
		}
	}

	@Test
	public void testPersistEntity() {
		// Create a sample ActorDTO
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(1L);
		actorDTO.setName("Test Actor");

		// Persist the actor and assert
		ActorDTO persistedActor = actorDAO.persistEntity(actorDTO);
		assertNotNull(persistedActor);
		assertEquals("Test Actor", persistedActor.getName());
	}

	@Test
	public void testFindAll() {
		// Persist a few actors
		ActorDTO actor1 = new ActorDTO();
		actor1.setId(2L);
		actor1.setName("Actor 1");

		ActorDTO actor2 = new ActorDTO();
		actor2.setId(3L);
		actor2.setName("Actor 2");

		actorDAO.persistEntity(actor1);
		actorDAO.persistEntity(actor2);

		// Retrieve all persisted actors and assert
		List<ActorDTO> actors = ActorDAO.findAll();
		assertNotNull(actors);
		assertTrue(actors.size() >= 2);  // Ensure that at least 2 actors are persisted
	}

	@Test
	public void testFindEntity() {
		// Persist an actor to test retrieval
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(4L);
		actorDTO.setName("Findable Actor");
		actorDAO.persistEntity(actorDTO);

		// Find the actor and assert
		ActorDTO foundActor = ActorDAO.findEntity(4L);
		assertNotNull(foundActor);
		assertEquals("Findable Actor", foundActor.getName());
	}

	@Test
	public void testRemoveEntity() {
		// Persist an actor and then remove it
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(5L);
		actorDTO.setName("Removable Actor");

		actorDAO.persistEntity(actorDTO);

		// Remove the actor
		ActorDAO.removeEntity(5L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> ActorDAO.findEntity(5L));
	}

	@Test
	public void testUpdateEntity() {
		// Persist an actor
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(6L);
		actorDTO.setName("Updatable Actor");

		actorDAO.persistEntity(actorDTO);

		// Update the actor
		actorDTO.setName("Updatable Actor - Updated");

		ActorDTO updatedActor = ActorDAO.updateEntity(actorDTO, 6L);

		assertNotNull(updatedActor);
		assertEquals("Updatable Actor - Updated", updatedActor.getName());
	}

	@Test
	public void testPersistListOfActors() {
		// Create a list of ActorDTOs
		ActorDTO actor1 = new ActorDTO(7L, "Actor 7");
		ActorDTO actor2 = new ActorDTO(8L, "Actor 8");

		List<ActorDTO> actorList = List.of(actor1, actor2);

		// Persist the list of actors and assert
		List<ActorDTO> persistedActors = actorDAO.persistListOfActors(actorList);
		assertNotNull(persistedActors);
		assertEquals(2, persistedActors.size());
		assertEquals("Actor 7", persistedActors.get(0).getName());
		assertEquals("Actor 8", persistedActors.get(1).getName());
	}
}
