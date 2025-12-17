package ext.org.apache.poi.hhwpf.model.datarecord;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderFillProperty;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderThickness;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderType;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.EachBorder;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.FillInfo;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 테두리/배경의 모양을 나타내는 레코드
 *
 * @author neolord
 */
public class BorderFill {
    private static final Logger logger = LoggerFactory.getLogger(BorderFill.class);
    /**
     * 속성
     */
    private BorderFillProperty property;
    /**
     * 왼쪽 선의 속성
     */
    private EachBorder leftBorder;
    /**
     * 오른쪽 선의 속성
     */
    private EachBorder rightBorder;
    /**
     * 위쪽 선의 속성
     */
    private EachBorder topBorder;
    /**
     * 아래쪽 선의 속성
     */
    private EachBorder bottomBorder;
    /**
     * 대각선의 속성
     */
    private EachBorder diagonalBorder;
    /**
     * 채우기 정보
     */
    private FillInfo fillInfo;

    /**
     * 생성자
     */
    public BorderFill() {
        this(new FillInfo()); // 내부적으로 fillType을 0으로 초기화 하여 가진다.
    }

    public BorderFill(FillInfo fillInfo) {
        property = new BorderFillProperty(0);
        leftBorder = new EachBorder(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0));
        rightBorder = new EachBorder(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0));
        topBorder = new EachBorder(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0));
        bottomBorder = new EachBorder(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0));
        diagonalBorder = new EachBorder(BorderType.Solid, BorderThickness.MM0_1, new Color4Byte(0));
        this.fillInfo = fillInfo;
    }

    public BorderFill(StreamReader sr) throws IOException, IllegalAccessException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        property = new BorderFillProperty(sr);
        leftBorder = new EachBorder(sr);
        rightBorder = new EachBorder(sr);
        topBorder = new EachBorder(sr);
        bottomBorder = new EachBorder(sr);
        diagonalBorder = new EachBorder(sr);
        fillInfo = new FillInfo(sr);
    }

    /**
     * 테두리/배경의 속성 객체를 반환한다.
     *
     * @return 테두리/배경의 속성 객체
     */
    public BorderFillProperty getProperty() {
        return property;
    }

    /**
     * 왼쪽 선의 속성 객체를 반환한다.
     *
     * @return 왼쪽 선의 속성 객체
     */
    public EachBorder getLeftBorder() {
        return leftBorder;
    }

    /**
     * 오른쪽 선의 속성 객체를 반환한다.
     *
     * @return 오른쪽 선의 속성 객체
     */
    public EachBorder getRightBorder() {
        return rightBorder;
    }

    /**
     * 위쪽 선의 속성 객체를 반환한다.
     *
     * @return 위쪽 선의 속성 객체
     */
    public EachBorder getTopBorder() {
        return topBorder;
    }

    /**
     * 아래쪽 선의 속성 객체를 반환한다.
     *
     * @return 아래쪽 선의 속성 객체
     */
    public EachBorder getBottomBorder() {
        return bottomBorder;
    }

    /**
     * 대각선의 속성 객체를 반환한다.
     *
     * @return 아래쪽 선의 속성 객체
     */
    public EachBorder getDiagonalBorder() {
        return diagonalBorder;
    }

    /**
     * 채우기 정보 객체를 반환한다.
     *
     * @return 채우기 정보 객체
     */
    public FillInfo getFillInfo() {
        return fillInfo;
    }

    public BorderFill clone() {
        BorderFill cloned = new BorderFill();
        cloned.property.copy(property);
        cloned.leftBorder.copy(leftBorder);
        cloned.rightBorder.copy(rightBorder);
        cloned.topBorder.copy(topBorder);
        cloned.bottomBorder.copy(bottomBorder);
        cloned.diagonalBorder.copy(diagonalBorder);
        cloned.fillInfo.copy(fillInfo);
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_BORDER_FILL, this.getSize());

        sw.writeUInt2(this.getProperty().getValue());
        this.getLeftBorder().write(sw);
        this.getRightBorder().write(sw);
        this.getTopBorder().write(sw);
        this.getBottomBorder().write(sw);
        this.getDiagonalBorder().write(sw);
        this.getFillInfo().write(sw);
    }

    /**
     * 테두리/배경 레코드의 크기를 반환한다.
     *
     * @return 테두리/배경 레코드의 크기
     */
    public final int getSize() {
        int size = 0;
        size += 2;
        size += (1 + 1 + 4) * 5;
        size += this.fillInfo.getSize();
        return size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                property,
                leftBorder,
                rightBorder,
                topBorder,
                bottomBorder,
                diagonalBorder,
                fillInfo
        );
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode()==o.hashCode();
    }
}
