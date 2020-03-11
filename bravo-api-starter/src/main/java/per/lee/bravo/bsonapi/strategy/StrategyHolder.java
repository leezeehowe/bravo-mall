package per.lee.bravo.bsonapi.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class StrategyHolder {

    @Setter
    List<GenerateBsonApiDataFieldStrategy> strategyList;

}
