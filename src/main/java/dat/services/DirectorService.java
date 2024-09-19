package dat.services;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import dat.dtos.CrewMemberDTO;
// import dat.dtos.DirectorDTO;
// import java.io.IOException;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.ArrayList;
// import java.util.List;
//
 public class DirectorService {
//
// 	private static final String API_KEY = System.getenv("api_key");
// 	private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";
// 	private static final String URL = "https://api.themoviedb.org/3/movie/";
// 	private static final HttpClient client = HttpClient.newHttpClient();
//
// 	public static List<DirectorDTO> getAllDirectorsFromJSON(int page) {
// 		List<DirectorDTO> listOfDirectorsDTO = new ArrayList<>();
//
// 		try {
// 			// Get all movies based on filter: danish movies from the recent 5 years
// 			String jsonAllMovies = MovieService.getAllMoviesJSON(page);
// 			// Get all movieIDs based on filter
// 			List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);
//
// 			// Retrieve directors for each movie
// 			for (Long movieID : movieIDs)
// 			{
// 				String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
// 				String jsonCredits = getJSONResponse(url);
// 				DirectorDTO directorDTO = DirectorService.extractDirectorFromCredits(jsonCredits);
// 				listOfDirectorsDTO.add(directorDTO);
// 			}
// 			return listOfDirectorsDTO;
//
// 		} catch (IOException | InterruptedException e) {
// 			e.printStackTrace();
// 		}
// 		return null;
// 	}
//
//
// 	// help method to getAllActorsJSON(). with help from chatgpt
// 	static String getJSONResponse (String url) throws IOException, InterruptedException {
// 		HttpRequest request = HttpRequest.newBuilder()
// 				.uri(URI.create(url))
// 				.GET()
// 				.build();
//
// 		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
// 		return response.body();
// 	}
//
// 	public static DirectorDTO extractDirectorFromCredits(String jsonCredits) {
// 		try {
// 			ObjectMapper objectMapper = new ObjectMapper();
//
// 			// Deserialize the JSON credits into a Credits object
// 			CreditDTO credits = objectMapper.readValue(jsonCredits, CreditDTO.class);
//
//
// 			// Iterate through the crew and find the director(s)
// 			for (CrewMemberDTO crewMemberDTO : credits.crew) {
// 				if ("Director".equalsIgnoreCase(crewMemberDTO.job)) {
// 					DirectorDTO directorDTO = new DirectorDTO();
// 					directorDTO.setName(crewMemberDTO.name); // Assign the director's name
// 					directorDTO.setId(crewMemberDTO.id); // Assign the director's name
// 					return directorDTO;
// 				}
// 			}
//
// 		} catch (JsonProcessingException e) {
// 			e.printStackTrace();
// 		}
// 		return null;
// 	}
}