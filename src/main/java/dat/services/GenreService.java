package dat.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.GenreDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GenreService {

	private static final String API_KEY = System.getenv("api_key");
	private static final String GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list?api_key=%s&language=en-US";

	private static final HttpClient httpClient = HttpClient.newHttpClient();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Fetch genres from TMDb API
	public static List<GenreDTO> getGenresJSON() {
		List<GenreDTO> genres = new ArrayList<>();

		try {
			String url = String.format(GENRE_URL, API_KEY);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			// Send the request and get the response
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				// Parse the response body
				JsonNode rootNode = objectMapper.readTree(response.body());
				JsonNode genresNode = rootNode.get("genres");

				// If the "genres" node is not empty, map it to a list of GenreDTOs
				if (genresNode != null && genresNode.isArray()) {
					for (JsonNode genreNode : genresNode) {
						GenreDTO genre = parseGenre(genreNode);
						genres.add(genre);
					}
				}
			} else {
				System.err.println("Failed to fetch genres. Status Code: " + response.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error fetching genres: " + e.getMessage());
		}

		return genres;
	}

	// Helper method to parse individual genre from the genres array
	private static GenreDTO parseGenre(JsonNode genreNode) {
		Long id = genreNode.get("id").asLong();
		String name = genreNode.get("name").asText();

		return new GenreDTO(id, name);
	}
}