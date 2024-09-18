package dat.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.ActorDTO;
import dat.dtos.DirectorDTO;
import lombok.Data;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class DirectorService {

    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";
    private static final String URL = "https://api.themoviedb.org/3/movie/";
    private static HttpClient client = HttpClient.newHttpClient();

    public static String getAllDirectorsJSON(int page) {
        Set<String> directorSet = new HashSet<>();

        try {
            // Get all movies based on filter: danish movies from the recent 5 years
            String jsonAllMovies = MovieService.getAllMoviesJSON(page);
            // Get all movieIDs based on filter
            List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);

            // Retrieve directors for each movie
            for (Long movieID : movieIDs) {
                String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
                String jsonCredits = getJSONResponse(url);
                List<DirectorDTO> movieDirectors = DirectorService.extractDirectorsFromCredits(jsonCredits);

                // Add the names of directors to the set to ensure uniqueness
                directorSet.addAll(movieDirectors.stream()
                        .map(DirectorDTO::getName)
                        .collect(Collectors.toSet()));
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Join all unique director names from the set into a single string with a delimiter
        return String.join(", ", directorSet);  // Joining with a comma and space delimiter
    }


    static String getJSONResponse (String url) throws IOException, InterruptedException {
    // help method to getAllActorsJSON(). with help from chatgpt
    private static String getJSONResponse(String url) throws IOException, InterruptedException
    {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CrewMember {
        public String name;
        public String job;
        public Long id;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Credits {
        public List<CrewMember> crew;
    }

    public static List<DirectorDTO> extractDirectorsFromCredits(String jsonCredits) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Deserialize the JSON credits into a Credits object
            Credits credits = objectMapper.readValue(jsonCredits, Credits.class);

            List<DirectorDTO> directors = new ArrayList<>();

            // Iterate through the crew and find the director(s)
            for (CrewMember crewMember : credits.crew) {
                if ("Director".equalsIgnoreCase(crewMember.job)) {
                    DirectorDTO directorDTO = new DirectorDTO();
                    directorDTO.setName(crewMember.name); // Assign the director's name
                    directorDTO.setId(crewMember.id); // Assign the director's name
                    directors.add(directorDTO);
                }
            }

            return directors; // Return the list of directors

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}