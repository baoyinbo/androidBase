package com.gxb.gxbcompanyintegrity.util;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;


import com.gxb.lazynetlibrary.json.JSONUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串操作工具包
 *
 * @author 江钰锋
 * @version [版本号, 2014年6月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class StringUtils {

    /**
     * @param password
     * @return 判断是否是7-23 的字母和数字
     */
    public static boolean isStrong(String password) {
        Pattern p = Pattern
                // .compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{7,23}$");
                // .compile("^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*]+$)[a-zA-Z\\d!@#$%^&*]+${7,23}$");
                .compile("^(?=.{7,23})(((?=.*[a-zA-Z])(?=.*[0-9]))|((?=.*[a-zA-Z])(?=.*[!@#$%^&*]))|((?=.*[!@#$%^&*])(?=.*[0-9]))).*$");
        Matcher m = p.matcher(password);
        return m.matches();
    }


    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean 若输入字符串为null或空字符串，返回true
     */
    public static boolean isEmpty(String input)
    {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 定义分割常量 （#在集合中的含义是每个元素的分割，|主要用于map类型的集合用于key与value中的分割）
     */
    private static final String SEP1 = ",";
    private static final String SEP2 = ":";

    /**
     * List转换String
     *
     * @param list :需要转换的List
     * @return String转换后的字符串
     */
    public static String listToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(listToString((List<?>) list.get(i)));
                    sb.append(SEP1);
                } else if (list.get(i) instanceof Map) {
                    sb.append(mapToString((Map<?, ?>) list.get(i)));
                    sb.append(SEP1);
                } else {
                    sb.append(list.get(i));
                    sb.append(SEP1);
                }
            }
        }
        String result = sb.toString();
        if (!TextUtils.isEmpty(result)) {
            return result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * Map转换String
     *
     * @param map :需要转换的Map
     * @return String转换后的字符串
     */
    public static String mapToString(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        // 遍历map
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            Object key = obj;
            Object value = map.get(key);
            if (value == null) {
                continue;
            }
            if (value instanceof List<?>) {
                sb.append(key.toString() + SEP1 + listToString((List<?>) value));
                sb.append(SEP2);
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString() + SEP1
                        + mapToString((Map<?, ?>) value));
                sb.append(SEP2);
            } else {
                sb.append(key.toString() + SEP1 + value.toString());
                sb.append(SEP2);
            }
        }
        String result = sb.toString();
        if (!TextUtils.isEmpty(result)) {
            return result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * map转字典字符串
     *
     * @param map
     * @return
     */
    public static String mapToDictionary(@NonNull Map<?, ?> map) {
        String result = JSONUtil.toJSON(map);
        if (result != null && result.length() >= 2) {
            return result.substring(1, result.length() - 1);
        }
        return result;
    }

    /**
     * map转object
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    /***
     * object转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            if (value != null) {
                map.put(field.getName(), field.get(obj));
            }
        }
        if(map.containsKey("serialVersionUID")){
            map.remove("serialVersionUID");
        }
        return map;
    }

    /**
     * 去重复,去空值
     *
     * @param map
     * @return
     */
    public static Map<String, String> doThing(Map<String, String> map) {
        Map<String, String> map2 = new HashMap<String, String>();
        Map<String, String> map3 = new HashMap<String, String>();
        //TreeMap:对map按key值排序
        TreeMap<String, String> treemap = new TreeMap<String, String>(map);
        Iterator<String> it = treemap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = treemap.get(key);
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value) || map2.containsKey(value)) {
                continue;
            } else {
                map2.put(value, value);
                map3.put(key, value);
            }

        }
        return map3;
    }

    public static SpannableString modifyStrColor(String source, String subString, int color) {
        int index[] = new int[1];
        index[0] = source.indexOf(subString);
        SpannableString style = new SpannableString(source);
        if (index[0] < 0) return style;
        style.setSpan(new ForegroundColorSpan(color), index[0],
                index[0] + subString.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public static SpannableString modifyStrSize(String source, String subString, int size) {
        int index = source.indexOf(subString);
        SpannableString style = new SpannableString(source);
        if (index < 0) return style;
        style.setSpan(new AbsoluteSizeSpan(size, true), index, index + subString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return style;
    }

    public static SpannableString modifyStrSize(String source, int index, int modifLength, int size) {
        SpannableString style = new SpannableString(source);
        if (index < 0) return style;
        style.setSpan(new AbsoluteSizeSpan(size, true), index, modifLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return style;
    }

    public static SpannableString modifyStrSizeAndColor(String source, String subString, int size, int color) {
        int index = source.indexOf(subString);
        SpannableString style = new SpannableString(source);
        if (index < 0) return style;
        style.setSpan(new ForegroundColorSpan(color), index,
                index + subString.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(size, true), index, index + subString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    /**
     * 自定义格式时间(20160720101103)
     *
     * @return
     */
    public static String getCustomTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 00:00:00  转为00:00
     * @param date
     * @return
     */
    public static String getHandMTime(String date) {
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        String backDateStr = null;
//        try {
//            Date d = format.parse(date);
//            backDateStr = format.format(d);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return backDateStr;
        return date.substring(0, date.length() - 3);
    }

    /**
     * 6位 数字连续 或 相同 则判断为简单密码 返回true
     * split("") 截取的第一位是"" 比如 "123456" 的结果是 "" ,"1" "2", "3"...
     *
     * @param pwd
     * @return
     */
    public static boolean isEasyPwd(String pwd) {
        char[] pwdArr = pwd.toCharArray();

        int a = Integer.parseInt(String.valueOf(pwdArr[0]));
        int b = Integer.parseInt(String.valueOf(pwdArr[1]));
        int c = Integer.parseInt(String.valueOf(pwdArr[2]));
        int d = Integer.parseInt(String.valueOf(pwdArr[3]));
        int e = Integer.parseInt(String.valueOf(pwdArr[4]));
        int f = Integer.parseInt(String.valueOf(pwdArr[5]));

        //6位密码相同
        if (a == b && a == c && a == d && a == e && a == f) {
            return true;
        }

        //等比加1
        if (a == b - 1 && a == c - 2 && a == d - 3 && a == e - 4 && a == f - 5) {
            return true;
        }

        //等比减1
        if (a == b + 1 && a == c + 2 && a == d + 3 && a == e + 4 && a == f + 5) {
            return true;
        }
        return false;
    }

    /**
     * 检验手机号是否有效
     * 13/14/15/17/18 号码段
     */
    public static boolean isPhoneNumbValid(String mobiles) {
        String eL = "^(13|14|15|17|18)[0-9]{9}$";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 身份证有效性验证
     *
     * @param idcard
     * @return
     */
    public static boolean isIdCardValid(String idcard) {
        String eL = "^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\\d{4}((19\\d{2}" +
                "(0[13-9]|1[012])(0[1-9]|[12]\\d|30))|(19\\d{2}(0[13578]|1[02])31)|(19\\d{2}" +
                "02(0[1-9]|1\\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\\d{3}(\\d|X|x)$";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    /**
     * 将名字用*替换：王**
     *
     * @param name
     * @return
     */
    public static String replaceString(String name) {
        StringBuffer realname = new StringBuffer();
        if (!TextUtils.isEmpty(name)) {
            realname.append(name.substring(0, 1));
            for (int i = 0; i < name.length() - 1; i++) {
                realname.append("*");
            }
        }
        return realname.toString();
    }

    /**
     * 截取手机最好四位
     *
     * @param phone
     * @return
     */
    public static String getPhoneTailNumber(String phone) {
        if (TextUtils.isEmpty(phone))
            return "";
        String phoneNumber = "";
        int len = phone.length();
        if (len >= 4) {
            phoneNumber = phone.substring(len - 4, len);
        }
        return "尾号" + phoneNumber;
    }

    /**
     * 截取银行卡号
     *
     * @param cardNumber
     * @return
     */
    public static String getBankCardTailNumber(String cardNumber) {
        if (TextUtils.isEmpty(cardNumber))
            return "";
        String bankInfo = "";
        int len = cardNumber.length();
        if (len >= 4) {
            bankInfo = cardNumber.substring(len - 4, len);
        }
        return "尾号" + bankInfo;
    }

    /**
     * 判断是否是银行卡格式
     *
     * @param cardNumber
     * @return
     */
    public static boolean isBankCardFormat(String cardNumber) {
        if (TextUtils.isEmpty(cardNumber)
                || cardNumber.length() < 16
                || cardNumber.length() > 25) {
            return false;
        }
        return true;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL != null && strURL.length() > 1) {
            if (arrSplit != null && arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = null;
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (TextUtils.isEmpty(strUrlParam)) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        if(arrSplit!=null){
            mapRequest = new HashMap<String, String>();
            for (String strSplit : arrSplit) {
                String[] arrSplitEqual = strSplit.split("[=]");
                //解析出键值
                if(arrSplitEqual!=null){
                    if (arrSplitEqual.length > 1) {
                        //正确解析
                        mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
                    } else {
                        if (arrSplitEqual[0] != "") {
                            //只有参数没有值，不加入
                            mapRequest.put(arrSplitEqual[0], "");
                        }
                    }
                }
            }
        }
        return mapRequest;
    }

    public static double toDouble(String s) {
        double i = 0;
        try {
            if (!TextUtils.isEmpty(s) && s.contains(",")) {
                s = s.replaceAll(",", "");
            }
            i = Double.parseDouble(s);
        } catch (Exception e) {
        }
        return i;
    }

    public static float toFloat(String s) {
        float i = 0;
        try {
            if (!TextUtils.isEmpty(s) && s.contains(",")) {
                s = s.replaceAll(",", "");
            }
            i = Float.parseFloat(s);
        } catch (Exception e) {
        }
        return i;
    }

    public static int toInt(String s) {
        int i = 0;
        try {
            if (!TextUtils.isEmpty(s) && s.contains(",")) {
                s = s.replaceAll(",", "");
            }
            i = Integer.parseInt(s);
        } catch (Exception e) {
        }
        return i;
    }

    public static long toLong(String s) {
        long i = 0;
        try {
            if (!TextUtils.isEmpty(s) && s.contains(",")) {
                s = s.replaceAll(",", "");
            }
            i = Long.parseLong(s);
        } catch (Exception e) {
        }
        return i;
    }

    /**
     * 将10000以上的值转化为 1万 格式
     * @param data
     * @return
     */
    public static String toMillion(String data) {
        Double orderLimit = Double.parseDouble(data);
        if (orderLimit >= 10000) {
            if (orderLimit % 10000 == 0) {
                return (int) (orderLimit / 10000) + "万";
            }
            return orderLimit / 10000 + "万";
        } else {
            return NumberUtils.formatAmount(data);
        }
    }
}
