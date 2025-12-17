package ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;

import java.io.IOException;
import java.util.Objects;

/**
 * 단색 채우기 객체
 *
 * @author neolord
 */
public class PatternFill implements StreamWritable {
    /**
     * 배경색
     */
    private Color4Byte backColor;
    /**
     * 무늬색
     */
    private Color4Byte patternColor;
    /**
     * 무늬 종류
     */
    private PatternType patternType;

    /**
     * 생성자
     */
    public PatternFill() {
        backColor = new Color4Byte();
        patternColor = new Color4Byte();
    }

    public PatternFill(Color4Byte backColor, Color4Byte patternColor, PatternType patternType) {
        this.backColor = backColor;
        this.patternColor = patternColor;
        this.patternType = patternType;
    }

    public PatternFill(StreamReader sr) throws IOException {
        this.backColor = new Color4Byte(sr.readUInt4());
        this.patternColor = new Color4Byte(sr.readUInt4());
        this.patternType = PatternType.valueOf((byte) sr.readSInt4());

    }

    /**
     * 배경색 객체를 반환한다.
     *
     * @return 배경색 객체
     */
    public Color4Byte getBackColor() {
        return backColor;
    }

    /**
     * 무늬색 객체를 반환한다.
     *
     * @return 무늬색 객체
     */
    public Color4Byte getPatternColor() {
        return patternColor;
    }

    /**
     * 무늬 종류를 반환한다.
     *
     * @return 무늬 종류
     */
    public PatternType getPatternType() {
        return patternType;
    }

    /**
     * 무늬 종류를 설정한다.
     *
     * @param patternType 무늬 종류
     */
    public void setPatternType(PatternType patternType) {
        this.patternType = patternType;
    }

    public void copy(PatternFill from) {
        backColor.copy(from.backColor);
        patternColor.copy(from.patternColor);
        patternType = from.patternType;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getBackColor().getValue());
        sw.writeUInt4(this.getPatternColor().getValue());
        sw.writeUInt4(this.getPatternType().getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.backColor, this.patternColor, this.patternType.getValue());
    }
}
