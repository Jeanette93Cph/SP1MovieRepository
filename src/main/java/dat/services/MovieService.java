package dat.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.MovieDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class MovieService {

	private static final String API_KEY = System.getenv("api_key");
	private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS =
			"https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&" +
					"primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-12-31&sort_by=popularity.desc&" +
					"with_original_language=da";

	private static final HttpClient httpClient = HttpClient.newHttpClient();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Fetch raw JSON response for a given page
	public static String getMoviesJSONRaw(int page) {
		try {
			String url = String.format("%s&page=%d&api_key=%s", BASE_URL_MOVIE_DANISH_RECENT_5_YEARS, page, API_KEY);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			// Send the request and get the response
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			// Check if the response status is OK (200)
			if (response.statusCode() == 200) {
				return response.body(); // Return raw JSON string
			} else {
				System.err.println("Failed to fetch movies. Status Code: " + response.statusCode());
				return null;
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error fetching movie data: " + e.getMessage());
			return null;
		}
	}

	// Parse movie JSON response into a list of MovieDTOs
	public static List<MovieDTO> parseMoviesFromJson(String jsonResponse) {
		List<MovieDTO> movies = new ArrayList<>();
		try {
			JsonNode rootNode = objectMapper.readTree(jsonResponse);
			JsonNode results = rootNode.get("results");

			// Parse each movie in the results
			for (JsonNode movieNode : results) {
				MovieDTO movieDTO = parseMovie(movieNode);
				movies.add(movieDTO);
			}
		} catch (IOException e) {
			System.err.println("Error parsing movie data: " + e.getMessage());
		}

		return movies;
	}

	// Helper method to parse a movie node from the TMDb API response
	private static MovieDTO parseMovie(JsonNode movieNode) {
		Long id = movieNode.get("id").asLong();
		String title = movieNode.get("title").asText();
		String originalLanguage = movieNode.get("original_language").asText();
		String releaseDate = movieNode.get("release_date").asText();
		Double popularity = movieNode.get("popularity").asDouble();
		Double voteAverage = movieNode.get("vote_average").asDouble();

		return new MovieDTO(id, title, originalLanguage, releaseDate, popularity, voteAverage, null, null, null);
	}

	// Fetch total pages from the movie JSON response
	public static int getTotalPages(String jsonResponse) {
		try {
			JsonNode rootNode = objectMapper.readTree(jsonResponse);
			return rootNode.get("total_pages").asInt();
		} catch (IOException e) {
			throw new RuntimeException("Failed to parse total pages from response", e);
		}
	}
}
