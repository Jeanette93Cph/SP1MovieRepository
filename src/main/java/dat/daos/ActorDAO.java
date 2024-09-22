package dat.daos;

import dat.dtos.ActorDTO;
import dat.entities.Actor;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object Layer
 * CRUD operations to create, read, update, and delete actors in the database
 */

public class ActorDAO {

	private static ActorDAO actorDAO;
	private static EntityManagerFactory emf;

	//constructor
	public ActorDAO (EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Singleton pattern - if there is no instance of ActorDAO, create one
	public static ActorDAO getInstance (EntityManagerFactory emf) {
		if (actorDAO == null) {
			actorDAO = new ActorDAO(emf);
		}
		return actorDAO;
	}

	// Persist a new actor object into db
	public static ActorDTO persistEntity (ActorDTO actorDTO) {
		Actor actor = new Actor();
		actor.setId(actorDTO.getId());
		actor.setName(actorDTO.getName());

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(actor);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to persist actor:" + e.getMessage());
		}
		return new ActorDTO(actor);
	}

	// Persist a list of actors into db
	public List<ActorDTO> persistListOfActors (List<ActorDTO> actorDTOList) {
		List<ActorDTO> persistedlist = new ArrayList<>();
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();

			for (ActorDTO dto : actorDTOList) {
				Actor actor = em.find(Actor.class, dto.getId());

				if (actor == null) {
					actor = new Actor(dto);
					em.persist(actor);
				} else {
					actor.setName(dto.getName());
					actor = em.merge(actor);
				}

				dto.setId(actor.getId());
				persistedlist.add(dto);

			}
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Failed to persist list of actors: ");
			e.printStackTrace();

		}
		return persistedlist;
	}

	// Find all actors in db
	public static List<ActorDTO> findAll () {
		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT new dat.dtos.ActorDTO(a) FROM Actor a", ActorDTO.class).getResultList();
		} catch (Exception e) {
			throw new JpaException("Failed to find all actors.");
		}
	}

	// Find an actor by id
	public static ActorDTO findEntity (Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			Actor actor = em.find(Actor.class, id);
			if (actor == null) {
				throw new JpaException("No actor found with id: " + id);
			}
			return new ActorDTO(em.find(Actor.class, id));
		} catch (Exception e) {
			throw new JpaException("Failed to find actor.");
		}
	}

	// Update an actor by id
	public static ActorDTO updateEntity (ActorDTO actorDTO, Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			Actor actor = em.find(Actor.class, id);

			if (actor == null) {
				throw new JpaException("No actor found with id: " + id);
			}

			actor.setName(actorDTO.getName());
			actor.setId(actorDTO.getId());

			em.merge(actor);
			em.getTransaction().commit();

			return new ActorDTO(actor);

		} catch (Exception e) {
			System.out.println("Failed to update actor: ");
			e.printStackTrace();
		}
		return null;
	}

 	// Delete an existing actor in the db by id
	public static void removeEntity (Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			Actor actor = em.find(Actor.class, id);
			if (actor == null) {
				throw new JpaException("No actor found with id: " + id);
			}
			em.getTransaction().begin();
			em.remove(actor);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Failed to delete actor: ");
			e.printStackTrace();
		}
	}
}