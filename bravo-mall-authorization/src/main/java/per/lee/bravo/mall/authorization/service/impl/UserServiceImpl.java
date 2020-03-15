package per.lee.bravo.mall.authorization.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import per.lee.bravo.mall.authorization.constant.operationError.OperationErrorEnum;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.mapper.UserMapper;
import per.lee.bravo.mall.authorization.restful.protocol.BravoApiException;
import per.lee.bravo.mall.authorization.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表，权限服务内部维护的用户表，为了和外部服务解耦 服务实现类
 * </p>
 *
 * @author leezeehowe
 * @since 2020-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Value("${token.uuidKey}")
    private String UUID_KEY;

    @Autowired
    private HttpServletRequest request;


    public String getRequestingUserExternalId() {
        return request.getHeader(UUID_KEY);
    }

    @Override
    public User getRequestingUser(boolean createIfAbsent) throws BravoApiException {
        String externalId = getRequestingUserExternalId();
        return getOne(externalId, true);
    }

    @Override
    public User getOne(String externalId, boolean createIfAbsent) throws BravoApiException {
        if(StrUtil.isEmptyOrUndefined(externalId)) {
            throw new BravoApiException(OperationErrorEnum.ILLEGAL_ARGUMENT, "账号ID不能为空，请登录后重试");
        }
        User user = getOne(new QueryWrapper<User>().eq("external_id", externalId), false);
        if(user == null && createIfAbsent) {
            if(save(new User(externalId, 0L, 0L))) {
                return getOne(externalId, false);
            }
            throw new BravoApiException(OperationErrorEnum.CREATE_ENTITY_FAIL, "创建账号失败，请重试");
        }
        else if(user == null){
            throw new BravoApiException(OperationErrorEnum.ABSENT_ENTITY_ERROR, "不存在该账号，请检查");
        }
        else {
            return user;
        }
    }
}
