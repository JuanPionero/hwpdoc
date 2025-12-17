package ext.org.apache.poi.hhwpf;

import ext.org.apache.poi.hhwpf.util.binary.Obfuscation;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.util.Arrays;

public final class StreamDecryptor {
    public static InputStream decryptStream(InputStream is)
            throws Exception {
        Key secretKey = secretKey(is);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new CipherInputStream(is, cipher);
    }

    private static Key secretKey(InputStream is) throws IOException {
        byte[] distributionDocumentData = readDistributionDocData(is);
        int offset = 4 + (distributionDocumentData[0] & 0xF);

        byte[] keyBytes = Arrays.copyOfRange(distributionDocumentData, offset, offset + 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] readDistributionDocData(InputStream is) throws IOException {
        byte[] header = new byte[4];
        is.read(header, 0, 4); // record header,

        byte[] body = new byte[256];
        is.read(body, 0, 256);
        Obfuscation.transform(body);
        return body;
    }
}
