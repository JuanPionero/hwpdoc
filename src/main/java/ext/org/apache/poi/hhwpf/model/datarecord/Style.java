package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.style.StyleProperty;
import ext.org.apache.poi.hhwpf.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 스타일에 대한 레코드
 *
 * @author neolord
 */
public class Style implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(Style.class);
    /**
     * 한글 이름
     */
    private String hangulName;
    /**
     * 영문 이름
     */
    private String englishName;
    /**
     * 속성
     */
    private StyleProperty proeprty;
    /**
     * 다음 스타일 아이디
     */
    private short nextStyleId;
    /**
     * 언어 아이디
     */
    private short languageId;
    /**
     * 참조된 문단 모양 아이디
     */
    private int paraShapeId;
    /**
     * 참조된 글자 모양 아이디
     */
    private int charShapeId;

    /**
     * 생성자
     */
    public Style() {
        proeprty = new StyleProperty();
    }
    public Style(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.hangulName = sr.readUTF16LEString();
        this.englishName = sr.readUTF16LEString();
        this.proeprty = new StyleProperty(sr);
        this.nextStyleId = sr.readUInt1();
        this.languageId = sr.readSInt2();
        this.paraShapeId = sr.getCorrectedParaShapeId( sr.readUInt2() ) ;
        this.charShapeId = sr.readUInt2();
        sr.readBytes(2);
    }

    public Style(String hangulName, String englishName, short propertyValue, short nextStyleId, short languageId, int paraShapeId, int charShapeId) {
        this.hangulName = hangulName;
        this.englishName = englishName;
        this.proeprty = new StyleProperty(propertyValue);
        this.nextStyleId = nextStyleId;
        this.languageId = languageId;
        this.paraShapeId = paraShapeId;
        this.charShapeId = charShapeId;
    }

    /**
     * 한글 이름을 반환한다.
     *
     * @return 한글 이름
     */
    public String getHangulName() {
        return hangulName;
    }

    /**
     * 한글 이름을 설정한다.
     *
     * @param hangulName 한글 이름
     */
    public void setHangulName(String hangulName) {
        this.hangulName = hangulName;
    }

    /**
     * 영문 이름을 반환한다.
     *
     * @return 영문 이름
     */
    public String getEnglishName() {
        return englishName;
    }

    /**
     * 영문 이름을 설정한다.
     *
     * @param englishName 영문 이름
     */
    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    /**
     * 스타일의 속성 객체를 반환한다.
     *
     * @return 스타일의 속성 객체
     */
    public StyleProperty getProeprty() {
        return proeprty;
    }

    /**
     * 다음 스타일 아이디를 반환한다.
     *
     * @return 다음 스타일 아이디
     */
    public short getNextStyleId() {
        return nextStyleId;
    }

    /**
     * 다음 스타일 아이디를 설정한다.
     *
     * @param nextStyleId 다음 스타일 아이디
     */
    public void setNextStyleId(short nextStyleId) {
        this.nextStyleId = nextStyleId;
    }

    /**
     * 언어 아이디를 반환한다.
     *
     * @return 언어 아이디
     */
    public short getLanguageId() {
        return languageId;
    }

    /**
     * 언어 아이디를 설정한다.
     *
     * @param languageId
     */
    public void setLanguageId(short languageId) {
        this.languageId = languageId;
    }

    /**
     * 참조된 문단 모양 아이디를 반환한다.
     *
     * @return 참조된 문단 모양 아이디
     */
    public int getParaShapeId() {
        return paraShapeId;
    }

    /**
     * 참조된 문단 모양 아이디를 설정한다.
     *
     * @param paraShapeId 참조된 문단 모양 아이디
     */
    public void setParaShapeId(int paraShapeId) {
        this.paraShapeId = paraShapeId;
    }

    /**
     * 참조된 글자 모양 아이디를 반환한다.
     *
     * @return 참조된 글자 모양 아이디
     */
    public int getCharShapeId() {
        return charShapeId;
    }

    /**
     * 참조된 글자 모양 아이디를 설정한다.
     *
     * @param charShapeId 참조된 글자 모양 아이디
     */
    public void setCharShapeId(int charShapeId) {
        this.charShapeId = charShapeId;
    }

    public Style clone() {
        Style cloned = new Style();
        cloned.hangulName = hangulName;
        cloned.englishName = englishName;
        cloned.proeprty.copy(proeprty);
        cloned.nextStyleId = nextStyleId;
        cloned.languageId = languageId;
        cloned.paraShapeId = paraShapeId;
        cloned.charShapeId = charShapeId;
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_STYLE, getSize());
        
        sw.writeUTF16LEString(this.getHangulName());
        sw.writeUTF16LEString(this.getEnglishName());
        sw.writeUInt1(this.getProeprty().getValue());
        sw.writeUInt1(this.getNextStyleId());
        sw.writeUInt2(this.getLanguageId());
        sw.writeUInt2(sw.getCorrectedParaShapeId(this.getParaShapeId()));
        sw.writeUInt2(this.getCharShapeId());
        sw.writeZero(2);
    }

    /**
     * 스타일 레코드의 크기를 반환한다.
     *
     * @return 스타일 레코드의 크기
     */
    public final int getSize() {
        int size = 0;
        size += StringUtil.getUTF16LEStringSize(this.getHangulName());
        size += StringUtil.getUTF16LEStringSize(this.getEnglishName());
        size += 8 + 2;
        return size;
    }
}
