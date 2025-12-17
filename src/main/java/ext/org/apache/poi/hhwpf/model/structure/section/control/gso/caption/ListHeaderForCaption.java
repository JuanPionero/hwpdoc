package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.caption;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.ListHeaderProperty;

import java.io.IOException;

/**
 * 캡션을 위한 문단 리스트 헤더 레코드
 *
 * @author neolord
 */
public class ListHeaderForCaption implements StreamWritable {
    /**
     * 문단 개수
     */
    private int paraCount;
    /**
     * 속성
     */
    private ListHeaderProperty property;
    /**
     * caption 속성
     */
    private ListHeaderCaptionProperty captionProperty;
    /**
     * 캡션 폭(세로 방향일 때만 사용)
     */
    private long captionWidth;
    /**
     * 캡션과 틀 사이 간격
     */
    private int spaceBetweenCaptionAndFrame;
    /**
     * 텍스트의 최대 길이(=객체 폭)
     */
    private long textWidth;

    /**
     * 생성자
     */
    public ListHeaderForCaption() {
        property = new ListHeaderProperty();
        captionProperty = new ListHeaderCaptionProperty();
    }
    public ListHeaderForCaption(StreamReader sr) throws IOException {
        this.paraCount = sr.readSInt4();
        this.property = new ListHeaderProperty(sr.readUInt4());
        this.captionProperty = new ListHeaderCaptionProperty(sr.readUInt4());
        this.captionWidth = sr.readUInt4();
        this.spaceBetweenCaptionAndFrame = sr.readUInt2();
        this.textWidth = sr.readUInt4();
        // 버전에 따라 8bytes가 있을 수 있음.
        sr.readRestOfDataRecordData();
    }

    /**
     * 문단 개수를 반환한다.
     *
     * @return 문단 개수
     */
    public int getParaCount() {
        return paraCount;
    }

    /**
     * 문단 개수를 설정한다.
     *
     * @param paraCount 문단 개수
     */
    public void setParaCount(int paraCount) {
        this.paraCount = paraCount;
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public ListHeaderProperty getProperty() {
        return property;
    }

    /**
     * caption 속성 객체를 반환한다.
     *
     * @return caption 속성 객체
     */
    public ListHeaderCaptionProperty getCaptionProperty() {
        return captionProperty;
    }

    /**
     * 캡션 폭을 반환한다. (세로 방향일 때만 사용)
     *
     * @return 캡션 폭
     */
    public long getCaptionWidth() {
        return captionWidth;
    }

    /**
     * 캡션 폭을 설정한다.
     *
     * @param captionWidth 캡션 폭
     */
    public void setCaptionWidth(long captionWidth) {
        this.captionWidth = captionWidth;
    }

    /**
     * 캡션과 틀 사이 간격의 크기를 반환한다.
     *
     * @return 캡션과 틀 사이 간격의 크기
     */
    public int getSpaceBetweenCaptionAndFrame() {
        return spaceBetweenCaptionAndFrame;
    }

    /**
     * 캡션과 틀 사이 간격의 크기를 설정한다.
     *
     * @param spaceBetweenCaptionAndFrame 캡션과 틀 사이 간격의 크기
     */
    public void setSpaceBetweenCaptionAndFrame(int spaceBetweenCaptionAndFrame) {
        this.spaceBetweenCaptionAndFrame = spaceBetweenCaptionAndFrame;
    }

    /**
     * 텍스트의 폭를 반환한다.
     *
     * @return 텍스트의 폭
     */
    public long getTextWidth() {
        return textWidth;
    }

    /**
     * 텍스트의 폭를 설정한다.
     *
     * @param textWidth 텍스트의 폭
     */
    public void setTextWidth(long textWidth) {
        this.textWidth = textWidth;
    }

    public void copy(ListHeaderForCaption from) {
        paraCount = from.paraCount;
        property.copy(from.property);
        captionProperty.copy(from.captionProperty);
        captionWidth = from.captionWidth;
        spaceBetweenCaptionAndFrame = from.spaceBetweenCaptionAndFrame;
        textWidth = from.textWidth;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeSInt4(this.getParaCount());
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt4(this.getCaptionProperty().getValue());
        sw.writeUInt4(this.getCaptionWidth());
        sw.writeUInt2(this.getSpaceBetweenCaptionAndFrame());
        sw.writeUInt4(this.getTextWidth());
        sw.writeZero(8);
    }
}
