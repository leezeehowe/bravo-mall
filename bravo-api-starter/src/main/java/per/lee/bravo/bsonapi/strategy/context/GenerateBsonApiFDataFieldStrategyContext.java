package per.lee.bravo.bsonapi.strategy.context;

import lombok.Setter;
import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.strategy.GenerateBsonApiDataFieldStrategy;
import per.lee.bravo.bsonapi.strategy.StrategyHolder;


import java.util.List;

public class GenerateBsonApiFDataFieldStrategyContext {

    @Setter
    private StrategyHolder strategyHolder;

    public List<DataItem> generateDataField(Object o) {
        for (GenerateBsonApiDataFieldStrategy strategy : strategyHolder.getStrategyList()) {
            if(strategy.getEntry().test(o)) {
                return strategy.execute(o);
            }
        }
        return List.of(new DataItem());
    }

}
