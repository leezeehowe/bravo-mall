package per.lee.bravo.mall.authorization.restful.strategy.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.lee.bravo.mall.authorization.restful.body.data.DataItem;
import per.lee.bravo.mall.authorization.restful.strategy.GenerateBsonApiBodyStrategy;

import java.util.List;

@Service
public class GenerateBsonApiBodyStrategyContext {

    @Autowired
    List<GenerateBsonApiBodyStrategy> strategyList;

    public List<DataItem> doSuccess(Object o) {
        for (GenerateBsonApiBodyStrategy strategy : strategyList) {
            if(strategy.getEntry().test(o)) {
                return strategy.execute(o);
            }
        }
        return List.of(new DataItem());
    }

}
