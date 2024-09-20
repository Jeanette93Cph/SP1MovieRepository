package dat.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.DirectorDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DirectorService {

	private static final String API_KEY = System.getenv("api_key");
	private static final String URL = "https://api.themoviedb.org/3/movie/%d/credits?api_key=%s";

	private static final HttpClient httpClient = HttpClient.newHttpClient();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	// Fetch director for a given movie ID
	public static DirectorDTO getDirectorsJSON(Long movieId) {
		try {
			String url = String.format(URL, movieId, API_KEY);
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

			// Send the request and get the response
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 200) {
				// Parse the response body
				JsonNode rootNode = objectMapper.readTree(response.body());
				JsonNode crewNode = rootNode.get("crew");

				// If the "crew" node is not empty, look for a director
				if (crewNode != null && crewNode.isArray()) {
					for (JsonNode crewMember : crewNode) {
						String job = crewMember.get("job").asText();
						if ("Director".equalsIgnoreCase(job)) {
							Long id = crewMember.get("id").asLong();
							String name = crewMember.get("name").asText();
							return new DirectorDTO(id, name);
						}
					}
				}
			} else {
				System.err.println("Failed to fetch director. Status Code: " + response.statusCode());
			}
		} catch (IOException | InterruptedException e) {
			System.err.println("Error fetching director: " + e.getMessage());
		}

		// Return null if no director was found
		return null;
	}
}
