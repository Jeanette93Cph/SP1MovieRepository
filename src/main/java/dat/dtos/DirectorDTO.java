package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Director;
import dat.entities.Movie;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    private static List<DirectorDTO> directors;

    public DirectorDTO(Director director) {
        this.id = director.getId();
        this.name = director.getName();
    }

    public DirectorDTO (long id, String name) {
        this.id = id;
        this.name = name;
    }
}