package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenreDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    //ignore this field when converting to db
    @ToString.Exclude   // Prevent infinite loop when fetching data
    private List<GenreDTO> genres;

    public GenreDTO(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    // convert from JSON to List of ActorDTO
    public static List<GenreDTO> convertToDTOFromJSONList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            GenreDTO genreDTO = objectMapper.readValue(json, GenreDTO.class);
            return genreDTO.genres;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to GenreDTO list.");
        }
    }
}