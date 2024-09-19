package dat.services;

import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;

public class FetchData {

	public static MovieDAO movieDAO;
	public static GenreDAO genreDAO;
	public static ActorDAO actorDAO;
	public static DirectorDAO directorDAO;

	// List of all movies
	public static void fetchAllMovies() {
		//movieDAO.findAll();
	}

	// List of all actors
	public static void fetchAllActors() {
		//actorDAO.findAll();
	}

	// List of all directors
	public static void fetchAllDirectors() {
		//directorDAO.findAll();
	}

	// List of all genres
	public static void fetchAllGenres() {
		//genreDAO.findAll();
	}

	// List of movies with a specific genre
	public static void fetchMoviesByGenre(String genre) {
		//movieDAO.findMovieByGenre(genre);
	}

	// List of movies with a specific actor
	public static void fetchMoviesByActor(String actor) {
		//movieDAO.findByActor(actor);
	}

	// List of movies with a specific director
	public static void fetchMoviesByDirector(String director) {
		//movieDAO.findByDirector(director);
	}

	// Update a movie by title
	public static void updateMovieByTitle(String title) {
		//movieDAO.updateByTitle(title);
	}

	// Update a movie by release date
	public static void updateMovieByReleaseDate(String releaseDate) {
		//movieDAO.updateByReleaseDate(releaseDate);
	}

	// Delete a movie by title
	public static void deleteMovieByTitle(String title) {
		//movieDAO.deleteByTitle(title);
	}

	// Delete a movie by release date
	public static void deleteMovieByReleaseDate(String releaseDate) {
		//movieDAO.deleteByReleaseDate(releaseDate);
	}

	// Search for a movie by title - case insensitive
	public static void searchMovieByTitle(String title) {
		//movieDAO.searchByTitle(title);
	}

	// Get the average rating of a movie
	public static void getAverageRating(String title) {
		//movieDAO.getAverageRating(title);
	}

	// Get the average rating of all movies
	public static void getAverageRatingOfAllMovies() {
		//movieDAO.getAverageRatingOfAllMovies();
	}

	// Get the top 10 lowest rated movies
	public static void getTop10LowestRatedMovies() {
		//movieDAO.getTop10LowestRatedMovies();
	}

	// Get the top 10 highest rated movies
	public static void getTop10HighestRatedMovies() {
		//movieDAO.getTop10HighestRatedMovies();
	}

	// Get the top 10 most popular movies
	public static void getTop10MostPopularMovies() {
		//movieDAO.getTop10MostPopularMovies();
	}












}

