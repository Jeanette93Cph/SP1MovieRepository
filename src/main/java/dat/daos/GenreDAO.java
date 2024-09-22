package dat.daos;

import dat.dtos.GenreDTO;
import dat.entities.Genre;
import dat.exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object Layer
 * CRUD operations to create, read, update, and delete genres in the database
 */

public class GenreDAO {
	private static GenreDAO genreDAO;
	private static EntityManagerFactory emf;

	//constructor
	public GenreDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	// Singleton pattern - if there is no instance of GenreDAO, create one
	public static GenreDAO getInstance(EntityManagerFactory emf) {
		if (genreDAO == null) {
			genreDAO = new GenreDAO(emf);
		}
		return genreDAO;
	}

	// Persist a new genre object into db - return the persisted genre as a DTO
	public static GenreDTO persistEntity(GenreDTO genreDTO) {
		Genre genre = new Genre();
		genre.setGenre_id(genreDTO.getId());
		genre.setName(genreDTO.getName());

		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(genre);
			em.getTransaction().commit();
		} catch (Exception e) {
			throw new JpaException("Failed to persist genre: " + e.getMessage());
		}
		return new GenreDTO(genre);
	}

	// Persist a list of genres into db - return the persisted genres as a list of DTOs
	public List<GenreDTO> persistListOfGenres(List<GenreDTO> genreDTOList) {
		List<GenreDTO> persistedlist = new ArrayList<>();
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();

			for (GenreDTO dto : genreDTOList) {
				Genre genre = em.find(Genre.class, dto.getId());

				if (genre == null) {
					genre = new Genre(dto);
					em.persist(genre);
				} else {
					genre.setName(dto.getName());
					genre = em.merge(genre);
				}

				dto.setId(genre.getGenre_id());
				persistedlist.add(dto);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Failed to persist list of genres: " + e.getMessage());
			e.printStackTrace();
		}
		return persistedlist;
	}

	// Find all genres in the database
	public static List<GenreDTO> findAll() {
		try (EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT new dat.dtos.GenreDTO(g) FROM Genre g", GenreDTO.class).getResultList();
		} catch (Exception e) {
			throw new JpaException("Failed to find all genres: " + e.getMessage());
		}
	}

	// Find a genre by id in the database by id
	public static GenreDTO findEntity(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			Genre genre = em.find(Genre.class, id);
			if (genre == null) {
				throw new JpaException("No genre found with id: " + id);
			}
			return new GenreDTO(genre);
		} catch (Exception e) {
			throw new JpaException("Failed to find genre: " + e.getMessage());
		}
	}

	// Update a genre in the database by id
	public static GenreDTO updateEntity(GenreDTO genreDTO, Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			Genre genre = em.find(Genre.class, id);

			if (genre == null) {
				throw new JpaException("No genre found with id: " + id);
			}

			genre.setName(genreDTO.getName());
			genre.setGenre_id(genreDTO.getId());

			em.merge(genre);
			em.getTransaction().commit();

			return new GenreDTO(genre);

		} catch (Exception e) {
			System.out.println("Failed to update genre: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// Delete an existing genre from the database by id
	public static void removeEntity(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
			Genre genre = em.find(Genre.class, id);
			if (genre == null) {
				throw new JpaException("No genre found with id: " + id);
			}
			em.getTransaction().begin();
			em.remove(genre);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Failed to delete genre: " + e.getMessage());
			e.printStackTrace();
		}
	}
}