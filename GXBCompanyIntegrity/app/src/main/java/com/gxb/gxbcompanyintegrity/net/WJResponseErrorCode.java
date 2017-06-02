/**
 * 文 件 名:  WJResponseErrorCode.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/19
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.net;

/**
 * 返回的错误码
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/19]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface WJResponseErrorCode {
    /**服务端返回的数据为空*/
    public static final int ERROR_EMPTY_DATA=00701;
    /**返回数据与app端定义数据不一致*/
    public static final int ERROR_DIFFER_DATA=00702;
}
