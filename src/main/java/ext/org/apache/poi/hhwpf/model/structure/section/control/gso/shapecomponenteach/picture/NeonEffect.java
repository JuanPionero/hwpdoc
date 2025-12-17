package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 네온 효과 속성
 *
 * @author neolord
 */
public class NeonEffect implements StreamWritable {
    /**
     * 네온 투명도
     */
    private float transparency;
    /**
     * 네온 반경
     */
    private float radius;
    /**
     * 네온 색상
     */
    private ColorWithEffect color;

    /**
     * 생성자
     */
    public NeonEffect() {
        color = new ColorWithEffect();
    }

    public NeonEffect(StreamReader sr) throws IOException {
        this.transparency= sr.readFloat();
        this.radius = sr.readFloat();
        color = new ColorWithEffect(sr);
    }
    /**
     * 네온 투명도를 반환한다.
     *
     * @return 네온 투명도
     */
    public float getTransparency() {
        return transparency;
    }

    /**
     * 네온 투명도를 설정한다.
     *
     * @param transparency 네온 투명도
     */
    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    /**
     * 네온 반경을 반환한다.
     *
     * @return 네온 반경
     */
    public float getRadius() {
        return radius;
    }

    /**
     * 네온 반경을 설정한다.
     *
     * @param radius 네온 반경
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    /**
     * 네온 색상 객체를 반환한다.
     *
     * @return 네온 색상 객체
     */
    public ColorWithEffect getColor() {
        return color;
    }

    public void copy(NeonEffect from) {
        transparency = from.transparency;
        radius = from.radius;
        color.copy(from.color);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeFloat(this.getTransparency());
        sw.writeFloat(this.getRadius());
        this.getColor().write(sw);
    }
}
