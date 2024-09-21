package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class GenreDAO implements IDAO<Genre> {

	private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester");
	private EntityManager entityManager;

	public GenreDAO (EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public void create (Genre genre) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(genre);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read (Genre genre) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			Genre genre1 = em.find(Genre.class, genre.getId());
			System.out.println(genre1);
		}
	}

	@Override
	public void update (Genre genre) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.merge(genre);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete (Genre genre) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.remove(genre);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Genre findById (Long id) {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Genre.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Genre> findAll() {
		EntityTransaction transaction = null;

		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}