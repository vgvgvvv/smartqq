package com.scienjus.smartqq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scienjus.smartqq.Util.HttpHelper;
import com.scienjus.smartqq.callback.MessageCallback;
import com.scienjus.smartqq.client.SmartQQClient;
import com.scienjus.smartqq.model.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author ScienJus
 * @date 2015/12/18.
 */
public class Application {

    private static SmartQQClient client;
    public static void main(String[] args){


        MessageCallback callback = new MessageCallback() {
            @Override
            public void onMessage(Message message) {
                System.out.println("onMessage：" + message.getContent());
                Response(client, message);
            }

            @Override
            public void onGroupMessage(GroupMessage message) {
                System.out.println("onGroupMessage：" + message.getContent());
                ResponseGroup(client, message);
            }

            @Override
            public void onDiscussMessage(DiscussMessage message) {
                System.out.println("onDiscussMessage：" + message.getContent());
            }
        };

        client = new SmartQQClient(callback);

    }

    public static void ResponseGroup(SmartQQClient client, GroupMessage message){
        if(message.getContent().contains("爱酱")){
            JSONObject obj = new JSONObject();
            obj.put("key", "52d6ce0aaa40481097ae3fc1205587c4");
            obj.put("info", message.getContent());
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
    }

    public static void Response(SmartQQClient client, Message message){
        JSONObject obj = new JSONObject();
        obj.put("key", "52d6ce0aaa40481097ae3fc1205587c4");
        obj.put("info", message.getContent());
        obj.put("userid", message.getUserId());
        String post = null;
        try {
            post = new String(obj.toJSONString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String result = HttpHelper.sendPost("http://www.tuling123.com/openapi/api", post);


        client.sendMessageToFriend(message.getUserId(), handleResult(result));

    }

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
