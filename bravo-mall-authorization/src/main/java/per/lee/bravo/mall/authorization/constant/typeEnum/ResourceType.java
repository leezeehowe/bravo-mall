package per.lee.bravo.mall.authorization.constant.typeEnum;

import lombok.Getter;
import lombok.Setter;

public enum ResourceType {

    API(0, "API资源"),
    MENU(1, "菜单资源");

    @Getter @Setter
    private int code;
    @Getter @Setter
    private String des;

    ResourceType(int i, String s) {
        this.code = i;
        this.des = s;
    }
}
