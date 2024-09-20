package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.ActorDTO;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ActorService {
	private static final String API_KEY = System.getenv("api_key");
	private static final String URL = "https://api.themoviedb.org/3/movie/";

	public static List<ActorDTO> getAllActorsFromJSON (int page) {
		List<ActorDTO> listOfActorsDTO = new ArrayList<>();

		try {
			// get all movies based on filter: danish movies from the recent 5 years
			String jsonAllMovies = MovieService.getAllMoviesJSON(page);
			// get all movieIDs based on filter
			List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);

			// retrieving actors for each movie
			for (Long movieID : movieIDs) {
				String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
				String jsonCredits = getJSONResponse(url);
				List<ActorDTO> actorList = ActorService.extractActorsFromCredits(jsonCredits);
				if (actorList != null) {
					listOfActorsDTO.addAll(actorList);
				}
			}
			return listOfActorsDTO;

		} catch (IOException | InterruptedException e) {
			throw new RuntimeException("Failed to fetch actors: " + e.getMessage());
		}
	}

	// help method to getAllActorsJSON(). with help from chatgpt
	private static String getJSONResponse (String url) throws IOException, InterruptedException {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}


	// help method to getAllActorsJSON(). with help from chatgpt
	public static List<ActorDTO> extractActorsFromCredits (String jsonCredits) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			// deserialize the JSON credits into a ActorDTO object
			ActorDTO actorDTO = objectMapper.readValue(jsonCredits, ActorDTO.class);

			List<ActorDTO> listActorDTO = actorDTO.cast;

			if (listActorDTO == null) {
				System.out.println("No actors found in credits.");
			}
			return listActorDTO;
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to extract actors from credits: " + e.getMessage());
		}
	}
}