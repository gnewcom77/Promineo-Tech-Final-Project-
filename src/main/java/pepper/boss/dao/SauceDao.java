package pepper.boss.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pepper.boss.entity.Sauce;

@Repository
public interface SauceDao extends JpaRepository<Sauce, Long>{
	 List<Sauce> findByPepper_PepperId(Long pepperId);

}
