package pepper.boss.controller;

import java.util.ArrayList;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pepper.boss.dao.PepperDao;
import pepper.boss.dao.SauceDao;
import pepper.boss.dto.SauceDTO;
import pepper.boss.entity.Pepper;
import pepper.boss.entity.Sauce;
import pepper.boss.error.ResourceNotFoundException;
import pepper.boss.mapper.EntityMapper;
import pepper.boss.dao.IngredientDao;
import pepper.boss.entity.Ingredient;

@RestController
@RequestMapping("/sauces")
public class SauceController {

	private final SauceDao sauceDao;
	private final PepperDao pepperDao;
	private final IngredientDao ingredientDao;

	public SauceController(SauceDao sauceDao, PepperDao pepperDao, IngredientDao ingredientDao) {
		this.sauceDao = sauceDao;
		this.pepperDao = pepperDao;
		this.ingredientDao = ingredientDao;
	}

	@GetMapping
	public List<Sauce> fetchSauces() {
		return sauceDao.findAll();
	}

	@GetMapping("/{id}")
	public Sauce fetchSauceById(@PathVariable Long id) {
		return sauceDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sauce " + id + " not found"));
	}

	@GetMapping("/by-pepper/{pepperId}")
	public List<Sauce> listSaucesByPepper(@PathVariable Long pepperId) {
		// ensure the pepper exists → nice 404 if not
		pepperDao.findById(pepperId)
				.orElseThrow(() -> new ResourceNotFoundException("Pepper " + pepperId + " not found"));

		return sauceDao.findByPepper_PepperId(pepperId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Sauce createSauce(@Valid @RequestBody SauceRequest body) {
		Pepper pepper = pepperDao.findById(body.pepperId())
				.orElseThrow(() -> new ResourceNotFoundException("Pepper " + body.pepperId() + " not found"));

		Sauce s = new Sauce();
		s.setName(body.name());
		s.setStyle(body.style());
		s.setHeatLevel(body.heatLevel());
		s.setNotes(body.notes());
		s.setPepper(pepper);

		return sauceDao.save(s);
	}

	@PutMapping("/{id}")
	public Sauce updateSauce(@PathVariable Long id, @Valid @RequestBody SauceRequest body) {
		Sauce existing = sauceDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Sauce " + id + " not found"));

		Pepper pepper = pepperDao.findById(body.pepperId())
				.orElseThrow(() -> new ResourceNotFoundException("Pepper " + body.pepperId() + " not found"));

		existing.setName(body.name());
		existing.setStyle(body.style());
		existing.setHeatLevel(body.heatLevel());
		existing.setNotes(body.notes());
		existing.setPepper(pepper);

		return sauceDao.save(existing);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSauce(@PathVariable Long id) {
		if (!sauceDao.existsById(id)) {
			throw new ResourceNotFoundException("Sauce " + id + " not found");
		}
		sauceDao.deleteById(id);
	}

	public static record SauceRequest(
			@NotBlank(message = "Name is required") @Size(max = 255, message = "Name must be ≤ 255 characters") String name,

			@Size(max = 255, message = "Style must be 255 characters or less") String style,

			@Size(max = 50, message = "Heat level must be 50 characters or less") String heatLevel,

			@Size(max = 500, message = "Notes must be 500 characters or less") String notes,

			@NotNull(message = "pepperId is required") Long pepperId) {
	}
	
	@PostMapping("/{sauceId}/ingredients/{ingredientId}")
	public SauceDTO addIngredientToSauce(@PathVariable Long sauceId, @PathVariable Long ingredientId) {
	    Sauce sauce = sauceDao.findById(sauceId)
	            .orElseThrow(() -> new ResourceNotFoundException("Sauce " + sauceId + " not found"));
	    Ingredient ingredient = ingredientDao.findById(ingredientId)
	            .orElseThrow(() -> new ResourceNotFoundException("Ingredient " + ingredientId + " not found"));

	    sauce.getIngredients().add(ingredient);
	    Sauce saved = sauceDao.save(sauce);
	    return EntityMapper.toDTO(saved);
	}

	@DeleteMapping("/{sauceId}/ingredients/{ingredientId}")
	public SauceDTO removeIngredientFromSauce(@PathVariable Long sauceId, @PathVariable Long ingredientId) {
	    Sauce sauce = sauceDao.findById(sauceId)
	            .orElseThrow(() -> new ResourceNotFoundException("Sauce " + sauceId + " not found"));
	    Ingredient ingredient = ingredientDao.findById(ingredientId)
	            .orElseThrow(() -> new ResourceNotFoundException("Ingredient " + ingredientId + " not found"));

	    sauce.getIngredients().remove(ingredient);
	    Sauce saved = sauceDao.save(sauce);
	    return EntityMapper.toDTO(saved);
	}
	
	@GetMapping("/dto")
	public List<SauceDTO> fetchSaucesDto() {
	    Iterable<Sauce> items = sauceDao.findAll();
	    List<SauceDTO> result = new ArrayList<>();
	    for (Sauce s : items) {
	        result.add(EntityMapper.toDTO(s)); // uses toDTO(Sauce) in your mapper
	    }
	    return result;
	}
	
	@GetMapping("/dto/{id}")
	public SauceDTO fetchSauceDtoById(@PathVariable Long id) {
	    Sauce s = sauceDao.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Sauce " + id + " not found"));
	    return EntityMapper.toDTO(s);
	}
}
