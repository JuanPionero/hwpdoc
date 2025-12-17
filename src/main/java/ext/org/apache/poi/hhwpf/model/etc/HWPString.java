package ext.org.apache.poi.hhwpf.model.etc;

import ext.org.apache.poi.hhwpf.StreamReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HWPString {
    byte[] bytes;

    public HWPString() {
        bytes = null;
    }

    public HWPString(StreamReader sr) throws IOException {
        this.bytes = sr.readHWPString();
    }

    public HWPString(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * String으로 초기화 시키는 것은 기존에 없었기에, UTF_16LE로 인코딩한 bytes를 얻는 것을 기본으로 함.
     * @param utf16LE
     */
    public HWPString(String utf16LE) {
        if(null!=utf16LE && utf16LE.length()>0) {
            this.bytes = utf16LE.getBytes(StandardCharsets.UTF_16LE);
        } else {
            this.bytes = null;
        }
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String toUTF16LEString() {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_16LE);
    }

    public void fromUTF16LEString(String utf16LE) {
        if (utf16LE != null && utf16LE.length() > 0) {
            bytes = utf16LE.getBytes(StandardCharsets.UTF_16LE);
        }
    }

    public HWPString clone() {
        HWPString cloned = new HWPString();
        cloned.copy(this);
        return cloned;
    }

    public void copy(HWPString from) {
        bytes = from.bytes;
    }

    public int getWCharsSize() {
        if (bytes != null) {
            return 2 + bytes.length;
        }
        return 2;
    }

    public boolean equals(HWPString other) {
        return Arrays.equals(bytes, other.bytes);
    }

    @Override
    public String toString() {
        return "HWPString{" +
                "string=" + (bytes==null?"null": new String(bytes, StandardCharsets.UTF_16LE)) +
                '}';
    }
}
