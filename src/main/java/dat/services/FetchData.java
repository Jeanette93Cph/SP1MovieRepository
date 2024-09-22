package dat.services;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.entities.Movie;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Genre;
import jakarta.persistence.EntityManagerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FetchData {
	EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");


	public List<MovieDTO> getAllMovies () {
		MovieDAO movieDAO = new MovieDAO(emf);
		List<MovieDTO> listOfMovieDTO = movieDAO.findAll();
		listOfMovieDTO.forEach(System.out::println);

		return listOfMovieDTO;
	}

	public List<DirectorDTO> getAllDirectors () {
		DirectorDAO directorDAO = new DirectorDAO(emf);

		// Returns entities
		List<DirectorDTO> directorDTOs = directorDAO.findAll();
		directorDTOs.forEach(System.out::println);

		return directorDTOs;
	}

	public List<ActorDTO> getAllActors () {
		ActorDAO actorDAO = new ActorDAO(emf);

		// Returns entities
		List<ActorDTO> actorDTOs = actorDAO.findAll();
		actorDTOs.forEach(System.out::println);

		return actorDTOs;
	}

	public List<GenreDTO> getAllGenre () {
		GenreDAO genreDAO = new GenreDAO(emf);
		List<GenreDTO> listOfGenreDTO = genreDAO.findAll();
		listOfGenreDTO.forEach(System.out::println);

		return listOfGenreDTO;
	}

	public MovieDTO persistEntity (MovieDTO movieDTO) {
		MovieDAO movieDAO = new MovieDAO(emf);
		movieDAO.persistEntity(movieDTO);
		System.out.println(movieDTO);

		return movieDTO;
	}

	public MovieDTO findEntity (Long id) {
		MovieDAO movieDAO = new MovieDAO(emf);
		MovieDTO movieDTO = movieDAO.findEntity(id);
		System.out.println(movieDTO);

		return movieDTO;
	}

	public MovieDTO updateEntity (MovieDTO movieDTO, Long id) {
		MovieDAO movieDAO = new MovieDAO(emf);
		movieDAO.updateEntity(movieDTO, id);
		System.out.println(movieDTO);

		return movieDTO;

	}

	public void removeEntity (Long id) {
		MovieDAO movieDAO = new MovieDAO(emf);
		movieDAO.removeEntity(id);

	}

	public MovieDTO findDirectorInASpecificMovie (String title) {
		MovieDAO movieDAO = new MovieDAO(emf);
		MovieDTO movieDTO = movieDAO.findDirectorInASpecificMovie(title);

		System.out.println(movieDTO);

		return movieDTO;
	}


	public MovieDTO findActorsInASpecificMovie (String title) {
		MovieDAO movieDAO = new MovieDAO(emf);
		MovieDTO movieDTO = movieDAO.findActorsInASpecificMovie(title);

		System.out.println(movieDTO);

		return movieDTO;
	}


	public MovieDTO findGenreInASpecificMovie (String title) {
		MovieDAO movieDAO = new MovieDAO(emf);
		MovieDTO movieDTO = movieDAO.findGenreInASpecificMovie(title);

		System.out.println(movieDTO);

		return movieDTO;

	}

	public List<MovieDTO> findAllMoviesInASpecificGenre (String genreName) {
		MovieDAO movieDAO = new MovieDAO(emf);
		List<MovieDTO> listOfMoviesDTO = movieDAO.findAllMoviesInASpecificGenre(genreName);
		listOfMoviesDTO.forEach(System.out::println);

		return listOfMoviesDTO;

	}


	// Search for a movie by title (case-insensitive) and return all movies that contain the search string in the title. help from chatgpt
	public List<MovieDTO> getMoviesByTitle (String title) {
		MovieDAO movieDAO = new MovieDAO(emf);
		List<MovieDTO> movies = movieDAO.findAll();

		System.out.println("All movies with title " + title + ":");

		List<MovieDTO> filteredMovies = movies.stream()
				.filter(movie -> movie.getTitle() != null)
				// case-insensitive contains check
				.filter(movieDTO -> movieDTO.getTitle().toLowerCase().contains(title.toLowerCase()))
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
				))
				// sort after filtering
				.sorted(Comparator.comparing(MovieDTO::getTitle))
				.collect(Collectors.toList());

		return filteredMovies;
	}


	// Get the average rating of all movies
	public void getAverageRatingOfAllMovies () {
		List<MovieDTO> movies = getAllMovies();
		double averageRating = movies.stream()
				.mapToDouble(MovieDTO::getVoteAverage)
				.average()
				.orElse(0);

		System.out.println("\nAverage rating of all movies: " + averageRating);
	}


	// Get the top 10 lowest-rated movies
	public List<MovieDTO> getTop10LowestRatedMovies () {

		MovieDAO movieDAO = new MovieDAO(emf);
		List<MovieDTO> allMovies = movieDAO.findAll();

		System.out.println("Top 10 lowest-rated Movies");

		// Filter out movies where voteAverage is null
		return allMovies.stream()
				.filter(movie -> movie.getVoteAverage() != null)
				.sorted(Comparator.comparing(MovieDTO::getVoteAverage))
				.limit(10)
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
								+ ", Vote Average: " + movie.getVoteAverage()
				))
				.collect(Collectors.toList());
	}


	// Get the top 10 highest-rated movies
	public List<MovieDTO> getTop10HighestRatedMovies () {

		MovieDAO movieDAO = new MovieDAO(emf);
		List<MovieDTO> allMovies = movieDAO.findAll();

		System.out.println("Top 10 highest-rated Movies");

		// Filter out movies where voteAverage is null
		return allMovies.stream()
				.filter(movie -> movie.getVoteAverage() != null)
				.sorted(Comparator.comparing(MovieDTO::getVoteAverage).reversed())
				.limit(10)
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
								+ ", Vote Average: " + movie.getVoteAverage()
				))
				.collect(Collectors.toList());
	}

	// Get the top 10 most popular movies
	public List<MovieDTO> getTop10MostPopularMovies () {
		MovieDAO movieDAO = new MovieDAO(emf);
		List<MovieDTO> allMovies = movieDAO.findAll();

		System.out.println("Top 10 highest-rated Movies");

		// Filter out movies where voteAverage is null
		return allMovies.stream()
				.filter(movie -> movie.getPopularity() != null)
				.sorted(Comparator.comparing(MovieDTO::getPopularity).reversed())
				.limit(10)
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
								+ ", Popularity: " + movie.getPopularity()
				))
				.collect(Collectors.toList());
	}

}