package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.GenreDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GenreService {

    private static final String API_KEY = System.getenv("api_key");
    private static final String GENRE = "https://api.themoviedb.org/3/genre/movie/list";


    public static String getAllGenresJSON () {
        String url = GENRE + "?api_key=" + API_KEY;

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
            e.printStackTrace();
        }
        return null;
    }

    // convert from JSON to List of ActorDTO
    public static List<GenreDTO> convertToDTOFromJSONList (String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        // enabling it to properly serialize / deserialize date and time like LocalDate
        objectMapper.registerModule(new JavaTimeModule());

        try {
            GenreDTO genreDTO = objectMapper.readValue(json, GenreDTO.class);
            return genreDTO.genres;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}