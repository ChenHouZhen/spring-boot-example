package com.chenhz.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable{

    private String phone;

    private String name;

    private int age;
}
