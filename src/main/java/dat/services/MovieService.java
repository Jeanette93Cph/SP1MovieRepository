package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.exceptions.JpaException;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";

    public static String getAllMoviesJSON(int page) {

        String url = BASE_URL_MOVIE_DANISH_RECENT_5_YEARS + "&api_key=" + API_KEY + "&page=" + page;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (InterruptedException | IOException e) {
            throw new JpaException("respond not found");
        }
    }



    // convert from JSON to List of MovieDTO through all pages. help from chatgpt
    public static List<MovieDTO> convertToDTOFromJSONList(String json) {
        List<MovieDTO> allMovies = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // if moviereponseDTO is not null, add the first pages moves to the list
        try
        {
            MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);

            if (movieResponseDTO != null)
            {
                allMovies.addAll(movieResponseDTO.getMovieList());
                totalPages = movieResponseDTO.getTotalPages();
            } else
            {
                throw new JpaException("Failed to handle the JSON String");
            }

            //Fetch and process the remaining pages

            //move to the second page
            currentPage++;
            while (currentPage <= totalPages)
            {
                //fetch JSON response for the current page
                String jsonResponse = getAllMoviesJSON(currentPage);

                // Convert the JSON response directly to MovieResponseDTO
                movieResponseDTO = objectMapper.readValue(jsonResponse, MovieResponseDTO.class);

                // Add the current page's movies to the list
                if (movieResponseDTO != null)
                {
                    allMovies.addAll(movieResponseDTO.getMovieList());
                }
                // Move to the next page
                currentPage++;
            }
        }
        catch(Exception e)
        {
            throw new JpaException("Could not parse JSON for page: " + currentPage);
        }
        return allMovies;
    }


    //used in ActorService and DirectorService
    public static List<Long> getAllMoviesIDJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
            List<MovieDTO> movieDTOS = movieResponseDTO.getMovieList();
            return movieDTOS.stream().map(MovieDTO::getId).collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            throw new JpaException("respond not found");
        }
    }

}