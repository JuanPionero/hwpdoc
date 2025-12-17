package ext.org.apache.poi.hhwpf.model.structure.section.control.table;

import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 테이블 정보를 포함하는 레코드
 *
 * @author neolord
 */
public class Table implements StreamWritable {
    /**
     * 속성
     */
    private TableProperty property;
    /**
     * 행의 개수
     */
    private int rowCount;
    /**
     * 열의 개수
     */
    private int columnCount;
    /**
     * 셀 사이의 공간
     */
    private int cellSpacing;
    /**
     * 왼쪽 안쪽 여백
     */
    private int leftInnerMargin;
    /**
     * 오른쪽 안쪽 여백
     */
    private int rightInnerMargin;
    /**
     * 위쪽 안쪽 여백
     */
    private int topInnerMargin;
    /**
     * 아래쪽 안쪽 여백
     */
    private int bottomInnerMargin;
    /**
     * 각 행의 셀의 개수를 저장하는 리스트
     */
    private ArrayList<Integer> cellCountOfRowList;
    /**
     * 참조된 테두리/배경 id
     */
    private int borderFillId;
    /**
     * 영역 속성 리스트 (5.0.1.0 이상)
     */
    private ArrayList<ZoneInfo> zoneInfoList;

    /**
     * 생성자
     */
    public Table() {
        property = new TableProperty();
        cellCountOfRowList = new ArrayList<Integer>();
        zoneInfoList = new ArrayList<ZoneInfo>();
    }

    public Table(StreamReader sr) throws IOException {
        this.property = new TableProperty( sr.readUInt4() );
        this.rowCount = sr.readUInt2();
        this.columnCount = sr.readUInt2();
        this.cellSpacing = sr.readUInt2();
        this.leftInnerMargin = sr.readUInt2();
        this.rightInnerMargin = sr.readUInt2();
        this.topInnerMargin = sr.readUInt2();
        this.bottomInnerMargin = sr.readUInt2();
        
        cellCountOfRowList = new ArrayList<Integer>();
        for(int index = 0; index < this.rowCount; index++) {
            this.cellCountOfRowList.add(sr.readUInt2());
        }
        this.borderFillId = sr.readUInt2();

        this.zoneInfoList = new ArrayList<ZoneInfo>();
        if(sr.getFileHeader().getFileVersion().isGreaterEqual((short)5,(short)0,(short)1,(short)0)) {
            int count = sr.readUInt2();
            for (int index = 0; index < count; index++) {
                this.zoneInfoList.add( new ZoneInfo(sr) );
            }
        }
    }

    public Table(Initializer<Table> initializer) throws Exception {
        initializer.init(this);
    }
    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public TableProperty getProperty() {
        return property;
    }

    /**
     * 행의 개수를 반환한다.
     *
     * @return 행의 개수
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * 행의 개수를 설정한다.
     *
     * @param rowCount 행의 개수
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * 열의 개수를 반환한다.
     *
     * @return 열의 개수
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * 열의 개수를 설정한다.
     *
     * @param columnCount 열의 개수
     */
    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    /**
     * 셀 사이의 공간의 크기를 반환한다.
     *
     * @return 셀 사이의 공간의 크기
     */
    public int getCellSpacing() {
        return cellSpacing;
    }

    /**
     * 셀 사이의 공간의 크기를 설정한다.
     *
     * @param cellSpacing 셀 사이의 공간의 크기
     */
    public void setCellSpacing(int cellSpacing) {
        this.cellSpacing = cellSpacing;
    }

    /**
     * 왼쪽 안쪽 여백의 크기를 반환한다.
     *
     * @return 왼쪽 안쪽 여백의 크기
     */
    public int getLeftInnerMargin() {
        return leftInnerMargin;
    }

    /**
     * 왼쪽 안쪽 여백의 크기를 설정한다.
     *
     * @param leftInnerMargin 왼쪽 안쪽 여백의 크기
     */
    public void setLeftInnerMargin(int leftInnerMargin) {
        this.leftInnerMargin = leftInnerMargin;
    }

    /**
     * 오른쪽 안쪽 여백의 크기를 반환한다.
     *
     * @return 오른쪽 안쪽 여백의 크기
     */
    public int getRightInnerMargin() {
        return rightInnerMargin;
    }

    /**
     * 오른쪽 안쪽 여백의 크기를 설정한다.
     *
     * @param rightInnerMargin 오른쪽 안쪽 여백의 크기
     */
    public void setRightInnerMargin(int rightInnerMargin) {
        this.rightInnerMargin = rightInnerMargin;
    }

    /**
     * 위쪽 안쪽 여백의 크기를 반환한다.
     *
     * @return 위쪽 안쪽 여백의 크기
     */
    public int getTopInnerMargin() {
        return topInnerMargin;
    }

    /**
     * 위쪽 안쪽 여백의 크기를 설정한다.
     *
     * @param topInnerMargin 위쪽 안쪽 여백의 크기
     */
    public void setTopInnerMargin(int topInnerMargin) {
        this.topInnerMargin = topInnerMargin;
    }

    /**
     * 아래쪽 안쪽 여백의 크기를 반환한다.
     *
     * @return 아래쪽 안쪽 여백의 크기
     */
    public int getBottomInnerMargin() {
        return bottomInnerMargin;
    }

    /**
     * 아래쪽 안쪽 여백의 크기를 설정한다.
     *
     * @param bottomInnerMargin 아래쪽 안쪽 여백의 크기
     */
    public void setBottomInnerMargin(int bottomInnerMargin) {
        this.bottomInnerMargin = bottomInnerMargin;
    }

    /**
     * 행의 셀 개수를 추가한다.
     *
     * @param cellCountOfRow 특정 행의 셀 개수
     */
    public void addCellCountOfRow(int cellCountOfRow) {
        cellCountOfRowList.add(cellCountOfRow);
    }

    public void setCellCountOfRowList(ArrayList<Integer> cellCountOfRowList) {
        this.cellCountOfRowList = cellCountOfRowList;
    }

    /**
     * 각 행의 셀의 개수를 저장하는 리스트를 반환한다.
     *
     * @return 각 행의 셀의 개수를 저장하는 리스트
     */
    public ArrayList<Integer> getCellCountOfRowList() {
        return cellCountOfRowList;
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
     * 새로운 영역 속성 객체를 생성하고 리스트에 추가한다. (5.0.1.0 이상)
     *
     * @return 새로 생성된 영역 속성 객체
     */
    public ZoneInfo addNewZoneInfo() {
        ZoneInfo zi = new ZoneInfo();
        zoneInfoList.add(zi);
        return zi;
    }

    /**
     * 영역 속성 리스트를 반환한다. (5.0.1.0 이상)
     *
     * @return 영역 속성 리스트
     */
    public ArrayList<ZoneInfo> getZoneInfoList() {
        return zoneInfoList;
    }

    public void copy(Table from) {
        property.copy(from.property);
        rowCount = from.rowCount;
        columnCount = from.columnCount;
        cellSpacing = from.cellSpacing;
        leftInnerMargin = from.leftInnerMargin;
        rightInnerMargin = from.rightInnerMargin;
        topInnerMargin = from.topInnerMargin;
        bottomInnerMargin = from.bottomInnerMargin;

        for (Integer integer : from.cellCountOfRowList) {
            cellCountOfRowList.add(integer);
        }

        borderFillId = from.borderFillId;

        zoneInfoList.clear();
        for (ZoneInfo zoneInfo : from.zoneInfoList) {
            zoneInfoList.add(zoneInfo.clone());
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_TABLE, getSize(sw.getFileVersion()));

        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt2(this.getRowCount());
        sw.writeUInt2(this.getColumnCount());
        sw.writeUInt2(this.getCellSpacing());
        sw.writeUInt2(this.getLeftInnerMargin());
        sw.writeUInt2(this.getRightInnerMargin());
        sw.writeUInt2(this.getTopInnerMargin());
        sw.writeUInt2(this.getBottomInnerMargin());

        for (int index = 0; index < this.getRowCount(); index++) {
            sw.writeUInt2(this.getCellCountOfRowList().get(index));
        }
        sw.writeUInt2(this.getBorderFillId());

        if (sw.getFileVersion().isGreaterEqual(5, 0, 1, 0)) {
            ArrayList<ZoneInfo> ziList = this.getZoneInfoList();
            int count = ziList.size();
            sw.writeUInt2(count);

            for (int index = 0; index < count; index++) {
                ziList.get(index).write(sw);
            }
        }
    }

    /**
     * 표 정보 레코드의 크기를 반환한다.
     *
     * @param version 파일 버전
     * @return 표 정보 레코드의 크기
     */
    public int getSize( FileVersion version) {
        int size = 0;

        size += 18;
        size += 2 * this.getRowCount();
        size += 2;
        if (version.isGreaterEqual(5, 0, 1, 0)) {
            size += 2;
            size += 10 * this.getZoneInfoList().size();
        }

        return size;
    }
}
