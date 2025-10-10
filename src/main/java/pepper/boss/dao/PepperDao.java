package pepper.boss.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pepper.boss.entity.Pepper;

public interface PepperDao extends JpaRepository<Pepper, Long>{

}
