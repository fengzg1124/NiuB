package cn.com.niub.dto;

import org.springframework.beans.BeanUtils;

import cn.com.niub.domain.RoleUser;

public class RoleUserDto {
    private String roleid;

    private String userid;

    public RoleUserDto() {
    }
    public RoleUserDto(RoleUser roleUser) {
    	//BeanUtils是org.springframework.beans.BeanUtils，前给后
    	//BeanUtils是org.apache.commons.beanutils.BeanUtils，后给前
    	BeanUtils.copyProperties(roleUser, this);
    }
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RoleUserDto other = (RoleUserDto) that;
        return (this.getRoleid() == null ? other.getRoleid() == null : this.getRoleid().equals(other.getRoleid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleid() == null) ? 0 : getRoleid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        return result;
    }
}