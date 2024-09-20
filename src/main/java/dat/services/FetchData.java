package dat.services;

import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Actor;
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

	public List<MovieDTO> sortByTitle() {
		List<MovieDTO> allMovies = movieDAO.findAll();
		allMovies.stream()
				.sorted((Comparator.comparing(MovieDTO::getOriginalTitle)))
				.forEach(System.out::println);
		return allMovies;
	}

	public List<MovieDTO> sortByReleaseDate() {
		List<MovieDTO> allmovies = movieDAO.findAll();
		allmovies.stream()
				.sorted(Comparator.comparing(MovieDTO::getReleaseDate))
				.forEach(System.out::println);
		return allmovies;
	}

	public List<MovieDTO> sortByRating() {
		List<MovieDTO> allMovies = movieDAO.findAll();
		allMovies.stream()
				.sorted(Comparator.comparing(MovieDTO::getVoteAverage))
				.forEach(System.out::println);
		return allMovies;
	}

	// List of all movies
	public List<MovieDTO> fetchAllMovies () {
		List<MovieDTO> allmovies = movieDAO.findAll();
		allmovies.forEach(System.out::println);
		return allmovies;
	}

	// List of all actors
	public List<ActorDTO> fetchAllActors() {
		List<ActorDTO> allActors = actorDAO.findAll();
		allActors.forEach(System.out::println);
		return allActors;
	}

	// List of all directors
	public List<DirectorDTO> fetchAllDirectors() {
		List<DirectorDTO> allDirectors = directorDAO.findAll();
		allDirectors.forEach(System.out::println);
		return allDirectors;
	}

	// List of all genres
	public List<GenreDTO> fetchAllGenres() {
		List<GenreDTO> allGenres = genreDAO.findAll();
		allGenres.forEach(System.out::println);
		return allGenres;
	}

	// List of movies with a specific genre
	public List<MovieDTO> fetchMoviesByGenre(String genre) {
		return null;
	}

	// List of movies with a specific actor
	public void fetchMoviesByActor(String actor) {
		movieDAO.findByActor(actor);
	}

	// List of movies with a specific director
	public void fetchMoviesByDirector(String director) {
		movieDAO.findByDirector(director);
	}

	// Update a movie by title
	public void updateMovieByTitle(String title) {
		movieDAO.updateByTitle(title);
	}

	// Update a movie by release date
	public void updateMovieByReleaseDate(String releaseDate) {
		movieDAO.updateByReleaseDate(releaseDate);
	}

	// Delete a movie by title
	public void deleteMovieByTitle(String title) {
		movieDAO.deleteByTitle(title);
	}

	// Delete a movie by release date
	public void deleteMovieByReleaseDate(String releaseDate) {
		movieDAO.deleteByReleaseDate(releaseDate);
	}

	// Search for a movie by title - should be case insensitive
	public List<MovieDTO> searchMovieByTitle(String title) {
		List<MovieDTO> movies = movieDAO.findAll();
		movies.stream()
				.filter(movie -> movie.getOriginalTitle().toLowerCase().contains(title.toLowerCase()))
				.forEach(System.out::println);
		return movies;
	}

	// Get the average rating of a movie
	public List<MovieDTO> getAverageRating(String title) {
		List<MovieDTO> movies = movieDAO.findAll();
		movies.stream()
				.filter(movie -> movie.getOriginalTitle().equals(title))
				.forEach(System.out::println);
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