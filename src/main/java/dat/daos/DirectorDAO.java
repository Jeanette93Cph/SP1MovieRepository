package dat.daos;

import dat.dtos.DirectorDTO;
import dat.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class DirectorDAO {
	private static DirectorDAO directorDAO;

	private static EntityManagerFactory emf;

	private DirectorDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static DirectorDAO getInstance(EntityManagerFactory emf) {
		if(directorDAO == null) {
			directorDAO = new DirectorDAO(emf);
		}
		return directorDAO;
	}

	//Persist one director
	public DirectorDTO persistDirector(DirectorDTO directorDTO) {
		Director director = new Director();
		try(EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();
			em.persist(director);
			em.getTransaction().commit();
		}
		return new DirectorDTO(director);
	}


	//Persist a list of directors
	public List<DirectorDTO> persistListOfDirectors(List<DirectorDTO> directorDTOList) {
		List<DirectorDTO> persistedlist = new ArrayList<>();
		try(EntityManager em = emf.createEntityManager()) {
			em.getTransaction().begin();

			for(DirectorDTO dto : directorDTOList) {
				if(em.find(Director.class, dto.getId()) == null) {
					Director director = new Director(dto);
					em.persist(director);
					dto.setId(director.getId());
					persistedlist.add(dto);
				}
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return persistedlist;
	}

	public List<DirectorDTO> findAll() {
		try(EntityManager em = emf.createEntityManager()) {
			return em.createQuery("SELECT new dat.dtos.DirectorDTO(d) FROM Director d", DirectorDTO.class).getResultList();
		}
	}
}