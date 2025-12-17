package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.shadowinfo;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;

import java.io.IOException;

/**
 * 그림자 정보
 *
 * @author neolord
 */
public class ShadowInfo implements StreamWritable {
    /**
     * 그림자 종류
     */
    private ShadowType type;
    /**
     * 그림자 색
     */
    private Color4Byte color;
    /**
     * 가로 방향 이동 거리
     */
    private int offsetX;
    /**
     * 세로 방향 이동 거리
     */
    private int offsetY;
    /**
     * 투명도
     */
    private short transparent;

    /**
     * 생성자
     */
    public ShadowInfo() {
        color = new Color4Byte();
    }

    public ShadowInfo(StreamReader sr) throws IOException {
        this.type=ShadowType.valueOf((byte) sr.readUInt4());
        this.color = new Color4Byte(sr.readUInt4());
        this.offsetX = sr.readSInt4();
        this.offsetY = sr.readSInt4();
    }

    /**
     * 그림자 종류를 반환한다.
     *
     * @return 그림자 종류
     */
    public ShadowType getType() {
        return type;
    }

    /**
     * 그림자 종류를 섷정한다.
     *
     * @param type 그림자 종류
     */
    public void setType(ShadowType type) {
        this.type = type;
    }

    /**
     * 그림자 색상 객체를 반환한다.
     *
     * @return 그림자 색상 객체
     */
    public Color4Byte getColor() {
        return color;
    }

    /**
     * 가로 방향 이동 거리를 반환한다.
     *
     * @return 가로 방향 이동 거리
     */
    public int getOffsetX() {
        return offsetX;
    }

    /**
     * 가로 방향 이동 거리를 설정한다.
     *
     * @param offsetX 가로 방향 이동 거리
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * 세로 방향 이동 거리를 반환한다.
     *
     * @return 세로 방향 이동 거리
     */
    public int getOffsetY() {
        return offsetY;
    }

    /**
     * 세로 방향 이동 거리를 설정한다.
     *
     * @param offsetY 세로 방향 이동 거리
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * 투명도를 반환한다.
     *
     * @return 투명도
     */
    public short getTransparent() {
        return transparent;
    }

    /**
     * 투명도를 설정한다.
     *
     * @param transparent 투명도
     */
    public void setTransparent(short transparent) {
        this.transparent = transparent;
    }

    public void copy(ShadowInfo from) {
        type = from.type;
        color.copy(from.color);
        offsetX = from.offsetX;
        offsetY = from.offsetY;
        transparent = from.transparent;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getType().getValue());
        sw.writeUInt4(this.getColor().getValue());
        sw.writeSInt4(this.getOffsetX());
        sw.writeSInt4(this.getOffsetY());
    }

    @Override
    public String toString() {
        return "ShadowInfo{" +
                "type=" + type +
                ", color=" + color.getValue() +
                ", offsetX=" + offsetX +
                ", offsetY=" + offsetY +
                ", transparent=" + transparent +
                '}';
    }
}
