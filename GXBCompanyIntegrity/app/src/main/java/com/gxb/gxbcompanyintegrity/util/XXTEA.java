package com.gxb.gxbcompanyintegrity.util;


import com.gxb.gxbcompanyintegrity.util.base64.Base64Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/***
 * XXTEA 加密解密
 */
public class XXTEA {

    private XXTEA() {
    }

    private static String base64Encode(byte[] data) {
        return Base64Utils.encodeToString(data);
    }

    private static byte[] base64Decode(String data) throws IOException {
        return Base64Utils.decode(data.getBytes());
    }

    private static byte[] base64Decode(byte[] data) throws IOException {
        return Base64Utils.decode(data);
    }

    /**
     * 加密 data with key.
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                encrypt(toIntArray(data, true), toIntArray(key, false)), false);
    }

    /**
     * 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptWithBase64(byte[] data, byte[] key) {
        return base64Encode(encrypt(data, key));
    }

    /***
     * 加密
     * @param data
     * @param key
     * @param encoding
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encryptWithBase64(String data, byte[] key,
                                           String encoding) throws UnsupportedEncodingException {
        return base64Encode(encrypt(data.getBytes(encoding), key));
    }

    /**
     * Decrypt data with key.
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        if (data.length == 0) {
            return data;
        }
        return toByteArray(
                decrypt(toIntArray(data, false), toIntArray(key, false)), true);
    }

    /**
     * 解密
     * @param data
     * @param key
     * @param encoding
     * @return
     * @throws IOException
     */
    public static byte[] decryptWithBase64(String data, byte[] key,
                                           String encoding) throws IOException {
        if (null == data || data.length() == 0) {
            return new byte[0];
        }
        return decrypt(base64Decode(data), key);
    }

    /**
     * 解密
     * @param data
     * @param key
     * @return
     * @throws IOException
     */
    public static byte[] decryptWithBase64(byte[] data, byte[] key)
            throws IOException {
        if (data.length == 0) {
            return data;
        }
        return decrypt(base64Decode(data), key);
    }

    /**
     * Encrypt data with key.
     *
     * @param v
     * @param k
     * @return
     */
    public static int[] encrypt(int[] v, int[] k) {
        int n = v.length - 1;

        if (n < 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];

            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
        int p, q = 6 + 52 / (n + 1);

        while (q-- > 0) {
            sum = sum + delta;
            e = sum >>> 2 & 3;
            for (p = 0; p < n; p++) {
                y = v[p + 1];
                z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                        + (k[p & 3 ^ e] ^ z);
            }
            y = v[0];
            z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                    + (k[p & 3 ^ e] ^ z);
        }
        return v;
    }

    /**
     * Decrypt data with key.
     *
     * @param v
     * @param k
     * @return
     */
    public static int[] decrypt(int[] v, int[] k) {
        int n = v.length - 1;

        if (n < 1) {
            return v;
        }
        if (k.length < 4) {
            int[] key = new int[4];

            System.arraycopy(k, 0, key, 0, k.length);
            k = key;
        }
        int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
        int p, q = 6 + 52 / (n + 1);

        sum = q * delta;
        while (sum != 0) {
            e = sum >>> 2 & 3;
            for (p = n; p > 0; p--) {
                z = v[p - 1];
                y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                        + (k[p & 3 ^ e] ^ z);
            }
            z = v[n];
            y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
                    + (k[p & 3 ^ e] ^ z);
            sum = sum - delta;
        }
        return v;
    }

    /**
     * Convert byte array to int array.
     *
     * @param data
     * @param includeLength
     * @return
     */
    private static int[] toIntArray(byte[] data, boolean includeLength) {
        int n = (((data.length & 3) == 0) ? (data.length >>> 2)
                : ((data.length >>> 2) + 1));
        int[] result;

        if (includeLength) {
            result = new int[n + 1];
            result[n] = data.length;
        } else {
            result = new int[n];
        }
        n = data.length;
        for (int i = 0; i < n; i++) {
            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
        }
        return result;
    }

    /**
     * Convert int array to byte array.
     *
     * @param data
     * @param includeLength
     * @return
     */
    private static byte[] toByteArray(int[] data, boolean includeLength) {
        int n = data.length << 2;

        ;
        if (includeLength) {
            int m = data[data.length - 1];

            if (m > n) {
                return null;
            } else {
                n = m;
            }
        }
        byte[] result = new byte[n];

        for (int i = 0; i < n; i++) {
            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
        }
        return result;
    }

    public static void main(String[] args) {
//        byte[] bytes="{'termId':'10683f8ea6e5','deviceId':'355136052972802','appVersion':'1.0.0','channelId':'WEIJIFIN','osVersion':'4.4.4','termTyp':'ANDROID','clientId':'AA89D6D64FB94F79B4A8060165A41A51','smsTyp':'1','requestTm':'160721 092134','mblNo':'13675859994'}171b3e2aa2002ae98a4ad9694838d99925bca81c".getBytes();
//        String str = encryptWithBase64(bytes, WJAppConfing.XXTEA_KET.getBytes());
//        System.out.println("Base64加密结果:" + str);
        String de="yKxuLyOuhrdoHG47ELNZ32p8oxnlvcqrHQYiESFH0kIiA76jI8tCHIii4sRmGSLXwgNgBB0bTJkdUQoMnjn11kMxKRxj5q6elcSmtDwBK47JyerS6oGQZhH7NbZQiPPxaO9NJTlEAAlxZz8fYXAWWhtL381XzdpqSJfmTOhUcA0Stb8rcCxEnIgizM+fwPvXL1B6EJfrE8A3iBz0BF09mZh+BrGpbO/6rWIr3fjqYzaFpeyCxNYPcf8gt62pxai2r1ZAosbWvuwbC0Y8d1Bu6jxKWDolt+xaznhJ1V+L6vMQQl1duOW3i2SiFWt7L3nEoW1r8BNlxBGc//hCnQLPB2+GHWbRU9SCzbiU7fjEw9OdgE0IeD5k+ETb9550m/0sQRU5vai+qq+VAuSU898vPsPQr0Xn5esGTcxxf9QSzh9famIVH1AqShjRhGvamPgATjtjbVc0QFqIwIdReW2U2vTuOaQ=";
//        String de="1n4KHBZ4DXuTPEXL7kcSHzoyWq6xo8TBj7ZUusKS+O4xAxUUNHDiQi6Xo0tJKNZu2RW8s6Aar4zgKq9MMktMF16qH+oXuobV74J5xByiIwAUXqDo8jAoQxjNIkbBBVKWA9vypeIgic9Q2BPLmdEM2h3jS4xD8cmxZQ82EPNpBTVqAuFBY8gzEkBndv4AzBcnzFAeQZ9YBvIhTT/tA8KJ86ySag9nzvUsOeadLri/1jNiu1jD7+6CJddp98/OoYRgxWCzKPGxYJQFjSVuSy30WlQwFRYuH+Ac07Urtn5yUqukqEhehOikTMZ/FL24C3v9sndP8nLJsqoJCAwJcD5/tpOkfHhb6bDEY0fS9VuCz4eQg/6r++4uuGpH9t6xS5NMD3FoFw==";
//        try {
//            de=new String(decryptWithBase64(de.getBytes(), GSAppConfing.XXTEA_KET.getBytes()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Base64解密结果:" + de);
    }
}
