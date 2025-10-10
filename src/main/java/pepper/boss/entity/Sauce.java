package pepper.boss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Sauce {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long sauceId;

	  @NotBlank(message = "Name is required")
	  @Size(max = 255, message = "Name must be 255 characters or less")
	  @Column(nullable = false, unique = true)
	  private String name;

	  @Size(max = 255, message = "Style must be 255 characters or less")
	  private String style;

	  // Finished sauce’s rating: "Mild", "Medium", "Hot", "SPICY!"
	  @Size(max = 50, message = "Heat level must be 50 characters or less")
	  private String heatLevel;

	  @Size(max = 500, message = "Notes must be 500 characters or less")
	  @Column(length = 500)
	  private String notes;

	  // Each Sauce uses one primary Pepper
	  @ManyToOne(optional = false, fetch = FetchType.EAGER)
	  @JoinColumn(name = "pepper_id", nullable = false)
	  private Pepper pepper;

}
