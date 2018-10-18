package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Room;
import cn.com.niub.dto.RoomDto;
import cn.com.niub.jpa.RoomRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRepository;
	
	public Room findRoomById(String id){
		return roomRepository.findOne(id);
	}
	
	public void saveRoom(RoomDto dto){
		roomRepository.saveAndFlush(new Room(dto));
	}
}
