package pepper.boss.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import pepper.boss.dao.PepperDao;
import pepper.boss.dto.PepperDTO;
import pepper.boss.entity.Pepper;
import pepper.boss.error.ResourceNotFoundException;
import pepper.boss.mapper.EntityMapper;

@RestController
@RequestMapping("/peppers")
public class PepperController {

	private final PepperDao pepperDao;

	public PepperController(PepperDao pepperDao) {
		this.pepperDao = pepperDao;
	}

	@GetMapping("/dto")
	public List<PepperDTO> fetchPeppersDto() {
		Iterable<Pepper> peppers = pepperDao.findAll();
		List<PepperDTO> result = new ArrayList<>();
		for (Pepper p : peppers) {
			result.add(EntityMapper.toPepperDTO(p));
		}
		return result;
	}

	@GetMapping
	public List<Pepper> fetchPeppers() {
		return pepperDao.findAll();
	}
	
	@GetMapping("/{id}")
	public Pepper fetchPepperById(@PathVariable Long id) {
	    return pepperDao.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Pepper " + id + " not found"));
	}

	@GetMapping("/dto/{id}")
	public PepperDTO fetchPepperDTOById(@PathVariable Long id) {
		return pepperDao.findById(id).map(p -> EntityMapper.toPepperDTO(p))
				.orElseThrow(() -> new ResourceNotFoundException("Pepper " + id + " not found"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pepper createPepper(@Valid @RequestBody Pepper body) {
		return pepperDao.save(body);
	}

	@PutMapping("/{id}")
	public Pepper updatePepper(@PathVariable Long id, @Valid @RequestBody Pepper body) {
		Pepper existing = pepperDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pepper " + id + " not found"));

		existing.setName(body.getName());
		existing.setHeatLevel(body.getHeatLevel());
		existing.setNotes(body.getNotes());

		return pepperDao.save(existing);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePepper(@PathVariable Long id) {
		if (!pepperDao.existsById(id)) {
			throw new ResourceNotFoundException("Pepper " + id + " not found");
		}
		pepperDao.deleteById(id);
	}
}