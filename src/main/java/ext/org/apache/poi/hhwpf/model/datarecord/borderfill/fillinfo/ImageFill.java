package ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;
import java.util.Objects;

/**
 * 이미지 채우기 객체
 *
 * @author neolord
 */
public class ImageFill implements StreamWritable {
    /**
     * 이미지 채우기 유형
     */
    private ImageFillType imageFillType;
    /**
     * 그림 정보
     */
    private PictureInfo pictureInfo;

    /**
     * 생성자
     */
    public ImageFill() {
        pictureInfo = new PictureInfo();
    }
    public ImageFill(StreamReader sr) throws IOException {
        this.imageFillType = ImageFillType.valueOf((byte) sr.readUInt1());
        this.pictureInfo = new PictureInfo(sr);
    }

    /**
     * 이미지 채우기 유형을 반환한다.
     *
     * @return 이미지 채우기 유형
     */
    public ImageFillType getImageFillType() {
        return imageFillType;
    }

    /**
     * 이미지 채우기 유형을 설정한다.
     *
     * @param imageFillType 이미지 채우기 유형
     */
    public void setImageFillType(ImageFillType imageFillType) {
        this.imageFillType = imageFillType;
    }

    /**
     * 그림 정보 객체를 반환한다.
     *
     * @return 그림 정보 객체
     */
    public PictureInfo getPictureInfo() {
        return pictureInfo;
    }

    public void copy(ImageFill from) {
        imageFillType = from.imageFillType;
        pictureInfo.copy(from.pictureInfo);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt1(this.getImageFillType().getValue());
        this.pictureInfo.write(sw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.imageFillType.getValue(), this.pictureInfo);
    }
}
