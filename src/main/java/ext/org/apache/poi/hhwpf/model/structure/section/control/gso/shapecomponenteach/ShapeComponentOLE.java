package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.lineinfo.LineInfoProperty;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ole.ShapeComponentOLEProperty;

import java.io.IOException;

/**
 * OLE 개체 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentOLE implements StreamWritable {
    /**
     * 속성
     */
    private ShapeComponentOLEProperty property;
    /**
     * 오브젝트 자체의 extent x크기
     */
    private int extentWidth;
    /**
     * 오브젝트 자체의 extent y크기
     */
    private int extentHeight;
    /**
     * 오브젝트가 사용하는 스토리지의 BinData ID
     */
    private int binDataId;
    /**
     * 테두리 색
     */
    private Color4Byte borderColor;
    /**
     * 테두리 두꼐
     */
    private int borderThickness;
    /**
     * 테두리 속성
     */
    private LineInfoProperty borderProperty;
    /**
     * 알 수 없는 데이터
     */
    private byte[] unknown;

    /**
     * 생성자
     */
    public ShapeComponentOLE() {
        property = new ShapeComponentOLEProperty();
        borderColor = new Color4Byte();
        borderProperty = new LineInfoProperty();
    }

    public ShapeComponentOLE(StreamReader sr) throws IOException {
        this.property = new ShapeComponentOLEProperty(sr.readUInt4());
        this.extentWidth = sr.readSInt4();
        this.extentHeight = sr.readSInt4();
        this.binDataId = sr.readUInt2();
        this.borderColor = new Color4Byte(sr.readUInt4());
        this.borderThickness = sr.readSInt4();
        this.borderProperty = new LineInfoProperty(sr.readUInt4());
        sr.readRestOfDataRecordData();
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public ShapeComponentOLEProperty getProperty() {
        return property;
    }

    /**
     * 오브젝트 자체의 extent x크기를 반환한다.
     *
     * @return 오브젝트 자체의 extent x크기
     */
    public int getExtentWidth() {
        return extentWidth;
    }

    /**
     * 오브젝트 자체의 extent x크기를 설정한다.
     *
     * @param extentWidth 오브젝트 자체의 extent x크기
     */
    public void setExtentWidth(int extentWidth) {
        this.extentWidth = extentWidth;
    }

    /**
     * 오브젝트 자체의 extent y크기를 반환한다.
     *
     * @return 오브젝트 자체의 extent y크기
     */
    public int getExtentHeight() {
        return extentHeight;
    }

    /**
     * 오브젝트 자체의 extent y크기를 설정한다.
     *
     * @param extentHeight 오브젝트 자체의 extent y크기
     */
    public void setExtentHeight(int extentHeight) {
        this.extentHeight = extentHeight;
    }

    /**
     * 오브젝트가 사용하는 스토리지의 BinData ID를 반환한다.
     *
     * @return 오브젝트가 사용하는 스토리지의 BinData ID
     */
    public int getBinDataId() {
        return binDataId;
    }

    /**
     * 오브젝트가 사용하는 스토리지의 BinData ID를 설정한다.
     *
     * @param binDataId 오브젝트가 사용하는 스토리지의 BinData ID
     */
    public void setBinDataId(int binDataId) {
        this.binDataId = binDataId;
    }

    /**
     * 테두리 색상 객체를 반환한다.
     *
     * @return 테두리 색상 객체
     */
    public Color4Byte getBorderColor() {
        return borderColor;
    }

    /**
     * 테두리 두꼐를 반환한다.
     *
     * @return 테두리 두꼐
     */
    public int getBorderThickness() {
        return borderThickness;
    }

    /**
     * 테두리 두꼐를 설정한다.
     *
     * @param borderThickness 테두리 두꼐
     */
    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    /**
     * 알 수 없는 데이터를 반환한다.
     *
     * @return 알 수 없는 데이터
     */
    public byte[] getUnknown() {
        return unknown;
    }

    /**
     * 알 수 없는 데이터를 설정한다.
     *
     * @param unknown
     */
    public void setUnknown(byte[] unknown) {
        this.unknown = unknown;
    }

    /**
     * 테두리 속성 객체를 반환한다.
     *
     * @return 테두리 속성 객체
     */
    public LineInfoProperty getBorderProperty() {
        return borderProperty;
    }

    public void copy(ShapeComponentOLE from) {
        property.copy(from.property);
        extentWidth = from.extentWidth;
        extentHeight = from.extentHeight;
        binDataId = from.binDataId;
        borderColor.copy(from.borderColor);
        borderThickness = from.borderThickness;
        borderProperty.copy(from.borderProperty);

        if (from.unknown != null) {
            unknown = from.unknown.clone();
        } else {
            unknown = null;
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT_OLE, this.getSize());
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeSInt4(this.getExtentWidth());
        sw.writeSInt4(this.getExtentHeight());
        sw.writeUInt2(this.getBinDataId());
        sw.writeUInt4(this.getBorderColor().getValue());
        sw.writeSInt4(this.getBorderThickness());
        sw.writeUInt4(this.getBorderProperty().getValue());
        if (this.getUnknown() != null) {
            sw.writeBytes(this.getUnknown());
        }
    }
    
    public long getSize() {
        if (this.getUnknown() != null) {
            return 26 + this.getUnknown().length;
        }
        return 26;
    }
}
