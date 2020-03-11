package per.lee.bravo.bsonapi.strategy;


import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.strategy.entry.GenerateBsonApiDataFieldStrategyEntryEnum;
import java.util.List;

/**
 * 生成bson-api的data字段的策略接口
 */
public interface GenerateBsonApiDataFieldStrategy {

    /**
     * 策略入口，该策略入口方法返回一个枚举，枚举中的值是一个接受一个控制层返回 {@link Object 的类型参数}且判断该参数是否是指定类的实现类
     * 的{@link GenerateBsonApiDataFieldStrategyEntryEnum lambda表达式 }
     * @return {@link GenerateBsonApiDataFieldStrategyEntryEnum lambda表达式}
     */
    GenerateBsonApiDataFieldStrategyEntryEnum getEntry();

    /**
     * 当策略入口处的校验通过，使用此方法执行策略。
     * @param data  控制层的返回参数
     * @return bson-api的data字段的值
     */
    <T> List<DataItem> execute(T data);

}
