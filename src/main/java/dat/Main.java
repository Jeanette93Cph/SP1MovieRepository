package dat;

import dat.config.HibernateConfig;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.services.ActorService;
import dat.services.DirectorService;
import dat.services.GenreService;
import dat.services.MovieService;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //ACTORS
        //Printing all actors from the danish movies for the recent 5 years
        String jsonAllActors = ActorService.getAllActorsJSON(3);
         List<ActorDTO> actorDTOS = ActorDTO.convertToDTOFromJSONList(jsonAllActors);
        actorDTOS.forEach(System.out::println);

        //DIRECTORS
        //Printing all directors from the danish movies for the recent 5 years
        //String jsonAllDirectors = DirectorService.getAllDirectorsJSON(1);
        //System.out.println(jsonAllDirectors);
        //

        // //Printing all directors from the danish movies for the recent 5 years as DirectorDTO's
        //List<DirectorDTO> directorDTOS = DirectorDTO.convertToDTOFromJSONList(jsonAllDirectors);
       //directorDTOS.forEach(System.out::println);

        //------------------------------------------------------------/

        //ADDING MOVIES TO DATABASE
        /* MovieDAO movieDAO = MovieDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(MovieService.getAllMoviesJSON(1));
        for (MovieDTO moviesDTO : moviesDTOs) {
           movieDAO.persistEntity(moviesDTO);
        }  */

       //------------------------------------------------------------/

        //ADDING GENRES TO DATABASE
        /*    GenreDAO genreDAO = GenreDAO.getInstance(HibernateConfig.getEntityManagerFactory("the_movie_db"));
        List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(GenreService.getAllGenresJSON());
        System.out.println(genreDTOS);

        for (GenreDTO genreDTO : genreDTOS) {
            genreDAO.persistEntity(genreDTO);
        } */

    }
}