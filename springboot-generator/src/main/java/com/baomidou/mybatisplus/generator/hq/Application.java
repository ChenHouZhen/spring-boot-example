package com.baomidou.mybatisplus.generator.hq;

public enum Application {
    VIEW(new String[]{"com.chenhz.view","springboot-view"}),
    BLOG(new String[]{"com.chenhz.blog","springboot-blog"});



    private String[] path;

    private Application(String[] path){
        this.path = path;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

}
