package ext.org.apache.poi.hhwpf.model.datarecord.borderfill;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.border.Border;
import java.io.IOException;
import java.util.Objects;

/**
 * 테두리/배경 객체에서 4방향의 각각의 선을 나타내는 객체
 *
 * @author neolord
 */
public class EachBorder implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(EachBorder.class);
    /**
     * 선 종류
     */
    private BorderType type;
    /**
     * 두께
     */
    private BorderThickness thickness;
    /**
     * 색상
     */
    private Color4Byte color;

    /**
     * 생성자
     */
    public EachBorder() {
        this(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0));
    }

    public EachBorder(BorderType borderType, BorderThickness borderThickness, Color4Byte color ) {
        this.type = borderType;
        this.thickness = borderThickness;
        this.color = color;
    }

   public EachBorder(StreamReader sr) throws IOException {
        this.type = BorderType.valueOf((byte) sr.readUInt1());
        this.thickness = BorderThickness.valueOf((byte) sr.readUInt1());
        this.color = new Color4Byte(sr.readUInt4());
    }

    /**
     * 선의 종류를 반환한다.
     *
     * @return 선의 종류
     */
    public BorderType getType() {
        return type;
    }

    /**
     * 선의 종류를 설정한다.
     *
     * @param type 선의 종류
     */
    public void setType(BorderType type) {
        this.type = type;
    }

    /**
     * 선의 두께를 반환한다.
     *
     * @return 선의 두께
     */
    public BorderThickness getThickness() {
        return thickness;
    }

    /**
     * 선의 두께를 설정한다.
     *
     * @param thickness 선의 두께
     */
    public void setThickness(BorderThickness thickness) {
        this.thickness = thickness;
    }

    /**
     * 선의 색상 객체을 반환한다.
     *
     * @return 선의 색상 객체
     */
    public Color4Byte getColor() {
        return color;
    }

    public void copy(EachBorder from) {
        type = from.type;
        thickness = from.thickness;
        color.copy(from.color);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt1(this.getType().getValue());
        sw.writeUInt1(this.getThickness().getValue());
        sw.writeUInt4(this.getColor().getValue());   
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type.getValue(), this.thickness.getValue(), this.color);
    }
}
