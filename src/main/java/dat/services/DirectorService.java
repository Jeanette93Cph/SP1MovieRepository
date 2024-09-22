package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dtos.CrewMemberDTO;
import dat.dtos.DirectorDTO;
import dat.exceptions.ApiException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DirectorService {

	/**
	 * This class is used to fetch directors from the movie database API endpoint via the URI.
	 * We use the extractDirectorsFromCredits() method to extract the directors from the JSON response.
	 * We also iterate through the crew and find the directors based on their job
	 */

	private static final String API_KEY = System.getenv("api_key");
	private static final String URL = "https://api.themoviedb.org/3/movie/";
	private static final HttpClient client = HttpClient.newHttpClient();

	public static List<DirectorDTO> getAllDirectorsFromJSON (int page) {
		List<DirectorDTO> listOfDirectorsDTO = new ArrayList<>();

		try {
			// Get all movies based on filter: danish movies from the recent 5 years
			String jsonAllMovies = MovieService.getAllMoviesJSON(page);

			// Get all movieIDs based on filter
			List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);

			// Retrieve directors for each movie
			for (Long movieID : movieIDs) {
				String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
				String jsonCredits = getJSONResponse(url);
				DirectorDTO directorDTO = DirectorService.extractDirectorFromCredits(jsonCredits);
				listOfDirectorsDTO.add(directorDTO);
			}
			return listOfDirectorsDTO;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getJSONResponse (String url) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

	public static DirectorDTO extractDirectorFromCredits (String jsonCredits) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();

			// Deserialize the JSON credits into a Credits object
			CrewMemberDTO crewMember = objectMapper.readValue(jsonCredits, CrewMemberDTO.class);

			// Iterate through the crew and find the director(s)
			for (CrewMemberDTO crewMemberDTO : crewMember.crew) {
				if ("Director".equalsIgnoreCase(crewMemberDTO.job)) {
					DirectorDTO directorDTO = new DirectorDTO();
					directorDTO.setName(crewMemberDTO.name);
					directorDTO.setId(crewMemberDTO.id);
					return directorDTO;
				}
			}
		} catch (JsonProcessingException e) {
			throw new ApiException("Failed to convert JSON to DTO: " + e.getMessage());
		}
		return null;
	}
}