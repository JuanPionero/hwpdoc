package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.sectiondefine.SectionDefineHeaderProperty;

import java.io.IOException;

/**
 * 구역 정의 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderSectionDefine extends CtrlHeader implements StreamWritable {
    /**
     * 속성
     */
    private SectionDefineHeaderProperty property;
    /**
     * 동일한 페이지에서 서로 다른 단 사이의 간격
     */
    private int columnGap;
    /**
     * 세로로 줄맞춤을 할지 여부
     */
    private int verticalLineAlign;
    /**
     * 가로로 줄맞춤을 할지 여부
     */
    private int horizontalLineAlign;
    /**
     * 기본 탭 간격
     */
    private long defaultTabGap;
    /**
     * 번호 문단 모양 ID
     */
    private int numberParaShapeId;
    /**
     * 쪽 시작 번호
     */
    private int pageStartNumber;
    /**
     * 그림 시작 번호
     */
    private int imageStartNumber;
    /**
     * 표 시작 번호
     */
    private int tableStartNumber;
    /**
     * 수식 시작 번호
     */
    private int equationStartNumber;
    /**
     * 대표Language(5.0.1.5 이상)
     */
    private int defaultLanguage;

    /**
     * 생성자
     */
    public CtrlHeaderSectionDefine() {
        super(ControlType.SectionDefine.getCtrlId());
        property = new SectionDefineHeaderProperty();
    }

    public CtrlHeaderSectionDefine(int propertyValue,
                   int columnGapValue,
                   int verticalLineAlignValue,
                   int horizontalLineAlignValue,
                   long defaultTabGapValue,
                   int numberParaShapeIdValue,
                   int pageStartNumberValue,
                   int imageStartNumberValue,
                   int tableStartNumberValue,
                   int equationStartNumberValue,
                   int defaultLanguageValue) {
        super(ControlType.SectionDefine.getCtrlId());
        this.property = new SectionDefineHeaderProperty(propertyValue);
        this.columnGap = columnGapValue;
        this.verticalLineAlign = verticalLineAlignValue;
        this.horizontalLineAlign = horizontalLineAlignValue;
        this.defaultTabGap = defaultTabGapValue;
        this.numberParaShapeId = numberParaShapeIdValue;
        this.pageStartNumber = pageStartNumberValue;
        this.imageStartNumber = imageStartNumberValue;
        this.tableStartNumber = tableStartNumberValue;
        this.equationStartNumber = equationStartNumberValue;
        this.defaultLanguage = defaultLanguageValue;
    }

    public CtrlHeaderSectionDefine(StreamReader sr) throws IOException, IllegalAccessException {
        super(ControlType.SectionDefine.getCtrlId());
        property = new SectionDefineHeaderProperty(sr.readUInt4());
        this.columnGap = sr.readUInt2();
        this.verticalLineAlign = sr.readUInt2();
        this.horizontalLineAlign = sr.readUInt2();
        this.defaultTabGap = sr.readUInt4();
        this.numberParaShapeId = sr.getCorrectedParaShapeId(sr.readUInt2());
        this.pageStartNumber = sr.readUInt2();
        this.imageStartNumber = sr.readUInt2();
        this.tableStartNumber = sr.readUInt2();
        this.equationStartNumber = sr.readUInt2();

        if (!sr.isEndOfDataRecord()
                && sr.getFileHeader().getFileVersion().isGreaterEqual((short)5, (short)0, (short)1, (short)2)) {
            this.defaultLanguage = sr.readUInt2();
        }

        if (sr.isEndOfDataRecord()) {
            return;
        }

        sr.readRestOfDataRecordData();
    }

    /**
     * 구역 정의 컨트롤의 속성 객체를 반환한다.
     *
     * @return 구역 정의 컨트롤의 속성 객체
     */
    public SectionDefineHeaderProperty getProperty() {
        return property;
    }

    /**
     * 동일한 페이지에서 서로 다른 단 사이의 간격을 반환한다.
     *
     * @return 동일한 페이지에서 서로 다른 단 사이의 간격
     */
    public int getColumnGap() {
        return columnGap;
    }

    /**
     * 동일한 페이지에서 서로 다른 단 사이의 간격을 설정한다.
     *
     * @param columnGap 동일한 페이지에서 서로 다른 단 사이의 간격
     */
    public void setColumnGap(int columnGap) {
        this.columnGap = columnGap;
    }

    /**
     * 세로로 줄맞춤을 할지 여부를 반환한다.
     *
     * @return 세로로 줄맞춤을 할지 여부
     */
    public int getVerticalLineAlign() {
        return verticalLineAlign;
    }

    /**
     * 세로로 줄맞춤을 할지 여부를 설정한다.
     *
     * @param verticalLineAlign 세로로 줄맞춤을 할지 여부
     */
    public void setVerticalLineAlign(int verticalLineAlign) {
        this.verticalLineAlign = verticalLineAlign;
    }

    /**
     * 가로로 줄맞춤을 할지 여부를 반환한다.
     *
     * @return 가로로 줄맞춤을 할지 여부
     */
    public int getHorizontalLineAlign() {
        return horizontalLineAlign;
    }

    /**
     * 가로로 줄맞춤을 할지 여부를 설정한다.
     *
     * @param horizontalLineAlign 가로로 줄맞춤을 할지 여부
     */
    public void setHorizontalLineAlign(int horizontalLineAlign) {
        this.horizontalLineAlign = horizontalLineAlign;
    }

    /**
     * 기본 탭 간격을 반환한다.
     *
     * @return 기본 탭 간격
     */
    public long getDefaultTabGap() {
        return defaultTabGap;
    }

    /**
     * 기본 탭 간격을 설정한다.
     *
     * @param defaultTabGap 기본 탭 간격
     */
    public void setDefaultTabGap(long defaultTabGap) {
        this.defaultTabGap = defaultTabGap;
    }

    /**
     * 번호 문단 모양 ID를 반환한다.
     *
     * @return 번호 문단 모양 ID
     */
    public int getNumberParaShapeId() {
        return numberParaShapeId;
    }

    /**
     * 번호 문단 모양 ID를 설정한다.
     *
     * @param numberParaShapeId 번호 문단 모양 ID
     */
    public void setNumberParaShapeId(int numberParaShapeId) {
        this.numberParaShapeId = numberParaShapeId;
    }

    /**
     * 쪽 시작 번호를 반환한다.
     *
     * @return 쪽 시작 번호
     */
    public int getPageStartNumber() {
        return pageStartNumber;
    }

    /**
     * 쪽 시작 번호를 섫정한다.
     *
     * @param pageStartNumber 쪽 시작 번호
     */
    public void setPageStartNumber(int pageStartNumber) {
        this.pageStartNumber = pageStartNumber;
    }

    /**
     * 아미지 시작 번호를 반환한다.
     *
     * @return 아미지 시작 번호
     */
    public int getImageStartNumber() {
        return imageStartNumber;
    }

    /**
     * 아미지 시작 번호를 설정한다.
     *
     * @param imageStartNumber 아미지 시작 번호
     */
    public void setImageStartNumber(int imageStartNumber) {
        this.imageStartNumber = imageStartNumber;
    }

    /**
     * 표 시작 번호를 반환한다.
     *
     * @return 표 시작 번호
     */
    public int getTableStartNumber() {
        return tableStartNumber;
    }

    /**
     * 표 시작 번호를 설정한다.
     *
     * @param tableStartNumber 표 시작 번호
     */
    public void setTableStartNumber(int tableStartNumber) {
        this.tableStartNumber = tableStartNumber;
    }

    /**
     * 수식 시작 번호를 반환한다.
     *
     * @return 수식 시작 번호
     */
    public int getEquationStartNumber() {
        return equationStartNumber;
    }

    /**
     * 수식 시작 번호를 설정한다.
     *
     * @param equationStartNumber 수식 시작 번호
     */
    public void setEquationStartNumber(int equationStartNumber) {
        this.equationStartNumber = equationStartNumber;
    }

    /**
     * 대표Language 값을 반환한다. (5.0.1.5 이상)
     *
     * @return 대표Language 값
     */
    public int getDefaultLanguage() {
        return defaultLanguage;
    }

    /**
     * 대표Language 값을 설정한다. (5.0.1.5 이상)
     *
     * @param defaultLanguage 대표Language 값
     */
    public void setDefaultLanguage(int defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderSectionDefine from2 = (CtrlHeaderSectionDefine) from;
        property.copy(from2.property);
        columnGap = from2.columnGap;
        verticalLineAlign = from2.verticalLineAlign;
        horizontalLineAlign = from2.horizontalLineAlign;
        defaultTabGap = from2.defaultTabGap;
        numberParaShapeId = from2.numberParaShapeId;
        pageStartNumber = from2.pageStartNumber;
        imageStartNumber = from2.imageStartNumber;
        tableStartNumber = from2.tableStartNumber;
        equationStartNumber = from2.equationStartNumber;
        defaultLanguage = from2.defaultLanguage;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt2(this.getColumnGap());
        sw.writeUInt2(this.getVerticalLineAlign());
        sw.writeUInt2(this.getHorizontalLineAlign());
        sw.writeUInt4(this.getDefaultTabGap());
        sw.writeUInt2(sw.getCorrectedParaShapeId(sw.getCorrectedParaShapeId(this.getNumberParaShapeId())));
        sw.writeUInt2(this.getPageStartNumber());
        sw.writeUInt2(this.getImageStartNumber());
        sw.writeUInt2(this.getTableStartNumber());
        sw.writeUInt2(this.getEquationStartNumber());
        if (sw.getFileVersion().isGreaterEqual(5, 0, 1, 2)) {
            sw.writeUInt2(this.getDefaultLanguage());
        }
        sw.writeZero(8);
    }
}
