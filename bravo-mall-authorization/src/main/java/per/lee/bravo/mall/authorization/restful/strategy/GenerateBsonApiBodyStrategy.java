package per.lee.bravo.mall.authorization.restful.strategy;

import per.lee.bravo.mall.authorization.restful.body.data.DataItem;
import per.lee.bravo.mall.authorization.restful.strategy.entry.GenerateBsonApiBodyEntryEnum;

import java.util.List;

public interface GenerateBsonApiBodyStrategy {

    GenerateBsonApiBodyEntryEnum getEntry();

    <T> List<DataItem> execute(T data);

}
