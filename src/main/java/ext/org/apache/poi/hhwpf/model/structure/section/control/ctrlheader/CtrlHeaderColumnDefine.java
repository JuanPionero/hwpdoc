package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.EachBorder;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.FactoryForControl;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.columndefine.ColumnDefineHeaderProperty;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.columndefine.ColumnInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 단 정의 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderColumnDefine extends CtrlHeader implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(CtrlHeaderColumnDefine.class);
    /**
     * 속성
     */
    private ColumnDefineHeaderProperty property;
    /**
     * 단 사이 간격
     */
    private int gapBetweenColumn;
    /**
     * 속성2(정보 없음)
     */
    private int property2;
    /**
     * 단 정보 리스트
     */
    private ArrayList<ColumnInfo> columnInfoList;
    /**
     * 단 구분선 정보
     */
    private EachBorder divideLine;

    /**
     * 생성자
     */
    public CtrlHeaderColumnDefine() {
        super(ControlType.ColumnDefine.getCtrlId());

        this.property = new ColumnDefineHeaderProperty();
        this.columnInfoList = new ArrayList<ColumnInfo>();
        this.divideLine = new EachBorder();
    }

    public CtrlHeaderColumnDefine(int propertyValue, int gapBetweenColumnValue, int property2Value, EachBorder divideLineValue) {
        super(ControlType.ColumnDefine.getCtrlId());
        this.property = new ColumnDefineHeaderProperty(propertyValue);
        this.columnInfoList = new ArrayList<ColumnInfo>();
        this.property2 = property2Value;
        this.divideLine = divideLineValue;
    }

    public CtrlHeaderColumnDefine(StreamReader sr) throws IOException, IllegalAccessException {
        super(ControlType.ColumnDefine.getCtrlId());

        this.property = new ColumnDefineHeaderProperty(sr.readUInt2());
        int count = this.property.getColumnCount();
        boolean sameWidth = this.property.isSameWidth();
        this.columnInfoList = new ArrayList<ColumnInfo>();

        if (count < 2 || sameWidth == true) {
            this.gapBetweenColumn = sr.readUInt2();
            this.property2 = sr.readUInt2();
        } else {
            this.property2 = sr.readUInt2();
            for (int index = 0; index < count; index++) {
                this.columnInfoList.add( new ColumnInfo( sr ) );
            }
        }

        this.divideLine = new EachBorder(sr);
        if(sr.isReadingDataRecordData()) {
            sr.readRestOfDataRecordData();
        }

    }

    /**
     * 단 정의 컨트롤의 속성 객체를 반환한다.
     *
     * @return 단 정의 컨트롤의 속성 객체
     */
    public ColumnDefineHeaderProperty getProperty() {
        return property;
    }

    /**
     * 단 사이 간격을 반환한다.
     *
     * @return 단 사이 간격
     */
    public int getGapBetweenColumn() {
        return gapBetweenColumn;
    }

    /**
     * 단 사이 간격를 설정한다.
     *
     * @param gapBetweenColumn 단 사이 간격
     */
    public void setGapBetweenColumn(int gapBetweenColumn) {
        this.gapBetweenColumn = gapBetweenColumn;
    }

    /**
     * 속성2를 반환한다.
     *
     * @return 속성2
     */
    public int getProperty2() {
        return property2;
    }

    /**
     * 속성2를 설정한다.
     *
     * @param property2 속성2
     */
    public void setProperty2(int property2) {
        this.property2 = property2;
    }

    /**
     * 새로운 단 정보 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 단 정보 객체
     */
    public ColumnInfo addNewColumnInfo() {
        ColumnInfo ci = new ColumnInfo();
        columnInfoList.add(ci);
        return ci;
    }

    /**
     * 단 정보 리스트를 반환한다.
     *
     * @return 단 정보 리스트
     */
    public ArrayList<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    /**
     * 단 구분선 정보를 반환한다.
     *
     * @return 단 구분선 종류
     */
    public EachBorder getDivideLine() {
        return divideLine;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderColumnDefine from2 = (CtrlHeaderColumnDefine) from;
        property.copy(from2.property);
        gapBetweenColumn = from2.gapBetweenColumn;
        property2 = from2.property2;

        for (ColumnInfo columnInfo : from2.columnInfoList) {
            columnInfoList.add(columnInfo.clone());
        }
        divideLine.copy(from2.divideLine);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, this.getSize());
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt2(this.getProperty().getValue());

        int columnCount = this.getProperty().getColumnCount();
        boolean sameWidth = this.getProperty().isSameWidth();
        if (columnCount < 2 || sameWidth == true) {
            sw.writeUInt2(this.getGapBetweenColumn());
            sw.writeUInt2(this.getProperty2());
        } else {
            sw.writeUInt2(this.getProperty2());
//            columnInfos(h, sw);
            for(ColumnInfo ci : this.columnInfoList) {
                ci.write(sw);
            }
        }

        sw.writeUInt1(this.getDivideLine().getType().getValue());
        sw.writeUInt1(this.getDivideLine().getThickness().getValue());
        sw.writeUInt4(this.getDivideLine().getColor().getValue());
    }
    
    public int getSize() {
        int size = 0;
        size += 6;

        int columnCount = this.getProperty().getColumnCount();
        boolean sameWidth = this.getProperty().isSameWidth();
        if (columnCount < 2 || sameWidth == true) {
            size += 4;
        } else {
            size += 2 + columnCount * 4;
        }
        size += 6;
        return size;
    }
}