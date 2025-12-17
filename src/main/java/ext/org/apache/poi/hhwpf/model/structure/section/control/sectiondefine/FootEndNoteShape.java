package ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.EachBorder;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;

import java.io.IOException;

/**
 * 미주/각주 모양 정보에 대한 레코드
 *
 * @author neolord
 */
public class FootEndNoteShape implements StreamWritable {
    /**
     * 속성
     */
    private FootNoteShapeProperty property;
    /**
     * 사용자 기호
     */
    private HWPString userSymbol;
    /**
     * 앞 장식 문자
     */
    private HWPString beforeDecorativeLetter;
    /**
     * 뒤 장식 문자
     */
    private HWPString afterDecorativeLetter;
    /**
     * 시작 번호
     */
    private int startNumber;
    /**
     * 구분선 길이
     */
    private long divideLineLength;
    /**
     * 구분선 위 여백
     */
    private int divideLineTopMargin;
    /**
     * 구분선 아래 여백
     */
    private int divideLineBottomMargin;
    /**
     * 주석 사이 여백
     */
    private int betweenNotesMargin;
    /**
     * 구분선 정보
     */
    private EachBorder divideLine;
    /**
     * 알수 없는 4 byte;
     */
    private long unknown;

    /**
     * 생성자
     */
    public FootEndNoteShape() {
        property = new FootNoteShapeProperty();
        userSymbol = new HWPString();
        beforeDecorativeLetter = new HWPString();
        afterDecorativeLetter = new HWPString();
        divideLine = new EachBorder();
    }

    public FootEndNoteShape(int propertyValue,
                            String userSymbolValue,
                            String beforeDecorativeLetterValue,
                            String afterDecorativeLetterValue,
                            int startNumberValue,
                            long divideLineLengthValue,
                            int divideLineTopMarginValue,
                            int divideLineBottomMarginValue,
                            int betweenNotesMarginValue,
                            EachBorder divideLineValue,
                            long unknownValue) {
        this.property = new FootNoteShapeProperty(propertyValue);
        this.userSymbol = new HWPString(userSymbolValue);
        this.beforeDecorativeLetter = new HWPString(beforeDecorativeLetterValue);
        this.afterDecorativeLetter = new HWPString(afterDecorativeLetterValue);
        this.startNumber = startNumberValue;
        this.divideLineLength = divideLineLengthValue;
        this.divideLineTopMargin = divideLineTopMarginValue;
        this.divideLineBottomMargin = divideLineBottomMarginValue;
        this.betweenNotesMargin = betweenNotesMarginValue;
        this.divideLine = divideLineValue;
        this.unknown = unknownValue;
    }

    public FootEndNoteShape(StreamReader sr) throws IOException, IllegalAccessException {
        this.property = new FootNoteShapeProperty(sr.readUInt4());
        this.userSymbol = new HWPString(sr.readWChar());
        this.beforeDecorativeLetter = new HWPString(sr.readWChar());
        this.afterDecorativeLetter = new HWPString(sr.readWChar());

        this.startNumber = sr.readUInt2();
        this.divideLineLength = sr.readUInt4();
        this.divideLineTopMargin = sr.readUInt2();
        this.divideLineBottomMargin = sr.readUInt2();
        this.betweenNotesMargin = sr.readUInt2();

        this.divideLine = new EachBorder(sr);


        if (sr.isEndOfDataRecord())
            return;

        this.unknown = sr.readUInt4();
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public FootNoteShapeProperty getProperty() {
        return property;
    }

    /**
     * 사용자 기호를 반환한다.
     *
     * @return 사용자 기호
     */
    public HWPString getUserSymbol() {
        return userSymbol;
    }

    /**
     * 앞 장식 문자를 반환한다.
     *
     * @return 앞 장식 문자
     */
    public HWPString getBeforeDecorativeLetter() {
        return beforeDecorativeLetter;
    }

    /**
     * 뒤 장식 문자를 반환한다.
     *
     * @return 뒤 장식 문자
     */
    public HWPString getAfterDecorativeLetter() {
        return afterDecorativeLetter;
    }

    /**
     * 시작 번호를 반환한다.
     *
     * @return 시작 번호
     */
    public int getStartNumber() {
        return startNumber;
    }

    /**
     * 시작 번호를 설정한다.
     *
     * @param startNumber 시작 번호
     */
    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    /**
     * 구분선 길이를 반환한다.
     *
     * @return 구분선 길이
     */
    public long getDivideLineLength() {
        return divideLineLength;
    }

    /**
     * 구분선 길이를 설정한다.
     *
     * @param divideLineLength 구분선 길이
     */
    public void setDivideLineLength(long divideLineLength) {
        this.divideLineLength = divideLineLength;
    }

    /**
     * 구분선 위 여백의 크기를 반환한다.
     *
     * @return 구분선 위 여백의 크기
     */
    public int getDivideLineTopMargin() {
        return divideLineTopMargin;
    }

    /**
     * 구분선 위 여백의 크기를 설정한다.
     *
     * @param divideLineTopMargin 구분선 위 여백의 크기
     */
    public void setDivideLineTopMargin(int divideLineTopMargin) {
        this.divideLineTopMargin = divideLineTopMargin;
    }

    /**
     * 구분선 아래 여백의 크기를 반환한다.
     *
     * @return 구분선 아래 여백의 크기
     */
    public int getDivideLineBottomMargin() {
        return divideLineBottomMargin;
    }

    /**
     * 구분선 아래 여백의 크기를 설정한다.
     *
     * @param divideLineBottomMargin 구분선 아래 여백의 크기
     */
    public void setDivideLineBottomMargin(int divideLineBottomMargin) {
        this.divideLineBottomMargin = divideLineBottomMargin;
    }

    /**
     * 주석 사이 여백의 크기를 반환한다.
     *
     * @return 주석 사이 여백의 크기
     */
    public int getBetweenNotesMargin() {
        return betweenNotesMargin;
    }

    /**
     * 주석 사이 여백의 크기를 설정한다.
     *
     * @param betweenNotesMargin 주석 사이 여백의 크기
     */
    public void setBetweenNotesMargin(int betweenNotesMargin) {
        this.betweenNotesMargin = betweenNotesMargin;
    }

    /**
     * 구분선 정보를 반환한다.
     *
     * @return 구분선 정보
     */
    public EachBorder getDivideLine() {
        return divideLine;
    }

    /**
     * 알수 없는 4byte를 반환한다.
     *
     * @return 알수 없는 4byte
     */
    public long getUnknown() {
        return unknown;
    }

    /**
     * 알수 없는 4byte를 설정한다.
     *
     * @param unknown 알수 없는 4byte
     */
    public void setUnknown(long unknown) {
        this.unknown = unknown;
    }

    public void copy(FootEndNoteShape from) {
        property.copy(from.property);
        userSymbol.copy(from.userSymbol);
        beforeDecorativeLetter.copy(from.beforeDecorativeLetter);
        afterDecorativeLetter.copy(from.afterDecorativeLetter);
        startNumber = from.startNumber;
        divideLineLength = from.divideLineLength;
        divideLineTopMargin = from.divideLineTopMargin;
        divideLineBottomMargin = from.divideLineBottomMargin;
        betweenNotesMargin = from.betweenNotesMargin;
        divideLine.copy(from.divideLine);
        unknown = from.unknown;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_FOOTNOTE_SHAPE, 28);
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeWChar(this.getUserSymbol().getBytes());
        sw.writeWChar(this.getBeforeDecorativeLetter().getBytes());
        sw.writeWChar(this.getAfterDecorativeLetter().getBytes());
        sw.writeUInt2(this.getStartNumber());
        sw.writeUInt4(this.getDivideLineLength());
        sw.writeUInt2(this.getDivideLineTopMargin());
        sw.writeUInt2(this.getDivideLineBottomMargin());
        sw.writeUInt2(this.getBetweenNotesMargin());
        sw.writeUInt1(this.getDivideLine().getType().getValue());
        sw.writeUInt1(this.getDivideLine().getThickness().getValue());
        sw.writeUInt4(this.getDivideLine().getColor().getValue());
    }
}
