package dat.daos;

import dat.entities.Actor;
import java.util.Collection;
import java.util.List;

public class ActorDAO implements GenericDAO<Actor, Long> {

	//henter data med entitet.
	@Override
	public Collection<Actor> findAll () {
		return List.of();
	}

	@Override
	public void persistEntity (Actor entity) {

	}

	@Override
	public void removeEntity (Long id) {

	}

	@Override
	public Actor findEntity (Long id) {
		return null;
	}

	@Override
	public void updateEntity (Actor entity, Long id) {

	}
}