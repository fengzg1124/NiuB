package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Contacts;
import cn.com.niub.dto.ContactsDto;
import cn.com.niub.jpa.ContactsRepository;

@Service
public class ContactsService {

	@Autowired
	ContactsRepository contactsRepository;
	
	public Contacts findContactsById(String id){
		return contactsRepository.findOne(id);
	}

	public void saveContacts(ContactsDto dto){
		contactsRepository.saveAndFlush(new Contacts(dto));
	}
}
