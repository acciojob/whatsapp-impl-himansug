package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
   // private HashSet<String> userMobile;
    private HashMap<String,User> userData;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        //this.userMobile = new HashSet<>();
        this.userData= new HashMap<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public boolean isNewUser(String mobile){
        if(userData.containsKey(mobile)) return false;
        return true;
    }
    public void createUser(String name, String mobile) {
        userData.put(mobile,new User(name,mobile));
    }

    public Group createGroup(List<User> users){
        if(users.size()==2)
            return this.createPersonalChat(users);

        this.customGroupCount++;
        String groupName="Group "+this.customGroupCount;
        Group group= new Group(groupName,users.size());
        groupUserMap.put(group,users);
        adminMap.put(group,users.get(0));
        return group;
    }

    public Group createPersonalChat(List<User> users){
        String groupName=users.get(1).getName();
        Group personalGroup= new Group(groupName,2);
        groupUserMap.put(personalGroup,users);
        return personalGroup;
    }
    public int createMessage(String content){
        this.messageId++;
        Message message= new Message(messageId,content);
        return this.messageId;
    }
}
