package cn.com.niub.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.Supplementary;
import lombok.Data;

//补充材料
@Data
public class SupplementaryDto {

	private String id;
	private String orderId;
	
	private String bjingYingType;
	private String bgongShangHao;
	private String bchangSuo;
	private String byueZu;
	private String bchengLiDate;
	private String bshuiZhengNumber;
	private String brenShu;
	private String bnianYingLi;
	
	private String creater;
	private Date createTime;
	private String updater;
	private Date updateTime;
	
	public SupplementaryDto(){}
	
	public SupplementaryDto(Supplementary supplementary){
		//BeanUtils是org.springframework.beans.BeanUtils，前给后
		//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
		BeanUtils.copyProperties(supplementary, this);
	}
}
