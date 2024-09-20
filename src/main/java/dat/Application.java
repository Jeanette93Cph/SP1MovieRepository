package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dtos.MovieDTO;
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


	}

	// Method to populate the database with Danish movies from TMDb API
	private static void populateDatabase () {
		List<MovieDTO> movies = fetchMovieDataFromTMDbAPI();

		try(EntityManager em = emf.createEntityManager()) {
			MovieDAO movieDAO = new MovieDAO(em);
			movieDAO.saveMovies(movies);
		}
		emf.close();
	}

	// Method to fetch Danish movies with actors, directors, and genres from TMDb API
	private static List<MovieDTO> fetchMovieDataFromTMDbAPI () {
		return GetAllPagesService.getDanishMoviesWithDetails();
	}
}
