package org.lbw.story.common;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class VerificationCodeUtils {

    //定义一个存储验证码的缓存，每一个验证码，以键值对形式存储，key有前段传来，值为验证码
    private static Map<String, String> cache = new HashMap<>();

    public static void outImage(String key, OutputStream out) throws IOException {
        VerificationCode verificationCode = new VerificationCode();

        BufferedImage image = verificationCode.getImage();

        String code = verificationCode.getText();

        ImageIO.write(image, "JPEG", out);

        cache.put(key, code);//将验证码存入缓存

        //立即启动一个线程，该线程直接阻塞60s，阻塞结束后删除缓存中的key值
        new Thread(() -> {
            try {
                Thread.sleep(60*1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cache.remove(key);
            log.debug("--------------------验证码key为{}的验证码被清除--------------------",key);
        }).start();


    }

    public static String getVerifyCode(String key) {
        return cache.get(key);
    }

}
