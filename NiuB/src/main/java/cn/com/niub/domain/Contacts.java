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

import cn.com.niub.dto.ContactsDto;
import lombok.Data;

//联系人
@Entity
@Data
@Table(name="TBL_CONTACTS")
@EntityListeners(AuditingEntityListener.class)
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
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	private String updater;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	public Contacts(){}
	
	public Contacts(ContactsDto contactsDto){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(contactsDto, this);
	}
}
