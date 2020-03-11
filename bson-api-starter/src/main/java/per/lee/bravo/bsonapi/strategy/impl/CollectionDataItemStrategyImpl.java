package per.lee.bravo.bsonapi.strategy.impl;

import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.strategy.GenerateBsonApiDataFieldStrategy;
import per.lee.bravo.bsonapi.strategy.entry.GenerateBsonApiDataFieldStrategyEntryEnum;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionDataItemStrategyImpl implements GenerateBsonApiDataFieldStrategy {

    private SingleDataItemStrategyImpl singleStrategy = new SingleDataItemStrategyImpl();

    @Override
    public GenerateBsonApiDataFieldStrategyEntryEnum getEntry() {
        return GenerateBsonApiDataFieldStrategyEntryEnum.Collection;
    }

    @Override
    public <T> List<DataItem> execute(T data) {
        List<DataItem> dataItemList = new ArrayList<>();
        // 这里直接强制类型转换，因为策略入口处已经做了判定
        Collection<Object> collection = (Collection) data;
        for (Object o : collection) {
            if(singleStrategy.getEntry().test(o)) {
                dataItemList.add(singleStrategy.execute(o).get(0));
            }
        }
        return dataItemList;
    }
}
