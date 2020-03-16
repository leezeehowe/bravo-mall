import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class CodeGenerator {

    public static String DB_URL_TEMPLATE = "jdbc:mysql://localhost:3306/%s?serverTimezone=Asia/Shanghai";
    public static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    // 代码生成器模板的路径
    public static String TEMPLATE_PATH = "/templates/mapper.xml.ftl";
    // 如果模板引擎是 velocity
//    public static String TEMPLATE_PATH = "/templates/mapper.xml.vm";


    /**
     * 获取用户输入的数据库名
     * @return 数据库连接字符串
     */
    public static String getDbUrlFromScanner() {
        return String.format(DB_URL_TEMPLATE, scanner("数据库名"));
    }

    /**
     * 获取用户输入的数据库账户名
     * @return 账户名
     */
    public static String getDbUsernameFromScanner() {
        return scanner("数据库用户名");
    }

    /**
     * 获取用户输入的数据库账户名
     * @return 账户名
     */
    public static String getDbPwdFromScanner() {
        return scanner("数据库密码");
    }

    /**
     * 获取用户输入的数据库账户名
     * @return 账户名
     */
    public static String getAuthorNameFromScanner() {
        return scanner("请输入你的昵称(用于Java类的@author)");
    }

    public static boolean getFileOverrideChoiceFromScanner() {
        String res = scanner("是否覆盖已有文件，Y/N or y/n");
        if("y".equals(res) || "Y".equals(res)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "\\" + scanner("请输入Maven模块名");
        // 生成文件的输出文件夹
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(getAuthorNameFromScanner());
        gc.setOpen(false);
        gc.setFileOverride(getFileOverrideChoiceFromScanner());
        // gc.setSwagger2(true); 实体属性 Swagger2 注解

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(getDbUrlFromScanner());
        // dsc.setSchemaName("public");
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(getDbUsernameFromScanner());
        dsc.setPassword(getDbPwdFromScanner());

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName();
        pc.setParent(scanner("请输入包名"));
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = TEMPLATE_PATH;
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(scanner("请输入实体类父类的完整路径（无：输入 $ ）"));
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass(scanner("请输入控制器类父类的完整路径（无：输入 $）"));
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setExclude(scanner("请输入不需要生成代码的表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(pc.getModuleName() + "_");


        cfg.setFileOutConfigList(focList);
        mpg.setTemplate(templateConfig);
        mpg.setCfg(cfg);
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(tip);
        String input = "";
        if (scanner.hasNext()) {
            input = scanner.next();
            if("$".equals(input)) input = "";
            return input;
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
