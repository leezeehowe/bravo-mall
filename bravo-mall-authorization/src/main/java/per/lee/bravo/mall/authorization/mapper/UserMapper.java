package per.lee.bravo.mall.authorization.mapper;

import per.lee.bravo.mall.authorization.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表，权限服务内部维护的用户表，为了和外部服务解耦 Mapper 接口
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
public interface UserMapper extends BaseMapper<User> {

}
