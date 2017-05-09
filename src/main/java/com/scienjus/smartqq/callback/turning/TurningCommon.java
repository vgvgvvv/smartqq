package com.scienjus.smartqq.callback.turning;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scienjus.smartqq.util.HttpHelper;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.GroupMessage;
import com.scienjus.smartqq.model.Message;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;

/**
 * Created by 35207 on 2017/5/8 0008.
 */
public class TurningCommon {

    private static boolean speaking = false;

    static Logger logger = Logger.getLogger(TurningCommon.class);

    /**
     * 回复群
     * @param client
     * @param message
     */
    public static void ResponseGroup(SmartQQClient client, GroupMessage message){
        if(message.getContent().equals("召唤爱酱！")){
            speaking = true;
            logger.info("爱酱来啦！");
            client.sendMessageToGroup(message.getGroupId(), "爱酱来啦！");
            return;
        }
        else if(message.getContent().equals("爱酱闭嘴！")){
            speaking = false;
            logger.info("哦！");
            client.sendMessageToGroup(message.getGroupId(), "哦！");
            return;
        }
        else if(speaking){
            String info = message.getContent();
            JSONObject obj = new JSONObject();
            obj.put("key", "52d6ce0aaa40481097ae3fc1205587c4");
            obj.put("info", info);
            obj.put("userid", message.getGroupId());
            String post = null;
            try {
                post = new String(obj.toJSONString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String result = HttpHelper.sendPost("http://www.tuling123.com/openapi/api", post);

            client.sendMessageToGroup(message.getGroupId(), handleResult(result));
        }
        else if(message.getContent().startsWith("爱酱")){
            if(message.getContent().equals("爱酱")){
                client.sendMessageToGroup(message.getGroupId(), "干哈？");
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("key", "52d6ce0aaa40481097ae3fc1205587c4");
            obj.put("info", message.getContent().substring(2));
            obj.put("userid", message.getGroupId());
            String post = null;
            try {
                post = new String(obj.toJSONString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String result = HttpHelper.sendPost("http://www.tuling123.com/openapi/api", post);

            String res = handleResult(result);
            logger.info(res);
            client.sendMessageToGroup(message.getGroupId(), res);
        }
    }

    /**
     * 回复个人
     * @param client
     * @param message
     */
    public static void Response(SmartQQClient client, Message message){
        String info = message.getContent();
        JSONObject obj = new JSONObject();
        obj.put("key", "52d6ce0aaa40481097ae3fc1205587c4");
        obj.put("info", info);
        obj.put("userid", message.getUserId());
        String post = null;
        try {
            post = new String(obj.toJSONString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e);
        }

        String result = HttpHelper.sendPost("http://www.tuling123.com/openapi/api", post);

        String res = handleResult(result);
        logger.info(res);
        client.sendMessageToFriend(message.getUserId(), res);

    }

    /**
     * 处理返回信息
     * @param res
     * @return
     */
    public static String handleResult(String res){
        JSONObject obj = JSONObject.parseObject(res);
        switch ((Integer) obj.get("code")){
            case 100000:{
                return (String)obj.get("text");
            }
            case 200000:{
                return (String)obj.get("text") + "\n" + (String)obj.get("url");
            }
            case 302000:{
                JSONArray list = obj.getJSONArray("list");
                return res;//(String)obj.get("text") + "\n";
            }
            case 308000:{
                return res;//(String)obj.get("text") + "\n" + (String)obj.get("url");
            }
            case 40001:{
                return "我他妈报错，快联系迪赛！";
            }
            case 40002:{
                return "奥比嗯侧后？";
            }
            case 40004:{
                return "次数用完了，再见！";
            }
            case 40007:{
                return "我他妈数据不对，快联系迪赛！";
            }
        }
        return res;
    }

}
