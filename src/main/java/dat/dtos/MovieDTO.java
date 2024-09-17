package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO
{

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private double voteAverage;

    private List<Genre> genres;

    private List<Actor> actors;

    private List<Director> directors;



    // convert from JSON to List of MovieDTO
    public static List<MovieDTO> convertToDTOFromJSONList(String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        //enabling it to properly serialize / deserialize date and time like LocalDate
        objectMapper.registerModule(new JavaTimeModule());

        try
        {
            MovieResponseDTO movieResponseDTO = objectMapper.readValue(json, MovieResponseDTO.class);
            return movieResponseDTO.getMovieList();
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
