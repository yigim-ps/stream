package stream.sdk;

import java.security.*;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SHA1HMACSignature {

    private String key;
    private String method = "GET";
    private String uri = "/";
    private final List<Object> list
            = new ArrayList();

    public SHA1HMACSignature setKey(String value) {
        this.key = value;
        return this;
    }

    public SHA1HMACSignature setMethod(String value) {
        this.method = value;
        return this;
    }

    public SHA1HMACSignature setURI(String value) {
        this.uri = value;
        return this;
    }

    public SHA1HMACSignature addParameter(
            Object value
            ) {
        if (value != null) {
            list.add(value);
        }
        return this;
    }

    public SHA1HMACSignature(String key) {
        this.key = key;
    }

    public String calculate()
            throws
            NoSuchAlgorithmException,
            InvalidKeyException {
        return calculate(System.currentTimeMillis() / 200);
    }

    public String calculate(long time)
            throws
            NoSuchAlgorithmException,
            InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec(
                this.key.getBytes(),
                "HmacSHA1"
                );
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);
        String data = String.format(
                "%d%s%d%s%s%d",
                method.length(),
                method,
                uri.length(),
                uri,
                String.join("", list.stream().map(
                        e -> e.toString().length() + e.toString()
                        ).toArray(
                        String[]::new)
                        ),
                        time
                );
        byte[] array = mac.doFinal(
                data.getBytes()
                );
        return Base64.getEncoder().encodeToString(array);
    }

    public boolean validate(
            String value
            )
            throws
            NoSuchAlgorithmException,
            InvalidKeyException {
        if (value == null) {
            return false;
        }
        long time = System.currentTimeMillis() / 200;
        for (int i = -5 * 300; i < 5 * 300; i++) {
            String signature = calculate(time - i);
            if (signature.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        list.clear();
    }
}