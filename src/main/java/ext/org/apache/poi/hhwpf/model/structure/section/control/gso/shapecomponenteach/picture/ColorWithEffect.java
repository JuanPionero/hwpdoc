package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import org.apache.poi.UnsupportedFileFormatException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 색상 효과가 포함된 색상
 *
 * @author neolord
 */
public class ColorWithEffect implements StreamWritable {
    /**
     * 색상 타입 (정보 없음)
     */
    private int type;
    /**
     * 타입에 따른 색상 값 (정보 없음)
     */
    private byte[] color;
    /**
     * 색상 효과의 리스트
     */
    private ArrayList<ColorEffect> colorEffectList;

    /**
     * 생성자
     */
    public ColorWithEffect() {
        colorEffectList = new ArrayList<ColorEffect>();
    }

    public ColorWithEffect(StreamReader sr) throws IOException {
        this.colorEffectList = new ArrayList<ColorEffect>();
        this.type = sr.readSInt4();
        if (this.type == 0) {
            this.color = sr.readBytes(4);
        } else {
            throw new UnexpectedFileFormatException("Unexpected color in ColorWithEffect");
        }
        int colorEffectCount = (int) sr.readUInt4();
        for (int index = 0; index < colorEffectCount; index++) {
            this.colorEffectList.add(new ColorEffect(sr));
        }
    }

    /**
     * 색상 타입을 반환한다. (정보 없음)
     *
     * @return 색상 타입
     */
    public int getType() {
        return type;
    }

    /**
     * 색상 타입을 설정한다. (정보 없음)
     *
     * @param type 색상 타입
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 타입에 따른 색상 값을 반환한다.(정보 없음)
     *
     * @return 타입에 따른 색상 값
     */
    public byte[] getColor() {
        return color;
    }

    /**
     * 타입에 따른 색상 값을 설정한다.
     *
     * @param color 타입에 따른 색상 값
     */
    public void setColor(byte[] color) {
        this.color = color;
    }

    /**
     * 새로운 색성 효과 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 색성 효과 객체
     */
    public ColorEffect addNewColorEffect() {
        ColorEffect ce = new ColorEffect();
        colorEffectList.add(ce);
        return ce;
    }

    /**
     * 색성 효과의 리스트를 반환한다.
     *
     * @return 색성 효과의 리스트
     */
    public ArrayList<ColorEffect> getColorEffectList() {
        return colorEffectList;
    }

    public void copy(ColorWithEffect from) {
        type = from.type;
        color = from.color.clone();

        colorEffectList.clear();
        for (ColorEffect colorEffect : from.colorEffectList) {
            colorEffectList.add(colorEffect.clone());
        }

    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeSInt4(this.getType());
        if (this.getType() == 0) {
            sw.writeBytes(this.getColor());
        } else {
            throw new IOException("not supported color type !!!");
        }

        int colorEffectCount = this.getColorEffectList().size();
        sw.writeUInt4(colorEffectCount);

        for (ColorEffect ce : this.getColorEffectList()) {
            ce.write(sw);
        }
    }
}
