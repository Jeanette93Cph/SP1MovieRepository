package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrewMemberDTO {
	public String name;
	public String job;
	public Long id;
}