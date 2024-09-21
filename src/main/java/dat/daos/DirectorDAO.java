package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DirectorDAO implements IDAO<Director> {

	private static final Logger logger = LoggerFactory.getLogger(DirectorDAO.class);

	private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester");
	private EntityManager entityManager;

	public DirectorDAO (EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void create (Director t) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read (Director director) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			Director director1 = em.find(Director.class, director.getId());
			System.out.println(director1);
		}
	}

	@Override
	public void update (Director director) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.merge(director);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//example with logging
	@Override
	public void delete(Director director) {
		EntityTransaction transaction = null;
		EntityManager em = emf.createEntityManager();

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			if (em.contains(director)) {
				em.remove(director);
				logger.info("Director with ID {} removed successfully", director.getId());
			} else {
				Director mergedDirector = em.merge(director);
				em.remove(mergedDirector);
				logger.info("Director with ID {} merged and removed successfully", director.getId());
			}
			em.getTransaction().commit();
		} catch (IllegalArgumentException e) {
			logger.error("Error removing director: Entity is not managed or not present, ID: {}", director.getId(), e);
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
				logger.warn("Transaction rolled back due to an IllegalArgumentException");
			}
		} catch (Exception e) {
			logger.error("Error occurred while deleting director with ID {}", director.getId(), e);
			if (em != null && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
				logger.warn("Transaction rolled back due to an unexpected error");
			}
		} finally {
			if (em != null) {
				em.close();
				logger.info("EntityManager closed");
			}
		}
	}

	@Override
	public Director findById (Long id) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Director.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Director> findAll() {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT d FROM Director d", Director.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}