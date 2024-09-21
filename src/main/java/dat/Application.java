package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;
import dat.services.FetchData;
import dat.services.GetAllPagesService;
import dat.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Application {

	// Initialize EntityManagerFactory
	public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester"); // WORKS
	// public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db"); //TODO WE SHOULD USE THIS

	public static void main (String[] args) {
		// Keep EntityManagerFactory open until the application finishes its work
		EntityManager em = null;
		try {
			// Create an EntityManager
			em = emf.createEntityManager();

			System.out.println("THE MOVIE DB API APPLICATION");

			// Uncomment the line below to populate the database with Danish movies from TMDb API
			// populateDatabase();

			// Instantiate FetchData with EntityManagerFactory
			FetchData f = new FetchData(em);

			// Uncomment the methods below to test the functionality of the application
			f.getAllMovies();
			// f.getAllActors();
			// f.getAllMovies();
			// f.getAllGenres();
			// f.getAllDirectors();
			//
			// f.getAverageRatingOfAllMovies();
			// f.getAverageRating("The Promised Land");
			//
			// f.getTop10MostPopularMovies();
			// f.getTop10HighestRatedMovies();
			// f.getTop10LowestRatedMovies();
			//
			// f.getMoviesByActor("Mads Mikkelsen");
			// f.getMoviesByDirector("Lars von Trier");
			// f.getMovieByGenre("Drama");
			// f.getMovieByTitle("The Promised Land");
			//
			// f.getDirectorsInMovie("The Promised Land");
			// f.getGenresInMovie("The Promised Land");
			// f.getActorsInMovie("The Promised Land");
			//
			// f.searchForMovieByString("The");
			//
			// MovieDTO MovieDTO = new MovieDTO();
			// f.addNewMovie(MovieDTO);
			// f.deleteMovie(MovieDTO);

			// Test fetching all movies
			f.getAllMovies().forEach(System.out::println);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close EntityManager if it's open
			if (em != null && em.isOpen()) {
				em.close();
			}

			// Close EntityManagerFactory at the very end of the program
			if (emf != null && emf.isOpen()) {
				emf.close();
			}
		}
	}

	// Method to populate the database with Danish movies from TMDb API
	private void populateDatabase () {
		List<MovieDTO> movies = GetAllPagesService.getDanishMoviesWithDetails();

		try (EntityManager em = emf.createEntityManager()) {
			MovieDAO movieDAO = new MovieDAO(em);
			movieDAO.saveMovies(movies);
		}
		emf.close();
	}

	// Method to fetch Danish movies with actors, directors, and genres from TMDb API
	private static List<MovieDTO> fetchMovieDataFromTMDbAPI () {
		GetAllPagesService service = new GetAllPagesService();
		return GetAllPagesService.getDanishMoviesWithDetails();
	}
}