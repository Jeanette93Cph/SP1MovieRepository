package dat;

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
        //Printing all danish movies for the recent 5 years in JSON
        String jsonAllMovies = MovieService.getAllMoviesJSON(2);
       // System.out.println(jsonAllMovies);

        //Printing all danish movies for the recent 5 years as MovieDTO's
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        moviesDTOs.forEach(System.out::println);


        /* ACTORS */
        //Printing all cast from the danish movies for the recent 5 years
       // String jsonAllActors = ActorService.getAllActorsJSON(3);
        //List<ActorDTO> actorDTOS = ActorDTO.convertToDTOFromJSONList(jsonAllActors);
        //actorDTOS.forEach(System.out::println);


        /* DIRECTORS */
        //Printing all directors from the danish movies for the recent 5 years
        //String jsonAllDirectors = DirectorService.getAllDirectorsJSON(1);
        //System.out.println(jsonAllDirectors);

        //Printing all directors from the danish movies for the recent 5 years as DirectorDTO's
       //List<DirectorDTO> directorDTOS = DirectorDTO.convertToDTOFromJSONList(jsonAllDirectors);
       //directorDTOS.forEach(System.out::println);


        /* GENRE */
        //Printing all genres
        //String allGenres = GenreService.getAllGenresJSON();
        //List<GenreDTO> genreDTOS = GenreDTO.convertToDTOFromJSONList(allGenres);
       //genreDTOS.forEach(System.out::println);


    }
}