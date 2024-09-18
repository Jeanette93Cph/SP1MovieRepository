package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Movie;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO
{
    private Long id;

    private String name;

    private List<GenreDTO> genres;


    // convert from JSON to List of ActorDTO
    public static List<GenreDTO> convertToDTOFromJSONList(String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        //enabling it to properly serialize / deserialize date and time like LocalDate
        objectMapper.registerModule(new JavaTimeModule());

        try
        {
            GenreDTO genreDTO = objectMapper.readValue(json, GenreDTO.class);
            return genreDTO.genres;
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
