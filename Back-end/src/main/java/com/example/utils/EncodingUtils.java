package com.example.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class EncodingUtils {

    public static String encode(String str) {
        BASE64Encoder encoder = new BASE64Encoder();
        str = encoder.encodeBuffer(str.getBytes());
        return str;
    }

    public static String decode(String str) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            str = new String(decoder.decodeBuffer(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}
