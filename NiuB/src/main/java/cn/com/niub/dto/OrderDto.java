package cn.com.niub.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import cn.com.niub.domain.Order;
import lombok.Data;

@Data
public class OrderDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String daiKuanYongTu;
	private String daikuanType;
	private String jieKuanSum;
	private String qiXian;
	private String danBaoType;
	private String huanKuanType;
	//private String detailId;
	
	//基本信息
	private String name;
	private String sex;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date chuShengRiQi;
	private String age;
	private String icard;
	private String jiGuan;
	private String diZhi;
	private String hunYin;
	private String xueLi;
	private String sheBao;
	private String phone;
	private String zuoJi;
	
	//审核意见
	private String auditResults;
	private String auditOpinion;
	
	private String creater;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date createTime;
	private String updater;
	@DateTimeFormat(pattern = "yyyy-MM-dd") 
	private Date updateTime;
	private Integer delFlag;
	private String status;
	
	//房
	RoomDto room;
	//车
	CarDto car;
	//职业
	JobDto job;
	//补充材料
	SupplementaryDto supplementary;
	//配偶信息
	SpouseDto spouse;
	//联系人
	ContactsDto contacts;
	
	public OrderDto(){
		this.room = new RoomDto();
		this.car = new CarDto();
		this.job = new JobDto();
		this.supplementary = new SupplementaryDto();
		this.spouse = new SpouseDto();
		this.contacts = new ContactsDto();
	}
	
	public OrderDto(Order order){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(order, this);
	}
	public OrderDto(RoomDto room,CarDto car,JobDto job,SupplementaryDto supplementary,SpouseDto spouse,ContactsDto contacts){
		this.room = room;
		this.car = car;
		this.job = job;
		this.supplementary = supplementary;
		this.spouse = spouse;
		this.contacts = contacts;
	}
	
	public void setDto(RoomDto room,CarDto car,JobDto job,SupplementaryDto supplementary,SpouseDto spouse,ContactsDto contacts){
		this.room = room;
		this.car = car;
		this.job = job;
		this.supplementary = supplementary;
		this.spouse = spouse;
		this.contacts = contacts;
	}
	
}
