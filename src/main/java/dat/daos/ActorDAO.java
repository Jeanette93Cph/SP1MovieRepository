package dat.daos;

import dat.config.HibernateConfig;
import dat.dtos.ActorDTO;
import dat.entities.Actor;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;

import java.util.List;

public class ActorDAO implements IDAO<Actor> {

	EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester");

	public ActorDAO (EntityManager em) {
		em = emf.createEntityManager();
	}

	@Override
	public void create(Actor actor) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(actor);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read(Actor actor) {
		try (EntityManager em = emf.createEntityManager()) {
			Actor actor1 = em.find(Actor.class, actor.getId());
			System.out.println(actor1);
		}
	}

	@Override
	public void update(Actor actor) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.merge(actor);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Actor actor) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.remove(actor);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Actor findById(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Actor.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Actor> findAll() {
		try (EntityManager em = emf.createEntityManager()) {
			Query query = em.createQuery("SELECT a FROM Actor a");
			List<Actor> actors = query.getResultList();
			return actors;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}