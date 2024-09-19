package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Director;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private static String convertPlainStringToJSONArray(String plainString) {
        return Arrays.stream(plainString.split(",\\s*"))
                .map(name -> String.format("{\"name\": \"%s\"}", name.trim()))
                .collect(Collectors.joining(",", "[", "]"));
    }
}