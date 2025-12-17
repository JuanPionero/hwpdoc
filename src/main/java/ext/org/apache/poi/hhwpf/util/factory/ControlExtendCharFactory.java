package ext.org.apache.poi.hhwpf.util.factory;

import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPCharControlExtend;

public class ControlExtendCharFactory {
    private ControlExtendCharFactory() {}
    public static HWPCharControlExtend make(String str) {
        int code = HWPCharControlExtend.additionCodeMap.getOrDefault(str, -1);
        if(code==-1) {
            throw new IllegalArgumentException("Unexpected string value");
        }
        byte[] addition = new byte[12];
        addition[3] = (byte) str.codePointAt(0);
        addition[2] = (byte) str.codePointAt(1);
        addition[1] = (byte) str.codePointAt(2);
        addition[0] = (byte) str.codePointAt(3);
        return new HWPCharControlExtend((short) code, addition);
    }

    public static void append(Paragraph paragraph, String str) {
        paragraph.getParaText().getCharList().add(make(str));
    }

}
