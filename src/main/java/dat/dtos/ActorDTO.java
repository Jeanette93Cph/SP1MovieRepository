package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dat.entities.Movie;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO
{
    private Long id;

    private String name;

    private List<Movie> movies;

}
