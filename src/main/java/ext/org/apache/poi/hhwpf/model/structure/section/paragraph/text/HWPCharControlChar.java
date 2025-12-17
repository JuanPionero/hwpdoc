package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text;

import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * 문자 컨트롤 Character
 *
 * @author neolord
 */
public class HWPCharControlChar extends HWPChar implements StreamWritable {
    /**
     * 생성자
     */
    public HWPCharControlChar() {
    }

    public HWPCharControlChar(int code) {
        this.code = code;
    }

    /**
     * 글자의 종류을 반환한다.
     *
     * @return 글자의 타입
     */
    @Override
    public HWPCharType getType() {
        return HWPCharType.ControlChar;
    }

    /**
     * 문자 코드를 설정한다.
     *
     * @param ch 문자열
     * @throws UnsupportedEncodingException
     */
    public void setCode(String ch) throws UnsupportedEncodingException {
        byte[] b = ch.getBytes(StandardCharsets.UTF_16LE);

        if (b.length >= 2) {
            setCode((((b[1] & 0xFF) << 8) | (b[0] & 0xFF)));
        } else if (b.length == 1) {
            setCode((b[0] & 0xFF));
        } else {
            setCode(0);
        }
    }

    public HWPChar clone() {
        HWPCharControlChar cloned = new HWPCharControlChar();
        cloned.code = code;
        return cloned;
    }

    @Override
    public int getCharSize() {
        return 1;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.getCode());
    }
}
