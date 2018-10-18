package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Car;
import cn.com.niub.dto.CarDto;
import cn.com.niub.jpa.CarRepository;

@Service
public class CarService {
	
	@Autowired
	CarRepository carRepository;
	
	public Car findCarById(String id){
		return carRepository.findOne(id);
	}

	public void saveCar(CarDto dto){
		carRepository.saveAndFlush(new Car(dto));
	}
}
