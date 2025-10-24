package pepper.boss.controller.DTO;

import java.util.List;

public class SauceDTO {
	private Long sauceId;
	private String name;
	private String style;
	private String heatLevel;
	private String notes;

	private Long pepperId;
	private List<Long> ingredientIds;

	public Long getSauceId() {
		return sauceId;
	}

	public void setSauceId(Long sauceId) {
		this.sauceId = sauceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public Long getPepperId() {
		return pepperId;
	}

	public void setPepperId(Long pepperId) {
		this.pepperId = pepperId;
	}

	public List<Long> getIngredientIds() {
		return ingredientIds;
	}

	public void setIngredientIds(List<Long> ingredientIds) {
		this.ingredientIds = ingredientIds;
	}
}
