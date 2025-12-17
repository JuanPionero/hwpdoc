package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.gso.GsoHeaderProperty;
import ext.org.apache.poi.hhwpf.util.binary.BitFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * 그리기 개체을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderGso extends CtrlHeader implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(CtrlHeaderGso.class);
    /**
     * 속성
     */
    private GsoHeaderProperty property;
    /**
     * 세로 오프셋 값
     */
    private long yOffset;
    /**
     * 가로 오프셋 값
     */
    private long xOffset;
    /**
     * 오브젝트의 폭
     */
    private long width;
    /**
     * 오브젝트의 높이
     */
    private long height;
    /**
     * z-order
     */
    private int zOrder;
    /**
     * 오브젝트의 바깥 왼쪾 여백
     */
    private int outterMarginLeft;
    /**
     * 오브젝트의 바깥 오른쪽 여백
     */
    private int outterMarginRight;
    /**
     * 오브젝트의 바깥 위쪽 여백
     */
    private int outterMarginTop;
    /**
     * 오브젝트의 바깥 아래쪽 여백
     */
    private int outterMarginBottom;
    /**
     * 문서 내 각 개체에 대한 고유 아이디
     */
    private long instanceId;
    /**
     * 쪽나눔 방지 on(1) / off(0) = (객체와 조판 부호를 항상 같은 쪽에 넣기) ??
     */
    private boolean preventPageDivide;
    /**
     * 개체 설명문
     */
    private HWPString explanation;
    /**
     * 알 수 없는 바이트 : 2024 버전에서...
     */
    private byte[] unknown;

    public CtrlHeaderGso() {
        super(ControlType.Gso.getCtrlId());
        property = new GsoHeaderProperty();
        explanation = new HWPString();
        unknown = null;
    }

    /**
     * 생성자
     *
     * @param controlType 컨트롤 타입
     */
    public CtrlHeaderGso(ControlType controlType) {
        super(controlType.getCtrlId());

        property = new GsoHeaderProperty();
        explanation = new HWPString();
    }

    /**
     * 생성자
     * 가이드의 표 69 참고: ctrl ID 는 parent class에 저장
     */
    public CtrlHeaderGso(StreamReader sr) throws IOException, IllegalAccessException {
        this(sr, ControlType.Gso);
    }

    public CtrlHeaderGso(StreamReader sr, ControlType controlType) throws IOException, IllegalAccessException {
        super(controlType.getCtrlId());

        this.property = new GsoHeaderProperty( sr.readUInt4() ); // 가이드 표 70 참고
        this.yOffset = sr.readUInt4();
        this.xOffset = sr.readUInt4();
        this.width = sr.readUInt4();
        this.height = sr.readUInt4();
        this.zOrder = sr.readSInt4();
        this.outterMarginLeft = sr.readUInt2();
        this.outterMarginRight = sr.readUInt2();
        this.outterMarginTop = sr.readUInt2();
        this.outterMarginBottom = sr.readUInt2();
        this.instanceId = sr.readUInt4();

        if(sr.isEndOfDataRecord()) {
//            logger.trace("end before preventPageDivide in CtrlHeaderGso constructor");
            return;
        }
        this.preventPageDivide = BitFlag.get(sr.readSInt4(), 0);

        if(sr.isEndOfDataRecord()) {
//            logger.trace("end before explanation in CtrlHeaderGso constructor");
            return;
        }
        this.explanation = new HWPString(sr.readHWPString());

        if(sr.isEndOfDataRecord()) {
//            logger.trace("end before unknown in CtrlHeaderGso constructor");
            return;
        }
        this.unknown = sr.readRestOfDataRecordData();
    }


    /**
     * 그리기 객체 컨트롤의 속성 객체를 반환한다.
     *
     * @return 그리기 객체 컨트롤의 속성 객체
     */
    public GsoHeaderProperty getProperty() {
        return property;
    }

    /**
     * 세로 오프셋 값을 반환한다.
     *
     * @return 세로 오프셋 값
     */
    public long getyOffset() {
        return yOffset;
    }

    /**
     * 세로 오프셋 값을 설정한다.
     *
     * @param yOffset 세로 오프셋 값
     */
    public void setyOffset(long yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * 가로 오프셋 값을 반환한다.
     *
     * @return 가로 오프셋 값
     */
    public long getxOffset() {
        return xOffset;
    }

    /**
     * 가로 오프셋 값을 설정한다.
     *
     * @param xOffset 가로 오프셋 값
     */
    public void setxOffset(long xOffset) {
        this.xOffset = xOffset;
    }

    /**
     * 오브젝트의 폭을 반환한다.
     *
     * @return 오브젝트의 폭
     */
    public long getWidth() {
        return width;
    }

    /**
     * 오브젝트의 폭를 설정한다.
     *
     * @param width 오브젝트의 폭
     */
    public void setWidth(long width) {
        this.width = width;
    }

    /**
     * 오브젝트의 높이를 반환한다.
     *
     * @return 오브젝트의 높이
     */
    public long getHeight() {
        return height;
    }

    /**
     * 오브젝트의 높이를 설정한다.
     *
     * @param height 오브젝트의 높이
     */
    public void setHeight(long height) {
        this.height = height;
    }

    /**
     * z-order을 반환한다.
     *
     * @return z-order
     */
    public int getzOrder() {
        return zOrder;
    }

    /**
     * z-order을 설정한다.
     *
     * @param zOrder z-order
     */
    public void setzOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    /**
     * 오브젝트의 바깥 왼쪾 여백을 반환한다.
     *
     * @return 오브젝트의 바깥 왼쪾 여백
     */
    public int getOutterMarginLeft() {
        return outterMarginLeft;
    }

    /**
     * 오브젝트의 바깥 왼쪾 여백을 설정한다.
     *
     * @param outterMarginLeft 오브젝트의 바깥 왼쪾 여백
     */
    public void setOutterMarginLeft(int outterMarginLeft) {
        this.outterMarginLeft = outterMarginLeft;
    }

    /**
     * 오브젝트의 바깥 오른쪽 여백을 반환한다.
     *
     * @return 오브젝트의 바깥 오른쪽 여백
     */
    public int getOutterMarginRight() {
        return outterMarginRight;
    }

    /**
     * 오브젝트의 바깥 오른쪽 여백을 설정한다.
     *
     * @param outterMarginRight 오브젝트의 바깥 오른쪽 여백
     */
    public void setOutterMarginRight(int outterMarginRight) {
        this.outterMarginRight = outterMarginRight;
    }

    /**
     * 오브젝트의 바깥 위쪽 여백을 반환한다.
     *
     * @return 오브젝트의 바깥 위쪽 여백
     */
    public int getOutterMarginTop() {
        return outterMarginTop;
    }

    /**
     * 오브젝트의 바깥 위쪽 여백을 설정한다.
     *
     * @param outterMarginTop 오브젝트의 바깥 위쪽 여백
     */
    public void setOutterMarginTop(int outterMarginTop) {
        this.outterMarginTop = outterMarginTop;
    }

    /**
     * 오브젝트의 바깥 아래쪽 여백을 반환한다.
     *
     * @return 오브젝트의 바깥 아래쪽 여백
     */
    public int getOutterMarginBottom() {
        return outterMarginBottom;
    }

    /**
     * 오브젝트의 바깥 아래쪽 여백을 설정한다.
     *
     * @param outterMarginBottom 오브젝트의 바깥 아래쪽 여백
     */
    public void setOutterMarginBottom(int outterMarginBottom) {
        this.outterMarginBottom = outterMarginBottom;
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
     * 쪽나눔 방지를 반환한다.
     *
     * @return 쪽나눔 방지
     */
    public boolean isPreventPageDivide() {
        return preventPageDivide;
    }

    /**
     * 쪽나눔 방지를 설정한다.
     *
     * @param preventPageDivide 쪽나눔 방지
     */
    public void setPreventPageDivide(boolean preventPageDivide) {
        this.preventPageDivide = preventPageDivide;
    }

    /**
     * 개체 설명문을 반환한다.
     *
     * @return 개체 설명문
     */
    public HWPString getExplanation() {
        return explanation;
    }

    public byte[] getUnknown() {
        return unknown;
    }

    public void setUnknown(byte[] unknown) {
        this.unknown = unknown;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderGso from2 = (CtrlHeaderGso) from;
        property.copy(from2.property);
        yOffset = from2.yOffset;
        xOffset = from2.xOffset;
        width = from2.width;
        height = from2.height;
        zOrder = from2.zOrder;
        outterMarginLeft = from2.outterMarginLeft;
        outterMarginRight = from2.outterMarginRight;
        outterMarginTop = from2.outterMarginTop;
        outterMarginBottom = from2.outterMarginBottom;
        instanceId = from2.instanceId;
        preventPageDivide = from2.preventPageDivide;
        explanation.copy(from2.explanation);
        if (from2.unknown != null) {
            unknown = new byte[from2.unknown.length];
            System.arraycopy(from2.unknown, 0, unknown, 0, from2.unknown.length);
        } else {
            unknown = null;
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, getSize());
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt4(this.getyOffset());
        sw.writeUInt4(this.getxOffset());
        sw.writeUInt4(this.getWidth());
        sw.writeUInt4(this.getHeight());
        sw.writeSInt4(this.getzOrder());
        sw.writeUInt2(this.getOutterMarginLeft());
        sw.writeUInt2(this.getOutterMarginRight());
        sw.writeUInt2(this.getOutterMarginTop());
        sw.writeUInt2(this.getOutterMarginBottom());
        sw.writeUInt4(this.getInstanceId());
        int temp = 0;
        temp = BitFlag.set(temp, 0, this.isPreventPageDivide());
        sw.writeSInt4(temp);
        sw.writeHWPString(this.getExplanation());
        if (this.getUnknown() != null) {
            sw.writeBytes(this.getUnknown());
        }
    }

    /**
     * 그리기 개체의 컨트롤 헤더 레코드의 크기를 반환한다.
     *
     * @return 그리기 개체의 컨트롤 헤더 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 44;
        size += this.getExplanation().getWCharsSize();
        if (this.getUnknown() != null) {
            size += this.getUnknown().length;
        }
        return size;
    }

    @Override
    public String toString() {
        return "CtrlHeaderGso{" +
                "property=" + property.getValue() +
                ", yOffset=" + yOffset +
                ", xOffset=" + xOffset +
                ", width=" + width +
                ", height=" + height +
                ", zOrder=" + zOrder +
                ", outterMarginLeft=" + outterMarginLeft +
                ", outterMarginRight=" + outterMarginRight +
                ", outterMarginTop=" + outterMarginTop +
                ", outterMarginBottom=" + outterMarginBottom +
                ", instanceId=" + instanceId +
                ", preventPageDivide=" + preventPageDivide +
                ", explanation=" + explanation +
                ", unknown=" + Arrays.toString(unknown) +
                '}';
    }
}
