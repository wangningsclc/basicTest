package com.util.pattern.proto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wn on 2018/2/11.
 */
public class ConcreateProtoType extends ProtoType {
    private String head;
    private String body;
    private String feet;
    BigDecimal weight;
    List<String> list;
    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFeet() {
        return feet;
    }

    public void setFeet(String feet) {
        this.feet = feet;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void show(){
        System.out.println(String.format("%s...%s...%s...", head, body, feet)+weight+list);
    }

    public ConcreateProtoType(String head, String body, String feet) {
        this.head = head;
        this.body = body;
        this.feet = feet;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ConcreateProtoType concreateProtoType = new ConcreateProtoType("h","b","f");
        concreateProtoType.setWeight(new BigDecimal("2.343"));
        concreateProtoType.setList(Arrays.asList(new String[]{"c", "d", "e"}));
        for(int i=0;i<10;i++){
            ConcreateProtoType c1= (ConcreateProtoType) concreateProtoType.clone();
            c1.show();
        }

    }
}
