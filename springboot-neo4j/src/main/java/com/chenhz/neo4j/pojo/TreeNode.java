package com.chenhz.neo4j.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TreeNode {

    private String title;

    private List<TreeNode> nexts;

    public TreeNode(){
        if (nexts == null){
            nexts = new ArrayList<>();
        }
    }


    public void addChild(TreeNode n){
        nexts.add(n);
    }
}
