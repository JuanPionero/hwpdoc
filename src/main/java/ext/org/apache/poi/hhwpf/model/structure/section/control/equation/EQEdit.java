package ext.org.apache.poi.hhwpf.model.structure.section.control.equation;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.Specification.EQUATION_VERSION;

/**
 * 수식 컨트롤의 수식 정보를 나타내는 레코드
 *
 * @author neolord
 */
public class EQEdit implements StreamWritable {
    /**
     * 속성(스크립트가 차지하는 범위. 첫 비트가 켜져 있으면 줄 단위, 꺼져 있으면 글자 단위??)
     */
    private long property;
    /**
     * 한글 수식 스크립트
     */
    private HWPString script;
    /**
     * 수식 글자 크기
     */
    private long letterSize;
    /**
     * 글자 색상
     */
    private Color4Byte letterColor;
    /**
     * base line
     */
    private short baseLine;
    /**
     * 알수 없는 2 byte;
     */
    private int unknown;
    /**
     * 버전 정보
     */
    private HWPString versionInfo;
    /**
     * 폰트 이름
     */
    private HWPString fontName;

    /**
     * 생성자
     */
    public EQEdit() {
        this.script = new HWPString();
        this.letterColor = new Color4Byte();
        this.versionInfo = new HWPString(EQUATION_VERSION);
        this.fontName = new HWPString();
    }

    public EQEdit(StreamReader sr) throws IOException, IllegalAccessException {
        this.property = sr.readUInt4();
        this.script = new HWPString(sr.readHWPString());
        this.letterSize = sr.readUInt4();
        this.letterColor = new Color4Byte(sr.readUInt4());
        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.baseLine = sr.readSInt2();

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.unknown = sr.readUInt2();

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.versionInfo = new HWPString(sr.readHWPString());

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.fontName = new HWPString(sr.readHWPString());
    }

    /**
     * 속성값을 반환한다.
     *
     * @return 속성값
     */
    public long getProperty() {
        return property;
    }

    /**
     * 속성값을 설정한다.
     *
     * @param property 속성값
     */
    public void setProperty(long property) {
        this.property = property;
    }

    /**
     * 한글 수식 스크립트를 반환한다.
     *
     * @return 한글 수식 스크립트
     */
    public HWPString getScript() {
        return script;
    }

    public void setScript(String str) {
        this.script = new HWPString(str);
    }

    /**
     * 수식 글자 크기를 반환한다.
     *
     * @return 수식 글자 크기
     */
    public long getLetterSize() {
        return letterSize;
    }

    /**
     * 수식 글자 크기를 설정한다.
     *
     * @param letterSize 수식 글자 크기
     */
    public void setLetterSize(long letterSize) {
        this.letterSize = letterSize;
    }

    /**
     * 글자 색상 객체를 반환한다.
     *
     * @return 글자 색상 객체
     */
    public Color4Byte getLetterColor() {
        return letterColor;
    }

    /**
     * base line을 반환한다.
     *
     * @return base line
     */
    public short getBaseLine() {
        return baseLine;
    }

    /**
     * base line을 설정한다.
     *
     * @param baseLine base line
     */
    public void setBaseLine(short baseLine) {
        this.baseLine = baseLine;
    }

    /**
     * 알수 없는 2 byte를 반환한다.
     */
    public int getUnknown() {
        return unknown;
    }

    /**
     * 알수 없는 2 byte를 설정한다.
     *
     * @param unknown 알수 없는 2 byte
     */
    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    /**
     * 버전 정보를 반환한다.
     *
     * @return 버전 정보
     */
    public HWPString getVersionInfo() {
        return versionInfo;
    }

    /**
     * 폰트 이름을 반환한다.
     *
     * @return 폰트 이름
     */
    public HWPString getFontName() {
        return fontName;
    }

    public void copy(EQEdit from) {
        property = from.property;
        script.copy(from.script);
        letterSize = from.letterSize;
        letterColor.copy(from.letterColor);
        baseLine = from.baseLine;
        unknown = from.unknown;
        versionInfo.copy(from.versionInfo);
        fontName.copy(from.fontName);
    }

    @Override
    public String toString() {
        return "EQEdit{" +
                "property=" + property +
                ", script=" + script +
                ", letterSize=" + letterSize +
                ", letterColor=" + letterColor.getValue() +
                ", baseLine=" + baseLine +
                ", unknown=" + unknown +
                ", versionInfo=" + versionInfo +
                ", fontName=" + fontName +
                '}';
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_EQEDIT, this.getSize());
        
        sw.writeUInt4(this.getProperty());
        sw.writeHWPString(this.getScript());
        sw.writeUInt4(this.getLetterSize());
        sw.writeUInt4(this.getLetterColor().getValue());
        sw.writeSInt2(this.getBaseLine());
        sw.writeUInt2(this.getUnknown());
        sw.writeHWPString(this.getVersionInfo());
        sw.writeHWPString(this.getFontName());
    }
    
    /**
     * 수식 정보 레코드의 크기를 반환한다.
     *
     * @return 수식 정보 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 4;
        size += this.getScript().getWCharsSize();
        size += 12;
        size += this.getVersionInfo().getWCharsSize();
        size += this.getFontName().getWCharsSize();
        return size;
    }
}
