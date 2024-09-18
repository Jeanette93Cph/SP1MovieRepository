package dat.daos;

import dat.entities.Movie;

import java.util.Set;

public class MovieDAO implements IDAO<Movie, Long> {
	@Override
	public boolean create (Movie type) {
		return false;
	}

	@Override
	public boolean delete (Movie type) {
		return false;
	}

	@Override
	public Movie getById (Long id) {
		return null;
	}d

	@Override
	public Set<Movie> getAll () {
		return Set.of();
	}

	@Override
	public boolean update (Movie movie) {
		return false;
	}
}