package cn.com.niub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.niub.domain.Spouse;

public interface SpouseRepository extends JpaRepository<Spouse, String>, JpaSpecificationExecutor<Spouse> {

}
