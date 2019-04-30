package com.chenhz.view.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menus {

    public Menus(){
        chinds = new ArrayList<>();
    }

    private Integer id;

    private String name;

    private List<Menus> chinds;
}
