package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.lineinfo;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;

import java.io.IOException;

/**
 * 테두리 선 정보
 *
 * @author neolord
 */
public class LineInfo implements StreamWritable {
    /**
     * 선 색상
     */
    private Color4Byte color;
    /**
     * 선 굵기
     */
    private int thickness;
    /**
     * 속성
     */
    private LineInfoProperty property;
    /**
     * outline style
     */
    private OutlineStyle outlineStyle;

    /**
     * 생성자
     */
    public LineInfo() {
        color = new Color4Byte();
        property = new LineInfoProperty();
    }

    public LineInfo(StreamReader sr) throws IOException {
        this.color = new Color4Byte(sr.readUInt4());
        this.thickness = sr.readSInt4();
        this.property = new LineInfoProperty(sr.readUInt4());
        this.outlineStyle = OutlineStyle.valueOf( (byte) sr.readUInt1());
    }

    /**
     * 선 색상 객체를 반환한다.
     *
     * @return 선 색상 객체
     */
    public Color4Byte getColor() {
        return color;
    }

    /**
     * 선 굵기를 반환한다.
     *
     * @return 선 굵기
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * 선 굵기를 설정한다.
     *
     * @param thickness 선 굵기
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public LineInfoProperty getProperty() {
        return property;
    }

    /**
     * outline style를 반환한다.
     *
     * @return outline style
     */
    public OutlineStyle getOutlineStyle() {
        return outlineStyle;
    }

    /**
     * outline style를 설정한다.
     *
     * @param outlineStyle outline style
     */
    public void setOutlineStyle(OutlineStyle outlineStyle) {
        this.outlineStyle = outlineStyle;
    }

    public void copy(LineInfo from) {
        color.copy(from.color);
        thickness = from.thickness;
        property.copy(from.property);
        outlineStyle = from.outlineStyle;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getColor().getValue());
        sw.writeSInt4(this.getThickness());
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt1(this.getOutlineStyle().getValue());
    }

    @Override
    public String toString() {
        return "LineInfo{" +
                "color=" + color.getValue() +
                ", thickness=" + thickness +
                ", property=" + property.getValue() +
                ", outlineStyle=" + outlineStyle.getValue() +
                '}';
    }
}
