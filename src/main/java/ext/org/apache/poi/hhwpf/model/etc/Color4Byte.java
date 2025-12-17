package ext.org.apache.poi.hhwpf.model.etc;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.util.binary.BitFlag;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 4byte 색상 객체. windows API의 COLORREF에 대응하는 객체
 *
 * @author neolord
 */
public class Color4Byte {
    /**
     * unsigned 4 byte color 값을 저장
     */
    private long value;

    /**
     * 생성자
     */
    public Color4Byte() {
        this.value = 0L;
    }

    public Color4Byte(StreamReader sr) throws IOException {
        this.value = sr.readUInt4();
    }

    public Color4Byte(long value) {
        this.value = value;
    }

    public Color4Byte(int r, int g, int b, int a) {
        setR((short) r);
        setG((short) g);
        setB((short) b);
        setA((short) a);
    }

    public Color4Byte(int r, int g, int b) {
        setR((short) r);
        setG((short) g);
        setB((short) b);
        setA((short) 0);
    }

    /**
     * unsigned 4 byte color 값을 반환한다.
     *
     * @return unsigned 4 byte color 값
     */
    public long getValue() {
        return value;
    }

    /**
     * unsigned 4 byte color 값을 설정한다.
     *
     * @param value unsigned 4 byte color 값, windows API에서 COLORREF의 값
     */
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * 색상의 red 값을 반환한다.
     *
     * @return red 값(0~255)
     */
    public short getR() {
        return (short) BitFlag.get(value, 0, 7);
    }

    /**
     * 색상의 red 값을 설정한다.
     *
     * @param r red 값(0~255)
     */
    public void setR(short r) {
        value = BitFlag.set(value, 0, 7, r);
    }

    /**
     * 색상의 green 값을 반환한다.
     *
     * @return green 값(0~255)
     */
    public short getG() {
        return (short) BitFlag.get(value, 8, 15);
    }

    /**
     * 색상의 green 값을 설정한다.
     *
     * @param g green 값(0~255)
     */
    public void setG(short g) {
        value = BitFlag.set(value, 8, 15, g);
    }

    /**
     * 색상의 blue 값을 반환한다.
     *
     * @return blue 값(0~255)
     */
    public short getB() {
        return (short) BitFlag.get(value, 16, 23);
    }

    /**
     * 색상의 blue 값을 설정한다.
     *
     * @param b blue 값(0~255)
     */
    public void setB(short b) {
        value = BitFlag.set(value, 16, 23, b);
    }

    /**
     * 색상의 alpha 값을 반환한다.
     *
     * @return alpha 값(0~255)
     */
    public short getA() {
        return (short) BitFlag.get(value, 24, 31);
    }

    /**
     * 색상의 alpha 값을 설정한다.
     *
     * @param a alpha 값(0~255)
     */
    public void setA(short a) {
        value = BitFlag.set(value, 24, 31, a);
    }

    public Color4Byte clone() {
        Color4Byte cloned = new Color4Byte();
        cloned.copy(this);
        return cloned;
    }

    public void copy(Color4Byte from) {
        value = from.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
