package dat;

import dat.dtos.MovieDTO;
import dat.services.ActorService;
import dat.services.MovieService;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        //Printing all danish movies for the recent 5 years in JSON
        String jsonAllMovies = MovieService.getAllMoviesJSON(2);
       // System.out.println(jsonAllMovies);

        //Printing all danish movies for the recent 5 years as MovieDTO's
        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
        //moviesDTOs.forEach(System.out::println);

        //Printing all cast from the danish movies for the recent 5 years
        ActorService.getAllActorsJSON(2).forEach(System.out::println);
    }
}