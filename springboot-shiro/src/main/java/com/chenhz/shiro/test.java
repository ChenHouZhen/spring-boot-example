package com.chenhz.shiro;

import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
        int[] myArray = new int[] {1, 2, 3};
        List myList = Arrays.asList(new Integer[]{1, 2, 3});
        System.out.println(myList.size());
        System.out.println(myList.get(0));
        System.out.println(myList.get(1));
//        int [] array=(int[]) myList.get(0);
//        System.out.println(array[0]);
    }
}
