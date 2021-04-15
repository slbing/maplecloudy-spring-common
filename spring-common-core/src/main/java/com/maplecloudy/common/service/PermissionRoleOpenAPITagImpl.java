package com.maplecloudy.common.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingheo.scrm.entity.Role;
import com.xingheo.scrm.entity.User;
import com.xingheo.scrm.model.auth.ResourceTree;
import com.xingheo.scrm.model.auth.RoleVo;
import com.xingheo.scrm.repo.RoleRepo;
import com.xingheo.scrm.repo.UserRepo;
import com.xingheo.scrm.service.RoleService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import io.swagger.v3.oas.models.Paths;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.webmvc.api.OpenApiWebMvcResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springdoc.core.Constants.API_DOCS_URL;

@Service("permissionService")
public class PermissionRoleOpenAPITagImpl {
  @Autowired
  RoleRepo roleRepo;

  @Autowired
  UserRepo userRepo;
  @Autowired
  OpenAPI customOpenAPI;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  OpenApiWebMvcResource openApiWebMvcResource;
  @Value(API_DOCS_URL)
  String apiDocsUrl;
  @Autowired
  private RoleService roleService;

  public boolean hasPermission(HttpServletRequest request,
      Authentication authentication) throws JsonProcessingException {
    Object principal = authentication.getPrincipal();
    String username = null;
    if (principal instanceof Jwt) {
      username = ((Jwt) principal).getClaimAsString("user_name");
    } else {
      return false;
    }
    if (Objects.isNull(authentication) || StringUtils.isAllBlank(username)) {
      return true;
    }
    Optional<User> user = userRepo.findById(username);
    if (!user.isPresent()) {
      return false;
    }
    request.setAttribute("user", user.get());
    Optional<Role> role = roleRepo
        .findByIdAndDelAndCorpId(user.get().getRoleId(), false,
            user.get().getCorpId());
    List<ResourceTree> resources = Collections.emptyList();
    if (role.isPresent()) {

      RoleVo roleVo = new RoleVo();
      roleVo.setAdmin(role.get().isAdmin());
      roleVo.setName(role.get().getName());
      roleVo.setDataType(role.get().getDataType());
      if (!StringUtils.isBlank(role.get().getResources())) {
        roleVo.setResources(ResourceTree
            .getResourceTree(objectMapper, role.get().getResources()));
      } else {
        roleVo.setResources(roleService.getDefaultResourceTree());
      }
      request.setAttribute("role", roleVo);
      resources = roleVo.getResources();
    }
    openApiWebMvcResource.openapiJson(request, apiDocsUrl);
    Paths paths = customOpenAPI.getPaths();

    if (paths == null) {
      return true;
    }
    String path = request.getServletPath();

    if (StringUtils.isBlank(path)) return true;
    PathItem pathItem = paths.get(path);
    if (ObjectUtils.isEmpty(pathItem)) {
      return true;
    }

    Operation operation = pathItem.readOperationsMap()
        .get(HttpMethod.valueOf(request.getMethod()));
    if (operation == null || CollectionUtils.isEmpty(operation.getTags())) {
      return true;
    } else if (CollectionUtils.isEmpty(resources)) {
      return false;
    }
    String tag = operation.getTags().get(0);
    String[] tagPaths = tag.split("/");
    for (String tagPath : tagPaths) {
      List<ResourceTree> matchRes = resources.stream()
          .filter(value -> value.name.equals(tagPath))
          .collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(matchRes)) {
        if (matchRes.get(0).auth) {
          return true;
        } else {
          if (CollectionUtils.isEmpty(matchRes.get(0).childs)) {
            return false;
          }
          resources = matchRes.get(0).childs;
        }
      } else {
        return false;
      }
    }
    return false;
    //    // 先判断tag是否与拥有的资源权限匹配
    //    for (ResourceVo rv : rvs) {
    //
    //        TagExp te = TagExp.getTagExp(tag);
    //        if (!StringUtils.
    //            isBlank(te.resourcePath)) {
    //          if (te.resourcePath.startsWith(rv.resource) || te.resourcePath
    //              .equals(rv.resource)) {
    //            // 当scope为空的情况下，只验证resource
    //            if (CollectionUtils.isEmpty(te.scopes) || CollectionUtils
    //                .isEmpty(rv.scope)) {
    //              return true;
    //            }
    //            // 判断scope是否匹配
    //            if (CollectionUtils.containsAny(te.scopes, rv.scope)) {
    //              return true;
    //            }
    //
    //          }
    //        }
    //      }
    //    }return false;
  }

}
