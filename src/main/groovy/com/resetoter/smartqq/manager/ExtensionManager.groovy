package com.resetoter.smartqq.manager

import com.resetoter.battleqq.Entity.PlayerEx
import com.resetoter.smartqq.client.SmartQQClientEx
import com.resetoter.smartqq.util.extension.StringEx

/**
 * Created by 35207 on 2017/5/8 0008.
 */
class ExtensionManager {

    static applyExtesion(){
        StringEx.extesion()
        SmartQQClientEx.extension()
        PlayerEx.extension()
    }
}
