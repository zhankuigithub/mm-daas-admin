package com.mm.common.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * <p>
 * Rsa加解密工具类
 * </p>
 *
 * @author
 * @date 2023-01-18
 */
@Component
public class RsaUtils {

    private static RSA rsa;

    public static void setRsa(RSA rsa) {
        RsaUtils.rsa = rsa;
    }

    @Value("${rsa.publicKey}")
    private String publicKey;

    @Value("${rsa.privateKey}")
    private String privateKey;

    @PostConstruct
    public void init() {
        setRsa(new RSA(privateKey, publicKey));
    }

    /**
     * <p> Rsa加密 </p>
     *
     * @param str 明文
     * @return 密文
     **/
    public static String encrypt(String str) {
        byte[] encrypt = rsa.encrypt(str, KeyType.PublicKey);
        return Base64.encodeBase64String(encrypt);
    }

    /**
     * <p> Rsa解密 </p>
     *
     * @param str 密文
     * @return 明文
     **/
    public static String decrypt(String str) {
        byte[] decrypt = rsa.decrypt(str, KeyType.PrivateKey);
        return StrUtil.str(decrypt, Charset.defaultCharset());
    }

}
