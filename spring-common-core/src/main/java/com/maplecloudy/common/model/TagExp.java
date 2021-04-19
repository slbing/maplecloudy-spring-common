
package com.maplecloudy.common.model;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/*
 * pars from open api tag String to a atuh object,such as:
 * 
 * "客户管理/我的客户,scope:read,scope:write"
 * 
 * the tag String splite with ',' first is the resource tree path,others scopes
 */
public class TagExp {
  public String resourcePath;
  public List<String> scopes;
  
  public static TagExp getTagExp(String tag) {
    if (StringUtils.isBlank(tag)) return null;
    TagExp tagExp = new TagExp();
    String[] strs = tag.split(",");
    if (strs.length > 0) tagExp.resourcePath = strs[0].trim();
    if (strs.length > 1) {
      tagExp.scopes = Lists.newArrayListWithCapacity(strs.length - 1);
      for (int i = 1; i < strs.length; i++) {
        if (!StringUtils.isBlank(strs[i])) {
          tagExp.scopes.add(strs[i].trim());
        }
      }
    }
    return tagExp;
  }
  
}
