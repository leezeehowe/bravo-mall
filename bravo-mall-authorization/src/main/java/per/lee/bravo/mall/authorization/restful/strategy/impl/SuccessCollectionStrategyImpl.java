package per.lee.bravo.mall.authorization.restful.strategy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import per.lee.bravo.mall.authorization.restful.body.data.DataItem;
import per.lee.bravo.mall.authorization.restful.strategy.GenerateBsonApiBodyStrategy;
import per.lee.bravo.mall.authorization.restful.strategy.entry.GenerateBsonApiBodyEntryEnum;
import per.lee.bravo.mall.authorization.restful.vo.BsonApiDataItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class SuccessCollectionStrategyImpl implements GenerateBsonApiBodyStrategy {

    @Autowired
    SuccessSingleStrategyImpl singleStrategy;

    @Override
    public GenerateBsonApiBodyEntryEnum getEntry() {
        return GenerateBsonApiBodyEntryEnum.Collection;
    }

    @Override
    public <T> List<DataItem> execute(T data) {
        List<DataItem> dataItemList = new ArrayList<>();
        // 这里直接强制类型转换，因为策略入口处已经做了判定
        Collection<BsonApiDataItem> collection = (Collection) data;
        for (BsonApiDataItem bsonApiDataItem : collection) {
            dataItemList.add(singleStrategy.execute(bsonApiDataItem).get(0));
        }
        return dataItemList;
    }
}
