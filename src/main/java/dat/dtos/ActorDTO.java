package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            if(!json.trim().startsWith("["))
            {
                json = convertPlainStringToJSONArray(json);
            }

            return objectMapper.readValue(json, new TypeReference<List<ActorDTO>>() {});
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //help method to convertToDTOFromJSONList()
    private static String convertPlainStringToJSONArray(String plainString)
    {
        return Arrays.stream(plainString.split(",\\s*"))
                .map(name -> String.format("{\"name\": \"%s\"}", name.trim()))
                .collect(Collectors.joining(",", "[", "]"));
    }



}
