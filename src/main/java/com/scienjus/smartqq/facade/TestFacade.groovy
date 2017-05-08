package com.scienjus.smartqq.facade

import com.scienjus.smartqq.manager.ExtensionManager

/**
 * Created by 35207 on 2017/5/8 0008.
 */
class TestFacade {

    static main(args){
        ExtensionManager.applyExtesion();
        println "123,123,123".getValue(List.class);
    }

}
