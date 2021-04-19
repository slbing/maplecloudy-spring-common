package com.maplecloudy.common.model;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResourceTree {
  public final static String resourceDefine =
      "[\n" + "    {\n" + "        \"name\": \"首页\",\n"
          + "        \"enName\": \"WORKBENCH\",\n"
          + "        \"config\": false,\n" + "        \"auth\": true\n"
          + "    },\n" + "    {\n" + "        \"name\": \"客户管理\",\n"
          + "        \"enName\": \"CUSTOMERS\",\n"
          + "        \"config\": true,\n" + "        \"auth\": true,\n"
          + "        \"childs\": [\n" + "            {\n"
          + "                \"name\": \"我的客户\",\n"
          + "                \"enName\": \"MYCUSTOMERS\",\n"
          + "                \"config\": false,\n"
          + "                \"auth\": true\n" + "            },\n"
          + "            {\n" + "                \"name\": \"客户列表\",\n"
          + "                \"enName\": \"CUSTOMERSLIST\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"客户公海\",\n"
          + "                \"enName\": \"SEAS\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"企业\",\n"
          + "                \"enName\": \"ENTERPRISES\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"商机\",\n"
          + "                \"enName\": \"BUSINESSES\",\n"
          + "                \"config\": false,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"客户标签\",\n"
          + "                \"enName\": \"TAGS\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            }\n"
          + "        ]\n" + "    },\n" + "    {\n"
          + "        \"name\": \"营销中心\",\n"
          + "        \"enName\": \"MARKETING\",\n"
          + "        \"config\": true,\n" + "        \"auth\": false,\n"
          + "        \"childs\": [\n" + "            {\n"
          + "                \"name\": \"素材库\",\n"
          + "                \"enName\": \"MATERIAL\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"企业话术库\",\n"
          + "                \"enName\": \"DIALOGUE\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"朋友圈素材\",\n"
          + "                \"enName\": \"WECHATMATERIAL\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"渠道管理\",\n"
          + "                \"enName\": \"CHANNEL\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"渠道欢迎语\",\n"
          + "                \"enName\": \"WELCOME\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"渠道活码\",\n"
          + "                \"enName\": \"QRCODE\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"群发助手\",\n"
          + "                \"enName\": \"GROUPSEND\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            }\n"
          + "        ]\n" + "    },\n" + "    {\n"
          + "        \"name\": \"员工管理\",\n"
          + "        \"enName\": \"EMPLOYEELIST\",\n"
          + "        \"config\": true,\n" + "        \"auth\": false,\n"
          + "        \"childs\": [\n" + "            {\n"
          + "                \"name\": \"员工管理\",\n"
          + "                \"enName\": \"EMPLOYEELIST\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            },\n"
          + "            {\n" + "                \"name\": \"角色管理\",\n"
          + "                \"enName\": \"ROLES\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            }\n"
          + "        ]\n" + "    },\n" + "    {\n"
          + "        \"name\": \"系统设置\",\n"
          + "        \"enName\": \"SETTINGS\",\n"
          + "        \"config\": true,\n" + "        \"auth\": false,\n"
          + "        \"childs\": [\n" + "            {\n"
          + "                \"name\": \"客户设置\",\n"
          + "                \"enName\": \"CUSTOMERSETTINGS\",\n"
          + "                \"config\": true,\n"
          + "                \"auth\": false\n" + "            }\n"
          + "        ]\n" + "    }\n" + "]";
  @Schema(description = "资源名")
  public String name;
  @Schema(description = "资源名enName")
  public String enName;
  public List<Scope> scope;
  @Schema(description = "是否可支持配置")
  public boolean config = true;
  
  @Schema(description = "角色是否拥有这个资源")
  public boolean auth = false;
  
  public List<ResourceTree> childs;
  
  public static TypeReference<List<ResourceTree>> type = new TypeReference<List<ResourceTree>>() {};
  
  public static List<ResourceTree> getResourceTree(ObjectMapper objectMapper,
      String resources) throws JsonMappingException, JsonProcessingException {
    if(Objects.isNull(resources)){return Collections.emptyList();}
    return objectMapper.readValue(resources, type);
  }
  
//  public static List<ResourceVo> getResourceVo(List<ResourceTree> resourceTree,
//      String path) {
//    
//    if (CollectionUtils.isEmpty(resourceTree)) {
//      return Collections.emptyList();
//    }
//    List<ResourceVo> rvs = Lists.newArrayList();
//    // TODO
//    for (ResourceTree rt : resourceTree) {
//      if (rt.auth) {
//        ResourceVo rv = new ResourceVo();
//        if (StringUtils.isBlank(path)) {
//          rv.resource = rt.name;
//        } else {
//          rv.resource = path + "/" + rt.name;
//        }
//        if (!CollectionUtils.isEmpty(rt.scope)) {
//          rt.scope.forEach(value -> {
//            if (value.auth) {
//              rv.scope.add(value.scope);
//            }
//          });
//        }
//        rvs.add(rv);
//      } else if (!CollectionUtils.isEmpty(rt.childs)) {
//        rvs.addAll(getResourceVo(rt.childs, rt.name));
//      }
//    }
//    return rvs;
//  }
  
  public static void main(String[] args)
      throws JsonMappingException, JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    om.setSerializationInclusion(Include.NON_EMPTY);
    om.setSerializationInclusion(Include.NON_NULL);
    
    List<ResourceTree> rt = om.readValue(resourceDefine,
        new TypeReference<List<ResourceTree>>() {});
    System.out.println(om.writeValueAsString(rt));
  }
}
