package per.lee.bravo.mall.usercenter.convert;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import per.lee.bravo.mall.usercenter.constant.entityStatus.UserGenderEnum;

import java.util.Optional;

public class UserGenderEnumConvert implements Converter<String, UserGenderEnum> {

    @Override
    public UserGenderEnum convert(String source) {
        return Optional
                .ofNullable(UserGenderEnum.valueOf(Integer.parseInt(source)))
                .orElseThrow(() -> new IllegalArgumentException(StrUtil.format("{}不是可接受的选项", source)));
    }

}
