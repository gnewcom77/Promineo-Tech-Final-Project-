package pepper.boss.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pepper.boss.entity.SauceIngredient;

public interface SauceIngredientDao extends JpaRepository<SauceIngredient, Long>{
	List<SauceIngredient> findBySauce_SauceId(Long sauceId);
	boolean existsBySauce_SauceIdAndIngredient_IngredientId(Long sauceId, Long ingredientId);
}
