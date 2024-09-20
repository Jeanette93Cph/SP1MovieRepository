package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Genre;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {
    private Long id;
    private String name;
    private List<GenreDTO> genres;


    public GenreDTO(Genre genre)
    {
        this.id = genre.getId();
        this.name = genre.getName();
    }



    // convert from JSON to List of ActorDTO
    public static List<GenreDTO> convertToDTOFromJSONList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); //enabling it to properly serialize / deserialize date and time like LocalDate

        try {
            GenreDTO genreDTO = objectMapper.readValue(json, GenreDTO.class);
            return genreDTO.genres;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}