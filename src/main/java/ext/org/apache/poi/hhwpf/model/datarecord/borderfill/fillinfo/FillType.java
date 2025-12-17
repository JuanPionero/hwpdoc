package ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.util.binary.BitFlag;

import java.io.IOException;
import java.util.Objects;

/**
 * 채우기 종류
 *
 * @author neolord
 */
public class FillType {
    /**
     * 파일에 저장되는 정수값(unsigned 4 byte)
     */
    private long value;

    /**
     * 생성자
     */
    public FillType() {
        this(0);
    }

    public FillType(long value) {
        this.value = value;
    }

    public FillType(StreamReader sr) throws IOException {
        this.value = sr.readUInt4();
    }

    /**
     * 파일에 저장되는 정수값을 반환한다.
     *
     * @return 파일에 저장되는 정수값
     */
    public long getValue() {
        return value;
    }

    /**
     * 파일에 저장되는 정수값을 설정한다.
     *
     * @param value 파일에 저장되는 정수값
     */
    public void setValue(long value) {
        this.value = value;
    }

    /**
     * 단색 채우기 적용 여부를 반환한다. (0 bit)
     *
     * @return 단색 채우기 적용 여부
     */
    public boolean hasPatternFill() {
        return BitFlag.get(value, 0);
    }

    /**
     * 단색 채우기 적용 여부를 설정한다. (0 bit)
     *
     * @param patternFill 단색 채우기 적용 여부
     */
    public void setPatternFill(boolean patternFill) {
        value = BitFlag.set(value, 0, patternFill);
    }

    /**
     * 이미지 채우기 적용 여부를 반환한다. (1 bit)
     *
     * @return 이미지 채우기 적용 여부
     */
    public boolean hasImageFill() {
        return BitFlag.get(value, 1);
    }

    /**
     * 이미지 채우기 적용 여부를 설정한다. (1 bit)
     *
     * @param imageFill 이미지 채우기 적용 여부
     */
    public void setImageFill(boolean imageFill) {
        value = BitFlag.set(value, 1, imageFill);
    }

    /**
     * 그러데이션 채우기 적용 여부를 반환한다. (2 bit)
     *
     * @return 그러데이션 채우기 적용 여부
     */
    public boolean hasGradientFill() {
        return BitFlag.get(value, 2);
    }

    /**
     * 그러데이션 채우기 적용 여부를 설정한다. (2 bit)
     *
     * @param gradientFill 그러데이션 채우기 적용 여부
     */
    public void setGradientFill(boolean gradientFill) {
        value = BitFlag.set(value, 2, gradientFill);
    }

    public void copy(FillType from) {
        value = from.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.value);
    }
}
