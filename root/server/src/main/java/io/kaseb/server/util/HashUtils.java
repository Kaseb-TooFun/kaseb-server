package io.kaseb.server.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@Slf4j
public class HashUtils {
    private HashUtils() {
    }

    public static String hash(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes(StandardCharsets.UTF_8));
            byte[] md5Bytes = md.digest();
            return Base64Utils.encodeToString(md5Bytes);
        } catch (NoSuchAlgorithmException e) {
            logger.error("no such algorithm {} base64", "MD5", e);
        }
        return "";
    }

}
