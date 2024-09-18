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
public class ActorDTO
{
    private Long id;

    private String name;

    private List<ActorDTO> actors;


    // convert from JSON to List of ActorDTO
    public static List<ActorDTO> convertToDTOFromJSONList(String json)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        //enabling it to properly serialize / deserialize date and time like LocalDate
        objectMapper.registerModule(new JavaTimeModule());

        try
        {
            ActorDTO actorDTO = objectMapper.readValue(json, ActorDTO.class);
            return actorDTO.actors;
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
