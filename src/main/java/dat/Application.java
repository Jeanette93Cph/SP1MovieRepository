package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;
import dat.services.MovieDataService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class Application {

	public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester");
	//public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");

	public static void main (String[] args) {

		// Fetch Danish movies with actors, directors, and genres from TMDb API
		List<MovieDTO> movies = fetchMovieDataFromTMDbAPI();

		try(EntityManager em = emf.createEntityManager()) {
			MovieDAO movieDAO = new MovieDAO(em);
			movieDAO.saveMovies(movies);
		}
		emf.close();
	}

	private static List<MovieDTO> fetchMovieDataFromTMDbAPI () {
		// Fetch Danish movies with actors, directors, and genres from TMDb API
		return MovieDataService.getDanishMoviesWithDetails();
	}
}

// 		FetchData f = new FetchData(emf);
//
// 		System.out.println("THE MOVIE DB API APPLICATION");
//
// 		//f.getAllActors();
// 		//f.getAllMovies();
// 		//f.getAllGenres();
// 		//f.getAllDirectors();
// 		// f.getAverageRatingOfAllMovies();
// 		// f.getAverageRating("The Promised Land");
// 		// f.getTop10MostPopularMovies();
// 		// f.getTop10HighestRatedMovies();
// 		// f.getTop10LowestRatedMovies();
//
// 		//TODO: implement these methods
// 		// f.getActorsInMovie("The Promised Land");
// 		// f.getDirectorsInMovie("The Promised Land");
// 		// f.getGenresInMovie("The Promised Land");
// 		// f.addNewMovie();
// 		// f.updateExistingMovie();
// 		// f.deleteMovie();
// 		// f.searchForMovie("The Promised Land"); //case insensitive - search by string
//
// 	}
// }
