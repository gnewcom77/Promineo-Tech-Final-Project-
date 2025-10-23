//pepper is contributor

package pepper.boss.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


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
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "pepper", cascade = CascadeType.ALL)
	@JsonManagedReference
	private Set<Sauce> sauces = new HashSet<>();
	
	
}