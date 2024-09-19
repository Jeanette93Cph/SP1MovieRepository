package dat.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.ActorDTO;
import dat.dtos.CreditDTO;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;

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

import static dat.services.DirectorService.getJSONResponse;

public class ActorService {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";
    private static final String URL = "https://api.themoviedb.org/3/movie/";

    private static HttpClient client = HttpClient.newHttpClient();


    //with help from chatgpt
    public static String getAllActorsJSON(int page)
    {
        Set<String> actorSet = new HashSet<>();

        try {
            //get all movies based on filter: danish movies from the recent 5 years
            String jsonAllMovies = MovieService.getAllMoviesJSON(page);
            //get all movieIDs based on filter
            List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);

            //retrieving actors for each movie
            for (Long movieID : movieIDs) {
				//https://api.themoviedb.org/3/movie/1115377/credits?api_key=<<APIKEY>>&page=1
                String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
                String jsonCredits = getJSONResponse(url);
                List<ActorDTO> movieActors = ActorService.extractActorsFromCredits(jsonCredits);
                //adds the names of actors to the set to ensure uniqueness
                actorSet.addAll(movieActors.stream().map(ActorDTO::getName).collect(Collectors.toSet()));
            }

        } catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        // Join all unique actors names from the set into a single string with a delimiter
        return String.join(", ", actorSet);  // Joining with a comma and space delimiter

    }

	// help method to getAllActorsJSON(). with help from chatgpt
	private static String getJSONResponse(String url) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

	// help method to getAllActorsJSON(). with help from chatgpt
	public static List<ActorDTO> extractActorsFromCredits(String jsonCredits) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			//deserialize the JSON credits into a Credits object
			CreditDTO creditsDTO = objectMapper.readValue(jsonCredits, CreditDTO.class);

			return creditsDTO.getCrew().stream()
					.filter(crewDTO -> crewDTO.getJob().equals("Actor"))
					.map(crewDTO -> new ActorDTO(crewDTO.getName()))
					.collect(Collectors.toList());

		} catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return null;
	}


}
