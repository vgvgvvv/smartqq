package com.resetoter.smartqq.util.extension

import java.lang.reflect.Type


/**
 * Created by 35207 on 2017/5/8 0008.
 */
class StringEx {

    static extesion(){
        String.metaClass.getValue = { Type type ->
            doGetValue(delegate, type)
        }
    }

    static doGetValue(String str, Type type){
        if(type == Integer.class)
            Integer.parseInt(str)
        else if(type == Boolean.class)
            Boolean.parseBoolean(str)
        else if(type == Character.class)
            str.charAt(0)
        else if(type == Byte.class)
            Byte.valueOf(str)
        else if(type == Short.class)
            Short.parseShort(str)
        else if(type == Long.class)
            Long.parseLong(str)
        else if(type == Float.class)
            Float.parseFloat(str)
        else if(type == Double.class)
            Double.parseDouble(str)
        else if(type == String.class)
            str
    }

}
