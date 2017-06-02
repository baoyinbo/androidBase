/**
 * 文 件 名:  BaseResponseModel.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/13
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.model;

import java.io.Serializable;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/13]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BaseResponseModel<T> implements Serializable {
    protected String msg;// 错误信息
    protected String code;  //OK FAILED
    protected String msgtype;   //消息类型 默认为0
    protected T result;// 结果

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    //    /**返回码(00000表示成功其余均表示失败)*/
//    private String rspCd;
//    /**返回码描述信息*/
//    private String rspInf;
//    /**响应时间(格式:20140910144133)*/
//    private String responseTm;
//
//    public String getRspCd() {
//        return rspCd;
//    }
//
//    public void setRspCd(String rspCd) {
//        this.rspCd = rspCd;
//    }
//
//    public String getRspInf() {
//        return rspInf;
//    }
//
//    public void setRspInf(String rspInf) {
//        this.rspInf = rspInf;
//    }
//
//    public String getResponseTm() {
//        return responseTm;
//    }
//
//    public void setResponseTm(String responseTm) {
//        this.responseTm = responseTm;
//    }

    /**
     * 后台返回的数据是否为空
     * @return
     */
    public boolean isEmpty(){
        return false;
    }
}
