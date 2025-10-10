package pepper.boss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Pepper {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pepperId;
	
	@Column(nullable = false, unique = true)
	@NotBlank(message = "Name is required")
	@Size(max = 255, message = "Name must be 255 characters or less")
	private String name;
	
	@Column
	@Size(max = 255, message = "Heat Level must be 255 characters or less")
	private String heatLevel;
	
	@Size(max = 500, message = "Notes must be 500 characters or less")
	@Column(length = 500)
	private String notes;
	
	/*
	public Long getPepperId() {
		return pepperId;
	}
	
	public void setPepperId(Long pepperId) {
		this.pepperId = pepperId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHeatLevel() {
		return heatLevel;
	}
	
	public void setHeatLevel(String heatLevel) {
		this.heatLevel = heatLevel;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
*/


}
