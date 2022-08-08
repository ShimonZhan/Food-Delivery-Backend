package uk.ac.soton.food_delivery;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.Collections;
import java.util.List;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-07 03:10:40
 */
@SuppressWarnings("deprecation")
public class DataBaseCodeGenerator {
    public static void main(String[] args) {
        List<String> tables = Collections.singletonList("role");

        FastAutoGenerator.create("jdbc:mysql://****:3306/food-delivery-dev?serverTimezone=GMT%2B0",
                        "****", "****")
                .globalConfig(builder -> {
                    builder.author("ShimonZhan")               //作者
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")    //输出路径(写到java目录)
                            .enableSwagger()           //开启swagger
                            .commentDate("yyyy-MM-dd")
                            .fileOverride();            //开启覆盖之前生成的文件

                })
                .packageConfig(builder -> builder.parent("uk.ac.soton")
                        .moduleName("food_delivery")
                        .entity("entity")
                        .service("service")
                        .serviceImpl("serviceImpl")
                        .controller("controller")
                        .mapper("mapper")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/xml")))
                .strategyConfig(builder -> builder.addInclude(tables)
//                            .addTablePrefix("p_")
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .entityBuilder()
                        .idType(IdType.AUTO)
                        .disableSerialVersionUID()
                        .addTableFills(
                                new Property("createTime", FieldFill.INSERT),
                                new Property("updateTime", FieldFill.INSERT_UPDATE))
                        .enableLombok()
                        .enableChainModel()
                        .logicDeleteColumnName("deleted")
                        .enableTableFieldAnnotation()
                        .controllerBuilder()
                        .formatFileName("%sController")
                        .enableRestStyle()
                        .mapperBuilder()
                        .enableBaseResultMap()  //生成通用的resultMap
                        .superClass(BaseMapper.class)
                        .formatMapperFileName("%sMapper")
                        .enableMapperAnnotation()
                        .formatXmlFileName("%sMapper"))
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
