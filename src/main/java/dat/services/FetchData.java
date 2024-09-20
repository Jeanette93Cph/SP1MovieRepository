package dat.services;

import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.Comparator;
import java.util.List;

public class FetchData {

	private final MovieDAO movieDAO;
	private final GenreDAO genreDAO;
	private final ActorDAO actorDAO;
	private final DirectorDAO directorDAO;

	// Constructor to initialize the DAOs
	public FetchData(EntityManagerFactory emf) {
		this.movieDAO = MovieDAO.getInstance(emf);
		this.genreDAO = GenreDAO.getInstance(emf);
		this.actorDAO = ActorDAO.getInstance(emf);
		this.directorDAO = DirectorDAO.getInstance(emf);
	}

	// List of all actors
	public List<ActorDTO> fetchAllActors() {
		List<ActorDTO> allActors = actorDAO.findAll();
		allActors.forEach(System.out::println);
		return allActors;
	}

	//List of all movies
	public List<MovieDTO> fetchAllMovies () {
		List<MovieDTO> allmovies = movieDAO.findAll();
		allmovies.forEach(System.out::println);
		return allmovies;
	}

	// Get the average rating of a movie
	public List<MovieDTO> getAverageRating(String title) {
		List<MovieDTO> movies = movieDAO.findAll();
		double averageRating = movies.stream()
				.filter(movie -> movie.getTitle().equals(title))
				.mapToDouble(MovieDTO::getVoteAverage)
				.average()
				.orElse(0);
		System.out.println("Average rating of " + title + ": " + averageRating);
		return movies;
	}

	// Get the average rating of all movies
	public List<MovieDTO> getAverageRatingOfAllMovies() {
		List<MovieDTO> movies = movieDAO.findAll();
		double averageRating = movies.stream()
				.mapToDouble(MovieDTO::getVoteAverage)
				.average()
				.orElse(0);
		System.out.println("Average rating of all movies: " + averageRating);
		return movies;
	}

	// Get the top 10 lowest rated movies
	public List<MovieDTO> getTop10LowestRatedMovies() {
		List<MovieDTO> movies = movieDAO.findAll();
		movies.stream()
				.sorted(Comparator.comparing(MovieDTO::getVoteAverage))
				.limit(10)
				.forEach(System.out::println);
		return movies;
	}

	// Get the top 10 highest rated movies
	public List<MovieDTO> getTop10HighestRatedMovies() {
		List<MovieDTO> movies = movieDAO.findAll();
		movies.stream()
				.sorted(Comparator.comparing(MovieDTO::getVoteAverage).reversed())
				.limit(10)
				.forEach(System.out::println);
		return movies;
	}

	// Get the top 10 most popular movies
	public List<MovieDTO> getTop10MostPopularMovies() {
		List<MovieDTO> movies = movieDAO.findAll();
		movies.stream()
				.sorted(Comparator.comparing(MovieDTO::getPopularity).reversed())
				.limit(10)
				.forEach(System.out::println);
		return movies;
	}
}