package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import dat.entities.Actor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
    }

    public static List<ActorDTO> convertFromJSON (String jsonCredits) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonCredits, mapper.getTypeFactory().constructCollectionType(List.class, ActorDTO.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}