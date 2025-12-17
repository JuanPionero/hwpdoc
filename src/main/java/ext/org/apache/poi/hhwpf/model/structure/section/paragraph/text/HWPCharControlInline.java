package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;
import java.util.Map;

/**
 * 인라인 컨트롤 character
 *
 * @author neolord
 */
public class HWPCharControlInline extends HWPChar implements StreamWritable {
    // 참고: reference 5.0 가이드 내,
    // 표 6 제어 문자
    // 표 128 필드 컨트롤 ID
    public static final Map<String, Integer> additionCodeMap = Map.ofEntries(
// 필드의 끝
            Map.entry("%unk", 0x0004),
            Map.entry("%dte", 0x0004),
            Map.entry("%ddt", 0x0004),
            Map.entry("%pat", 0x0004),
            Map.entry("%bmk", 0x0004),
            Map.entry("%mmg", 0x0004),
            Map.entry("%xrf", 0x0004),
            Map.entry("%fmu", 0x0004),
            Map.entry("%clk", 0x0004),
            Map.entry("%smr", 0x0004),
            Map.entry("%usr", 0x0004),
            Map.entry("%hlk", 0x0004),
            Map.entry("%sig", 0x0004),
            Map.entry("%%*d", 0x0004),
            Map.entry("%%*a", 0x0004),
            Map.entry("%%*C", 0x0004),
            Map.entry("%%*S", 0x0004),
            Map.entry("%%*T", 0x0004),
            Map.entry("%%*P", 0x0004),
            Map.entry("%%*L", 0x0004),
            Map.entry("%%*c", 0x0004),
            Map.entry("%%*h", 0x0004),
            Map.entry("%%*A", 0x0004),
            Map.entry("%%*i", 0x0004),
            Map.entry("%%*t", 0x0004),
            Map.entry("%%*r", 0x0004),
            Map.entry("%%*l", 0x0004),
            Map.entry("%%*n", 0x0004),
            Map.entry("%%*e", 0x0004),
            Map.entry("%spl", 0x0004),
            Map.entry("%%mr", 0x0004),
            Map.entry("%%me", 0x0004),
            Map.entry("%cpr", 0x0004),
            Map.entry("%toc", 0x0004)
            );
    /**
     * 추가 정보
     */
    private byte[] addition;

    /**
     * 생성자
     */
    public HWPCharControlInline() {
    }

    public HWPCharControlInline( StreamReader sr ) throws IOException {
        this.addition = sr.readBytes(12);
        this.code = sr.readSInt2();
    }

    public HWPCharControlInline(int code, byte[] addition) {
        if (addition.length != 12) {
            throw new IllegalArgumentException("Unexpected addition's length");
        }
        this.code = code;
        this.addition = addition;
    }

    /**
     * 글자의 종류을 반환한다.
     *
     * @return 글자의 타입
     */
    @Override
    public HWPCharType getType() {
        return HWPCharType.ControlInline;
    }

    /**
     * 추가 정보를 반환한다.
     *
     * @return 추가 정보
     */
    public byte[] getAddition() {
        return addition;
    }

    /**
     * 추가 정보를 설정한다.
     *
     * @param addition 추가 정보
     * @throws Exception
     */
    public void setAddition(byte[] addition) throws Exception {
        if (addition.length != 12) {
            throw new Exception("addition's length must be 12");
        }
        this.addition = addition;
    }

    public HWPChar clone() {
        HWPCharControlInline cloned = new HWPCharControlInline();
        cloned.code = code;

        if (addition != null) {
            cloned.addition = addition.clone();
        } else {
            cloned.addition = null;
        }

        return cloned;
    }

    @Override
    public int getCharSize() {
        return 8;
    }

    public boolean isHyperlinkEnd() {
        if (getCode() == 0x0004
                && hasAddition('%', 'h', 'l', 'k')) {
            return true;
        }
        return false;
    }

    private boolean hasAddition(char byte1, char byte2, char byte3, char byte4) {
        if (addition != null
                && addition[3] == byte1
                && addition[2] == byte2
                && addition[1] == byte3
                && addition[0] == byte4) {
            return true;
        }
        return false;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.getCode());
        sw.writeBytes(this.getAddition());
        sw.writeUInt2(this.getCode());
    }
}
