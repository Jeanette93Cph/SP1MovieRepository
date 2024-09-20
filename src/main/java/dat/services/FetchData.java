package dat.services;

import dat.daos.ActorDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.MovieDTO;
import jakarta.persistence.EntityManagerFactory;
import java.util.Comparator;
import java.util.List;

public class FetchData {

	private ActorDAO actorDAO;
	private MovieDAO movieDAO;

	public FetchData (EntityManagerFactory emf) {
		this.actorDAO = new ActorDAO(emf);
		this.movieDAO = new MovieDAO(emf);
	}

	// List of all actors
	public void fetchAllActors() {
		List<ActorDTO> allActors = actorDAO.findAll();
		allActors.forEach(System.out::println);
	}

	//List of all movies
	public List<MovieDTO> fetchAllMovies () {
		List<MovieDTO> allMovies = movieDAO.findAll();
		allMovies.forEach(System.out::println);
		return allMovies;
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