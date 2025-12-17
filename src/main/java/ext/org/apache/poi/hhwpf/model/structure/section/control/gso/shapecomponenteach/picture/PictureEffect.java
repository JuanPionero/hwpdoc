package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 그림 효과
 *
 * @author neolord
 */
public class PictureEffect implements StreamWritable {
    /**
     * 속성
     */
    private PictureEffectProperty property;
    /**
     * 그림자 효과
     */
    private ShadowEffect shadowEffect;
    /**
     * 네온 효과
     */
    private NeonEffect neonEffect;
    /**
     * 부드러운 가장자리 효과
     */
    private SoftEdgeEffect softEdgeEffect;
    /**
     * 반사 효과
     */
    private ReflectionEffect reflectionEffect;

    /**
     * 생성자
     */
    public PictureEffect() {
        property = new PictureEffectProperty();
    }
    public PictureEffect(StreamReader sr) throws IOException {
        this.property = new PictureEffectProperty(sr.readUInt4());
        if(this.property.hasShadowEffect()) {
            this.shadowEffect = new ShadowEffect(sr);
        }
        if (this.property.hasNeonEffect()) {
            this.neonEffect = new NeonEffect(sr);
        }
        if (this.property.hasSoftBorderEffect()) {
            this.softEdgeEffect = new SoftEdgeEffect(sr);
        }
        if (this.property.hasReflectionEffect()) {
            this.reflectionEffect = new ReflectionEffect(sr);
        }
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public PictureEffectProperty getProperty() {
        return property;
    }

    /**
     * 그림자 효과 객체를 생성한다.
     */
    public void createShadowEffect() {
        shadowEffect = new ShadowEffect();
    }

    /**
     * 그림자 효과 객체를 삭제한다.
     */
    public void deleteShadowEffect() {
        shadowEffect = null;
    }

    /**
     * 그림자 효과 객체를 반환한다.
     *
     * @return 그림자 효과 객체
     */
    public ShadowEffect getShadowEffect() {
        return shadowEffect;
    }

    /**
     * 네온 효과 객체를 생성한다.
     */
    public void createNeonEffect() {
        neonEffect = new NeonEffect();
    }

    /**
     * 네온 효과 객체를 삭제한다.
     */
    public void deleteNeonEffect() {
        neonEffect = null;
    }

    /**
     * 네온 효과 객체를 반환한다.
     *
     * @return 네온 효과 객체
     */
    public NeonEffect getNeonEffect() {
        return neonEffect;
    }

    /**
     * 부드러운 가장자리 효과 객체를 생성한다.
     */
    public void createSoftEdgeEffect() {
        softEdgeEffect = new SoftEdgeEffect();
    }

    /**
     * 부드러운 가장자리 효과 객체를 삭제한다.
     */
    public void deleteSoftEdgeEffect() {
        softEdgeEffect = null;
    }

    /**
     * 부드러운 가장자리 효과 객체를 반환한다.
     *
     * @return 부드러운 가장자리 효과 객체
     */
    public SoftEdgeEffect getSoftEdgeEffect() {
        return softEdgeEffect;
    }

    /**
     * 반사 효과 객체를 생성한다.
     */
    public void createReflectionEffect() {
        reflectionEffect = new ReflectionEffect();
    }

    /**
     * 반사 효과 객체를 삭제한다.
     */
    public void deleteReflectionEffect() {
        reflectionEffect = null;
    }

    /**
     * 반사 효과 객체를 반환한다.
     *
     * @return 반사 효과 객체
     */
    public ReflectionEffect getReflectionEffect() {
        return reflectionEffect;
    }

    public void copy(PictureEffect from) {
        property.copy(from.property);

        if (from.shadowEffect != null) {
            createShadowEffect();
            shadowEffect.copy(from.shadowEffect);
        } else {
            shadowEffect = null;
        }

        if (from.neonEffect != null) {
            createNeonEffect();
            neonEffect.copy(from.neonEffect);
        } else {
            neonEffect = null;
        }

        if (from.softEdgeEffect != null) {
            createSoftEdgeEffect();
            softEdgeEffect.copy(from.softEdgeEffect);
        } else {
            softEdgeEffect = null;
        }

        if (from.reflectionEffect != null) {
            createReflectionEffect();
            reflectionEffect.copy(from.reflectionEffect);
        } else {
            reflectionEffect = null;
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getProperty().getValue());
        if(this.getShadowEffect()!=null) {
            this.getShadowEffect().write(sw);
        }
        if(this.getNeonEffect()!=null) {
            this.getNeonEffect().write(sw);
        }
        if(this.getSoftEdgeEffect()!=null) {
            this.getSoftEdgeEffect().write(sw);
        }
        if(this.getReflectionEffect()!=null) {
            this.getReflectionEffect().write(sw);
        }
    }

    public int getSize() {
        int size = 0;
        size += 4;
        if (this.getShadowEffect() != null) {
            size += 44;
            size += 8 + 4 + this.getShadowEffect().getColor().getColorEffectList().size() * 8;//    getSize(this.getShadowEffect().getColor());
        }
        if (this.getNeonEffect() != null) {
            size += 8;
            size += 8 + 4 + this.getNeonEffect().getColor().getColorEffectList().size() * 8; // getSize(this.getNeonEffect().getColor());
        }
        if (this.getSoftEdgeEffect() != null) {
            size += 4;
        }
        if (this.getReflectionEffect() != null) {
            size += 56;
        }
        return size;
    }

    @Override
    public String toString() {
        return "PictureEffect{" +
                "property=" + property.getValue() +
                ", shadowEffect=" + shadowEffect +
                ", neonEffect=" + neonEffect +
                ", softEdgeEffect=" + softEdgeEffect +
                ", reflectionEffect=" + reflectionEffect +
                '}';
    }
}
