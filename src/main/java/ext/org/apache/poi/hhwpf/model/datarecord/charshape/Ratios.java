package ext.org.apache.poi.hhwpf.model.datarecord.charshape;

import ext.org.apache.poi.hhwpf.StreamReader;

import java.io.IOException;
import java.util.Arrays;

/**
 * 언어별 글자 장평
 *
 * @author neolord
 */
public class Ratios {
    /**
     * 언어별 글자 장평의 값이 저장된 배열
     */
    private short[] array;

    /**
     * 생성자
     */
    public Ratios() {
        array = new short[7];
    }

    public Ratios(short ratio) {
        this();
        this.setForAll(ratio);
    }

    public Ratios(StreamReader sr) throws IOException {
        this.array = new short[7];
        for (int index = 0; index < 7; index++) {
            this.array[index] = sr.readUInt1();
        }
    }

    /**
     * 언어별 글자 장평의 값이 저장된 배열을 반환한다.
     *
     * @return 언어별 글자 장평의 값이 저장된 배열
     */
    public short[] getArray() {
        return array;
    }

    /**
     * 언어별 글자 장평의 값이 저장된 배열을 설정한다.
     *
     * @param array 언어별 글자 장평의 값이 저장된 배열
     * @throws Exception array 크기가 7이 아닐때 발생
     */
    public void setArray(short[] array) throws Exception {
        if (array.length != 7) {
            throw new Exception("array length must be 7");
        }
        this.array = array;
    }

    /**
     * 한글에 대한 글자 장평을 반환한다.
     *
     * @return 한글에 대한 글자 장평
     */
    public short getHangul() {
        return array[0];
    }

    /**
     * 한글에 대한 글자 장평을 설정한다.
     *
     * @param ratio 한글에 대한 글자 장평
     */
    public void setHangul(short ratio) {
        array[0] = ratio;
    }

    /**
     * 영어에 대한 글자 장평을 반환한다.
     *
     * @return 영어에 대한 글자 장평
     */
    public short getLatin() {
        return array[1];
    }

    /**
     * 영어에 대한 글자 장평을 설정한다.
     *
     * @param ratio 영어에 대한 글자 장평
     */
    public void setLatin(short ratio) {
        array[1] = ratio;
    }

    /**
     * 한자에 대한 글자 장평을 반환한다.
     *
     * @return 한자에 대한 글자 장평
     */
    public short getHanja() {
        return array[2];
    }

    /**
     * 한자에 대한 글자 장평을 설정한다.
     *
     * @param ratio 한자에 대한 글자 장평
     */
    public void setHanja(short ratio) {
        array[2] = ratio;
    }

    /**
     * 일본어에 대한 글자 장평을 반환한다.
     *
     * @return 일본어에 대한 글자 장평
     */
    public short getJapanese() {
        return array[3];
    }

    /**
     * 일본어에 대한 글자 장평을 설정한다.
     *
     * @param ratio 일본어에 대한 글자 장평
     */
    public void setJapanese(short ratio) {
        array[3] = ratio;
    }

    /**
     * 기타 언어에 대한 글자 장평을 반환한다.
     *
     * @return 기타 언어에 대한 글자 장평
     */
    public short getOther() {
        return array[4];
    }

    /**
     * 기타 언어에 대한 글자 장평을 설정한다.
     *
     * @param ratio 기타 언어에 대한 글자 장평
     */
    public void setOther(short ratio) {
        array[4] = ratio;
    }

    /**
     * 기호에 대한 글자 장평을 반환한다.
     *
     * @return 기호에 대한 글자 장평
     */
    public short getSymbol() {
        return array[5];
    }

    /**
     * 기호에 대한 글자 장평을 설정한다.
     *
     * @param ratio 기호에 대한 글자 장평
     */
    public void setSymbol(short ratio) {
        array[5] = ratio;
    }

    /**
     * 사용자 정의 글자 장평을 반환한다.
     *
     * @return 사용자 정의 글자 장평
     */
    public short getUser() {
        return array[6];
    }

    /**
     * 사용자 정의 글자 장평을 설정한다.
     *
     * @param ratio 사용자 정의 글자 장평
     */
    public void setUser(short ratio) {
        array[6] = ratio;
    }

    /**
     * 모든 문자에 대한 글자 장평을 설정한다.
     *
     * @param ratio 모든 문자에 대한 글자 장평
     */
    public void setForAll(short ratio) {
        for (int index = 0; index < 7; index++) {
            array[index] = ratio;
        }
    }

    public void copy(Ratios from) {
        System.arraycopy(from.array, 0, array, 0, from.array.length);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }
}
