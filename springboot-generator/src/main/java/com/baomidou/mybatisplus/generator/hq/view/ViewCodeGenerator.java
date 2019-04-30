package com.baomidou.mybatisplus.generator.hq.view;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.hq.Application;
import com.baomidou.mybatisplus.generator.hq.CodeGenerator;

import java.util.ArrayList;
import java.util.List;

public class ViewCodeGenerator extends CodeGenerator {

    @Override
    public Application getApp() {
        return Application.VIEW;
    }

    public ViewCodeGenerator(Application app){
        super(app);
    }

    public static void main(String[] args) {
        ViewCodeGenerator viewCodeGenerator = new ViewCodeGenerator(Application.VIEW);
        viewCodeGenerator.generateByTables("sys_menu");
    }

    @Override
    public PackageConfig getPackageConfig(Application app) {
        PackageConfig pc = new PackageConfig();
        pc.setParent(app.getPath()[0])
                .setEntity("domain")
                .setController("controller")
                .setService("service")
                .setServiceImpl("service.impl")
                .setMapper("dao");

        return pc;
    }

    @Override
    public InjectionConfig getInjectionConfig(){
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mappers/"
                        +tableInfo.getEntityName().replace("Entity","")+ "Mapper.xml";
            }
        });

        cfg.setFileOutConfigList(focList);

        return cfg;
    }
}
