package ext.org.apache.poi.hhwpf.model.datarecord;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 레이아웃 호환성에 대한 레코드
 *
 * @author neolord
 */
public class LayoutCompatibility {
    private static final Logger logger = LoggerFactory.getLogger(LayoutCompatibility.class);
    /**
     * 글자 단위 서식
     */
    private long letterLevelFormat;
    /**
     * 문단 단위 서식
     */
    private long paragraphLevelFormat;
    /**
     * 구역 단위 서식
     */
    private long sectionLevelFormat;
    /**
     * 개체 단위 서식
     */
    private long objectLevelFormat;
    /**
     * 필드 단위 서식
     */
    private long fieldLevelFormat;

    /**
     * 생성자
     */
    public LayoutCompatibility() {
        this.letterLevelFormat = 0;
        this.paragraphLevelFormat = 0;
        this.sectionLevelFormat = 0;
        this.objectLevelFormat = 0;
        this.fieldLevelFormat = 0;
    }

    public LayoutCompatibility(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.letterLevelFormat = sr.readUInt4();
        this.paragraphLevelFormat = sr.readUInt4();
        this.sectionLevelFormat = sr.readUInt4();
        this.objectLevelFormat = sr.readUInt4();
        this.fieldLevelFormat = sr.readUInt4();
    }

    /**
     * 글자 단위 서식을 반환한다.
     *
     * @return 글자 단위 서식
     */
    public long getLetterLevelFormat() {
        return letterLevelFormat;
    }

    /**
     * 글자 단위 서식을 설정한다.
     *
     * @param letterLevelFormat 글자 단위 서식
     */
    public void setLetterLevelFormat(long letterLevelFormat) {
        this.letterLevelFormat = letterLevelFormat;
    }

    /**
     * 문단 단위 서식을 반환한다.
     *
     * @return 문단 단위 서식
     */
    public long getParagraphLevelFormat() {
        return paragraphLevelFormat;
    }

    /**
     * 문단 단위 서식을 설정한다.
     *
     * @param paragraphLevelFormat 문단 단위 서식
     */
    public void setParagraphLevelFormat(long paragraphLevelFormat) {
        this.paragraphLevelFormat = paragraphLevelFormat;
    }

    /**
     * 구역 단위 서식을 반환한다.
     *
     * @return 구역 단위 서식
     */
    public long getSectionLevelFormat() {
        return sectionLevelFormat;
    }

    /**
     * 구역 단위 서식을 설정한다.
     *
     * @param sectionLevelFormat 구역 단위 서식
     */
    public void setSectionLevelFormat(long sectionLevelFormat) {
        this.sectionLevelFormat = sectionLevelFormat;
    }

    /**
     * 개체 단위 서식을 반환한다.
     *
     * @return 개체 단위 서식
     */
    public long getObjectLevelFormat() {
        return objectLevelFormat;
    }

    /**
     * 개체 단위 서식을 설정한다.
     *
     * @param objectLevelFormat 개체 단위 서식
     */
    public void setObjectLevelFormat(long objectLevelFormat) {
        this.objectLevelFormat = objectLevelFormat;
    }

    /**
     * 필드 단위 서식을 반환한다.
     *
     * @return 필드 단위 서식
     */
    public long getFieldLevelFormat() {
        return fieldLevelFormat;
    }

    /**
     * 필드 단위 서식을 설정한다.
     *
     * @param fieldLevelFormat 필드 단위 서식
     */
    public void setFieldLevelFormat(long fieldLevelFormat) {
        this.fieldLevelFormat = fieldLevelFormat;
    }

    public LayoutCompatibility clone() {
        LayoutCompatibility cloned = new LayoutCompatibility();
        cloned.letterLevelFormat = letterLevelFormat;
        cloned.paragraphLevelFormat = paragraphLevelFormat;
        cloned.sectionLevelFormat = sectionLevelFormat;
        cloned.objectLevelFormat = objectLevelFormat;
        cloned.fieldLevelFormat = fieldLevelFormat;
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_LAYOUT_COMPATIBILITY, 20);

        sw.writeUInt4(this.letterLevelFormat);
        sw.writeUInt4(this.paragraphLevelFormat);
        sw.writeUInt4(this.sectionLevelFormat);
        sw.writeUInt4(this.objectLevelFormat);
        sw.writeUInt4(this.fieldLevelFormat);
    }
}
