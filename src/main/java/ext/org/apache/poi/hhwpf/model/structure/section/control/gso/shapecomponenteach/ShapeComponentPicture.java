package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.PictureInfo;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.lineinfo.LineInfoProperty;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture.InnerMargin;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture.PictureEffect;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.polygon.PositionXY;

import java.io.IOException;

/**
 * 그림 개체 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentPicture implements StreamWritable {
    /**
     * 테두리 색
     */
    private Color4Byte borderColor;
    /**
     * 테두리 두꼐
     */
    private int borderThickness;
    /**
     * 테두리 속성
     */
    private LineInfoProperty borderProperty;
    /**
     * left,top 좌표
     */
    private PositionXY leftTop;
    /**
     * right,top 좌표
     */
    private PositionXY rightTop;
    /**
     * left, bottom 좌표
     */
    private PositionXY leftBottom;
    /**
     * right, bottom 좌표
     */
    private PositionXY rightBottom;
    /**
     * 자르기 한 후 사각형의 left좌표
     */
    private int leftAfterCutting;
    /**
     * 자르기 한 후 사각형의 top좌표
     */
    private int topAfterCutting;
    /**
     * 자르기 한 후 사각형의 right좌표
     */
    private int rightAfterCutting;
    /**
     * 자르기 한 후 사각형의 bottom좌표
     */
    private int bottomAfterCutting;
    /**
     * 안쪽 여백 정보
     */
    private InnerMargin innerMargin;
    /**
     * 그림 정보
     */
    private PictureInfo pictureInfo;
    /**
     * 테두리 투명도
     */
    private short borderTransparency;
    /**
     * 문서 내 각 개체에 대한 고유 아이디
     */
    private long instanceId;
    /**
     * 그림 효과 정보
     */
    private PictureEffect pictureEffect;
    /**
     * 이미지 너비 (??)
     */
    private long imageWidth;
    /**
     * 이미지 높이 (??)
     */
    private long imageHeight;

    /**
     * 생성자
     */
    public ShapeComponentPicture() {
        borderColor = new Color4Byte();
        borderProperty = new LineInfoProperty();
        leftTop = new PositionXY();
        rightTop = new PositionXY();
        leftBottom = new PositionXY();
        rightBottom = new PositionXY();
        innerMargin = new InnerMargin();
        pictureInfo = new PictureInfo();
        pictureEffect = new PictureEffect();
    }

    public ShapeComponentPicture(StreamReader sr) throws IOException, IllegalAccessException {
        this.borderColor = new Color4Byte(sr.readUInt4());
        this.borderThickness = sr.readSInt4();
        this.borderProperty = new LineInfoProperty(sr.readUInt4());

        this.leftTop = new PositionXY(sr.readSInt4(), sr.readSInt4());
        this.rightTop = new PositionXY(sr.readSInt4(), sr.readSInt4());

        this.rightBottom = new PositionXY(sr.readSInt4(), sr.readSInt4());
        this.leftBottom = new PositionXY(sr.readSInt4(), sr.readSInt4());

        this.leftAfterCutting = sr.readSInt4();
        this.topAfterCutting = sr.readSInt4();
        this.rightAfterCutting = sr.readSInt4();
        this.bottomAfterCutting = sr.readSInt4();
        this.innerMargin = new InnerMargin(sr.readUInt2(),sr.readUInt2(),sr.readUInt2(),sr.readUInt2());
        this.pictureInfo = new PictureInfo(sr);

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.borderTransparency = sr.readUInt1();

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.instanceId = sr.readUInt4();

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.pictureEffect = new PictureEffect(sr);

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.imageWidth = sr.readUInt4();
        this.imageHeight = sr.readUInt4();

        if(sr.isEndOfDataRecord()) {
            return;
        }
        sr.readRestOfDataRecordData();
    }

    /**
     * 테두리 색상 객체를 반환한다.
     *
     * @return 테두리 색상 객체
     */
    public Color4Byte getBorderColor() {
        return borderColor;
    }

    /**
     * 테두리 두꼐를 반환한다.
     *
     * @return 테두리 두꼐
     */
    public int getBorderThickness() {
        return borderThickness;
    }

    /**
     * 테두리 두꼐를 설정한다.
     *
     * @param borderThickness 테두리 두꼐
     */
    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    /**
     * 테두리 선의 속성 객체를 반환한다.
     *
     * @return 테두리 선의 속성 객체
     */
    public LineInfoProperty getBorderProperty() {
        return borderProperty;
    }

    /**
     * left,top 좌표 객체를 반환한다.
     *
     * @return left, top 좌표 객체
     */
    public PositionXY getLeftTop() {
        return leftTop;
    }
    public void setLeftTop(long x, long y) {
        leftTop.setX(x);
        leftTop.setY(y);
    }
    /**
     * right,top 좌표 객체를 반환한다.
     *
     * @return right, top 좌표 객체
     */
    public PositionXY getRightTop() {
        return rightTop;
    }
    public void setRightTop(long x, long y) {
        rightTop.setX(x);
        rightTop.setY(y);
    }
    /**
     * left, bottom 좌표 객체를 반환한다.
     *
     * @return left, bottom 좌표 객체
     */
    public PositionXY getLeftBottom() {
        return leftBottom;
    }
    public void setLeftBottom(long x, long y) {
        leftBottom.setX(x);
        leftBottom.setY(y);
    }


    /**
     * right, bottom 좌표 객체를 반환한다.
     *
     * @return right, bottom 좌표 객체
     */
    public PositionXY getRightBottom() {
        return rightBottom;
    }
    public void setRightBottom(long x, long y) {
        rightBottom.setX(x);
        rightBottom.setY(y);
    }

    /**
     * 자르기 한 후 사각형의 left좌표를 반환한다.
     *
     * @return 자르기 한 후 사각형의 left좌표
     */
    public int getLeftAfterCutting() {
        return leftAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 left좌표를 설정한다.
     *
     * @param leftAfterCutting 자르기 한 후 사각형의 left좌표
     */
    public void setLeftAfterCutting(int leftAfterCutting) {
        this.leftAfterCutting = leftAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 top좌표를 반환한다.
     *
     * @return 자르기 한 후 사각형의 top좌표
     */
    public int getTopAfterCutting() {
        return topAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 top좌표를 설정한다.
     *
     * @param topAfterCutting 자르기 한 후 사각형의 top좌표
     */
    public void setTopAfterCutting(int topAfterCutting) {
        this.topAfterCutting = topAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 right좌표를 반환한다.
     *
     * @return 자르기 한 후 사각형의 right좌표
     */
    public int getRightAfterCutting() {
        return rightAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 right좌표를 설정한다.
     *
     * @param rightAfterCutting 자르기 한 후 사각형의 right좌표
     */
    public void setRightAfterCutting(int rightAfterCutting) {
        this.rightAfterCutting = rightAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 bottom좌표를 반환한다.
     *
     * @return 자르기 한 후 사각형의 bottom좌표
     */
    public int getBottomAfterCutting() {
        return bottomAfterCutting;
    }

    /**
     * 자르기 한 후 사각형의 bottom좌표를 설정한다.
     *
     * @param bottomAfterCutting 자르기 한 후 사각형의 bottom좌표
     */
    public void setBottomAfterCutting(int bottomAfterCutting) {
        this.bottomAfterCutting = bottomAfterCutting;
    }

    /**
     * 안쪽 여백 정보 객체를 반환한다.
     *
     * @return 안쪽 여백 정보 객체
     */
    public InnerMargin getInnerMargin() {
        return innerMargin;
    }

    /**
     * 그림 정보 객체를 반환한다.
     *
     * @return 그림 정보 객체
     */
    public PictureInfo getPictureInfo() {
        return pictureInfo;
    }

    /**
     * 테두리 투명도를 반환한다.
     *
     * @return 테두리 투명도
     */
    public short getBorderTransparency() {
        return borderTransparency;
    }

    /**
     * 테두리 투명도를 설정한다.
     *
     * @param borderTransparency 테두리 투명도
     */
    public void setBorderTransparency(short borderTransparency) {
        this.borderTransparency = borderTransparency;
    }

    /**
     * 문서 내 각 개체에 대한 고유 아이디를 반환한다.
     *
     * @return 문서 내 각 개체에 대한 고유 아이디
     */
    public long getInstanceId() {
        return instanceId;
    }

    /**
     * 문서 내 각 개체에 대한 고유 아이디를 설정한다.
     *
     * @param instanceId 문서 내 각 개체에 대한 고유 아이디
     */
    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * 그림 효과 정보 객체를 반환한다.
     *
     * @return 그림 효과 정보 객체
     */
    public PictureEffect getPictureEffect() {
        return pictureEffect;
    }

    /**
     * 이미지 폭을 반환한다.(??)
     *
     * @return 이미지 폭
     */
    public long getImageWidth() {
        return imageWidth;
    }

    /**
     * 이미지 폭을 설정한다. (??)
     *
     * @param imageWidth 이미지 폭
     */
    public void setImageWidth(long imageWidth) {
        this.imageWidth = imageWidth;
    }

    /**
     * 이미지 높이를 반환한다. (??)
     *
     * @return 이미지 높이
     */
    public long getImageHeight() {
        return imageHeight;
    }

    /**
     * 이미지 높이를 설정한다. (??)
     *
     * @param imageHeight 이미지 높이
     */
    public void setImageHeight(long imageHeight) {
        this.imageHeight = imageHeight;
    }

    public void copy(ShapeComponentPicture from) {
        borderColor.copy(from.borderColor);
        borderThickness = from.borderThickness;
        borderProperty.copy(from.borderProperty);
        leftTop.copy(from.leftTop);
        rightTop.copy(from.rightTop);
        leftBottom.copy(from.leftBottom);
        rightBottom.copy(from.rightBottom);
        leftAfterCutting = from.leftAfterCutting;
        topAfterCutting = from.topAfterCutting;
        rightAfterCutting = from.rightAfterCutting;
        bottomAfterCutting = from.bottomAfterCutting;
        innerMargin.copy(from.innerMargin);
        pictureInfo.copy(from.pictureInfo);
        borderTransparency = from.borderTransparency;
        instanceId = from.instanceId;
        pictureEffect.copy(from.pictureEffect);
        imageWidth = from.imageWidth;
        imageHeight = from.imageHeight;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT_PICTURE, this.getSize());
        sw.writeUInt4(this.getBorderColor().getValue());
        sw.writeSInt4(this.getBorderThickness());
        sw.writeUInt4(this.getBorderProperty().getValue());
        sw.writeSInt4((int) this.getLeftTop().getX());
        sw.writeSInt4((int) this.getLeftTop().getY());
        sw.writeSInt4((int) this.getRightTop().getX());
        sw.writeSInt4((int) this.getRightTop().getY());
        sw.writeSInt4((int) this.getRightBottom().getX());
        sw.writeSInt4((int) this.getRightBottom().getY());
        sw.writeSInt4((int) this.getLeftBottom().getX());
        sw.writeSInt4((int) this.getLeftBottom().getY());
        sw.writeSInt4(this.getLeftAfterCutting());
        sw.writeSInt4(this.getTopAfterCutting());
        sw.writeSInt4(this.getRightAfterCutting());
        sw.writeSInt4(this.getBottomAfterCutting());
        this.getInnerMargin().write(sw);
        this.getPictureInfo().write(sw);
        sw.writeUInt1(this.getBorderTransparency());
        sw.writeUInt4(this.getInstanceId());
        this.getPictureEffect().write(sw);
        sw.writeUInt4(this.getImageWidth());
        sw.writeUInt4(this.getImageHeight());
    }

    /**
     * 그림 개체 속성 레코드의 크기를 반환한다.
     *
     * @return 그림 개체 속성 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 60;
        size += 8; // inner margin;
        size += 5; // pictureInfo;
        size += 5;
        size += this.getPictureEffect().getSize();// ForPictureEffect.getSize(this.getPictureEffect());
        size += 8;
        return size;
    }

    @Override
    public String toString() {
        return "ShapeComponentPicture{" +
                "borderColor=" + borderColor.getValue() +
                ", borderThickness=" + borderThickness +
                ", borderProperty=" + borderProperty.getValue() +
                ", leftTop=" + leftTop +
                ", rightTop=" + rightTop +
                ", leftBottom=" + leftBottom +
                ", rightBottom=" + rightBottom +
                ", leftAfterCutting=" + leftAfterCutting +
                ", topAfterCutting=" + topAfterCutting +
                ", rightAfterCutting=" + rightAfterCutting +
                ", bottomAfterCutting=" + bottomAfterCutting +
                ", innerMargin=" + innerMargin +
                ", pictureInfo=" + pictureInfo +
                ", borderTransparency=" + borderTransparency +
                ", instanceId=" + instanceId +
                ", pictureEffect=" + pictureEffect +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                '}';
    }
}
