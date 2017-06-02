/**
 * 文 件 名:  Base64Utils.java
 * 版    权:  Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  江钰锋 00501
 * 修改时间:  16/7/15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */

package com.gxb.gxbcompanyintegrity.util.base64;


import java.nio.charset.Charset;

/**
 * <一句话功能简述>
 * <功能详细描述>
 *
 * @author 江钰锋 00501
 * @version [版本号, 16/7/15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Base64Utils {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final Base64Utils.Base64Delegate delegate;

    public Base64Utils() {
    }

    private static void assertSupported() {
        Assert.state(delegate != null, "Neither Java 8 nor Apache Commons Codec found - Base64 encoding not supported");
    }

    public static byte[] encode(byte[] src) {
        assertSupported();
        return delegate.encode(src);
    }

    public static String encodeToString(byte[] src) {
        assertSupported();
        return src == null?null:(src.length == 0?"":new String(delegate.encode(src), DEFAULT_CHARSET));
    }

    public static byte[] decode(byte[] src) {
        assertSupported();
        return delegate.decode(src);
    }

    public static byte[] decodeFromString(String src) {
        assertSupported();
        return src == null?null:(src.length() == 0?new byte[0]:delegate.decode(src.getBytes(DEFAULT_CHARSET)));
    }

    static {
        Object delegateToUse = null;
        delegateToUse = new CommonsCodecBase64Delegate();
        delegate = (Base64Delegate)delegateToUse;
    }

    private static class CommonsCodecBase64Delegate implements Base64Utils.Base64Delegate {
        private final Base64 base64;

        private CommonsCodecBase64Delegate() {
            this.base64 = new Base64();
        }

        public byte[] encode(byte[] src) {
            return this.base64.encode(src);
        }

        public byte[] decode(byte[] src) {
            return this.base64.decode(src);
        }
    }

    private interface Base64Delegate {
        byte[] encode(byte[] var1);

        byte[] decode(byte[] var1);
    }
}
