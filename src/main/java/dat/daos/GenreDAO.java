package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public abstract class GenreDAO implements IDAO<Genre> {

	EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester");

	@Override
	public void create (Genre genre) {
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
		try (EntityManager em = emf.createEntityManager()) {
			Genre genre1 = em.find(Genre.class, genre.getId());
			System.out.println(genre1);
		}
	}

	@Override
	public void update (Genre genre) {
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
		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Genre.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Genre> findAll() {
		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}