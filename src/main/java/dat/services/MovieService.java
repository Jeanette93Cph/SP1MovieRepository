package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.exceptions.JpaException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
            throw new JpaException("json processing error");
        }
    }

    public static List<Long> getAllMoviesIDJSON(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
            List<MovieDTO> movieDTOS = movieResponseDTO.getMovieList();
            return movieDTOS.stream().map(MovieDTO::getId).collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new JpaException("json processing error");
        }
    }
}