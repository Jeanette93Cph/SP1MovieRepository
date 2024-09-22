package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Director;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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

    public DirectorDTO (long id, String name) {
        this.id = id;
        this.name = name;
    }
}