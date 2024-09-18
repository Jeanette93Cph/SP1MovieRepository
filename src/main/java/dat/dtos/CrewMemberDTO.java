package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {
	public String name;
	public String job;
	public Long id;
}