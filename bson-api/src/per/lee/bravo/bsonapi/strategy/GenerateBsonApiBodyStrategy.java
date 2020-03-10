package per.lee.bravo.bsonapi.strategy;


import per.lee.bravo.bsonapi.body.data.DataItem;
import per.lee.bravo.bsonapi.strategy.entry.GenerateBsonApiBodyEntryEnum;

import java.util.List;

public interface GenerateBsonApiBodyStrategy {

    GenerateBsonApiBodyEntryEnum getEntry();

    <T> List<DataItem> execute(T data);

}
