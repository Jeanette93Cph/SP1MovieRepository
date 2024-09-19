package dat.daos;

import dat.dtos.GenreDTO;
import dat.entities.Genre;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GenreDAO implements GenericDAO<GenreDTO, Long> {

	private static GenreDAO instance;
	private final EntityManagerFactory emf;

	public GenreDAO (EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static synchronized GenreDAO getInstance(EntityManagerFactory emf) {
		if (instance == null) {
			instance = new GenreDAO(emf);
		}
		return instance;
	}

	@Override
	public Collection<GenreDTO> findAll () {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
			List<Genre> genres = query.getResultList();
			return genres.stream().map(GenreDTO::new).collect(Collectors.toList());
		} catch (Exception e) {
			throw new JpaException("Failed to find all genres.");
		}
	}


	@Override
	public void persistEntity (GenreDTO genreDTO) {
		Genre genre = new Genre(genreDTO);
		try(var em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(genre);
			em.getTransaction().commit();
		}
	}

	@Override
	public void removeEntity (Long id) {

	}

	@Override
	public GenreDTO findEntity (Long id) {
		return null;
	}

	@Override
	public void updateEntity (GenreDTO entity, Long id) {

	}
}
