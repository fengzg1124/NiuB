package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.niub.dto.RoomDto;
import lombok.Data;

@Entity
@Data
@Table(name="TBL_ROOM")
public class Room {

	@Id
	private String id;
	private String orderId;
	
	private String ffangChanType;
	private String fzunJin;
	private String ffangZheng;
	private String fmianJi;
	private String fjiaGe;
	private String fjianZhuNianFen;
	private String fanJieYinHang;
	private String fdaiKuanNianXian;
	private String fyueGong;
	private String fdaiKuanZongE;
	private String fqianKuan;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public Room(){}
	
	public Room(RoomDto roomDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(roomDto, this);
	}
}
