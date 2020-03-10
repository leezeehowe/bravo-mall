package per.lee.bravo.mall.authorization.restful.strategy.entry;

import per.lee.bravo.mall.authorization.restful.vo.BsonApiDataItem;

public enum GenerateBsonApiBodyEntryEnum {

    SINGLE((o ->
        o instanceof BsonApiDataItem
    )),
    Collection((o) ->
        o instanceof java.util.Collection
    );

    private StrategyEntry strategyEntry;

    GenerateBsonApiBodyEntryEnum(StrategyEntry strategyEntry) {
        this.strategyEntry = strategyEntry;
    }

    public boolean test(Object o) {
        return strategyEntry.isSon(o);
    }
}
