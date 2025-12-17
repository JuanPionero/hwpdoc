package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 색상 효과 속성
 *
 * @author neolord
 */
public class ColorEffect implements StreamWritable {
    /**
     * 색상 효과 종류 (정보 없음)
     */
    private ColorEffectSort sort;
    /**
     * 색상 효과 값
     */
    private float value;

    /**
     * 생성자
     */
    public ColorEffect() {
    }

    public ColorEffect(StreamReader sr) throws IOException {
        this.sort = ColorEffectSort.valueOf(sr.readSInt4());
        this.value = sr.readFloat();
    }

    /**
     * 색상 효과 종류를 반환한다.
     *
     * @return 색상 효과 종류
     */
    public ColorEffectSort getSort() {
        return sort;
    }

    /**
     * 색상 효과 종류를 설정한다.
     *
     * @param sort 색상 효과 종류
     */
    public void setSort(ColorEffectSort sort) {
        this.sort = sort;
    }

    /**
     * 색상 효과 값을 반환한다.
     *
     * @return 색상 효과 값
     */
    public float getValue() {
        return value;
    }

    /**
     * 색상 효과 값를 설정한다.
     *
     * @param value 색상 효과 값
     */
    public void setValue(float value) {
        this.value = value;
    }

    public ColorEffect clone() {
        ColorEffect cloned = new ColorEffect();
        cloned.sort = sort;
        cloned.value = value;
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeSInt4(this.getSort().getValue());
        sw.writeFloat(this.getValue());
    }
}
