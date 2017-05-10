package com.resetoter.smartqq.client

/**
 * Created by 35207 on 2017/5/10 0010.
 */
class SmartQQClientEx {

    static extension(){
        SmartQQClient.metaClass.getGroup = {
            long groupId ->
                SmartQQClient client = (SmartQQClient)delegate
                client.getGroupInfo(groupId)
        }
    }

}
