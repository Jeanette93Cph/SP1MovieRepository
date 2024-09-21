package dat.services;

import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.MovieDTO;
import dat.dtos.GenreDTO;
import dat.entities.Actor;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.entities.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FetchData {
	private MovieDAO movieDAO;
	private ActorDAO actorDAO;
	private GenreDAO genreDAO;
	private DirectorDAO directorDAO;

	// Constructor initializes MovieDAO with EntityManager
	public FetchData (EntityManagerFactory emf) {
		this.movieDAO = new MovieDAO(emf);  // Pass only EntityManager
		this.actorDAO = new ActorDAO(emf);
		this.genreDAO = new GenreDAO(emf);
		this.directorDAO = new DirectorDAO(emf);
	}

	// List of all actors (convert entities to DTOs)
	public void getAllActors () {
		List<Actor> allActors = actorDAO.findAll(); // Returns entities
		List<ActorDTO> actorDTOs = allActors.stream()
				.map(this::convertToActorDTO)
				.collect(Collectors.toList());
		actorDTOs.forEach(System.out::println);
	}

	// List of all movies
	public List<MovieDTO> getAllMovies () {
		List<Movie> allMovies = movieDAO.findAll(); // Returns entities
		List<MovieDTO> movieDTOList = allMovies.stream()
				.map(this::convertToMovieDTO)
				.collect(Collectors.toList());
		movieDTOList.forEach(System.out::println);
		return movieDTOList;
	}

	// List of all genres
	public void getAllGenres () {
		List<Genre> allGenres = genreDAO.findAll(); // Returns entities
		List<GenreDTO> genreDTOs = allGenres.stream()
				.map(this::convertToGenreDTO)
				.collect(Collectors.toList());
		genreDTOs.forEach(System.out::println);
	}

	// List of all directors
	public List<DirectorDTO> getAllDirectors () {
		List<Director> allDirectors = directorDAO.findAll(); // Returns entities
		List<DirectorDTO> directorDTOs = allDirectors.stream()
				.map(this::convertToDirectorDTO)
				.collect(Collectors.toList());
		directorDTOs.forEach(System.out::println);
		return directorDTOs;
	}

	// List of all actors in a movie
	public List<MovieDTO> getActorsInMovie (String title) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All actors in " + title + ":");

		return movies.stream()
				.filter(movie -> movie.getTitle().equals(title))
				.peek(movie -> movie.getActors().forEach(actor -> System.out.println(actor.getName())))
				.toList();
	}

	// Get the average rating of a movie
	public double getAverageRating (String title) {
		List<MovieDTO> movies = getAllMovies();
		double averageRating = movies.stream()
				.filter(movie -> movie.getTitle().equals(title))
				.mapToDouble(MovieDTO::getVoteAverage)
				.average()
				.orElse(0);

		System.out.println("\nAverage rating of " + title + ": " + averageRating);
		return averageRating;
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
		List<Movie> allMovies = movieDAO.findAll();

		System.out.println("Top 10 lowest-rated Movies");

		// Filter out movies where voteAverage is null
		return allMovies.stream()
				.filter(movie -> movie.getVoteAverage() != null)
				.sorted(Comparator.comparing(Movie::getVoteAverage))
				.limit(10)
				.map(this::convertToMovieDTO)
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
								+ ", Vote Average: " + movie.getVoteAverage()
				))
				.collect(Collectors.toList());
	}

	// Get the top 10 highest-rated movies
	public List<MovieDTO> getTop10HighestRatedMovies () {
		List<Movie> allMovies = movieDAO.findAll();

		System.out.println("Top 10 highest-rated Movies");

		// Filter out movies where voteAverage is null
		return allMovies.stream()
				.filter(movie -> movie.getVoteAverage() != null)
				.sorted(Comparator.comparing(Movie::getVoteAverage).reversed())
				.limit(10)
				.map(this::convertToMovieDTO)
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
								+ ", Vote Average: " + movie.getVoteAverage()
				))
				.collect(Collectors.toList());

	}

	// Get the top 10 most popular movies
	public List<MovieDTO> getTop10MostPopularMovies () {
		List<Movie> allMovies = movieDAO.findAll();

		System.out.println("Top 10 popular Movies");

		// Filter out movies where popularity is null
		return allMovies.stream()
				.filter(movie -> movie.getPopularity() != null)
				.sorted(Comparator.comparing(Movie::getPopularity).reversed())  // Sort by popularity in descending order
				.limit(10)  // Limit to top 10
				.map(this::convertToMovieDTO)  // Convert to DTOs
				.peek(movie -> System.out.println(
						"Movie ID: " + movie.getId()
								+ ", Title: " + movie.getTitle()
								+ ", Popularity: " + movie.getPopularity()
				))  // Print each movie in the required format
				.collect(Collectors.toList());
	}

	// Search for a movie by title (case-insensitive)
	public List<MovieDTO> getMovieByTitle (String title) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All movies with title " + title + ":");

		return movies.stream()
				.filter(movie -> movie.getTitle().toLowerCase().equals(title.toLowerCase()))
				.peek(movie -> System.out.println(movie.getTitle()))
				.toList();
	}

	// // Search for a movie by genre (case-insensitive)
	// public List<MovieDTO> getMovieByGenre (String genre) {
	// 	List<MovieDTO> movies = MovieDAO.searchForMovieByGenre(genre);
	//
	// 	System.out.println("All movies with genre " + genre + ":");
	// 	movies.forEach(System.out::println);
	// 	return movies;
	// }

	// List of all movies that a particular actor has been part of
	public List<MovieDTO> getMoviesByActor (String name) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All movies with " + name);

		return movies.stream()
				.filter(movie -> movie.getActors().stream()
						.anyMatch(actorDTO -> actorDTO.getName().equals(name)))
				.peek(movie -> System.out.println(movie.getTitle()))
				.toList();
	}

	// List of all movies that a particular director has directed
	public List<MovieDTO> getMoviesByDirector (String name) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All movies with " + name);

		return movies.stream()
				.filter(movie -> movie.getDirector().getName().equals(name))
				.peek(movie -> System.out.println(movie.getTitle()))
				.toList();
	}

	// List directors in a movie
	public List<MovieDTO> getDirectorsInMovie (String title) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All directors in movie with the title " + title + ":");
		return movies.stream()
				.filter(movie -> movie.getTitle().equals(title))
				.peek(movie -> System.out.println(movie.getDirector().getName()))
				.toList();
	}

	// List genres in a movie
	public List<MovieDTO> getGenresInMovie (String title) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All genres in movie with the title " + title + ":");
		movies.stream()
				.filter(movie -> movie.getTitle().equals(title))
				.forEach(movie -> movie.getGenres().forEach(genre -> System.out.println(genre.getName())));
		return movies;

	}

	// Search for a movie by string (can match in title, genre, director)
	public void searchForMovieByString (String s) {
		List<MovieDTO> movies = getAllMovies();

		System.out.println("All movies with:  " + s);

		movies.stream()
				.filter(movie -> movie.getTitle().toLowerCase().contains(s.toLowerCase())
						|| movie.getGenres().stream().anyMatch(genre -> genre.getName().toLowerCase().contains(s.toLowerCase()))
						|| movie.getDirector().getName().toLowerCase().contains(s.toLowerCase()))
				.forEach(System.out::println);
	}

	// Add a new movie
	public void addNewMovie (MovieDTO movieDTO) {
		movieDAO.create(convertToMovie(movieDTO));
	}

	// Update an existing movie
	public void updateExistingMovie (MovieDTO movieDTO) {
		movieDAO.update(convertToMovie(movieDTO));
	}

	// Delete a movie
	public void deleteMovie (MovieDTO movieDTO) {
		movieDAO.delete(convertToMovie(movieDTO));
	}

	// Conversion method from MovieDTO to Movie entity
	private Movie convertToMovie (MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setTitle(movieDTO.getTitle());
		movie.setReleaseDate(movieDTO.getReleaseDate());
		return movie;
	}

	// Conversion method from Movie to MovieDTO
	private MovieDTO convertToMovieDTO(Movie movie) {
		DirectorDTO directorDTO = null;

		if (movie.getDirector() != null) {
			Director director = movie.getDirector();
			directorDTO = new DirectorDTO(director.getId(), director.getName());
		}

		return new MovieDTO(
				movie.getId(),
				movie.getTitle(),
				movie.getOriginalLanguage(),
				movie.getReleaseDate(),
				movie.getPopularity(),
				movie.getVoteAverage(),
				movie.getGenres(),
				movie.getActors(),
				directorDTO
		);
	}


	// Conversion method from Actor entity to ActorDTO
	private ActorDTO convertToActorDTO (Actor actor) {
		return new ActorDTO(
				actor.getId(),
				actor.getName()
		);
	}

	// Conversion method from Genre entity to GenreDTO
	private GenreDTO convertToGenreDTO (Genre genre) {
		return new GenreDTO(
				genre.getId(),
				genre.getName()
		);
	}

	// Conversion method from Director entity to DirectorDTO
	private DirectorDTO convertToDirectorDTO (Director director) {
		return new DirectorDTO(
				director.getId(),
				director.getName()
		);
	}
}