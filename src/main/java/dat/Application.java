package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;
import dat.services.FetchData;
import dat.services.GetAllPagesService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class Application {

	public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester"); //WORKS
	//public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db"); //TODO WE SHOULD USE THIS

	public static void main (String[] args) {

		System.out.println("THE MOVIE DB API APPLICATION");
		//populateDatabase();

		FetchData f = new FetchData(emf);

		f.getAllMovies();
		f.getAllActors();
		f.getAllMovies();
		f.getAllGenres();
		f.getAllDirectors();

		f.getAverageRatingOfAllMovies();
		f.getAverageRating("The Promised Land");

		f.getTop10MostPopularMovies();
		f.getTop10HighestRatedMovies();
		f.getTop10LowestRatedMovies();

		f.getMoviesByActor("Mads Mikkelsen");
		f.getMoviesByDirector("Lars von Trier");
		f.getMovieByGenre("Drama");
		f.getMovieByTitle("The Promised Land");

		f.getDirectorsInMovie("The Promised Land");
		f.getGenresInMovie("The Promised Land");
		f.getActorsInMovie("The Promised Land");

		f.searchForMovieByString("The");

		f.addNewMovie();
		f.updateExistingMovie();
		f.deleteMovie();
	}

	// Method to populate the database with Danish movies from TMDb API
	private static void populateDatabase () {
		List<MovieDTO> movies = fetchMovieDataFromTMDbAPI();

		try(EntityManager em = emf.createEntityManager()) {
			MovieDAO movieDAO = new MovieDAO(emf);
			movieDAO.saveMovies(movies);
		}
		emf.close();
	}

	// Method to fetch Danish movies with actors, directors, and genres from TMDb API
	private static List<MovieDTO> fetchMovieDataFromTMDbAPI () {
		return GetAllPagesService.getDanishMoviesWithDetails();
	}
}
