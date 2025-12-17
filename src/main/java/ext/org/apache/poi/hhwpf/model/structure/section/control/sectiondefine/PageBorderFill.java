package ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;

import java.io.IOException;

/**
 * 쪽 테두리/배경 정보에 대한 레코드
 *
 * @author neolord
 */
public class PageBorderFill implements StreamWritable {
    /**
     * 속성
     */
    private PageBorderFillProperty property;
    /**
     * 테두리/배경 위치 왼쪽 간격
     */
    private int leftGap;
    /**
     * 테두리/배경 위치 오른쪽 간격
     */
    private int rightGap;
    /**
     * 테두리/배경 위치 위쪽 간격
     */
    private int topGap;
    /**
     * 테두리/배경 위치 아래쪽 간격
     */
    private int bottomGap;
    /**
     * 참조된 테두리/배경의 id
     */
    private int borderFillId;

    /**
     * 생성자
     */
    public PageBorderFill() {
        this.property = new PageBorderFillProperty();
    }

    public PageBorderFill(int propertyValue,
                          int leftGapValue,
                          int rightGapValue,
                          int topGapValue,
                          int bottomGapValue,
                          int borderFillIdValue) {
        this.property = new PageBorderFillProperty(propertyValue);
        this.leftGap = leftGapValue;
        this.rightGap = rightGapValue;
        this.topGap = topGapValue;
        this.bottomGap = bottomGapValue;
        this.borderFillId = borderFillIdValue;
    }

    public PageBorderFill(StreamReader sr) throws IOException {
        this.property = new PageBorderFillProperty(sr.readUInt4());
        this.leftGap = sr.readUInt2();
        this.rightGap = sr.readUInt2();
        this.topGap = sr.readUInt2();
        this.bottomGap = sr.readUInt2();
        this.borderFillId = sr.readUInt2();
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public PageBorderFillProperty getProperty() {
        return property;
    }

    /**
     * 테두리/배경 위치 왼쪽 간격을 반환한다.
     *
     * @return 테두리/배경 위치 왼쪽 간격
     */
    public int getLeftGap() {
        return leftGap;
    }

    /**
     * 테두리/배경 위치 왼쪽 간격을 설정한다.
     *
     * @param leftGap 테두리/배경 위치 왼쪽 간격
     */
    public void setLeftGap(int leftGap) {
        this.leftGap = leftGap;
    }

    /**
     * 테두리/배경 위치 오른쪽 간격을 반환한다.
     *
     * @return 테두리/배경 위치 오른쪽 간격
     */
    public int getRightGap() {
        return rightGap;
    }

    /**
     * 테두리/배경 위치 오른쪽 간격을 설정한다.
     *
     * @param rightGap 테두리/배경 위치 오른쪽 간격
     */
    public void setRightGap(int rightGap) {
        this.rightGap = rightGap;
    }

    /**
     * 테두리/배경 위치 위쪽 간격을 반환한다.
     *
     * @return 테두리/배경 위치 위쪽 간격
     */
    public int getTopGap() {
        return topGap;
    }

    /**
     * 테두리/배경 위치 위쪽 간격을 설정한다.
     *
     * @param topGap 테두리/배경 위치 위쪽 간격
     */
    public void setTopGap(int topGap) {
        this.topGap = topGap;
    }

    /**
     * 테두리/배경 위치 아래쪽 간격을 반환한다.
     *
     * @return 테두리/배경 위치 아래쪽 간격
     */
    public int getBottomGap() {
        return bottomGap;
    }

    /**
     * 테두리/배경 위치 아래쪽 간격을 설정한다.
     *
     * @param bottomGap 테두리/배경 위치 아래쪽 간격
     */
    public void setBottomGap(int bottomGap) {
        this.bottomGap = bottomGap;
    }

    /**
     * 참조된 테두리/배경의 id를 반환한다.
     *
     * @return 참조된 테두리/배경의 id
     */
    public int getBorderFillId() {
        return borderFillId;
    }

    /**
     * 참조된 테두리/배경의 id를 설정한다.
     *
     * @param borderFillId 참조된 테두리/배경의 id
     */
    public void setBorderFillId(int borderFillId) {
        this.borderFillId = borderFillId;
    }

    public void copy(PageBorderFill from) {
        property.copy(from.property);
        leftGap = from.leftGap;
        rightGap = from.rightGap;
        topGap = from.topGap;
        bottomGap = from.bottomGap;
        borderFillId = from.borderFillId;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PAGE_BORDER_FILL, 14);
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt2(this.getLeftGap());
        sw.writeUInt2(this.getRightGap());
        sw.writeUInt2(this.getTopGap());
        sw.writeUInt2(this.getBottomGap());
        sw.writeUInt2(this.getBorderFillId());
    }
}
