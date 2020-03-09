package per.lee.bravo.mall.authorization.service.impl;

import per.lee.bravo.mall.authorization.entity.ApiResource;
import per.lee.bravo.mall.authorization.mapper.ApiResourceMapper;
import per.lee.bravo.mall.authorization.service.IApiResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 接口资源表 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class ApiResourceServiceImpl extends ServiceImpl<ApiResourceMapper, ApiResource> implements IApiResourceService {

}
