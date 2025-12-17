package ext.org.apache.poi.hhwpf.util;

final public class UnitConverter {
    private UnitConverter() {}

    /**
     * 폰트 pt 사이즈를 줄 높이 사이즈로 변환 합니다.
     *
     * @param pt
     * @return int
     */
    static public int ptToLineHeight(double pt) {
        return (int) (pt * 100.0f);
    }

    /**
     * mm를 Hwp에 포맷에 맞게 변환 합니다.
     *
     * @param mm
     * @return long
     */
    static public long mmToHwp(double mm) {
        return (long) (mm * 72000.0f / 254.0f + 0.5f);
    }

    /**
     * px값을 mm로 변환합니다.
     *
     * @param px
     * @return double
     */
    static public double pxTomm(double px) { return px * 0.264583333; }

    /**
     * 기본 pt사이즈를 지정합니다.
     *
     * @param pt
     * @return int
     */
    static public int ptToBaseSize(int pt) {
        return pt * 100;
    }

}
