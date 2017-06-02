package com.gxb.gxbcompanyintegrity.config;


import com.gxb.gxbcompanyintegrity.GSApplication;
import com.gxb.gxbcompanyintegrity.model.GSUserInfoModel;

/**
 * 配置用户信息
 * Created by baoyb on 2017/5/31.
 */

public class GSUserInfoConfig {

    /**
     * 保存用户信息
     * @param model
     */
    public static void saveUserinfo(GSUserInfoModel model) {
        GSApplication.getApplication().setUserinfoModel(model);
        GSApplication.getApplication().getPreferenceConfig().setConfig(model);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static GSUserInfoModel getUserinfo() {
        GSUserInfoModel userinfoModel = GSApplication.getApplication().getUserinfoModel();
        if (userinfoModel == null) {
            userinfoModel = GSApplication.getApplication()
                    .getPreferenceConfig().getConfig(GSUserInfoModel.class);
            GSApplication.getApplication().setUserinfoModel(userinfoModel);
        }
        return userinfoModel;
    }

    /**
     * 删除用户信息用户信息
     *
     * @return
     */
    public static void deteleUserInfo() {
        GSApplication.getApplication().setUserinfoModel(null);
        GSApplication.getApplication()
                .getPreferenceConfig().remove(GSUserInfoModel.class.getName());
    }

    /**
     * 判断用户是否登入
     *
     * @return
     */
    public static boolean isLogin() {
        if (getUserinfo() != null) {
            return true;
        }
        return false;
    }
}
