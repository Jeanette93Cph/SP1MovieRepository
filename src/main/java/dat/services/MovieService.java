package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;
import dat.exceptions.ApiException;
import dat.exceptions.JpaException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to fetch movies from the movie database API endpoint via the URI.
 * We use the convertToDTOFromJSONList() method to convert the JSON response to a list of MovieDTO.
 * We use the getAllMoviesIDJSON() method to extract the movie IDs from the JSON response. This is used in the other service classes.
 */

public class MovieService {

	private static final String API_KEY = System.getenv("api_key");
	private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";

	public static String getAllMoviesJSON (int page) {
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
			if (response.statusCode() != HttpURLConnection.HTTP_OK) { //200 status code
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


	// convert from JSON to List of MovieDTO through all pages. help from chatgpt
	public static List<MovieDTO> convertToDTOFromJSONList (String json) {
		List<MovieDTO> allMovies = new ArrayList<>();
		int currentPage = 1;
		int totalPages;

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		// if moviereponseDTO is not null, add the first pages moves to the list
		try {
			MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);

			if (movieResponseDTO != null) {
				allMovies.addAll(movieResponseDTO.getMovieList());
				totalPages = movieResponseDTO.getTotalPages();
			} else {
				throw new JpaException("Failed to handle the JSON String");
			}

			// Fetch and process the remaining pages
			currentPage++; // move to the second page
			while (currentPage <= totalPages) {
				// fetch JSON response for the current page
				String jsonResponse = getAllMoviesJSON(currentPage);

				// Convert the JSON response directly to MovieResponseDTO
				movieResponseDTO = objectMapper.readValue(jsonResponse, MovieResponseDTO.class);

				// Add the current page's movies to the list
				if (movieResponseDTO != null) {
					allMovies.addAll(movieResponseDTO.getMovieList());
				}
				// Iterate through the remaining pages until the last page
				currentPage++;
			}
		} catch (Exception e) {
			throw new JpaException("Could not parse JSON for page: " + currentPage);
		}
		return allMovies;
	}


	// used in ActorService and DirectorService
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
}