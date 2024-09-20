package dat;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.services.*;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class Application {

	public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("tester");
	//public static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("the_movie_db");

	public static void main (String[] args) {

		FetchData f = new FetchData(emf);

		System.out.println("THE MOVIE DB API APPLICATION");

		// f.fetchAllActors();
		f.fetchAllMovies();
		// f.getAverageRatingOfAllMovies();
		// f.getAverageRating("The Promised Land");
		// f.getTop10MostPopularMovies();
		// f.getTop10HighestRatedMovies();
		// f.getTop10LowestRatedMovies();

		//populateDatabase();
	}

	private static void populateDatabase () {
		DirectorDAO directorDAO = new DirectorDAO(emf);
		GenreDAO genreDAO = new GenreDAO(emf);
		ActorDAO actorDAO = new ActorDAO(emf);
		MovieDAO movieDAO = new MovieDAO(emf);

		// persist directors to database
		List<DirectorDTO> allDirectorsDTO = DirectorService.getAllDirectorsFromJSON(1); // fetch all data from the movie db api
		directorDAO.persistListOfDirectors(allDirectorsDTO); // persist all directors to the database

		// persist genres to database
		String allGenres = GenreService.getAllGenresJSON();
		List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(allGenres);
		genreDAO.persistListOfGenres(genreDTOS);

		// persist actors to database
		List<ActorDTO> actorDTOS = ActorService.getAllActorsFromJSON(1);
		actorDAO.persistListOfActors(actorDTOS);

		// persist movieList to database
		String jsonAllMovies = MovieService.getAllMoviesJSON(1);
		List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
		movieDAO.persistListOfMovies(moviesDTOs);
	}
}