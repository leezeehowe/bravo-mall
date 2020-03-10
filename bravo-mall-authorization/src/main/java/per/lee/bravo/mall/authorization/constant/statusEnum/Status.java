package per.lee.bravo.mall.authorization.constant.statusEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.Setter;

public enum Status {

    EFFECTIVE(0, "有效的"),
    NONEFFECTIVE(1, "无效的"),
    UNDEFINED(-1, "未定义的");

    @Getter @Setter @EnumValue
    private int code;
    @Getter @Setter
    private String description;

    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public boolean equals(Status status) {
        if(status == null || status == this) return false;
        return status.code == this.code;
    }

    public static Status valueOf(int code) {
        for (Status status : Status.values()) {
            if(status.code == code) return status;
        }
        return Status.UNDEFINED;
    }
}
