package com.baomidou.mybatisplus.generator.hq.blog;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.hq.Application;
import com.baomidou.mybatisplus.generator.hq.CodeGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CHZ
 * @create 2019/5/1
 */
public class BlogCodeGenerator extends CodeGenerator{

    public BlogCodeGenerator(Application app) {
        super(app);
    }

    @Override
    public Application getApp() {
        return Application.BLOG;
    }

    public static void main(String[] args) {
        BlogCodeGenerator blogCodeGenerator = new BlogCodeGenerator(Application.BLOG);
        blogCodeGenerator.generateByTables("sys_user");
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
