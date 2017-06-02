/*
 * 文 件 名:  DebugActivity.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/5/11
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.ui.debug;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxb.gxbcompanyintegrity.GSApplication;
import com.gxb.gxbcompanyintegrity.R;
import com.gxb.gxbcompanyintegrity.config.GSUrlDefines;
import com.gxb.gxbcompanyintegrity.ui.base.BaseActivity;
import com.gxb.gxbcompanyintegrity.util.DensityUtils;
import com.gxb.gxbcompanyintegrity.util.H5ArrayList;
import com.gxb.gxbcompanyintegrity.util.ToastShowUtils;
import com.gxb.gxbcompanyintegrity.util.URLArrayList;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 用来切换服务器地址等
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/5/11]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GSDebugActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, Handler.Callback {

    private final static String DEBUG_ACTICITY_ACTION = "com.gxb.gxbshare.ui.debug.GSDebugActivity";
    private final static String[] debugUrls = {
            "http://app.matanfax.com/",
            "http://172.16.1.12:8080/",
            "http://172.16.1.13:8080/"
    };
    private final static String[] debugH5Urls = {
            "http://h5.matanfax.com/",
            "http://172.16.1.12:3334/",
            "http://172.16.1.13:3334/"
    };
    public static Button floatbtnDebug;

    private RadioGroup radioGroup;
    private EditText editText;
    private Button btnOk;
    //PopupWindow对象
    private PopupWindow selectPopupWindow = null;
    //自定义Adapter
    private OptionsAdapter optionsAdapter = null;
    //下拉框依附组件
    private RelativeLayout parent;
    //下拉框依附组件宽度，也将作为下拉框的宽度
    private int pwidth;
    //下拉箭头图片组件
    private ImageButton ibDropDown;
    //用来处理选中或者删除下拉项消息
    private Handler handler;
    //是否初始化完成标志
    private boolean flag = false;

    //下拉框依附组件
    private RelativeLayout h5Parent;
    private EditText etH5;
    private ImageButton ibH5DropDown;

    public static void initFloatBtn(final Context context) {
        if (GSDebugActivity.floatbtnDebug != null)
            return;

        floatbtnDebug = new Button(GSApplication.getApplication());
        floatbtnDebug.setBackgroundResource(R.mipmap.ic_launcher);
        final WindowManager wm = (WindowManager) GSApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   //2002;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = 100;
        wmParams.height = 100;
        //wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = DensityUtils.dipTopx(context, 60);
        wmParams.y = 0;
        wm.addView(floatbtnDebug, wmParams);

        floatbtnDebug.setOnTouchListener(new View.OnTouchListener() {
            private float x, y;
            private float org_x, org_y;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 捕获手指触摸按下动作
                        // 获取相对View的坐标，即以此View左上角为原点
                        x = event.getX();
                        y = event.getY();
                        org_x = event.getRawX();
                        org_y = event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE: // 捕获手指触摸移动动作
                        wmParams.x = (int) (event.getRawX() - x);
                        wmParams.y = (int) (event.getRawY() - getStatusBarHeight() - y);
                        wm.updateViewLayout(v, wmParams);
                        break;

                    case MotionEvent.ACTION_UP: // 捕获手指触摸离开动作
                        if (Math.abs(event.getRawX() - org_x) < 5 && Math.abs(event.getRawY() - org_y) < 5) {
                            Intent intent = new Intent(DEBUG_ACTICITY_ACTION);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private static int getStatusBarHeight() {
        int statusBarHeight = 0;
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = GSApplication.getApplication().getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s.toString())) {
                btnOk.setEnabled(false);
            } else {
                btnOk.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void doCreate(Bundle savedInstanceState) {
        setContentView(R.layout.gs_act_debug);
    }

    @Override
    public void initView() {
        initSystemBarTint(false, R.color.white);
//        addStatusBarView((LinearLayout)findViewById(R.id.llStatusBar),0,0);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        editText = (EditText) findViewById(R.id.editText);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        editText.addTextChangedListener(textWatcher);
        radioGroup.setOnCheckedChangeListener(this);
        editText.setText(GSUrlDefines.BASE_API_URL_RELEASE);
        ibDropDown = (ImageButton) findViewById(R.id.ibDropDown);
        ibDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    showUrls();
                }
            }
        });

        etH5=(EditText)findViewById(R.id.etH5);
        ibH5DropDown=(ImageButton)findViewById(R.id.ibH5DropDown);
        etH5.setText(GSUrlDefines.BASE_H5_URL_RELEASE);
        //设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
        ibH5DropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    showH5s();
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = 0;
        if (checkedId == R.id.rbRelease) {
            index = 0;
        } else if (checkedId == R.id.rbDev) {
            index = 1;
        } else if (checkedId == R.id.rbDebug) {
            index = 2;
        }
        editText.setText(debugUrls[index]);
        etH5.setText(debugH5Urls[index]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //点击了返回键
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnOk) {
            String url = editText.getText().toString();
            String h5=etH5.getText().toString();
            if(TextUtils.isEmpty(url)|| TextUtils.isEmpty(h5)){
                ToastShowUtils.showTextToast("地址不能为空");
                return;
            }
            //以下是h5
            if(!GSUrlDefines.BASE_H5_URL_RELEASE.equals(h5)){
                GSApplication.getApplication().getPreferenceConfig().setString("KEY_DEBUG_H5_ADDRESS", h5);
                H5ArrayList h5s = GSApplication.getApplication().getPreferenceConfig().getConfig(H5ArrayList.class);
                if (h5s == null) {
                    h5s = new H5ArrayList();
                }
                if (!h5s.contains(h5)) {
                    h5s.add(h5);
                }
                GSApplication.getApplication().getPreferenceConfig().setConfig(h5s);
            }

            //储存当前使用的服务器url
            if (!GSUrlDefines.BASE_API_URL_RELEASE.equals(url)){
                GSApplication.getApplication().getPreferenceConfig().setString("KEY_DEBUG_RUL_ADDRESS", url);
                URLArrayList urls = GSApplication.getApplication().getPreferenceConfig().getConfig(URLArrayList.class);
                if (urls == null) {
                    urls = new URLArrayList();
                }
                if (!urls.contains(url)) {
                    urls.add(url);
                }
                GSApplication.getApplication().getPreferenceConfig().setConfig(urls);

//                UserInfoConfig.deteleUserInfo();
//                WJLoginStateWrapper.starLogintActivity(this);

                setOpenExitTransition(true);
            }
            GSUrlDefines.initUrl();
            finish();
        }
    }

    /**
     * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
     * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        while (!flag) {
            initWedget();
            flag = true;
        }

    }

    /**
     * 初始化界面控件
     */
    private void initWedget() {
        //初始化Handler,用来处理消息
        handler = new Handler(this);
        //初始化界面组件
        parent = (RelativeLayout) findViewById(R.id.rlayout);
        //获取下拉框依附的组件宽度
        pwidth = parent.getWidth();
        h5Parent=(RelativeLayout)findViewById(R.id.rlayoutH5);
        //初始化PopupWindow
        initPopuWindow();
    }

    /**
     * 初始化PopupWindow
     */
    private void initPopuWindow() {
        View loginwindow = getLayoutInflater().inflate(R.layout.popupwindow_debug_list, null);
        ListView listView = (ListView) loginwindow.findViewById(R.id.listView);
        //设置自定义Adapter
        optionsAdapter = new OptionsAdapter(this);
        listView.setAdapter(optionsAdapter);
        selectPopupWindow = new PopupWindow(loginwindow, pwidth, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        selectPopupWindow.setOutsideTouchable(true);
        //这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
        selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }


    /**
     * 显示urls
     */
    private void showUrls() {
        optionsAdapter.setNewList(GSApplication.getApplication().getPreferenceConfig().getConfig(URLArrayList.class));
        //将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        //这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        //（是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
        if (optionsAdapter.isEmpty()) {
            return;
        }
        selectPopupWindow.showAsDropDown(parent, 0, DensityUtils.dipTopx(this, -3));
    }

    /**
     * 显示h5s
     */
    private void showH5s() {

        optionsAdapter.setNewList(GSApplication.getApplication().getPreferenceConfig().getConfig(H5ArrayList.class));
        //将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
        //这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
        //（是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
        if (optionsAdapter.isEmpty()) {
            return;
        }
        selectPopupWindow.showAsDropDown(h5Parent, 0, DensityUtils.dipTopx(this, -3));
    }

    /**
     * PopupWindow消失
     */
    private void dismiss() {
        selectPopupWindow.dismiss();
    }

    /**
     * 处理Hander消息
     */
    @Override
    public boolean handleMessage(Message message) {
        Bundle data = message.getData();
        switch (message.what) {
            case 1:
                int type=data.getInt("type");
                //选中下拉项，下拉框消失
                int selIndex = data.getInt("selIndex");
                if(type==1){
                    editText.setText(optionsAdapter.getItem(selIndex));
                }else if(type==2){
                    etH5.setText(optionsAdapter.getItem(selIndex));
                }
                dismiss();
                break;
            case 2:
                //移除下拉项数据
                int delIndex = data.getInt("delIndex");
                optionsAdapter.remove(delIndex);
                break;
        }
        return false;
    }

    class OptionsAdapter extends BaseAdapter {
        /**adapter类型(1:url;2:h5)*/
        private int type;
        private ArrayList<String> list;
        private Activity activity = null;

        /**
         * 自定义构造方法
         *
         * @param activity
         */
        public OptionsAdapter(Activity activity) {
            this.activity = activity;
            list=new ArrayList<>();
        }

        public void setNewList(ArrayList list) {
            if (list == null) return;
            this.list = list;
            if(this.list instanceof URLArrayList){
                type=1;
            }else if(this.list instanceof H5ArrayList){
                type=2;
            }
            notifyDataSetChanged();
        }

        public void remove(String item) {
            list.remove(item);
            notifyDataSetChanged();
            GSApplication.getApplication().getPreferenceConfig().setConfig(list);
        }

        public void remove(int position) {
            list.remove(position);
            notifyDataSetChanged();
            GSApplication.getApplication().getPreferenceConfig().setConfig(list);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(activity).inflate(R.layout.listitem_debug_pw, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.display(position, type, getItem(position));
            return convertView;
        }

    }

    class ViewHolder {
        TextView textView;
        ImageButton imageButton;

        ViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(R.id.textView);
            imageButton = (ImageButton) convertView.findViewById(R.id.imageButton);
        }

        private void display(final int position, final int type,String data) {
            textView.setText(data);

            //为下拉框选项文字部分设置事件，最终效果是点击将其文字填充到文本框
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    //设置选中索引
                    data.putInt("selIndex", position);
                    data.putInt("type",type);
                    msg.setData(data);
                    msg.what = 1;
                    //发出消息
                    handler.sendMessage(msg);
                }
            });

            //为下拉框选项删除图标部分设置事件，最终效果是点击将该选项删除
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    //设置删除索引
                    data.putInt("delIndex", position);
                    msg.setData(data);
                    msg.what = 2;
                    //发出消息
                    handler.sendMessage(msg);
                }
            });
        }
    }

}
