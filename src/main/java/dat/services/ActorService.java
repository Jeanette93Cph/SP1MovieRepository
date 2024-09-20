package dat.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dtos.ActorDTO;
import dat.dtos.MovieDTO;
import dat.dtos.MovieResponseDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static dat.services.DirectorService.getJSONResponse;

public class ActorService {
    private static final String API_KEY = System.getenv("api_key");
    private static final String BASE_URL_MOVIE_DANISH_RECENT_5_YEARS = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-10-01&sort_by=popularity.desc&with_original_language=da";
    private static final String URL = "https://api.themoviedb.org/3/movie/";

    private static HttpClient client = HttpClient.newHttpClient();


    //with help from chatgpt
    public static List<ActorDTO> getAllActorsFromJSON(int page)
    {
        List<ActorDTO> listOfActorsDTO = new ArrayList<>();

        try {
            //get all movies based on filter: danish movies from the recent 5 years
            String jsonAllMovies = MovieService.getAllMoviesJSON(page);
            //get all movieIDs based on filter
            List<Long> movieIDs = MovieService.getAllMoviesIDJSON(jsonAllMovies);

            //retrieving actors for each movie
            for (Long movieID : movieIDs)
            {
                String url = URL + movieID + "/credits?api_key=" + API_KEY + "&page=" + page;
                String jsonCredits = getJSONResponse(url);
                List<ActorDTO> actorList = ActorService.extractActorsFromCredits(jsonCredits);
               	if(actorList != null)
				{
					listOfActorsDTO.addAll(actorList);
				}
            }
			return listOfActorsDTO;

        } catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

	// help method to getAllActorsJSON(). with help from chatgpt
	private static String getJSONResponse(String url) throws IOException, InterruptedException
	{
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}



	// help method to getAllActorsJSON(). with help from chatgpt
	public static List<ActorDTO> extractActorsFromCredits(String jsonCredits)
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();

			//deserialize the JSON credits into a ActorDTO object
			ActorDTO actorDTO = objectMapper.readValue(jsonCredits, ActorDTO.class);

			List<ActorDTO> listActorDTO = actorDTO.cast;

			if(listActorDTO == null)
			{
				System.out.println("No actors found in credits.");
			}


			return listActorDTO;

		} catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return null;
	}


}
