package dat;

import dat.config.HibernateConfig;
import dat.daos.DirectorDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.services.ActorService;
import dat.services.DirectorService;
import dat.services.GenreService;
import dat.services.MovieService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //the_movie_db


        //Printing all danish movies for the recent 5 years in JSON
        String jsonAllMovies = MovieService.getAllMoviesJSON(2);
        // System.out.println(jsonAllMovies);
        //
        // //Printing all danish movies for the recent 5 years as MovieDTO's
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        // moviesDTOs.forEach(System.out::println);
        //
        // /* ACTORS */
        // //Printing all cast from the danish movies for the recent 5 years
        //String jsonAllActors = ActorService.getAllActorsJSON(3);
         //List<ActorDTO> actorDTOS = ActorDTO.convertToDTOFromJSONList(jsonAllActors);
        //actorDTOS.forEach(System.out::println);


        /* DIRECTORS */
        //Printing all directors from the danish movies for the recent 5 years
       List<DirectorDTO> allDirectorsDTO = DirectorService.getAllDirectorsFromJSON(1);
        //allDirectorsDTO.forEach(System.out::println);

        //persist directorList to database
        DirectorDAO directorDAO = DirectorDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        //directorDAO.persistListOfDirectors(allDirectorsDTO);

        //persist one director to database
        //DirectorDTO directorDTO = new DirectorDTO();
        //directorDTO.setName("Hans Hansen");
        //directorDTO.setId(333L);
        //directorDAO.persistEntity(directorDTO);

       //find all directors in the database
        //DirectorDAO.findAll().forEach(System.out::println);

        //find director by id
        //System.out.println(DirectorDAO.findEntity(558205L));

        //update director
//        DirectorDTO directorDTO1 = DirectorDAO.findEntity(558205L);
//        directorDTO1.setName("Julie Sørensen");
//        DirectorDAO.updateEntity(directorDTO1, 558205L);
//        System.out.println(DirectorDAO.findEntity(558205L));

        //delete director
       //DirectorDAO.removeEntity(333L);
        



        /* GENRE */
        //Printing all genres
         //String allGenres = GenreService.getAllGenresJSON();
        //List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(allGenres);
       //genreDTOS.forEach(System.out::println);



        /*Testing the connection to the database*/
//        MovieDAO movieDAO = MovieDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
//
//        for (MovieDTO moviesDTO : moviesDTOs) {
//            movieDAO.persistEntity(moviesDTO);
//        }


    }
}