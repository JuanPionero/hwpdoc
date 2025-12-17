package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 부드러운 가장자리 효과 속성
 *
 * @author neolord
 */
public class SoftEdgeEffect implements StreamWritable {
    /**
     * 변경
     */
    private float radius;

    /**
     * 생성
     */
    public SoftEdgeEffect() {
    }

    public SoftEdgeEffect(StreamReader sr) throws IOException {
        this.radius = sr.readFloat();
    }

    /**
     * 부드러운 가장자리 반경을 반환한다.
     *
     * @return 부드러운 가장자리 반경
     */
    public float getRadius() {
        return radius;
    }

    /**
     * 부드러운 가장자리 반경을 설정한다.
     *
     * @param radius 부드러운 가장자리 반경
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void copy(SoftEdgeEffect from) {
        radius = from.radius;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeFloat(this.getRadius());
    }
}
