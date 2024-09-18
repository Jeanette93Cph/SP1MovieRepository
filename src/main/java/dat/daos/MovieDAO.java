package dat.daos;

import dat.dtos.MovieDTO;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDAO implements GenericDAO<MovieDTO, Long> {

	private static MovieDAO instance;
	private static EntityManagerFactory emf;

	private MovieDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public static MovieDAO getInstance(EntityManagerFactory emf) {
		if (instance == null) {
			instance = new MovieDAO(emf);
		}
		return instance;
	}


	@Override
	public Collection<MovieDTO> findAll () {
		return List.of();
	}

	@Override
	public void persistEntity (MovieDTO entity) {

	}

	@Override
	public void removeEntity (Long id) {

	}

	@Override
	public MovieDTO findEntity (Long id) {
		return null;
	}

	@Override
	public void updateEntity (MovieDTO entity, Long id) {

	}
}