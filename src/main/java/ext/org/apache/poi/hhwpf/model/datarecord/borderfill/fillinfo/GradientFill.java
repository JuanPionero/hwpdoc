package ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo;



import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * 그러데이션 채우기 객체
 *
 * @author neolord
 */
public class GradientFill implements StreamWritable {
    /**
     * 그러데이션 유형
     */
    private GradientType gradientType;
    /**
     * 그러데이션의 기울임 (문서 오류 : 4byte)
     */
    private long startAngle;
    /**
     * 그러데이션의 가로 중심 (문서 오류 : 4byte)
     */
    private long centerX;
    /**
     * 그러데이션의 세로 중심 (문서 오류 : 4byte)
     */
    private long centerY;
    /**
     * 그러데이션 번짐 정도(0 -100) (문서 오류 : 4byte)
     */
    private long blurringDegree;
    /**
     * 색상이 바뀌는 곳의 위치 리스트 (num > 2 일 경우에만)
     */
    private ArrayList<Integer> changePointList;
    /**
     * 색상 리스트
     */
    private ArrayList<Color4Byte> colorList;
    /**
     * 번짐 중심 (추가 속성)
     */
    private short blurringCenter;


    /**
     * 생성자
     */
    public GradientFill() {
        changePointList = new ArrayList<Integer>();
        colorList = new ArrayList<Color4Byte>();
    }
    public GradientFill(StreamReader sr) throws IOException {
        this.gradientType = GradientType.valueOf(sr.readSInt1());
        // 공식문서에 잘못기재된 부분 존재: 2byte가 아닌 4byte임.
        this.startAngle = sr.readUInt4();
        this.centerX = sr.readUInt4();
        this.centerY = sr.readUInt4();
        this.blurringDegree = sr.readUInt4();

        this.changePointList = new ArrayList<Integer>();
        this.colorList = new ArrayList<Color4Byte>();

        long colorCount = sr.readUInt4();
        if(colorCount > 2) {
            for (int index = 0; index < colorCount; index++) {
                this.changePointList.add(sr.readSInt4());
            }
        }
        for (int index = 0; index < colorCount; index++) {
            this.colorList.add( new Color4Byte( sr.readUInt4() ) );
        }
    }

    /**
     * 그러데이션 유형을 반환한다.
     *
     * @return 그러데이션 유형
     */
    public GradientType getGradientType() {
        return gradientType;
    }

    /**
     * 그러데이션 유형을 설정한다.
     *
     * @param gradientType 그러데이션 유형
     */
    public void setGradientType(GradientType gradientType) {
        this.gradientType = gradientType;
    }

    /**
     * 그러데이션의 기울임을 반환한다.
     *
     * @return 그러데이션의 기울임
     */
    public long getStartAngle() {
        return startAngle;
    }

    /**
     * 그러데이션의 기울임를 설정한다.
     *
     * @param startAngle 그러데이션의 기울임
     */
    public void setStartAngle(long startAngle) {
        this.startAngle = startAngle;
    }

    /**
     * 그러데이션의 가로 중심를 반황한다.
     *
     * @return 그러데이션의 가로 중심
     */
    public long getCenterX() {
        return centerX;
    }

    /**
     * 그러데이션의 가로 중심를 설정한다.
     *
     * @param centerX 그러데이션의 가로 중심
     */
    public void setCenterX(long centerX) {
        this.centerX = centerX;
    }

    /**
     * 그러데이션의 세로 중심을 반환한다.
     *
     * @return 그러데이션의 세로 중심
     */
    public long getCenterY() {
        return centerY;
    }

    /**
     * 그러데이션의 세로 중심을 설정한다.
     *
     * @param centerY 그러데이션의 세로 중심
     */
    public void setCenterY(long centerY) {
        this.centerY = centerY;
    }

    /**
     * 그러데이션 번짐 정도을 반환한다.
     *
     * @return 그러데이션 번짐 정도
     */
    public long getBlurringDegree() {
        return blurringDegree;
    }

    /**
     * 그러데이션 번짐 정도를 설정한다.
     *
     * @param blurringDegree 그러데이션 번짐 정도
     */
    public void setBlurringDegree(long blurringDegree) {
        this.blurringDegree = blurringDegree;
    }

    /**
     * 색상이 바뀌는 곳의 위치를 추가한다.
     *
     * @param changePoint 색상이 바뀌는 곳의 위치
     */
    public void addChangePoint(int changePoint) {
        changePointList.add(changePoint);
    }

    /**
     * 색상이 바뀌는 곳의 위치 리스트를 반환한다.
     *
     * @return 색상이 바뀌는 곳의 위치 리스트
     */
    public ArrayList<Integer> getChangePointList() {
        return changePointList;
    }

//    /**
//     * 새로운 색상 객체를 생성하고 색상 리스트에 추가한다.
//     *
//     * @return 새로 생성된 색상 객체
//     */
//    public Color4Byte addNewColor() {
//        Color4Byte c = new Color4Byte();
//        colorList.add(c);
//        return c;
//    }

    /**
     * 색상 리스트를 반환한다.
     *
     * @return 색상 리스트
     */
    public ArrayList<Color4Byte> getColorList() {
        return colorList;
    }

    /**
     * 번짐 중심을 반환한다.
     *
     * @return 번짐 중심
     */
    public short getBlurringCenter() {
        return blurringCenter;
    }

    /**
     * 번짐 중심을 설정한다.
     *
     * @param blurringCenter 번짐 중심
     */
    public void setBlurringCenter(short blurringCenter) {
        this.blurringCenter = blurringCenter;
    }

    public void copy(GradientFill from) {
        gradientType = from.gradientType;
        startAngle = from.startAngle;
        centerX = from.centerX;
        centerY = from.centerY;
        blurringDegree = from.blurringDegree;

        changePointList.clear();
        for (Integer integer : from.changePointList) {
            // changePointList.add(new Integer(integer.intValue()));
            // 사소한 warning 제거
            changePointList.add(Integer.valueOf(integer.intValue()));
        }

        colorList.clear();
        for (Color4Byte color4Byte : from.colorList) {
            colorList.add(color4Byte.clone());
        }

        blurringCenter = from.blurringCenter;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeSInt1(this.getGradientType().getValue());
        sw.writeUInt4(this.getStartAngle());
        sw.writeUInt4(this.getCenterX());
        sw.writeUInt4(this.getCenterY());
        sw.writeUInt4(this.getBlurringDegree());

        long colorCount = this.getColorList().size();
        sw.writeUInt4(colorCount);
        if (colorCount > 2) {
            for (int cp : this.getChangePointList()) {
                sw.writeSInt4(cp);
            }
        }
        for (Color4Byte c : this.getColorList()) {
            sw.writeUInt4(c.getValue());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.gradientType.getValue(),
                this.startAngle, this.centerX, this.centerY, this.blurringDegree,
                Arrays.hashCode(changePointList.toArray()), Arrays.hashCode(colorList.toArray()),
                this.blurringCenter);
    }
}
