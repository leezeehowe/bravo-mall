package per.lee.bravo.bsonapi.starter.configurer;

import cn.hutool.core.util.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import per.lee.bravo.bsonapi.starter.prop.MetaProperties;
import per.lee.bravo.bsonapi.strategy.GenerateBsonApiDataFieldStrategy;
import per.lee.bravo.bsonapi.strategy.StrategyHolder;
import per.lee.bravo.bsonapi.strategy.context.GenerateBsonApiFDataFieldStrategyContext;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
@EnableConfigurationProperties(MetaProperties.class)
public class BsonApiAutoConfiguration {

    private List<GenerateBsonApiDataFieldStrategy> strategyList = new ArrayList<>();

    public BsonApiAutoConfiguration() {
        generateBsonApiBodyStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(GenerateBsonApiFDataFieldStrategyContext.class)
    public GenerateBsonApiFDataFieldStrategyContext generateBsonApiBodyStrategyContext() {
        log.info("装配bean: {}", GenerateBsonApiFDataFieldStrategyContext.class.getSimpleName());
        GenerateBsonApiFDataFieldStrategyContext generateBsonApiFDataFieldStrategyContext = new GenerateBsonApiFDataFieldStrategyContext();
        generateBsonApiFDataFieldStrategyContext.setStrategyHolder(strategyHolder());
        return generateBsonApiFDataFieldStrategyContext;
    }

    @Bean
    @ConditionalOnMissingBean(StrategyHolder.class)
    public StrategyHolder strategyHolder() {
        return new StrategyHolder(strategyList);
    }

    private void generateBsonApiBodyStrategy() {
        for (Class<?> aClass : ClassUtil.scanPackage("per.lee.bravo.bsonapi.strategy.impl")) {
            try {
                GenerateBsonApiDataFieldStrategy generateBsonApiDataFieldStrategy = (GenerateBsonApiDataFieldStrategy) aClass.getDeclaredConstructor().newInstance();
                log.info("实例化该Bson-Api策略: {}", aClass.getSimpleName());
                strategyList.add(generateBsonApiDataFieldStrategy);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.info("无法实例化该Bson-Api策略：{}", aClass.getSimpleName());
            }
        }
    }

}
