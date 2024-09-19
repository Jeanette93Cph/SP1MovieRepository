package dat.services;

import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import jakarta.persistence.EntityManagerFactory;

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

	// List of all movies
	public void fetchAllMovies () {
		movieDAO.findAll();
	}

	// List of all actors
	public void fetchAllActors() {
		actorDAO.findAll();
	}

	// List of all directors
	public void fetchAllDirectors() {
		directorDAO.findAll();
	}

	// List of all genres
	public void fetchAllGenres() {
		genreDAO.findAll();
	}

	// List of movies with a specific genre
	public void fetchMoviesByGenre(String genre) {
		movieDAO.findMovieByGenre(genre);
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
	public void searchMovieByTitle(String title) {
		movieDAO.searchByTitle(title);
	}

	// Get the average rating of a movie
	public void getAverageRating(String title) {
		movieDAO.getAverageRating(title);
	}

	// Get the average rating of all movies
	public void getAverageRatingOfAllMovies() {
		movieDAO.getAverageRatingOfAllMovies();
	}

	// Get the top 10 lowest rated movies
	public void getTop10LowestRatedMovies() {
		movieDAO.getTop10LowestRatedMovies();
	}

	// Get the top 10 highest rated movies
	public void getTop10HighestRatedMovies() {
		movieDAO.getTop10HighestRatedMovies();
	}

	// Get the top 10 most popular movies
	public void getTop10MostPopularMovies() {
		movieDAO.getTop10MostPopularMovies();
	}












}

