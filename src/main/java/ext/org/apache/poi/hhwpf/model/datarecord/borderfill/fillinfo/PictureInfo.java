package ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;
import java.util.Objects;

/**
 * 그림 정보 객체
 *
 * @author neolord
 */
public class PictureInfo {
    /**
     * 밝기
     */
    private byte brightness;
    /**
     * 명암
     */
    private byte contrast;
    /**
     * 그림 효과
     */
    private PictureEffect effect;
    /**
     * BinItem의 아이디 참조값 - 이미지가 저장된 바이너리 데이터의 id
     */
    private int binItemID;


    /**
     * 생성자
     */
    public PictureInfo() {
        effect = PictureEffect.RealPicture;
    }

    public PictureInfo(StreamReader sr) throws IOException {
        this.brightness = sr.readSInt1();
        this.contrast = sr.readSInt1();
        this.effect = PictureEffect.valueOf((byte) sr.readUInt1());
        this.binItemID = sr.readUInt2();
    }

    /**
     * 밝기를 반환한다.
     *
     * @return 밝기
     */
    public byte getBrightness() {
        return brightness;
    }

    /**
     * 밝기를 설정한다.
     *
     * @param brightness 밝기
     */
    public void setBrightness(byte brightness) {
        this.brightness = brightness;
    }

    /**
     * 명암을 반환한다.
     *
     * @return 명암
     */
    public byte getContrast() {
        return contrast;
    }

    /**
     * 명암를 설정한다.
     *
     * @param contrast 명암
     */
    public void setContrast(byte contrast) {
        this.contrast = contrast;
    }

    /**
     * 그림 효과를 반환한다.
     *
     * @return 그림 효과
     */
    public PictureEffect getEffect() {
        return effect;
    }

    /**
     * 그림 효과를 설정한다.
     *
     * @param effect 그림 효과
     */
    public void setEffect(PictureEffect effect) {
        this.effect = effect;
    }

    /**
     * BinItem의 아이디 참조값를 반환한다.
     *
     * @return BinItem의 아이디 참조값
     */
    public int getBinItemID() {
        return binItemID;
    }

    /**
     * BinItem의 아이디 참조값를 설정한다.
     *
     * @param binItemID BinItem의 아이디 참조값
     */
    public void setBinItemID(int binItemID) {
        this.binItemID = binItemID;
    }

    public void copy(PictureInfo from) {
        brightness = from.brightness;
        contrast = from.contrast;
        effect = from.effect;
        binItemID = from.binItemID;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeSInt1(this.brightness);
        sw.writeSInt1(this.contrast);
        sw.writeUInt1(this.effect.getValue());
        sw.writeUInt2(this.binItemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.brightness, this.contrast, this.effect.getValue(), this.binItemID);
    }

    @Override
    public String toString() {
        return "PictureInfo{" +
                "brightness=" + brightness +
                ", contrast=" + contrast +
                ", effect=" + effect.getValue() +
                ", binItemID=" + binItemID +
                '}';
    }
}
