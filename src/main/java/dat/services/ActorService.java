package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.ActorDTO;
import dat.dtos.CreditDTO;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dat.services.DirectorService.getJSONResponse;

public class ActorService {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";
    private static final String URL = "https://api.themoviedb.org/3/movie/";

    private static HttpClient client = HttpClient.newHttpClient();

    public static List<String> getAllActorsJSON(int page) {
        List<String> actors = new ArrayList<>();
        Set<String> actorSet = new HashSet<>();

        try {
            //get all movies based on filter: danish movies from the recent 5 years
            String jsonAllMovies = MovieService.getAllMoviesJSON(page);
            //get all movieIDs based on filter
            List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);

            //retrieving actors for each movie
            for (Long movieID : movieIDs) {
                String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
                String jsonCredits = getJSONResponse(url);
                List<ActorDTO> movieActors = ActorService.extractActorsFromCredits(jsonCredits);
                //adds the names of actors to the set to ensure uniqueness
                actorSet.addAll(movieActors.stream().map(ActorDTO::getName).collect(Collectors.toSet()));
            }

            //adds all unique actor names from the set to the list
            actors.addAll(actorSet);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public static List<ActorDTO> extractActorsFromCredits(String jsonCredits) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //deserialize the JSON credits into a Credits object
            CreditDTO credits = objectMapper.readValue(jsonCredits, CreditDTO.class);


        } catch(JsonProcessingException e) {
           throw new dat.exceptions.JpaException("json processing error");
        }
        return null;
    }
}