package ext.org.apache.poi.hhwpf.model.datarecord.numbering;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 문단 머리 정보 객체
 *
 * @author neolord
 */
public class ParagraphHeadInfo {
    /**
     * 속성
     */
    private ParagraphHeadInfoProperty property;
    /**
     * 너비 보정값
     */
    private int correctionValueForWidth;
    /**
     * 본문과의 거리
     */
    private int distanceFromBody;
    /**
     * 참조된 글자 모양 아이디
     */
    private long charShapeID;

    /**
     * 생성자
     */
    public ParagraphHeadInfo() {
        property = new ParagraphHeadInfoProperty();
    }
    public ParagraphHeadInfo(long propertyValue, int widthValue, int distanceValue, long charShapeIDValue) {
        this.property = new ParagraphHeadInfoProperty(propertyValue);
        this.correctionValueForWidth = widthValue;
        this.distanceFromBody = distanceValue;
        this.charShapeID = charShapeIDValue;

    }
    public ParagraphHeadInfo(StreamReader sr) throws IOException {
        this.property = new ParagraphHeadInfoProperty(sr.readUInt4());
        this.correctionValueForWidth = sr.readUInt2();
        this.distanceFromBody = sr.readUInt2();
        this.charShapeID = sr.readUInt4();
    }

    /**
     * 문단 머리 정보의 속성 객체를 반환한다.
     *
     * @return 문단 머리 정보의 속성 객체
     */
    public ParagraphHeadInfoProperty getProperty() {
        return property;
    }

    /**
     * 너비 보정값을 반환한다.
     *
     * @return 너비 보정값
     */
    public int getCorrectionValueForWidth() {
        return correctionValueForWidth;
    }

    /**
     * 너비 보정값을 설정한다.
     *
     * @param correctionValueForWidth 너비 보정값
     */
    public void setCorrectionValueForWidth(int correctionValueForWidth) {
        this.correctionValueForWidth = correctionValueForWidth;
    }

    /**
     * 본문과의 거리를 반환한다.
     *
     * @return 본문과의 거리
     */
    public int getDistanceFromBody() {
        return distanceFromBody;
    }

    /**
     * 본문과의 거리를 설정한다.
     *
     * @param distanceFromBody 본문과의 거리
     */
    public void setDistanceFromBody(int distanceFromBody) {
        this.distanceFromBody = distanceFromBody;
    }

    /**
     * 참조된 글자 모양 아이디를 반환한다.
     *
     * @return 참조된 글자 모양 아이디
     */
    public long getCharShapeID() {
        return charShapeID;
    }

    /**
     * 참조된 글자 모양 아이디를 설정한다.
     *
     * @param charShapeID 참조된 글자 모양 아이디
     */
    public void setCharShapeID(long charShapeID) {
        this.charShapeID = charShapeID;
    }

    public void copy(ParagraphHeadInfo from) {
        property.copy(from.property);
        correctionValueForWidth = from.correctionValueForWidth;
        distanceFromBody = from.distanceFromBody;
        charShapeID = from.charShapeID;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.property.getValue());
        sw.writeUInt2(this.correctionValueForWidth);
        sw.writeUInt2(this.distanceFromBody);
        sw.writeUInt4(this.charShapeID);
    }
}
