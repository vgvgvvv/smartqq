package com.resetoter.smartqq.facade;

import com.resetoter.smartqq.callback.MessageCallback;
import com.resetoter.smartqq.model.*;
import com.resetoter.smartqq.client.SmartQQClient;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 消息接收器 - Receiver
 * （经小规模测试可用，但不保证可用性）
 *
 * @author Dilant
 * @date 2017/3/19
 */
public class Receiver {

    static Logger logger = Logger.getLogger(Receiver.class);

    private List<Friend> friendList = new ArrayList<Friend>();                 //好友列表
    private List<Group> groupList = new ArrayList<Group>();                   //群列表
    private List<Discuss> discussList = new ArrayList<Discuss>();               //讨论组列表
    private Map<Long, Friend> friendFromID = new HashMap<Long, Friend>();            //好友id到好友映射
    private Map<Long, Group> groupFromID = new HashMap<Long, Group>();              //群id到群映射
    private Map<Long, GroupInfo> groupInfoFromID = new HashMap<Long, GroupInfo>();      //群id到群详情映射
    private Map<Long, Discuss> discussFromID = new HashMap<Long, Discuss>();          //讨论组id到讨论组映射
    private Map<Long, DiscussInfo> discussInfoFromID = new HashMap<Long, DiscussInfo>();  //讨论组id到讨论组详情映射

    private boolean working;
    private SmartQQClient client;
    private Date lastMapTime = null;

    public Receiver(SmartQQClient client){
        this.client = client;
    }

    /**
     * 是否正在工作
     * @return
     */
    public boolean isWorking(){
        return working;
    }


    /**
     * 获取本地系统时间
     *
     * @return 本地系统时间
     */
    public String getTime() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return time.format(new Date());
    }

    /**
     * 获取群id对应群详情
     *
     * @param id 被查询的群id
     * @return 该群详情
     */
    public GroupInfo getGroupInfoFromID(Long id) {
        if (!groupInfoFromID.containsKey(id)) {
            groupInfoFromID.put(id, client.getGroupInfo(groupFromID.get(id).getCode()));
        }
        return groupInfoFromID.get(id);
    }

    /**
     * 获取讨论组id对应讨论组详情
     *
     * @param id 被查询的讨论组id
     * @return 该讨论组详情
     */
    public DiscussInfo getDiscussInfoFromID(Long id) {
        if (!discussInfoFromID.containsKey(id)) {
            discussInfoFromID.put(id, client.getDiscussInfo(discussFromID.get(id).getId()));
        }
        return discussInfoFromID.get(id);
    }

    /**
     * 获取群消息所在群名称
     *
     * @param msg 被查询的群消息
     * @return 该消息所在群名称
     */
    public String getGroupName(GroupMessage msg) {
        return getGroup(msg).getName();
    }

    /**
     * 获取讨论组消息所在讨论组名称
     *
     * @param msg 被查询的讨论组消息
     * @return 该消息所在讨论组名称
     */
    public String getDiscussName(DiscussMessage msg) {
        return getDiscuss(msg).getName();
    }

    /**
     * 获取群消息所在群
     *
     * @param msg 被查询的群消息
     * @return 该消息所在群
     */
    public Group getGroup(GroupMessage msg) {
        return groupFromID.get(msg.getGroupId());
    }

    /**
     * 获取讨论组消息所在讨论组
     *
     * @param msg 被查询的讨论组消息
     * @return 该消息所在讨论组
     */
    private Discuss getDiscuss(DiscussMessage msg) {
        return discussFromID.get(msg.getDiscussId());
    }

    /**
     * 获取私聊消息发送者昵称
     *
     * @param msg 被查询的私聊消息
     * @return 该消息发送者
     */
    public String getFriendNick(Message msg) {
        Friend user = friendFromID.get(msg.getUserId());
        if (user.getMarkname() == null || user.getMarkname().equals("")) {
            return user.getNickname(); //若发送者无备注则返回其昵称
        } else {
            return user.getMarkname(); //否则返回其备注
        }

    }

    /**
     * 获取群消息发送者昵称
     *
     * @param msg 被查询的群消息
     * @return 该消息发送者昵称
     */
    public String getGroupUserNick(GroupMessage msg) {
        for (GroupUser user : getGroupInfoFromID(msg.getGroupId()).getUsers()) {
            if (user.getUin() == msg.getUserId()) {
                if (user.getCard() == null || user.getCard().equals("")) {
                    return user.getNick(); //若发送者无群名片则返回其昵称
                } else {
                    return user.getCard(); //否则返回其群名片
                }
            }
        }
        return "系统消息"; //若在群成员列表中查询不到，则为系统消息
        //TODO: 也有可能是新加群的用户或匿名用户
    }

    /**
     * 获取讨论组消息发送者昵称
     *
     * @param msg 被查询的讨论组消息
     * @return 该消息发送者昵称
     */
    public String getDiscussUserNick(DiscussMessage msg) {
        for (DiscussUser user : getDiscussInfoFromID(msg.getDiscussId()).getUsers()) {
            if (user.getUin() == msg.getUserId()) {
                return user.getNick(); //返回发送者昵称
            }
        }
        return "系统消息"; //若在讨论组成员列表中查询不到，则为系统消息
        //TODO: 也有可能是新加讨论组的用户
    }

    public void mapping(){
        try{
            if(lastMapTime != null && (new Date().getTime() - lastMapTime.getTime())/1000/60 < 5)
                return;

            working = true;                                    //映射建立完毕前暂停工作以避免NullPointerException
            friendList = client.getFriendList();                //获取好友列表
            groupList = client.getGroupList();                  //获取群列表
            discussList = client.getDiscussList();              //获取讨论组列表
            for (Friend friend : friendList) {                  //建立好友id到好友映射
                friendFromID.put(friend.getUserId(), friend);
            }
            for (Group group : groupList) {                     //建立群id到群映射
                groupFromID.put(group.getId(), group);
            }
            for (Discuss discuss : discussList) {               //建立讨论组id到讨论组映射
                discussFromID.put(discuss.getId(), discuss);
            }
            working = false;                                     //映射建立完毕后恢复工作
            lastMapTime = new Date();
            //为防止请求过多导致服务器启动自我保护
            //群id到群详情映射 和 讨论组id到讨论组详情映射 将在第一次请求时创建
            //TODO: 可考虑在出现第一条讨论组消息时再建立相关映射，以防Api错误返回
            logger.info("映射完成");
        }catch (Exception e){
            logger.error(e.getMessage(), e);
            working = false;
        }

    }

}