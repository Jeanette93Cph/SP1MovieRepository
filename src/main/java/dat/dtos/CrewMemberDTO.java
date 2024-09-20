package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//being used in DirectorService

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {

	public String name;
	public String job;

	public List<CrewMemberDTO> crew;

	@JsonProperty("id")
	public Long id;
}