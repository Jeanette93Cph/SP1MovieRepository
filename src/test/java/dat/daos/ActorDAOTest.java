package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.ActorDTO;
import dat.entities.Actor;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActorDAOTest {

	private EntityManagerFactory emf;
	private ActorDAO actorDAO;

	@BeforeAll
	public void setUp() {
		// Initialize EntityManagerFactory for test environment
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
		ActorDTO persistedActor = ActorDAO.persistEntity(actorDTO);
		assertNotNull(persistedActor);
		assertEquals("Test Actor", persistedActor.getName());
	}

	@Test
	public void testFindAll() {
		// Assuming some actors have already been persisted
		List<ActorDTO> actors = ActorDAO.findAll();
		assertNotNull(actors);
		assertFalse(actors.isEmpty());
	}

	@Test
	public void testFindEntity() {
		// Persist an actor to test retrieval
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(2L);
		actorDTO.setName("Test Actor");

		actorDAO.persistEntity(actorDTO);

		// Find the actor and assert
		ActorDTO foundActor = ActorDAO.findEntity(2L);
		assertNotNull(foundActor);
		assertEquals("Brad Pitt", foundActor.getName());
	}

	@Test
	public void testRemoveEntity() {
		// Persist an actor and then remove it
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(3L);
		actorDTO.setName("Morgan Freeman");

		actorDAO.persistEntity(actorDTO);

		// Remove the actor
		ActorDAO.removeEntity(3L);

		// Try to find it and assert it's gone
		assertThrows(dat.exceptions.JpaException.class, () -> ActorDAO.findEntity(3L));
	}

	@Test
	public void testUpdateEntity() {
		// Persist an actor
		ActorDTO actorDTO = new ActorDTO();
		actorDTO.setId(4L);
		actorDTO.setName("Matt Damon");

		actorDAO.persistEntity(actorDTO);

		// Update the actor
		actorDTO.setName("Matt Damon - Updated");

		ActorDTO updatedActor = ActorDAO.updateEntity(actorDTO, 4L);

		assertNotNull(updatedActor);
		assertEquals("Matt Damon - Updated", updatedActor.getName());
	}

	@Test
	public void testPersistListOfActors() {
		// Create a list of ActorDTOs
		ActorDTO actor1 = new ActorDTO(5L, "Robert Downey Jr.");
		ActorDTO actor2 = new ActorDTO(6L, "Chris Evans");

		List<ActorDTO> actorList = List.of(actor1, actor2);

		// Persist the list of actors and assert
		List<ActorDTO> persistedActors = actorDAO.persistListOfActors(actorList);
		assertNotNull(persistedActors);
		assertEquals(2, persistedActors.size());
		assertEquals("Robert Downey Jr.", persistedActors.get(0).getName());
		assertEquals("Chris Evans", persistedActors.get(1).getName());
	}
}