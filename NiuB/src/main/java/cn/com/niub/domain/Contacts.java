package cn.com.niub.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.niub.dto.ContactsDto;
import lombok.Data;

//联系人
@Entity
@Data
@Table(name="TBL_CONTACTS")
public class Contacts {

	@Id
	private String id;
	private String orderId;
	
	private String lname;
	private String lguanXi;
	private String licard;
	private String lshi;
	private String lqu;
	private String llu;
	private String lhao;
	private String lphone;
	private String ldwName;
	private String ldwPhone;
	private String lzhiWei;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public Contacts(){}
	
	public Contacts(ContactsDto contactsDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(contactsDto, this);
	}
}
