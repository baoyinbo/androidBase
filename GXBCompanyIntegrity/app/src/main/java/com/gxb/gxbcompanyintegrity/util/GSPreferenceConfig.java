package com.gxb.gxbcompanyintegrity.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import com.gxb.lazynetlibrary.utils.IoUtils;
import com.gxb.lazynetlibrary.utils.config.DataConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * Preference类型配置文件操作类
 *
 * @author 江钰锋
 * @version [版本号, 2014年7月15日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GSPreferenceConfig implements DataConfig {
    private static DataConfig mPreferenceConfig;

    private Context mContext;

    private Editor edit = null;

    private SharedPreferences mSharedPreferences;

    private String filename = "weiji";

    private Boolean isLoad = false;

    private GSPreferenceConfig(Context context) {
        this.mContext = context;

    }

    /**
     * 获得系统资源类
     *
     * @param context
     * @return
     */
    public static DataConfig getPreferenceConfig(Context context) {
        if (mPreferenceConfig == null) {
            mPreferenceConfig = new GSPreferenceConfig(context);
        }
        return mPreferenceConfig;
    }

    @Override
    public void loadConfig() {
        try {
            mSharedPreferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
            edit = mSharedPreferences.edit();
            isLoad = true;
        } catch (Exception e) {
            isLoad = false;
        }

    }

    @Override
    public Boolean isLoadConfig() {
        return isLoad;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void setString(String key, String value) {
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    public void setInt(String key, int value) {
        edit.putInt(key, value);
        edit.commit();
    }

    @Override
    public void setBoolean(String key, Boolean value) {
        edit.putBoolean(key, value);
        edit.commit();
    }

    @Override
    public void setByte(String key, byte[] value) {
        setString(key, String.valueOf(value));
    }

    @Override
    public void setShort(String key, short value) {
        setString(key, String.valueOf(value));
    }

    @Override
    public void setLong(String key, long value) {
        edit.putLong(key, value);
        edit.commit();
    }

    @Override
    public void setFloat(String key, float value) {
        edit.putFloat(key, value);
        edit.commit();
    }

    @Override
    public void setDouble(String key, double value) {
        setString(key, String.valueOf(value));
    }

    @Override
    public void setString(int resID, String value) {
        setString(this.mContext.getString(resID), value);
    }

    @Override
    public void setInt(int resID, int value) {
        setInt(this.mContext.getString(resID), value);
    }

    @Override
    public void setBoolean(int resID, Boolean value) {
        setBoolean(this.mContext.getString(resID), value);
    }

    @Override
    public void setByte(int resID, byte[] value) {
        setByte(this.mContext.getString(resID), value);
    }

    @Override
    public void setShort(int resID, short value) {
        setShort(this.mContext.getString(resID), value);
    }

    @Override
    public void setLong(int resID, long value) {
        setLong(this.mContext.getString(resID), value);
    }

    @Override
    public void setFloat(int resID, float value) {
        setFloat(this.mContext.getString(resID), value);
    }

    @Override
    public void setDouble(int resID, double value) {
        setDouble(this.mContext.getString(resID), value);
    }

    @Override
    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public boolean getBoolean(String key, Boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public byte[] getByte(String key, byte[] defaultValue) {
        try {
            return getString(key, "").getBytes();
        } catch (Exception e) {
        }
        return defaultValue;
    }

    @Override
    public short getShort(String key, Short defaultValue) {
        try {
            return Short.valueOf(getString(key, ""));
        } catch (Exception e) {
        }
        return defaultValue;
    }

    @Override
    public long getLong(String key, Long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(String key, Float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public double getDouble(String key, Double defaultValue) {
        try {
            return Double.valueOf(getString(key, ""));
        } catch (Exception e) {
        }
        return defaultValue;
    }

    @Override
    public String getString(int resID, String defaultValue) {
        return getString(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public int getInt(int resID, int defaultValue) {
        return getInt(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public boolean getBoolean(int resID, Boolean defaultValue) {
        return getBoolean(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public byte[] getByte(int resID, byte[] defaultValue) {
        return getByte(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public short getShort(int resID, Short defaultValue) {
        return getShort(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public long getLong(int resID, Long defaultValue) {
        return getLong(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public float getFloat(int resID, Float defaultValue) {
        return getFloat(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public double getDouble(int resID, Double defaultValue) {
        return getDouble(this.mContext.getString(resID), defaultValue);
    }

    @Override
    public void setConfig(Object entity) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream toByte = null;
        try {
            toByte = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(toByte);
            oos.writeObject(entity);
            // 对byte[]进行Base64编码
            String obj64 = new String(Base64.encode(toByte.toByteArray(),
                    Base64.DEFAULT));
            setString(entity.getClass().getName(), obj64);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtils.closeSilently(oos);
            IoUtils.closeSilently(toByte);
        }
    }

    @Override
    public <T> T getConfig(Class<T> clazz) {
        ObjectInputStream ois=null;
        try {
            String obj64 = getString(clazz.getName(), "");
            if (TextUtils.isEmpty(obj64)) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(obj64, Base64.DEFAULT);
            ois = new ObjectInputStream(new ByteArrayInputStream(base64Bytes));
            return (T) ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            IoUtils.closeSilently(ois);
        }
        return null;
    }

    @Override
    public void remove(String key) {
        edit.remove(key);
        edit.commit();
    }

    @Override
    public void remove(String... keys) {
        for (String key : keys){
            remove(key);
        }
    }

    @Override
    public void clear() {
        edit.clear();
        edit.commit();
    }

    @Override
    public void open() {

    }

}
