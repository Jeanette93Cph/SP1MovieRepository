package dat.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GenreService
{

    //should be resplaced with your API key
    private static final String API_KEY = System.getenv("api_key");
    private static final String GENRE = "https://api.themoviedb.org/3/genre/movie/list";


    public static String getAllGenresJSON()
    {
        String url = GENRE + "&api_key=" + API_KEY;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try
        {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        }catch (InterruptedException | IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }





}
