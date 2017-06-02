/**
 * 文 件 名:  OnPermissionListener.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/8/16
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.permission;

import com.karumi.dexter.PermissionToken;

/**
 * 权限监听的回调
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/8/16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface OnPermissionListener {
    /**
     *
     * @param token
     */
    void showPermissionRationale(PermissionToken token);

    /**
     * 同意
     * @param permission
     */
    void showPermissionGranted(String permission);

    /**
     * 不同意
     * @param permission
     * @param isPermanentlyDenied
     */
    void showPermissionDenied(String permission, boolean isPermanentlyDenied);
}
