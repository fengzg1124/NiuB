package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import cn.com.niub.dto.RoomDto;
import lombok.Data;

@Entity
@Data
@Table(name="TBL_ROOM")
@EntityListeners(AuditingEntityListener.class)
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
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String updater;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	public Room(){}
	
	public Room(RoomDto roomDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(roomDto, this);
	}
}
