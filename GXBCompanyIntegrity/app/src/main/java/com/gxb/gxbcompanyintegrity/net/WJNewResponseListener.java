/**
 * 文 件 名:  WJNewResponseListener.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  2016/11/29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net;

/**
 * 用于构造新的请求反馈监听
 *
 * @author 江钰锋 00501
 * @version [版本号, 2016/11/29]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface WJNewResponseListener extends WJResponseListener{
    /**
     * 新的WJResponseListener构造器
     * @param objects
     * @return
     */
    WJNewResponseListener newBuilder(Object... objects);
}
