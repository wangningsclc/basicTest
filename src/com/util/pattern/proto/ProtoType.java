package com.util.pattern.proto;

/**
 * Created by wn on 2018/2/11.
 */
public class ProtoType implements Cloneable {
    public ProtoType Clone(){
        ProtoType protoType = null;
        try {
            protoType = (ProtoType)super.clone();
            return protoType;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return protoType;
    }
}
