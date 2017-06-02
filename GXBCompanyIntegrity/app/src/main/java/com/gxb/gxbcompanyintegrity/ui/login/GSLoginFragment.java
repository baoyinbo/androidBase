package com.gxb.gxbcompanyintegrity.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gxb.gxbcompanyintegrity.R;
import com.gxb.gxbcompanyintegrity.api.GSApiManager;
import com.gxb.gxbcompanyintegrity.config.GSUserInfoConfig;
import com.gxb.gxbcompanyintegrity.model.BaseResponseModel;
import com.gxb.gxbcompanyintegrity.model.GSUserInfoModel;
import com.gxb.gxbcompanyintegrity.net.WJResponseListener;
import com.gxb.gxbcompanyintegrity.ui.base.BaseActivity;
import com.gxb.gxbcompanyintegrity.ui.base.BaseActivityFragment;
import com.gxb.gxbcompanyintegrity.ui.login.wrap.GSLoginStatusWrap;
import com.gxb.gxbcompanyintegrity.util.AddSpaceTextWatcher;
import com.gxb.gxbcompanyintegrity.util.CountDownUtils;
import com.gxb.gxbcompanyintegrity.util.ResourceUtils;
import com.gxb.gxbcompanyintegrity.util.StringUtils;
import com.gxb.gxbcompanyintegrity.util.ToastShowUtils;


/**
 * Created by baoyb on 2017/5/27.
 */

public class GSLoginFragment extends BaseActivityFragment implements View.OnClickListener, WJResponseListener {
    private EditText etPhone;
    private EditText etCode;
    private TextView tvGetCode;
    private Button btnLogin;

    private String account;
    private String code;
    /**获取验证码倒计时*/
    private CountDownUtils countDownUtils;
    @Override
    public int getLayoutId() {
        return R.layout.gs_fra_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setTitle(R.string.login);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCode = (EditText) findViewById(R.id.etCode);
        tvGetCode = (TextView) findViewById(R.id.tvGetCode);
        tvGetCode.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        etPhone.setHintTextColor(ResourceUtils.getColor(this.getContext(), R.color.text_hint_gray));
        etCode.setHintTextColor(ResourceUtils.getColor(this.getContext(), R.color.text_hint_gray));

        AddSpaceTextWatcher asTextWatcher = new TextWatcher(etPhone , 13);
        asTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
    }

    @Override
    public void onClick(View v) {
        account = etPhone.getText().toString().replace(" ", "");
        code = etCode.getText().toString();
        switch (v.getId()) {
            case R.id.tvGetCode:
                if (StringUtils.isEmpty(account)) {
                    ToastShowUtils.showTextToast("请输入手机号");
                    return;
                }
                if (!StringUtils.isPhoneNumbValid(account)) {
                    ToastShowUtils.showTextToast("请输入有效的手机号");
                    return;
                }
                countDownUtils = new CountDownUtils(getActivity(), 60000, 1000, tvGetCode);
                countDownUtils.start();
                break;
            case R.id.btnLogin:
                if (StringUtils.isEmpty(account)) {
                    ToastShowUtils.showTextToast("请输入手机号");
                    return;
                }
                if (!StringUtils.isPhoneNumbValid(account)) {
                    ToastShowUtils.showTextToast("请输入有效的手机号");
                    return;
                }
                if (StringUtils.isEmpty(code)) {
                    ToastShowUtils.showTextToast("请输入验证码");
                    return;
                }

                GSApiManager.instance().login(GSLoginFragment.this, account, code, this);
        }
    }

    @Override
    public void onSuccess(int messageId, BaseResponseModel data) {
        switch (messageId) {
            case R.string.url_login:
                GSUserInfoConfig.saveUserinfo((GSUserInfoModel) data);

                if (GSLoginStatusWrap.getLoginResponseCallback() != null) {
                    GSLoginStatusWrap.getLoginResponseCallback()
                            .onLoginSuccess(Activity.RESULT_OK,
                                    (BaseActivity) GSLoginFragment.this.getActivity(), null);
                }

                finishActivity();
        }
    }

    @Override
    public void onFail(int messageId, String statusCode, String error) {
        switch (messageId) {
            case R.string.url_login:
                ToastShowUtils.showTextToast(error);
                break;
        }
    }


    class TextWatcher extends AddSpaceTextWatcher {
        public TextWatcher(EditText editText, int maxLenght) {
            super(editText, maxLenght);
        }
        @Override
        public void afterTextChanged(Editable s) {
            super.afterTextChanged(s);
        }
    }
}
