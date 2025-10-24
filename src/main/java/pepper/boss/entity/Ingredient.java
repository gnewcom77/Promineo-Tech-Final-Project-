//sauce = amenities

package pepper.boss.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingredientId;

	@NotBlank(message = "Name is required")
	@Size(max = 255, message = "Name must be 255 characters or less")
	@Column(nullable = false, unique = true)
	private String name;

	@Size(max = 500, message = "Notes must be 500 characters or less")
	@Column(length = 500)
	private String notes;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
//	@JsonBackReference
	@ManyToMany(mappedBy = "ingredients")
	private Set<Sauce> sauces = new HashSet<>();
}
