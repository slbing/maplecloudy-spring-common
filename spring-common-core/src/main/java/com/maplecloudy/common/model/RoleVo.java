package com.maplecloudy.common.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

public class RoleVo {
  
  @Schema(description = "角色", required = true)
  private String name;
  
  private String corpId;

  private Date createTime = new Date();// '创建时间'
  
  @Schema(description = "资源范围,表示当前角色拥有资源权限的Json", required = true)
  private List<ResourceTree> resources;
  
  @Schema(description = "是否管理员", required = true)
  private boolean admin;
  
  @Schema(description = "数据范围", required = true)
  private DataType dataType;
  
  private boolean del = false;
  
  public boolean isDel() {
    return del;
  }
  
  public void setDel(boolean del) {
    this.del = del;
  }
  
  public boolean isAdmin() {
    return admin;
  }
  
  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
  
  public DataType getDataType() {
    return dataType;
  }
  
  public void setDataType(DataType dataType) {
    this.dataType = dataType;
  }
  
  public List<ResourceTree> getResources() {
    return resources;
  }
  
  public void setResources(List<ResourceTree> resources) {
    this.resources = resources;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getCorpId() {
    return corpId;
  }
  
  public void setCorpId(String corpId) {
    this.corpId = corpId;
  }
  
  public Date getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public enum DataType {
    Self, SelfDept, SelfDeptAndChildDept, All
  }
}
