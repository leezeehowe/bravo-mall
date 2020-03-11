package per.lee.bravo.mall.authorization.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import per.lee.bravo.bsonapi.constant.enums.DbOperation;
import per.lee.bravo.bsonapi.exception.dao.DaoOperationAbstractException;
import per.lee.bravo.bsonapi.exception.dto.IllegalDtoParameterAbstractException;
import per.lee.bravo.mall.authorization.entity.User;
import per.lee.bravo.mall.authorization.mapper.UserMapper;
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
    public User getRequestingUser(boolean createIfAbsent) throws DaoOperationAbstractException, IllegalDtoParameterAbstractException {
        String externalId = getRequestingUserExternalId();
        return getOne(externalId, true);
    }

    @Override
    public User getOne(String externalId, boolean createIfAbsent) throws IllegalDtoParameterAbstractException, DaoOperationAbstractException {
        if(StrUtil.isEmptyOrUndefined(externalId)) {
            throw new IllegalDtoParameterAbstractException().withParameter("externalId");
        }
        User user = getOne(new QueryWrapper<User>().eq("external_id", externalId), false);
        if(user == null && createIfAbsent) {
            if(save(new User(externalId, 0L, 0L))) {
                return getOne(externalId, false);
            }
            throw new EntityCreateException(DbOperation.INSERT, User.class);
        }
        else if(user == null){
            throw new EntityNotFoundException(User.class).withParameter("externalId");
        }
        else {
            return user;
        }
    }
}
