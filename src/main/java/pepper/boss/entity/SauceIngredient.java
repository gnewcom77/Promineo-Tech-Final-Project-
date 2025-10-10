package pepper.boss.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "sauce_ingredient", uniqueConstraints = @UniqueConstraint(columnNames = { "sauce_id", "ingredient_id" }))

public class SauceIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sauceIngredientId;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "sauce_id", nullable = false)
	private Sauce sauce;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id", nullable = false)
	private Ingredient ingredient;

	@NotNull(message = "Quantity is required")
	@Column(precision = 10, scale = 2, nullable = false)
	private BigDecimal quantity;

	@Size(max = 50, message = "Unit must be 50 characters or less")
	@Column(length = 50)
	private String unit;

}
