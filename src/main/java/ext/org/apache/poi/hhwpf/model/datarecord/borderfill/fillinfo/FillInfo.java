package ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 채우기 정보를 나태내는 객체
 *
 * @author neolord
 */
public class FillInfo implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(FillInfo.class);
    /**
     * 채우기 종류
     */
    private FillType type;
    /**
     * 단색 채우기
     */
    private PatternFill patternFill;
    /**
     * 그러데이션 채우기
     */
    private GradientFill gradientFill;
    /**
     * 이미지 채우기
     */
    private ImageFill imageFill;


    /**
     * 생성자
     */
    public FillInfo() {
        this(new FillType());
    }

    public FillInfo(FillType fillType) {
        this.type = fillType;
    }

    public FillInfo(FillType fillType, PatternFill patternFill) {
        this.type = fillType;
        this.patternFill = patternFill;
    }

    public FillInfo(StreamReader sr) throws IOException, IllegalAccessException {
        this.type = new FillType(sr);
        if (this.type.getValue() != 0) {
            if (this.type.hasPatternFill()) {
                this.patternFill = new PatternFill(sr);
            }

            if (this.type.hasGradientFill()) {
                this.gradientFill = new GradientFill(sr);
            }

            if (this.type.hasImageFill()) {
                this.imageFill = new ImageFill(sr);
            }

            // 추가 속성
            long size = sr.readUInt4();
            if (size == 1) {
                if (this.type.hasGradientFill()) {
                    this.gradientFill.setBlurringCenter(sr.readUInt1());
                }
            } else {
                sr.readBytes((int) size);
            }

            if (sr.isEndOfDataRecord())
                return;

            // 정체 불명의 추가 데이터
            if (this.type.hasPatternFill()) {
                sr.readBytes(1);
            }
            if (this.type.hasGradientFill()) {
                sr.readBytes(1);
            }
            if (this.type.hasImageFill()) {
                sr.readBytes(1);
            }
        } else {
            sr.readBytes(4);
        }
    }



    /**
     * 채우기 종류 객체를 반환한다.
     *
     * @return 채우기 종류 객체
     */
    public FillType getType() {
        return type;
    }

    /**
     * 단색 채우기 객체를 생성한다.
     */
    public void createPatternFill() {
        patternFill = new PatternFill();
    }

    /**
     * 단색 채우기 객체를 삭제한다.
     */
    public void deletePatternFill() {
        patternFill = null;
    }

    /**
     * 단색 채우기 객체를 반환한다.
     *
     * @return 단색 채우기 객체
     */
    public PatternFill getPatternFill() {
        return patternFill;
    }

    /**
     * 그러데이션 채우기 객체를 생성한다.
     */
    public void createGradientFill() {
        gradientFill = new GradientFill();
    }

    /**
     * 그러데이션 채우기 객체를 삭제한다.
     */
    public void deleteGradientFill() {
        gradientFill = null;
    }

    /**
     * 그러데이션 채우기 객체를 반환한다.
     *
     * @return 그러데이션 채우기 객체
     */
    public GradientFill getGradientFill() {
        return gradientFill;
    }

    /**
     * 이미지 채우기 객체를 생성한다.
     */
    public void createImageFill() {
        imageFill = new ImageFill();
    }

    /**
     * 이미지 채우기 객체를 삭제한다.
     */
    public void deleteImageFill() {
        imageFill = null;
    }

    /**
     * 이미지 채우기 객체를 반환한다.
     *
     * @return 이미지 채우기 객체
     */
    public ImageFill getImageFill() {
        return imageFill;
    }

    public void copy(FillInfo from) {
        type.copy(from.type);

        if (from.patternFill != null) {
            createPatternFill();
            patternFill.copy(from.patternFill);
        } else {
            deletePatternFill();
        }

        if (from.gradientFill != null) {
            createGradientFill();
            gradientFill.copy(from.gradientFill);
        } else {
            deleteGradientFill();
        }
        if (from.imageFill != null) {
            createImageFill();
            imageFill.copy(from.imageFill);
        } else {
            deleteImageFill();
        }
    }
    
    public int getSize() {
        int size = 0;
        size += 4;
        if (this.getType().getValue() != 0) {
            if (this.getType().hasPatternFill()) {
                size += 12;
            }
            if (this.getType().hasGradientFill()) {
                size += 17;

                size += 4;
                long colorCount = this.getGradientFill().getColorList().size();
                if (colorCount > 2) {
                    size += colorCount * 4;
                }
                size += colorCount * 4;
            }
            if (this.getType().hasImageFill()) {
                size += 6;
            }

            // additionalProperty
            if (this.getType().hasGradientFill() == true) {
                size += 4;
                size += 1;
            } else {
                size += 4;
            }

            // unknownBytes
            if (this.getType().hasPatternFill()) {
                size += 1;
            }
            if (this.getType().hasGradientFill()) {
                size += 1;
            }
            if (this.getType().hasImageFill()) {
                size += 1;
            }
        } else {
            size += 4;
        }
        return size;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getType().getValue());
        if (this.getType().getValue() != 0) {
            if (this.getType().hasPatternFill()) {
                this.getPatternFill().write(sw);
//                writePatternFill(this.getPatternFill(), sw);
            }
            if (this.getType().hasGradientFill()) {
                this.getGradientFill().write(sw);
//                writeGradientFill(this.getGradientFill(), sw);
            }
            if (this.getType().hasImageFill()) {
                this.getImageFill().write(sw);
//                writeImageFill(this.getImageFill(), sw);
            }
//            additionalProperty(fi, sw);
            if (this.getType().hasGradientFill() == true) {
                sw.writeUInt4(1);
                sw.writeUInt1(this.getGradientFill().getBlurringCenter());
            } else {
                sw.writeUInt4(0);
            }
            if (this.getType().hasPatternFill()) {
                sw.writeZero(1);
            }
            if (this.getType().hasGradientFill()) {
                sw.writeZero(1);
            }
            if (this.getType().hasImageFill()) {
                sw.writeZero(1);
            }
        } else {
            sw.writeZero(4);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type.getValue(), this.patternFill, this.gradientFill, this.imageFill);
    }

    @Override
    public String toString() {
        return "FillInfo{" +
                "type=" + type +
                ", patternFill=" + patternFill +
                ", gradientFill=" + gradientFill +
                ", imageFill=" + imageFill +
                '}';
    }
}
