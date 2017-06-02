package com.gxb.gxbcompanyintegrity.model;

/**
 * Created by baoyb on 2017/5/31.
 */

public class GSUserInfoModel extends BaseResponseModel {
    private String tokenId;
    private String username;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
