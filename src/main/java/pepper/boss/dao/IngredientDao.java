package pepper.boss.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pepper.boss.entity.Ingredient;

public interface IngredientDao extends JpaRepository<Ingredient, Long> {

}
