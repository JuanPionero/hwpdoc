package ext.org.apache.poi.hhwpf.model.structure.section.control.table;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.ParameterItem;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.ParameterSet;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.ParameterType;

import java.io.IOException;

/**
 * 셀의 문단 리스트 헤더 레코드
 *
 * @author neolord
 */
public class ListHeaderForCell implements StreamWritable {
    /**
     * 문단 개수
     */
    private int paraCount;
    /**
     * 속성
     */
    private ListHeaderPropertyForCell property;
    /**
     * 셀 주소(Column)
     */
    private int colIndex;
    /**
     * 셀 주소(Row)
     */
    private int rowIndex;
    /**
     * 열의 병합 개수
     */
    private int colSpan;
    /**
     * 행의 병합 개수
     */
    private int rowSpan;
    /**
     * 셀의 폭
     */
    private long width;
    /**
     * 셀의 높이
     */
    private long height;
    /**
     * 왼쪽 여백
     */
    private int leftMargin;
    /**
     * 오른쪽 여백
     */
    private int rightMargin;
    /**
     * 위쪽 여백
     */
    private int topMargin;
    /**
     * 아래쪽 여백
     */
    private int bottomMargin;
    /**
     * 참조된 테두리/배경 id
     */
    private int borderFillId;
    /**
     * 텍스트 폭
     */
    private long textWidth;
    /**
     * 필드 이름
     */
    private String fieldName;

    /**
     * 생성자
     */
    public ListHeaderForCell() {
        property = new ListHeaderPropertyForCell();
    }

    public ListHeaderForCell(StreamReader sr) throws IOException {
        this.paraCount = sr.readSInt4();
        this.property = new ListHeaderPropertyForCell(sr.readUInt4());
        this.colIndex = sr.readUInt2();
        this.rowIndex = sr.readUInt2();
        this.colSpan = sr.readUInt2();
        this.rowSpan = sr.readUInt2();
        this.width = sr.readUInt4();
        this.height = sr.readUInt4();
        this.leftMargin = sr.readUInt2();
        this.rightMargin = sr.readUInt2();
        this.topMargin = sr.readUInt2();
        this.bottomMargin = sr.readUInt2();
        this.borderFillId = sr.readUInt2();
        this.textWidth = sr.readUInt4();

        if(sr.isReadingDataRecordData()) {
            short flag = sr.readUInt1();
            if(flag ==0xff) {
                ParameterSet ps = new ParameterSet(sr);
                if(ps.getId()==0x21b) {
                    for(ParameterItem pi : ps.getParameterItemList()) {
                        if(pi.getId() == 0x4000 && pi.getType() == ParameterType.String) {
                            this.setFieldName(pi.getValue_BSTR());
                        }
                    }
                }
            }
            sr.readRestOfDataRecordData();
        }
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
    public ListHeaderPropertyForCell getProperty() {
        return property;
    }

    /**
     * 셀 주소(Column)을 반환한다.
     *
     * @return 셀 주소(Column)
     */
    public int getColIndex() {
        return colIndex;
    }

    /**
     * 셀 주소(Column)를 설정한다.
     *
     * @param colIndex 셀 주소(Column)
     */
    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    /**
     * 셀 주소(Row)를 반환한다.
     *
     * @return 셀 주소(Row)
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * 셀 주소(Row)를 설정한다.
     *
     * @param rowIndex 셀 주소(Row)
     */
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * 열의 병합 개수를 반환한다.
     *
     * @return 열의 병합 개수
     */
    public int getColSpan() {
        return colSpan;
    }

    /**
     * 열의 병합 개수를 설정한다.
     *
     * @param colSpan 열의 병합 개수
     */
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    /**
     * 행의 병합 개수를 반환한다.
     *
     * @return 행의 병합 개수
     */
    public int getRowSpan() {
        return rowSpan;
    }

    /**
     * 행의 병합 개수를 설정한다.
     *
     * @param rowSpan 행의 병합 개수
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    /**
     * 셀의 폭을 반환한다.
     *
     * @return 셀의 폭
     */
    public long getWidth() {
        return width;
    }

    /**
     * 셀의 폭을 설정한다.
     *
     * @param width 셀의 폭
     */
    public void setWidth(long width) {
        this.width = width;
    }

    /**
     * 셀의 높이를 반환한다.
     *
     * @return 셀의 높이
     */
    public long getHeight() {
        return height;
    }

    /**
     * 셀의 높이을 설정한다.
     *
     * @param height 셀의 높이
     */
    public void setHeight(long height) {
        this.height = height;
    }

    /**
     * 왼쪽 여백의 크기를 반환한다.
     *
     * @return 왼쪽 여백의 크기
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * 왼쪽 여백의 크기를 설정한다.
     *
     * @param leftMargin 왼쪽 여백의 크기
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * 오른쪽 여백의 크기를 반환한다.
     *
     * @return 오른쪽 여백의 크기
     */
    public int getRightMargin() {
        return rightMargin;
    }

    /**
     * 오른쪽 여백의 크기를 설정한다.
     *
     * @param rightMargin 오른쪽 여백의 크기
     */
    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    /**
     * 위쪽 여백의 크기를 반환한다.
     *
     * @return 위쪽 여백의 크기
     */
    public int getTopMargin() {
        return topMargin;
    }

    /**
     * 위쪽 여백의 크기를 설정한다.
     *
     * @param topMargin 위쪽 여백의 크기
     */
    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    /**
     * 아래쪽 여백의 크기를 반환한다.
     *
     * @return 아래쪽 여백의 크기
     */
    public int getBottomMargin() {
        return bottomMargin;
    }

    /**
     * 아래쪽 여백의 크기를 설정한다.
     *
     * @param bottomMargin 아래쪽 여백의 크기
     */
    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    /**
     * 참조된 테두리/배경 id를 반환한다.
     *
     * @return 참조된 테두리/배경 id
     */
    public int getBorderFillId() {
        return borderFillId;
    }

    /**
     * 참조된 테두리/배경 id를 설정한다.
     *
     * @param borderFillId 참조된 테두리/배경 id
     */
    public void setBorderFillId(int borderFillId) {
        this.borderFillId = borderFillId;
    }

    /**
     * 텍스트 폭을 반환한다.
     *
     * @return 텍스트 폭
     */
    public long getTextWidth() {
        return textWidth;
    }

    /**
     * 텍스트 폭을 설정한다.
     *
     * @param textWidth 텍스트 폭
     */
    public void setTextWidth(long textWidth) {
        this.textWidth = textWidth;
    }

    /**
     * 필드 이름을 반환한다.
     *
     * @return 필드 이름
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 필드 이름을 설정한다.
     *
     * @param fieldName 필드 이름
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void copy(ListHeaderForCell from) {
        paraCount = from.paraCount;
        property.copy(from.property);
        colIndex = from.colIndex;
        rowIndex = from.rowIndex;
        colSpan = from.colSpan;
        rowSpan = from.rowSpan;
        width = from.width;
        height = from.height;
        leftMargin = from.leftMargin;
        rightMargin = from.rightMargin;
        topMargin = from.topMargin;
        bottomMargin = from.bottomMargin;
        borderFillId = from.borderFillId;
        textWidth = from.textWidth;
        fieldName = from.fieldName;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        // ForCell.listHeader 코드를 재작성
        ParameterSet psFieldName = this.fieldName==null?null:new ParameterSet(this.fieldName);
        int size = 38 + 1 + (psFieldName==null?1:psFieldName.getSize()) + 8;
        sw.writeDataRecordHeader(TagID.HWPTAG_LIST_HEADER, size);

        sw.writeSInt4(this.getParaCount());
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt2(this.getColIndex());
        sw.writeUInt2(this.getRowIndex());
        sw.writeUInt2(this.getColSpan());
        sw.writeUInt2(this.getRowSpan());
        sw.writeUInt4(this.getWidth());
        sw.writeUInt4(this.getHeight());
        sw.writeUInt2(this.getLeftMargin());
        sw.writeUInt2(this.getRightMargin());
        sw.writeUInt2(this.getTopMargin());
        sw.writeUInt2(this.getBottomMargin());
        sw.writeUInt2(this.getBorderFillId());
        sw.writeUInt4(this.getTextWidth());
        if (psFieldName != null) {
            short flag = 0xff;
            sw.writeUInt1(flag);

            psFieldName.write(sw);
//            ForParameterSet.write(psFieldName, sw);
        } else {
            short flag = 0x0;
            sw.writeUInt1(flag);
        }
        sw.writeZero(8);
    }
}
