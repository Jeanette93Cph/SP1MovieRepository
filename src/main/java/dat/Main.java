package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dtos.DirectorDTO;
import dat.dtos.MovieDTO;
import dat.services.ActorService;
import dat.services.DirectorService;
import dat.services.MovieService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Printing all danish movies for the recent 5 years in JSON
        String jsonAllMovies = MovieService.getAllMoviesJSON(2);
       // System.out.println(jsonAllMovies);

        //Printing all danish movies for the recent 5 years as MovieDTO's
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        moviesDTOs.forEach(System.out::println);

        //Printing all cast from the danish movies for the recent 5 years
        //ActorService.getAllActorsJSON(3).forEach(System.out::println);

        //Printing all directors from the danish movies for the recent 5 years
        //String jsonAllDirectors = DirectorService.getAllDirectorsJSON(1);
        //System.out.println(jsonAllDirectors);


        // List<DirectorDTO> directorDTOS = DirectorDTO.convertToDTOFromJSONList(jsonAllDirectors);
        // directorDTOS.forEach(System.out::println);

        // //persist movies to the database
        // MovieDAO movieDAO = MovieDAO.getInstance(HibernateConfig.getEntityManagerFactory("tmdb"));
		// moviesDTOs.forEach(movieDAO::persistEntity);


    }
}