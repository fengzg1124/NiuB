package cn.com.niub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.niub.domain.Supplementary;

public interface SupplementaryRepository extends JpaRepository<Supplementary, String>, JpaSpecificationExecutor<Supplementary> {

}
