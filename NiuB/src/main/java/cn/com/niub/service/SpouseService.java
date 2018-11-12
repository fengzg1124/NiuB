package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Spouse;
import cn.com.niub.dto.SpouseDto;
import cn.com.niub.jpa.SpouseRepository;

@Service
public class SpouseService {

	@Autowired
	SpouseRepository spouseRepository;
	
	public SpouseDto findSpouseById(String id){
		return new SpouseDto(spouseRepository.findOne(id));
	}
	
	public void saveSpouse(SpouseDto dto){
		spouseRepository.saveAndFlush(new Spouse(dto));
	}
}
