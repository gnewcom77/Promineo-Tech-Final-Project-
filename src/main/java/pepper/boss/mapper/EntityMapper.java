package pepper.boss.mapper;

import java.util.stream.Collectors;

import pepper.boss.dto.IngredientDTO;
import pepper.boss.dto.PepperDTO;
import pepper.boss.dto.SauceDTO;
import pepper.boss.entity.Ingredient;
import pepper.boss.entity.Pepper;
import pepper.boss.entity.Sauce;

public class EntityMapper {

	public static PepperDTO toPepperDTO(Pepper pepper) {
		if (pepper == null)
			return null;

		PepperDTO dto = new PepperDTO();
		dto.setPepperId(pepper.getPepperId());
		dto.setName(pepper.getName());
		dto.setHeatLevel(pepper.getHeatLevel());
		dto.setNotes(pepper.getNotes());

		if (pepper.getSauces() != null) {
			dto.setSauceIds(pepper.getSauces().stream().map(Sauce::getSauceId).collect(Collectors.toList()));
		}

		return dto;
	}

	public static SauceDTO toDTO(Sauce sauce) {
		if (sauce == null)
			return null;

		SauceDTO dto = new SauceDTO();
		dto.setSauceId(sauce.getSauceId());
		dto.setName(sauce.getName());
		dto.setStyle(sauce.getStyle());
		dto.setHeatLevel(sauce.getHeatLevel());
		dto.setNotes(sauce.getNotes());

		if (sauce.getPepper() != null) {
			dto.setPepperId(sauce.getPepper().getPepperId());
		}

		if (sauce.getIngredients() != null) {
			dto.setIngredientIds(
					sauce.getIngredients().stream().map(Ingredient::getIngredientId).collect(Collectors.toList()));
		}

		return dto;
	}

	public static IngredientDTO toDTO(Ingredient ingredient) {
		if (ingredient == null)
			return null;

		IngredientDTO dto = new IngredientDTO();
		dto.setIngredientId(ingredient.getIngredientId());
		dto.setName(ingredient.getName());
		dto.setNotes(ingredient.getNotes());
		return dto;
	}
}
