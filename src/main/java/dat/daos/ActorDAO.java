package dat.daos;

import dat.dtos.ActorDTO;
import dat.entities.Actor;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class ActorDAO {

	private ActorDAO actorDAO;
	private EntityManagerFactory emf;

	private ActorDAO (EntityManagerFactory emf) {
		this.emf = emf;
	}

	public ActorDAO getInstance (EntityManagerFactory emf) {
		if (actorDAO == null) {
			actorDAO = new ActorDAO(emf);
		}
		return actorDAO;
	}

	public ActorDTO persistEntity (ActorDTO actorDTO) {
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

	public List<ActorDTO> findAll () {
		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT new dat.dtos.ActorDTO(a) FROM Actor a", ActorDTO.class).getResultList();
		} catch (Exception e) {
			throw new JpaException("Failed to find all actors.");
		}
	}

	public ActorDTO findEntity (Long id) {
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

	public ActorDTO updateEntity (ActorDTO actorDTO, Long id) {
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

	public void removeEntity (Long id) {
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