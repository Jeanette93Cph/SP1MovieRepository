package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {

	public String name;
	public String job;

	@JsonProperty("id")
	public Long id;
}