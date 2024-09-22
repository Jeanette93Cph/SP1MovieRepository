package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.ActorDTO;
import dat.exceptions.ApiException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to fetch actors from the movie database API endpoint via the URI.
 * We use the extractActorsFromCredits() method to extract the actors from the JSON response.
 */

public class ActorService {
	private static final String API_KEY = System.getenv("api_key");
	private static final String URL = "https://api.themoviedb.org/3/movie/";

	private static HttpClient httpClient = HttpClient.newHttpClient();

	public static List<ActorDTO> getAllActorsFromJSON (int page) {
		List<ActorDTO> listOfActorsDTO = new ArrayList<>();

		try {
			// get all danish movies from the recent 5 years (on page)
			String jsonAllMovies = MovieService.getAllMoviesJSON(page);

			// get all ids from the movies
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

		} catch (IOException | InterruptedException e) { //throws exception if the request fails
			throw new ApiException("Failed to fetch actors: " + e.getMessage());
		}
	}

	// Method to fetch a JSON response given a URL - searching for actor credits
	public static String getJSONResponse (String url) throws IOException, InterruptedException {

		// we establish a connection to the API through HttpClient
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			// Check if the response status is not successful
			if (response.statusCode() != HttpURLConnection.HTTP_OK) { //HTTP status code 200 means the request was successful
				String errorMsg = "Failed to fetch actors. HTTP Status Code: " + response.statusCode() + ". Response: " + response.body();
				throw new ApiException(errorMsg);
			}
			return response.body();
		} catch (Exception e) {
			throw new ApiException("Failed to fetch actors: " + e.getMessage());
		}
	}

	// Method to extract actors from credits - converts them to ActerDTO objects and saves in List
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
			throw new ApiException("Failed to extract actors from credits: " + e.getMessage());
		}
	}
}