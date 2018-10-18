package cn.com.niub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.niub.domain.Room;

public interface RoomRepository extends JpaRepository<Room, String>, JpaSpecificationExecutor<Room> {

}
