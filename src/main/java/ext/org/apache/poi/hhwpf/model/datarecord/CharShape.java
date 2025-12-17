package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.charshape.*;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 글자 모양을 나타내는 레코드
 *
 * @author neolord
 */
public class CharShape {
    private static final Logger logger = LoggerFactory.getLogger(CharShape.class);
    /**
     * 언어별 글꼴 ID(FaceID) 참조 값
     */
    private FaceNameIds faceNameIds;
    /**
     * 언어별 장평, 50%~200%
     */
    private Ratios ratios;
    /**
     * 언어별 자간, -50%~50%
     */
    private CharSpaces charSpaces;
    /**
     * 언어별 상대 크기, 10%~250%
     */
    private RelativeSizes relativeSizes;
    /**
     * 언어별 글자 위치, -100%~100%
     */
    private CharOffsets charOffsets;
    /**
     * 기준 크기
     */
    private int baseSize;
    /**
     * 속성
     */
    private CharShapeProperty property;
    /**
     * 그림자 간격1, -100%~100%
     */
    private byte shadowGap1;
    /**
     * 그림자 간격2, -100%～100%
     */
    private byte shadowGap2;
    /**
     * 글자 색
     */
    private Color4Byte charColor;
    /**
     * 밑줄 색
     */
    private Color4Byte underLineColor;
    /**
     * 음영 색
     */
    private Color4Byte shadeColor;
    /**
     * 그림자 색
     */
    private Color4Byte shadowColor;
    /**
     * 글자의 테두리/배경 ID 참조 값
     */
    private int borderFillId;
    /**
     * 취소선 색 (5.0.3.0 이상)
     */
    private Color4Byte strikeLineColor;

    /**
     * 생성자
     */
    public CharShape() {
        faceNameIds = new FaceNameIds();
        ratios = new Ratios();
        charSpaces = new CharSpaces();
        relativeSizes = new RelativeSizes();
        charOffsets = new CharOffsets();
        property = new CharShapeProperty();
        charColor = new Color4Byte();
        underLineColor = new Color4Byte();
        shadeColor = new Color4Byte();
        shadowColor = new Color4Byte();
        strikeLineColor = new Color4Byte();
    }

    public CharShape(int faceNameIdsValue,
                     short ratiosValue,
                     byte charSpacesValue,
                     short relativeSizesValue,
                     byte charOffsetsValue,
                     int baseSizeValue,
                     long propertyValue,
                     byte shadowGap1Value,
                     byte shadowGap2Value,
                     long charColorValue,
                     long underLineColorValue,
                     long shadeColorValue,
                     long shadowColorValue,
                     int borderFillIdValue,
                     long strikeLineColorValue) {
        this.faceNameIds = new FaceNameIds(faceNameIdsValue);
        this.ratios = new Ratios(ratiosValue);
        this.charSpaces = new CharSpaces(charSpacesValue);
        this.relativeSizes = new RelativeSizes(relativeSizesValue);
        this.charOffsets = new CharOffsets(charOffsetsValue);
        this.baseSize = baseSizeValue;
        this.property = new CharShapeProperty(propertyValue);
        this.shadowGap1 = shadowGap1Value;
        this.shadowGap2 = shadowGap2Value;
        this.charColor = new Color4Byte(charColorValue);
        this.underLineColor = new Color4Byte(underLineColorValue);
        this.shadeColor = new Color4Byte(shadeColorValue);
        this.shadowColor = new Color4Byte(shadowColorValue);
        this.borderFillId = borderFillIdValue;
        this.strikeLineColor = new Color4Byte(strikeLineColorValue);
    }

    public CharShape(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.faceNameIds = new FaceNameIds(sr);
        this.ratios = new Ratios(sr);
        this.charSpaces = new CharSpaces(sr);
        this.relativeSizes = new RelativeSizes(sr);
        this.charOffsets = new CharOffsets(sr);

        this.baseSize = sr.readSInt4();
        this.property = new CharShapeProperty(sr.readUInt4());
        this.shadowGap1 = sr.readSInt1();
        this.shadowGap2 = sr.readSInt1();
        this.charColor = new Color4Byte(sr.readUInt4());
        this.underLineColor = new Color4Byte(sr.readUInt4());
        this.shadeColor = new Color4Byte(sr.readUInt4());
        this.shadowColor = new Color4Byte(sr.readUInt4());

        final FileVersion fileVersion = sr.getFileHeader().getFileVersion();

        if (sr.isAvailable() && fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 2, (short) 1)) {
            this.borderFillId = sr.readUInt2();
        }
        if (sr.isAvailable() && fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 3, (short) 0)) {
            this.strikeLineColor = new Color4Byte(sr.readUInt4());
        }

    }

    /**
     * 언어별로 참조된 글꼴 ID(FaceID)에 대한 객체를 반환한다.
     *
     * @return 언어별로 참조된 글꼴 ID(FaceID)에 대한 객체
     */
    public FaceNameIds getFaceNameIds() {
        return faceNameIds;
    }

    /**
     * 언어별 장평에 대한 객체를 반환한다.
     *
     * @return 언어별 장평에 대한 객체
     */
    public Ratios getRatios() {
        return ratios;
    }

    /**
     * 언어별 자간에 대한 객체를 반환한다.
     *
     * @return 언어별 자간에 대한 객체
     */
    public CharSpaces getCharSpaces() {
        return charSpaces;
    }

    /**
     * 언어별 상대 크기에 대한 객체를 반환한다.
     *
     * @return 언어별 상대 크기
     */
    public RelativeSizes getRelativeSizes() {
        return relativeSizes;
    }

    /**
     * 언어별 글자 위치에 대한 객체를 반환한다.
     *
     * @return 언어별 글자 위치에 대한 객체
     */
    public CharOffsets getCharOffsets() {
        return charOffsets;
    }

    /**
     * 기준 크기를 반환한다.
     *
     * @return 기준 크기
     */
    public int getBaseSize() {
        return baseSize;
    }

    /**
     * 기준 크기를 설정한다.
     *
     * @param baseSize 기준 크기
     */
    public void setBaseSize(int baseSize) {
        this.baseSize = baseSize;
    }

    /**
     * 글자모양 속성에 대한 객체를 반환한다.
     *
     * @return 글자모양 속성에 대한 객체
     */
    public CharShapeProperty getProperty() {
        return property;
    }

    /**
     * 그림자 간격1을 반한한다.
     *
     * @return 그림자 간격1
     */
    public byte getShadowGap1() {
        return shadowGap1;
    }

    /**
     * 그림자 간격1를 설정한다.
     *
     * @param shadowGap1 그림자 간격1
     */
    public void setShadowGap1(byte shadowGap1) {
        this.shadowGap1 = shadowGap1;
    }

    /**
     * 그림자 간격2를 반환한다.
     *
     * @return 그림자 간격2ㅁ
     */
    public byte getShadowGap2() {
        return shadowGap2;
    }

    /**
     * 그림자 간격2를 설정한다.
     *
     * @param shadowGap2 그림자 간격2
     */
    public void setShadowGap2(byte shadowGap2) {
        this.shadowGap2 = shadowGap2;
    }

    /**
     * 글자 색 객체를 반환한다.
     *
     * @return 글자 색 객체
     */
    public Color4Byte getCharColor() {
        return charColor;
    }

    /**
     * 밑줄 색 객체를 반환한다.
     *
     * @return 밑줄 색 객체
     */
    public Color4Byte getUnderLineColor() {
        return underLineColor;
    }

    /**
     * 음영 색 객체를 반환한다.
     *
     * @return 음영 색 객체
     */
    public Color4Byte getShadeColor() {
        return shadeColor;
    }

    /**
     * 그림자 색 객체를 반환한다.
     *
     * @return 그림자 색 객체
     */
    public Color4Byte getShadowColor() {
        return shadowColor;
    }

    /**
     * 참조된 테두리/배경 의 id를 반환한다.
     *
     * @return 참조된 테두리/배경 의 id
     */
    public int getBorderFillId() {
        return borderFillId;
    }

    /**
     * 참조된 테두리/배경 의 id를 설정한다.
     *
     * @param borderFillId 참조된 테두리/배경 의 id
     */
    public void setBorderFillId(int borderFillId) {
        this.borderFillId = borderFillId;
    }

    /**
     * 취소선 색 객체를 반환한다.
     *
     * @return 취소선 색 객체 (5.0.3.0 이상)
     */
    public Color4Byte getStrikeLineColor() {
        return strikeLineColor;
    }

    public CharShape clone() {
        CharShape cloned = new CharShape();
        cloned.faceNameIds.copy(faceNameIds);
        cloned.ratios.copy(ratios);
        cloned.charSpaces.copy(charSpaces);
        cloned.relativeSizes.copy(relativeSizes);
        cloned.charOffsets.copy(charOffsets);
        cloned.baseSize = baseSize;
        cloned.property.copy(property);
        cloned.shadowGap1 = shadowGap1;
        cloned.shadowGap2 = shadowGap2;
        cloned.charColor.copy(charColor);
        cloned.underLineColor.copy(underLineColor);
        cloned.shadeColor.copy(shadeColor);
        cloned.shadowColor.copy(shadowColor);
        cloned.borderFillId = borderFillId;
        cloned.strikeLineColor.copy(strikeLineColor);

        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CHAR_SHAPE, this.getSize(sw.getFileVersion()));
        
        this.faceNameIds(this.getFaceNameIds(), sw);
        this.ratios(this.getRatios(), sw);
        this.charSpaces(this.getCharSpaces(), sw);
        this.relativeSizes(this.getRelativeSizes(), sw);
        this.charPositions(this.getCharOffsets(), sw);

        sw.writeSInt4(this.getBaseSize());
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeSInt1(this.getShadowGap1());
        sw.writeSInt1(this.getShadowGap2());
        sw.writeUInt4(this.getCharColor().getValue());
        sw.writeUInt4(this.getUnderLineColor().getValue());
        sw.writeUInt4(this.getShadeColor().getValue());
        sw.writeUInt4(this.getShadowColor().getValue());

        if (sw.getFileVersion().isGreaterEqual(5, 0, 2, 1)) {
            sw.writeUInt2(this.getBorderFillId());
        }
        if (sw.getFileVersion().isGreaterEqual(5, 0, 3, 0)) {
            sw.writeUInt4(this.getStrikeLineColor().getValue());
        }
    }
    
    /**
     * 글자 모양 레코드의 크기를 반환한다.
     *
     * @param version 파일 버전
     * @return 글자 모양 레코드의 크기
     */
    public final int getSize(FileVersion version) {
        int size = 0;
        size += 14 + 7 + 7 + 7 + 7;
        size += 26;
        if (version.isGreaterEqual(5, 0, 2, 1)) {
            size += 2;
        }
        if (version.isGreaterEqual(5, 0, 3, 0)) {
            size += 4;
        }
        return size;
    }
    
    private void faceNameIds(FaceNameIds fni, StreamWriter sw)
            throws IOException {
        for (int faceNameId : fni.getArray()) {
            sw.writeUInt2(faceNameId);
        }
    }

    private void ratios(Ratios ratios, StreamWriter sw)
            throws IOException {
        for (short ratio : ratios.getArray()) {
            sw.writeUInt1(ratio);
        }
    }

    private void charSpaces(CharSpaces charSpaces, StreamWriter sw)
            throws IOException {
        for (byte charSpace : charSpaces.getArray()) {
            sw.writeSInt1(charSpace);
        }
    }

    private void relativeSizes(RelativeSizes rss, StreamWriter sw)
            throws IOException {
        for (short rs : rss.getArray()) {
            sw.writeUInt1(rs);
        }
    }

    private void charPositions(CharOffsets cos, StreamWriter sw)
            throws IOException {
        for (byte co : cos.getArray()) {
            sw.writeSInt1(co);
        }
    }

    @Override
    public int hashCode() {
//        logger.debug("faceNameIds={}, ratios={}, charSpaces={}, relativeSizes={}, charOffsets={}, baseSize={}, property={}, shadowGap1={}, shadowGap2={}, charColor={}, underLineColor={}, shadeColor={}, shadowColor={}, borderFillId={}, strikeLineColor",
//                Objects.hashCode(this.faceNameIds),
//                Objects.hashCode(this.ratios),
//                Objects.hashCode(this.charSpaces),
//                Objects.hashCode(this.relativeSizes),
//                Objects.hashCode(this.charOffsets),
//                Objects.hashCode(this.baseSize),
//                Objects.hashCode(this.property),
//                Objects.hashCode(this.shadowGap1),
//                Objects.hashCode(this.shadowGap2),
//                Objects.hashCode(this.charColor),
//                Objects.hashCode(this.underLineColor),
//                Objects.hashCode(this.shadeColor),
//                Objects.hashCode(this.shadowColor),
//                Objects.hashCode(this.borderFillId),
//                Objects.hashCode(this.strikeLineColor));
        return Objects.hash(
                this.faceNameIds,
                this.ratios,
                this.charSpaces,
                this.relativeSizes,
                this.charOffsets,
                this.baseSize,
                this.property,
                this.shadowGap1,
                this.shadowGap2,
                this.charColor,
                this.underLineColor,
                this.shadeColor,
                this.shadowColor,
                this.borderFillId,
                this.strikeLineColor
            );
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode()==o.hashCode();
    }
}
