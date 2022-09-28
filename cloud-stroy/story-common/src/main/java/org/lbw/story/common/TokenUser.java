package org.lbw.story.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 令牌中存放用户的信息
 *
 * */
@Getter
@AllArgsConstructor
public class TokenUser {

    //管理员类型
    public static final Integer TYPE_ADMIN = 1;
    //用户类型
    public static final Integer TYPE_USER = 2;


    public static final String CLAIM_NAME_TYPE = "CLAIM_NAME_TYPE";
    public static final String CLAIM_NAME_USERID = "CLAIM_NAME_USERID";
    public static final String CLAIM_NAME_USERNAME = "CLAIM_NAME_USERNAME";
    public static final String CLAIM_NAME_SHOPID = "CLAIM_NAME_SHOPID";
    public static final String CLAIM_NAME_SHOPNAME = "CLAIM_NAME_SHOPNAME";

    /**
     * 创建普通用户
     * @param userId
     * @param userName
     * @return
     */
    public static TokenUser creatUser(String userId,String userName){
        return new TokenUser(TYPE_USER,userId,userName,null,null);
    }

    /**
     * 创建管理员用户
     * @param userId
     * @param userName
     * @param shopId
     * @param shopName
     * @return
     */
    public static TokenUser creatAdmin(String userId,String userName,Integer shopId,String shopName){
        return new TokenUser(TYPE_ADMIN,userId,userName,shopId,shopName);
    }


    private Integer type;//用户类型

    private String userId;
    private String userName;

    private Integer shopId;//管理员所属门店编号
    private String shopName;//管理员所属门店名称

    public boolean isAdmin(){
        return TYPE_ADMIN.equals(type);
    }

    public Boolean isUser() {
        return TYPE_USER.equals(type);
    }
}
