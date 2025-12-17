package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlID;

import java.io.IOException;
import java.util.Map;

/**
 * 확장 컨트롤 Character
 *
 * @author neolord
 */
public class HWPCharControlExtend extends HWPChar implements StreamWritable {
    public static final Map<String, Integer> additionCodeMap = Map.ofEntries(Map.entry("secd", 0x0002),
            Map.entry("cold", 0x0002),
// 필드의 시작 - end 는 ControlInline에서
            Map.entry("%unk", 0x0003),
            Map.entry("%dte", 0x0003),
            Map.entry("%ddt", 0x0003),
            Map.entry("%pat", 0x0003),
            Map.entry("%bmk", 0x0003),
            Map.entry("%mmg", 0x0003),
            Map.entry("%xrf", 0x0003),
            Map.entry("%fmu", 0x0003),
            Map.entry("%clk", 0x0003),
            Map.entry("%smr", 0x0003),
            Map.entry("%usr", 0x0003),
            Map.entry("%hlk", 0x0003),
            Map.entry("%sig", 0x0003),
            Map.entry("%%*d", 0x0003),
            Map.entry("%%*a", 0x0003),
            Map.entry("%%*C", 0x0003),
            Map.entry("%%*S", 0x0003),
            Map.entry("%%*T", 0x0003),
            Map.entry("%%*P", 0x0003),
            Map.entry("%%*L", 0x0003),
            Map.entry("%%*c", 0x0003),
            Map.entry("%%*h", 0x0003),
            Map.entry("%%*A", 0x0003),
            Map.entry("%%*i", 0x0003),
            Map.entry("%%*t", 0x0003),
            Map.entry("%%*r", 0x0003),
            Map.entry("%%*l", 0x0003),
            Map.entry("%%*n", 0x0003),
            Map.entry("%%*e", 0x0003),
            Map.entry("%spl", 0x0003),
            Map.entry("%%mr", 0x0003),
            Map.entry("%%me", 0x0003),
            Map.entry("%cpr", 0x0003),
            Map.entry("%toc", 0x0003),

            Map.entry("tbl ", 0x000B),
            Map.entry("gso ", 0x000B),
            Map.entry("eqed", 0x000B),
            Map.entry("form", 0x000B),

            Map.entry("tcmt", 0x000F),

            Map.entry("head", 0x0010),
            Map.entry("foot", 0x0010),
            Map.entry("fn  ", 0x0011),
            Map.entry("en  ", 0x0011),
            Map.entry("atno", 0x0012),
            Map.entry("pghd", 0x0015),
            Map.entry("pgct", 0x0015),
            Map.entry("pgnp", 0x0015),
            Map.entry("idxm", 0x0016),
            Map.entry("bokm", 0x0016),
            Map.entry("tdut", 0x0017),
            Map.entry("tcps", 0x0017));
    /**
     * 추가 정보
     */
    private byte[] addition;

    /**
     * 생성자
     */
    public HWPCharControlExtend() {
    }
    public HWPCharControlExtend(StreamReader sr) throws IOException {
        this.addition = sr.readBytes(12);
        this.code = sr.readSInt2();
    }

    public HWPCharControlExtend(int code, byte[] addition) {
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
        return HWPCharType.ControlExtend;
    }

    /**
     * 컨트롤 객체의 Instance Id를 반환한다.
     *
     * @return 컨트롤 객체의 Instance Id
     */
    public String getInstanceId() {
        int bufferIndex = 0;
        boolean insert = false;
        byte[] buf = new byte[addition.length];
        for (int index = addition.length - 1; index >= 0; index--) {
            if (addition[index] != 0) {
                insert = true;
            }

            if (insert == true) {
                buf[bufferIndex++] = addition[index];
            }
        }
        return new String(buf, 0, bufferIndex);
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

    public boolean isSectionDefine() {
        if (getCode() == 0x0002 && hasAddition('s', 'e', 'c', 'd')) {
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

    public boolean isColumnDefine() {
        if (getCode() == 0x0002 && hasAddition('c', 'o', 'l', 'd')) {
            return true;
        }
        return false;
    }

    public boolean isFieldStart() {
        if (getCode() == 0x0003
                && addition != null) {
            long ctrlID = CtrlID.make((char) addition[3], (char) addition[2], (char) addition[1], (char) addition[0]);
            return ControlType.isField(ctrlID);
        }
        return false;
    }

    public boolean isHyperlinkStart() {
        if (getCode() == 0x0003 && hasAddition('%', 'h', 'l', 'k')) {
            return true;
        }
        return false;
    }

    public boolean isTable() {
        if (getCode() == 0x000b && hasAddition('t', 'b', 'l', ' ')) {
            return true;
        }
        return false;
    }

    public boolean isGSO() {
        if (getCode() == 0x000b && hasAddition('g', 's', 'o', ' ')) {
            return true;
        }
        return false;
    }

    public boolean isEquation() {
        if (getCode() == 0x000b && hasAddition('e', 'q', 'e', 'd')) {
            return true;
        }
        return false;
    }

    public boolean isForm() {
        if (getCode() == 0x000b && hasAddition('f', 'o', 'r', 'm')) {
            return true;
        }
        return false;
    }

    public boolean isHiddenComment() {
        if (getCode() == 0x000f && hasAddition('t', 'c', 'm', 't')) {
            return true;
        }
        return false;
    }

    public boolean isHeader() {
        if (getCode() == 0x0010 && hasAddition('h', 'e', 'a', 'd')) {
            return true;
        }
        return false;
    }

    public boolean isFooter() {
        if (getCode() == 0x0010 && hasAddition('f', 'o', 'o', 't')) {
            return true;
        }
        return false;
    }

    public boolean isFootNote() {
        if (getCode() == 0x11 && hasAddition('f', 'n', ' ', ' ')) {
            return true;
        }
        return false;
    }

    public boolean isEndNote() {
        if (getCode() == 0x11 && hasAddition('e', 'n', ' ', ' ')) {
            return true;
        }
        return false;
    }

    public boolean isAutoNumber() {
        if (getCode() == 0x12 && hasAddition('a', 't', 'n', 'o')) {
            return true;
        }
        return false;
    }

    public boolean isPageHide() {
        if (getCode() == 0x15 && hasAddition('p', 'g', 'h', 'd')) {
            return true;
        }
        return false;
    }

    public boolean isPageOddEvenAdjust() {
        if (getCode() == 0x15 && hasAddition('p', 'g', 'c', 't')) {
            return true;
        }
        return false;
    }

    public boolean isPageNumberPosition() {
        if (getCode() == 0x15 && hasAddition('p', 'g', 'n', 'p')) {
            return true;
        }
        return false;
    }

    public boolean isIndexMark() {
        if (getCode() == 0x0016 && hasAddition('i', 'd', 'x', 'm')) {
            return true;
        }
        return false;
    }

    public boolean isBookmark() {
        if (getCode() == 0x0016 && hasAddition('b', 'o', 'k', 'm')) {
            return true;
        }
        return false;
    }

    public boolean isAdditionalText() {
        if (getCode() == 0x0017 && hasAddition('t', 'd', 'u', 't')) {
            return true;
        }
        return false;
    }

    public boolean isOverlappingLetter() {
        if (getCode() == 0x0017 && hasAddition('t', 'c', 'p', 's')) {
            return true;
        }
        return false;
    }

    public HWPChar clone() {
        HWPCharControlExtend cloned = new HWPCharControlExtend();
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

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.getCode());
        sw.writeBytes(this.getAddition());
        sw.writeUInt2(this.getCode());
    }
}