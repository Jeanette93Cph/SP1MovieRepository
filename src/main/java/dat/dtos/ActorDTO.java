package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

	public List<ActorDTO> cast;

	public ActorDTO (Actor actor) {
		this.id = actor.getId();
		this.name = actor.getName();
	}

	public ActorDTO (long id, String name) {
		this.id = id;
		this.name = name;
	}
}