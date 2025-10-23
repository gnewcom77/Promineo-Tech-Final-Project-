package pepper.boss.dto;

import java.util.List;

public class PepperDTO {
	private Long pepperId;
	private String name;
	private String heatLevel;
	private String notes;

	private List<Long> sauceIds;

	public Long getPepperId() {
		return pepperId;
	}

	public void setPepperId(Long pepperId) {
		this.pepperId = pepperId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Long> getSauceIds() {
		return sauceIds;
	}

	public void setSauceIds(List<Long> sauceIds) {
		this.sauceIds = sauceIds;
	}

}
