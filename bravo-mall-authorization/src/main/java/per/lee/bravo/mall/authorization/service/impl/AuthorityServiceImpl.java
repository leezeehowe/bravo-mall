package per.lee.bravo.mall.authorization.service.impl;

import per.lee.bravo.mall.authorization.entity.Authority;
import per.lee.bravo.mall.authorization.mapper.AuthorityMapper;
import per.lee.bravo.mall.authorization.service.IAuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表，由角色和资源之间的映射 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements IAuthorityService {

}
