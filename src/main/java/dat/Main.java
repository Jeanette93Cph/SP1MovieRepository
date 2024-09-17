package dat;

import dat.dtos.MovieDTO;
import dat.services.MovieService;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {

        String jsonAllMovies = MovieService.getAllMoviesJSON();
        //System.out.println(jsonAllMovies);

        List<MovieDTO> moviesDTOs = MovieDTO.convertToDTOFromJSONList(jsonAllMovies);
         moviesDTOs.forEach(System.out::println);


    }
}