package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Supplementary;
import cn.com.niub.dto.SupplementaryDto;
import cn.com.niub.jpa.SupplementaryRepository;

@Service
public class SupplementaryService {

	@Autowired
	SupplementaryRepository supplementaryRepository;
	
	public SupplementaryDto findSupplementaryById(String id){
		return new SupplementaryDto(supplementaryRepository.findOne(id));
	}
	
	public void saveSupplementary(SupplementaryDto dto){
		supplementaryRepository.saveAndFlush(new Supplementary(dto));
	}
}
