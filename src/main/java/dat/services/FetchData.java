// /*
// package dat.services;
//
// import dat.daos.ActorDAO;
// import dat.daos.DirectorDAO;
// import dat.daos.GenreDAO;
// import dat.daos.MovieDAO;
// import dat.dtos.ActorDTO;
// import dat.dtos.DirectorDTO;
// import dat.dtos.GenreDTO;
// import dat.dtos.MovieDTO;
// import jakarta.persistence.EntityManagerFactory;
// import java.util.Comparator;
// import java.util.List;
//
// public class FetchData {
//
// 	private ActorDAO actorDAO;
// 	private MovieDAO movieDAO;
// 	private GenreDAO genreDAO;
// 	private DirectorDAO directorDAO;
//
// 	public FetchData (EntityManagerFactory emf) {
// 		this.actorDAO = new ActorDAO(emf);
// 		this.movieDAO = new MovieDAO(emf);
// 		this.genreDAO = new GenreDAO(emf);
// 		this.directorDAO = new DirectorDAO(emf);
// 	}
//
// 	// List of all actors
// 	public void getAllActors () {
// 		List<ActorDTO> allActors = actorDAO.findAll();
// 		allActors.forEach(System.out::println);
// 	}
//
// 	// List of all movies
// 	public List<MovieDTO> getAllMovies () {
// 		List<MovieDTO> allMovies = movieDAO.findAll();
// 		allMovies.forEach(System.out::println);
// 		return allMovies;
// 	}
//
// 	// List of all genres
// 	public List<GenreDTO> getAllGenres () {
// 		List<GenreDTO> allGenres = genreDAO.findAll();
// 		allGenres.forEach(System.out::println);
// 		return allGenres;
// 	}
//
// 	// List of all directors
// 	public List<DirectorDTO> getAllDirectors () {
// 		List<DirectorDTO> allDirectors = directorDAO.findAll();
// 		allDirectors.forEach(System.out::println);
// 		return allDirectors;
// 	}
//
//
// 	// List of all actors in a movie
// 	public List<MovieDTO> fetchActorsInMovie(String title) {
// 		List<MovieDTO> movies = movieDAO.findAll();
// 		movies.stream()
// 				.filter(movie -> movie.getTitle().equals(title))
// 				.forEach(movie -> movie.getActors().forEach(System.out::println));
// 		return movies;
// 	}
//
// 	// Get the average rating of a movie
// 	public List<MovieDTO> getAverageRating(String title) {
// 		List<MovieDTO> movies = movieDAO.findAll();
// 		double averageRating = movies.stream()
// 				.filter(movie -> movie.getTitle().equals(title))
// 				.mapToDouble(MovieDTO::getVoteAverage)
// 				.average()
// 				.orElse(0);
// 		System.out.println("Average rating of " + title + ": " + averageRating);
// 		return movies;
// 	}
//
// 	// Get the average rating of all movies
// 	public List<MovieDTO> getAverageRatingOfAllMovies() {
// 		List<MovieDTO> movies = movieDAO.findAll();
// 		double averageRating = movies.stream()
// 				.mapToDouble(MovieDTO::getVoteAverage)
// 				.average()
// 				.orElse(0);
// 		System.out.println("Average rating of all movies: " + averageRating);
// 		return movies;
// 	}
//
// 	// Get the top 10 lowest rated movies
// 	public List<MovieDTO> getTop10LowestRatedMovies() {
// 		List<MovieDTO> movies = movieDAO.findAll();
// 		movies.stream()
// 				.sorted(Comparator.comparing(MovieDTO::getVoteAverage))
// 				.limit(10)
// 				.forEach(System.out::println);
// 		return movies;
// 	}
//
// 	// Get the top 10 highest rated movies
// 	public List<MovieDTO> getTop10HighestRatedMovies() {
// 		List<MovieDTO> movies = movieDAO.findAll();
// 		movies.stream()
// 				.sorted(Comparator.comparing(MovieDTO::getVoteAverage).reversed())
// 				.limit(10)
// 				.forEach(System.out::println);
// 		return movies;
// 	}
//
// 	// Get the top 10 most popular movies
// 	public List<MovieDTO> getTop10MostPopularMovies() {
// 		List<MovieDTO> movies = movieDAO.findAll();
// 		movies.stream()
// 				.sorted(Comparator.comparing(MovieDTO::getPopularity).reversed())
// 				.limit(10)
// 				.forEach(System.out::println);
// 		return movies;
// 	}
// }*/
