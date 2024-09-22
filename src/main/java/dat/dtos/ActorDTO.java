package dat.dtos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Actor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO
{
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public List<ActorDTO> cast;

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
    }
}