package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * We use this DTO in the DirectorService and ActorService class,
 * to deserialize the JSON credits into a Credits object.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {

	@JsonProperty("name")
	public String name;
	public String job;

	public List<CrewMemberDTO> crew;

	@JsonProperty("cast")
	private List<CrewMemberDTO> cast;

	@JsonProperty("id")
	public Long id;
}