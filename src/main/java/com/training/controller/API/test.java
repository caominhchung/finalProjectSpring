package com.training.controller.API;

public class test {



    public static void main(String[] args) {

        String s = new String("hu");
        test te = new test();
        te.changeValue(s);
        System.out.println(s);
    }

    public void changeValue(String s){
        s = new String("ki");
    }
}
