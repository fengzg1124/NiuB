package cn.com.niub.domain;

import java.util.Arrays;

public class MenuWithBLOBs extends Menu {
    private byte[] iconPath;

    private byte[] smallIconPath;

    public byte[] getIconPath() {
        return iconPath;
    }

    public void setIconPath(byte[] iconPath) {
        this.iconPath = iconPath;
    }

    public byte[] getSmallIconPath() {
        return smallIconPath;
    }

    public void setSmallIconPath(byte[] smallIconPath) {
        this.smallIconPath = smallIconPath;
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
        MenuWithBLOBs other = (MenuWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
            && (this.getMenuUrl() == null ? other.getMenuUrl() == null : this.getMenuUrl().equals(other.getMenuUrl()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getMark() == null ? other.getMark() == null : this.getMark().equals(other.getMark()))
            && (this.getMenuDesc() == null ? other.getMenuDesc() == null : this.getMenuDesc().equals(other.getMenuDesc()))
            && (this.getMenuIndex() == null ? other.getMenuIndex() == null : this.getMenuIndex().equals(other.getMenuIndex()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (Arrays.equals(this.getIconPath(), other.getIconPath()))
            && (Arrays.equals(this.getSmallIconPath(), other.getSmallIconPath()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
        result = prime * result + ((getMenuUrl() == null) ? 0 : getMenuUrl().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getMark() == null) ? 0 : getMark().hashCode());
        result = prime * result + ((getMenuDesc() == null) ? 0 : getMenuDesc().hashCode());
        result = prime * result + ((getMenuIndex() == null) ? 0 : getMenuIndex().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + (Arrays.hashCode(getIconPath()));
        result = prime * result + (Arrays.hashCode(getSmallIconPath()));
        return result;
    }
}