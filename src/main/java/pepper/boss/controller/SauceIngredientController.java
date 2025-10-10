package pepper.boss.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pepper.boss.dao.IngredientDao;
import pepper.boss.dao.SauceDao;
import pepper.boss.dao.SauceIngredientDao;
import pepper.boss.entity.Ingredient;
import pepper.boss.entity.Sauce;
import pepper.boss.entity.SauceIngredient;
import pepper.boss.error.ResourceNotFoundException;

@RestController
@RequestMapping("/sauce-ingredients")
public class SauceIngredientController {

	private final SauceIngredientDao siDao;
	private final SauceDao sauceDao;
	private final IngredientDao ingredientDao;

	public SauceIngredientController(SauceIngredientDao siDao, SauceDao sauceDao, IngredientDao ingredientDao) {
		this.siDao = siDao;
		this.sauceDao = sauceDao;
		this.ingredientDao = ingredientDao;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SauceIngredientResponse addIngredientToSauce(@Valid @RequestBody SauceIngredientRequest body) {
		if (siDao.existsBySauce_SauceIdAndIngredient_IngredientId(body.sauceId(), body.ingredientId())) {
			throw new IllegalArgumentException("Ingredient already added to this sauce");
		}

		Sauce sauce = sauceDao.findById(body.sauceId())
				.orElseThrow(() -> new ResourceNotFoundException("Sauce " + body.sauceId() + " not found"));
		Ingredient ingredient = ingredientDao.findById(body.ingredientId())
				.orElseThrow(() -> new ResourceNotFoundException("Ingredient " + body.ingredientId() + " not found"));

		SauceIngredient si = new SauceIngredient();
		si.setSauce(sauce);
		si.setIngredient(ingredient);
		si.setQuantity(body.quantity());
		si.setUnit(body.unit());

		si = siDao.save(si);
		return SauceIngredientResponse.from(si);
	}

	@GetMapping("/by-sauce/{sauceId}")
	public List<SauceIngredientResponse> listBySauce(@PathVariable Long sauceId) {
		// Ensure sauce exists (nice 404 if not)
		sauceDao.findById(sauceId).orElseThrow(() -> new ResourceNotFoundException("Sauce " + sauceId + " not found"));

		return siDao.findBySauce_SauceId(sauceId).stream().map(SauceIngredientResponse::from).toList();
	}

	@PutMapping("/{id}")
	public SauceIngredientResponse updateLink(@PathVariable Long id, @Valid @RequestBody UpdateLinkRequest body) {
		SauceIngredient existing = siDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("SauceIngredient " + id + " not found"));

		existing.setQuantity(body.quantity());
		existing.setUnit(body.unit());

		existing = siDao.save(existing);
		return SauceIngredientResponse.from(existing);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteLink(@PathVariable Long id) {
		if (!siDao.existsById(id)) {
			throw new ResourceNotFoundException("SauceIngredient " + id + " not found");
		}
		siDao.deleteById(id);
	}

	// ====== Request/Response DTOs ======

	public static record SauceIngredientRequest(@NotNull(message = "sauceId is required") Long sauceId,
			@NotNull(message = "ingredientId is required") Long ingredientId,
			@NotNull(message = "quantity is required") @Positive(message = "quantity must be > 0") BigDecimal quantity,
			@Size(max = 50, message = "unit must be ≤ 50 characters") String unit) {
	}

	public static record UpdateLinkRequest(
			@NotNull(message = "quantity is required") @Positive(message = "quantity must be > 0") BigDecimal quantity,
			@Size(max = 50, message = "unit must be ≤ 50 characters") String unit) {
	}

	public static record SauceIngredientResponse(Long id, Long sauceId, String sauceName, Long ingredientId,
			String ingredientName, BigDecimal quantity, String unit) {
		public static SauceIngredientResponse from(SauceIngredient si) {
			return new SauceIngredientResponse(si.getSauceIngredientId(),
					si.getSauce().getSauceId(),
					si.getSauce().getName(),
					si.getIngredient().getIngredientId(),
					si.getIngredient().getName(),
					si.getQuantity(),
					si.getUnit());
		}
	}

}
