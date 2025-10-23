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
import pepper.boss.dao.IngredientDao;
import pepper.boss.dto.IngredientDTO;
import pepper.boss.entity.Ingredient;
import pepper.boss.entity.Sauce;
import pepper.boss.error.ResourceNotFoundException;
import pepper.boss.mapper.EntityMapper;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

	private final IngredientDao ingredientDao;

	public IngredientController(IngredientDao ingredientDao) {
		this.ingredientDao = ingredientDao;
	}

	@GetMapping
	public List<Ingredient> fetchIngredients() {
		return ingredientDao.findAll();
	}

	@GetMapping("/{id}")
	public Ingredient fetchIngredientById(@PathVariable Long id) {
		return ingredientDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ingredient " + id + " not found"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Ingredient createIngredient(@Valid @RequestBody Ingredient body) {
		return ingredientDao.save(body);
	}

	@PutMapping("/{id}")
	public Ingredient updateIngredient(@PathVariable Long id, @Valid @RequestBody Ingredient body) {
		Ingredient existing = ingredientDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ingredient " + id + " not found"));

		existing.setName(body.getName());
		existing.setNotes(body.getNotes());
		return ingredientDao.save(existing);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteIngredient(@PathVariable Long id) {
		if (!ingredientDao.existsById(id)) {
			throw new ResourceNotFoundException("Ingredient " + id + " not found");
		}
		ingredientDao.deleteById(id);
	}

	@GetMapping("/dto")
	public List<IngredientDTO> fetchIngredientsDto() {
		Iterable<Ingredient> items = ingredientDao.findAll();
		List<IngredientDTO> result = new ArrayList<>();
		for (Ingredient i : items) {
			result.add(EntityMapper.toDTO(i)); // uses toDTO(Ingredient)
		}
		return result;
	}

	@GetMapping("/dto/{id}")
	public IngredientDTO fetchIngredientDtoById(@PathVariable Long id) {
		var opt = ingredientDao.findById(id);
		Ingredient i = opt.orElseThrow(() -> new ResourceNotFoundException("Ingredient " + id + " not found"));
		return EntityMapper.toDTO(i); // uses toDTO(Ingredient)
	}
}