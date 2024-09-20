package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {

	@JsonProperty("id")
	public Long id;

	public String name;
	public String job;

	public List<CrewMemberDTO> crew;
}