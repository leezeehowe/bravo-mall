package per.lee.bravo.mall.authorization.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import per.lee.bravo.mall.authorization.constant.typeEnum.ResourceType;
import per.lee.bravo.mall.authorization.dto.DeleteAuthorityDto;
import per.lee.bravo.mall.authorization.dto.PostAuthorityDto;
import per.lee.bravo.mall.authorization.entity.Authority;
import per.lee.bravo.mall.authorization.entity.WebpageResource;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IAuthorityService;
import per.lee.bravo.mall.authorization.service.IWebpageResourceService;
import per.lee.bravo.mall.authorization.tree.po.PayloadNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表，由角色和资源之间的映射 前端控制器
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@RestController
@RequestMapping("/${api.version}/authority")
public class AuthorityController {

    @Autowired
    IWebpageResourceService webpageResourceService;
    @Autowired
    IAuthorityService authorityService;

    /**
     * 给角色授权，包括页面权限和API权限
     * @param dto
     * @return
     * @throws BravoApiException
     */
    @PostMapping
    public JSONObject issueAuthority(@RequestBody PostAuthorityDto dto) throws BravoApiException {
        List<Long> webpageIdList = dto.getWebpage();
        List<Long> apiIdList = dto.getApi();
        JSONObject responseData = new JSONObject();
        if(webpageIdList != null && webpageIdList.size() > 0) {
            responseData.put("webpage", authorityService.issueAuthority(dto.getRoleId(), ResourceType.MENU.getCode(), webpageIdList));
        }
        if(apiIdList != null && apiIdList.size() > 0) {
            responseData.put("api", authorityService.issueAuthority(dto.getRoleId(), ResourceType.API.getCode(), apiIdList));
        }
        return responseData;
    }

    @DeleteMapping
    public JSONObject removeAuthority(@RequestBody DeleteAuthorityDto dto) {
        JSONObject jsonObject = new JSONObject();
        try{
            if(dto.getWebpage() != null && dto.getWebpage().size() > 0) {
                authorityService.deleteAuthority(dto.getRoleId(), ResourceType.MENU.getCode(), dto.getWebpage());
            }
        }
        catch (Exception e) {
            jsonObject.put("deleteWebpageAuthorityFail", "删除页面权限失败！" + e.getMessage());
        }
        try{
            if(dto.getApi() != null && dto.getApi().size() > 0) {
                authorityService.deleteAuthority(dto.getRoleId(), ResourceType.API.getCode(), dto.getApi());
            }
        }
        catch (Exception e) {
            jsonObject.put("deleteApiAuthorityFail", "删除API权限失败！" + e.getMessage());
        }
        return jsonObject;
    }


    @GetMapping("/webpage/router")
    public Set<String> getRouterAuthority() throws BravoApiException {
        // todo 根据角色获取页面权限
        return authorityService.getRouterAuthority();
    }

    @GetMapping()
    public Map<String, List<Authority>> getAuthorityOfSpecifiedRoleId(@RequestParam Long roleId) {
        // todo 根据指定的角色id，获取它的所拥有的权限： 页面资源id数组 和 api资源id数组
        List<Authority> webpageAuthority = authorityService.getRouterAuthorityOfSpecifiedRole(roleId);
        List<Authority> apiAuthority = authorityService.getApiAuthorityOfSpecifiedRole(roleId);
        return Map.of("webpage", webpageAuthority, "api", apiAuthority);
    }
}
