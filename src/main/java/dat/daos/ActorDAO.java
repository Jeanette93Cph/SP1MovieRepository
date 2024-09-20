package dat.daos;

import dat.dtos.ActorDTO;
import dat.entities.Actor;
import jakarta.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.List;

public class ActorDAO {

	private static ActorDAO actorDAO;
	private static EntityManagerFactory emf;

	private ActorDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static ActorDAO getInstance(EntityManagerFactory emf) {
		if(actorDAO == null) {
			actorDAO = new ActorDAO(emf);
		}
		return actorDAO;
	}

	//henter data med entitet.
	public List<ActorDTO> findAll () {
		return null;
	}

	public void persistEntity (Actor entity) {

	}

	public void removeEntity (Long id) {

	}

	public Actor findEntity (Long id) {
		return null;
	}

	public void updateEntity (Actor entity, Long id) {

	}
}