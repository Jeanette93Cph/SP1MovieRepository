package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.exceptions.ApiException;

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

			// Check if the response status is not successful
			if (response.statusCode() != 200) {
				String errorMsg = "Failed to fetch movies. HTTP Status Code: " + response.statusCode() + ". Response: " + response.body();
				throw new ApiException(errorMsg); // Log the error using your custom exception
			}
			return response.body();

		} catch (IOException e) {
			// Handle IOException (e.g., network issues)
			String errorMsg = "Network error occurred while fetching movies: " + e.getMessage();
			throw new ApiException(errorMsg); // Logs the error

		} catch (InterruptedException e) {
			// Handle InterruptedException and restore the interrupted status
			Thread.currentThread().interrupt();
			String errorMsg = "Request was interrupted: " + e.getMessage();
			throw new ApiException(errorMsg); // Logs the error

		} catch (Exception e) {
			// Handle any other unexpected exception
			String errorMsg = "Unexpected error occurred while fetching movies: " + e.getMessage();
			throw new ApiException(errorMsg); // Logs the error
		}
	}

	public static List<Long> getAllMoviesIDJSON (String json) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
			List<MovieDTO> movieDTOS = movieResponseDTO.getMovieList();
			return movieDTOS.stream().map(MovieDTO::getId).collect(Collectors.toList());

		} catch (ApiException | JsonProcessingException e) { // thrown by objectMapper.readValue
			throw new ApiException("Failed to fetch movieID through API endpoint");
		}
	}

	// convert from JSON to List of MovieDTO
	public static List<MovieDTO> convertToDTOFromJSONList (String json) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		try {
			MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
			return movieResponseDTO.getMovieList();
		} catch (JsonProcessingException e) { // thrown by objectMapper.readValue
			throw new Exception("Failed to convert JSON to MovieDTO");
		}
	}
}