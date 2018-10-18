package cn.com.niub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.niub.domain.Car;

public interface CarRepository extends JpaRepository<Car, String>, JpaSpecificationExecutor<Car> {

}
