package dat.services;

import com.fasterxml.jackson.databind.JsonNode;
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
	private static final String URL = "https://api.themoviedb.org/3/movie/%d/credits?api_key=%s";

	private static final HttpClient httpClient = HttpClient.newHttpClient();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Fetch actors for a given movie ID
	public static List<ActorDTO> getActorsJSON(Long movieId) {
		List<ActorDTO> actors = new ArrayList<>();

		try {
			String url = String.format(URL, movieId, API_KEY);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			// Send the request and get the response
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				// Parse the response body
				JsonNode rootNode = objectMapper.readTree(response.body());
				JsonNode castNode = rootNode.get("cast");

				// If the "cast" node is not empty, map it to a list of ActorDTOs
				if (castNode != null && castNode.isArray()) {
					for (JsonNode actorNode : castNode) {
						ActorDTO actor = parseActor(actorNode);
						actors.add(actor);
					}
				}
			} else {
				System.err.println("Failed to fetch actors. Status Code: " + response.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error fetching actors: " + e.getMessage());
		}

		return actors;
	}

	// Helper method to parse individual actor from the cast array
	private static ActorDTO parseActor(JsonNode actorNode) {
		Long id = actorNode.get("id").asLong();
		String name = actorNode.get("name").asText();

		return new ActorDTO(id, name);
	}
}
