package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import ext.org.apache.poi.hhwpf.model.datarecord.parashape.ParaShapeProperty1;
import ext.org.apache.poi.hhwpf.model.datarecord.parashape.ParaShapeProperty2;
import ext.org.apache.poi.hhwpf.model.datarecord.parashape.ParaShapeProperty3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * 문단 모양에 대한 레코드
 *
 * @author neolord
 */
public class ParaShape {
    private static final Logger logger = LoggerFactory.getLogger(ParaShape.class);
    /**
     * 속성 1
     */
    private ParaShapeProperty1 property1;
    /**
     * 왼쪽 여백
     */
    private int leftMargin;
    /**
     * 오른쪽 여백
     */
    private int rightMargin;
    /**
     * 들여 쓰기/내어 쓰기
     */
    private int indent;
    /**
     * 문단 위 간격
     */
    private int topParaSpace;
    /**
     * 문단 아래 간격
     */
    private int bottomParaSpace;
    /**
     * 줄 간격. 한글 2007 이하 버전(5.0.2.5 버전 미만)에서 사용
     */
    private int lineSpace;
    /**
     * 참조된 탭 정의 아이디
     */
    private int tabDefId;
    /**
     * 참조된 문단 번호 아이디 나 참조된 글머리표 아이디
     */
    private int paraHeadId;
    /**
     * 참조된 테두리/배경 모양 아이디
     */
    private int borderFillId;
    /**
     * 문단 테두리 왼쪽 간격
     */
    private short leftBorderSpace;
    /**
     * 문단 테두리 오른쪽 간격
     */
    private short rightBorderSpace;
    /**
     * 문단 테두리 위쪽 간격
     */
    private short topBorderSpace;
    /**
     * 문단 테두리 아래쪽 간격
     */
    private short bottomBorderSpace;
    /**
     * 속성 2 (5.0.1.7 버전 이상)
     */
    private ParaShapeProperty2 property2;
    /**
     * 속성 3 (5.0.2.5 버전 이상)
     */
    private ParaShapeProperty3 property3;
    /**
     * 줄 간격(5.0.2.5 버전 이상)
     */
    private long lineSpace2;
    /**
     * 개요 수준(5.1.0.0 버전 이상)
     * property1.paraLevel 대신 사용하는 듯.
     */
    private long paraLevel;

    /**
     * 생성자
     */
    public ParaShape() {
        property1 = new ParaShapeProperty1();
        property2 = new ParaShapeProperty2();
        property3 = new ParaShapeProperty3();
    }

    public ParaShape(long property1Value,
                     int leftMarginValue,
                     int rightMarginValue,
                     int indentValue,
                     int topParaSpaceValue,
                     int bottomParaSpaceValue,
                     int lineSpaceValue,
                     int tabDefIdValue,
                     int paraHeadIdValue,
                     int borderFillIdValue,
                     short leftBorderSpaceValue,
                     short rightBorderSpaceValue,
                     short topBorderSpaceValue,
                     short bottomBorderSpaceValue,
                     long property2Value,
                     long property3Value,
                     long lineSpace2Value) {

        this.property1 = new ParaShapeProperty1(property1Value);
        this.leftMargin = leftMarginValue;
        this.rightMargin = rightMarginValue;
        this.indent = indentValue;
        this.topParaSpace = topParaSpaceValue;
        this.bottomParaSpace = bottomParaSpaceValue;
        this.lineSpace = lineSpaceValue;
        this.tabDefId = tabDefIdValue;
        this.paraHeadId = paraHeadIdValue;
        this.borderFillId = borderFillIdValue;

        this.leftBorderSpace = leftBorderSpaceValue;
        this.rightBorderSpace = rightBorderSpaceValue;
        this.topBorderSpace = topBorderSpaceValue;
        this.bottomBorderSpace = bottomBorderSpaceValue;
        this.property2 = new ParaShapeProperty2(property2Value);;
        this.property3 = new ParaShapeProperty3(property3Value);;
        this.lineSpace2 = lineSpace2Value;
        this.paraLevel = this.property1.getParaLevel();

    }

    public ParaShape(StreamReader sr) throws IOException, IllegalAccessException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        property1 = new ParaShapeProperty1(sr.readUInt4());
        this.leftMargin = sr.readSInt4();
        this.rightMargin = sr.readSInt4();
        this.indent = sr.readSInt4();
        this.topParaSpace = sr.readSInt4();
        this.bottomParaSpace = sr.readSInt4();
        this.lineSpace = sr.readSInt4();
        this.tabDefId = sr.readUInt2();
        this.paraHeadId = sr.readUInt2();
        this.borderFillId = sr.readUInt2();

        this.leftBorderSpace = sr.readSInt2();
        this.rightBorderSpace = sr.readSInt2();
        this.topBorderSpace = sr.readSInt2();
        this.bottomBorderSpace = sr.readSInt2();

        final FileVersion fileVersion = sr.getFileHeader().getFileVersion();
        // >= 5.0.1.7
        if (fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 1, (short) 7)) {
            this.property2 = new ParaShapeProperty2(sr.readUInt4());
        }

        if(sr.isEndOfDataRecord()) {
            return;
        }

        // >= 5.0.2.5
        if (fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 2, (short) 5)) {
            this.property3 = new ParaShapeProperty3(sr.readUInt4());
            this.lineSpace2 = sr.readUInt4();
        }

        if(sr.isEndOfDataRecord()) {
            return;
        }

        this.paraLevel = sr.readUInt4();

    }

    /**
     * 문단 모양의 속성1 객체를 반환한다.
     *
     * @return 문단 모양의 속성1 객체
     */
    public ParaShapeProperty1 getProperty1() {
        return property1;
    }

    /**
     * 왼쪽 여백을 반환한다.
     *
     * @return 왼쪽 여백
     */
    public int getLeftMargin() {
        return leftMargin;
    }

    /**
     * 왼쪽 여백을 설정한다.
     *
     * @param leftMargin 왼쪽 여백
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * 오른쪽 여백을 반환한다.
     *
     * @return 오른쪽 여백
     */
    public int getRightMargin() {
        return rightMargin;
    }

    /**
     * 오른쪽 여백을 설정한다.
     *
     * @param rightMargin 오른쪽 여백
     */
    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    /**
     * 들여 쓰기/내어 쓰기를 반환한다.
     *
     * @return 들여 쓰기/내어 쓰기
     */
    public int getIndent() {
        return indent;
    }

    /**
     * 들여 쓰기/내어 쓰기를 설정한다.
     *
     * @param indent 들여 쓰기/내어 쓰기
     */
    public void setIndent(int indent) {
        this.indent = indent;
    }

    /**
     * 문단 위 간격을 반환한다.
     *
     * @return 문단 위 간격
     */
    public int getTopParaSpace() {
        return topParaSpace;
    }

    /**
     * 문단 위 간격을 설정한다.
     *
     * @param topParaSpace 문단 위 간격
     */
    public void setTopParaSpace(int topParaSpace) {
        this.topParaSpace = topParaSpace;
    }

    /**
     * 문단 아래 간격을 반환한다.
     *
     * @return 문단 아래 간격
     */
    public int getBottomParaSpace() {
        return bottomParaSpace;
    }

    /**
     * 문단 아래 간격을 설정한다.
     *
     * @param bottomParaSpace 문단 아래 간격
     */
    public void setBottomParaSpace(int bottomParaSpace) {
        this.bottomParaSpace = bottomParaSpace;
    }

    /**
     * 줄 간격을 반환한다. 한글 2007 이하 버전(5.0.2.5 버전 미만)에서 사용
     *
     * @return 줄 간격
     */
    public int getLineSpace() {
        return lineSpace;
    }

    /**
     * 줄 간격을 설정한다. 한글 2007 이하 버전(5.0.2.5 버전 미만)에서 사용
     *
     * @param lineSpace 줄 간격
     */
    public void setLineSpace(int lineSpace) {
        this.lineSpace = lineSpace;
    }

    /**
     * 참조된 탭 정의 아이디를 반환한다.
     *
     * @return 참조된 탭 정의 아이디
     */
    public int getTabDefId() {
        return tabDefId;
    }

    /**
     * 참조된 탭 정의 아이디를 설정한다.
     *
     * @param tabDefId 참조된 탭 정의 아이디
     */
    public void setTabDefId(int tabDefId) {
        this.tabDefId = tabDefId;
    }

    /**
     * 참조된 문단 번호 아이디 나 참조된 글머리표 아이디를 반환한다.
     *
     * @return 참조된 문단 번호 아이디 나 참조된 글머리표 아이디
     */
    public int getParaHeadId() {
        return paraHeadId;
    }

    /**
     * 참조된 문단 번호 아이디 나 참조된 글머리표 아이디릂 설정한다.
     *
     * @param paraHeadId 참조된 문단 번호 아이디 나 참조된 글머리표 아이디
     */
    public void setParaHeadId(int paraHeadId) {
        this.paraHeadId = paraHeadId;
    }

    /**
     * 참조된 테두리/배경 모양 아이디를 반환한다.
     *
     * @return 참조된 테두리/배경 모양 아이디
     */
    public int getBorderFillId() {
        return borderFillId;
    }

    /**
     * 참조된 테두리/배경 모양 아이디를 설정한다.
     *
     * @param borderFillId 참조된 테두리/배경 모양 아이디
     */
    public void setBorderFillId(int borderFillId) {
        this.borderFillId = borderFillId;
    }

    /**
     * 문단 테두리 왼쪽 간격을 반환한다.
     *
     * @return 문단 테두리 왼쪽 간격
     */
    public short getLeftBorderSpace() {
        return leftBorderSpace;
    }

    /**
     * 문단 테두리 왼쪽 간격을 설정한다.
     *
     * @param leftBorderSpace 문단 테두리 왼쪽 간격
     */
    public void setLeftBorderSpace(short leftBorderSpace) {
        this.leftBorderSpace = leftBorderSpace;
    }

    /**
     * 문단 테두리 오른쪽 간격을 반환한다.
     *
     * @return 문단 테두리 오른쪽 간격
     */
    public short getRightBorderSpace() {
        return rightBorderSpace;
    }

    /**
     * 문단 테두리 오른쪽 간격을 설정한다.
     *
     * @param rightBorderSpace 문단 테두리 오른쪽 간격
     */
    public void setRightBorderSpace(short rightBorderSpace) {
        this.rightBorderSpace = rightBorderSpace;
    }

    /**
     * 문단 테두리 위쪽 간격을 반환한다.
     *
     * @return 문단 테두리 위쪽 간격
     */
    public short getTopBorderSpace() {
        return topBorderSpace;
    }

    /**
     * 문단 테두리 위쪽 간격을 설정한다.
     *
     * @param topBorderSpace 문단 테두리 위쪽 간격
     */
    public void setTopBorderSpace(short topBorderSpace) {
        this.topBorderSpace = topBorderSpace;
    }

    /**
     * 문단 테두리 아래쪽 간격을 반환한다.
     *
     * @return 문단 테두리 아래쪽 간격
     */
    public short getBottomBorderSpace() {
        return bottomBorderSpace;
    }

    /**
     * 문단 테두리 아래쪽 간격을 설정한다.
     *
     * @param bottomBorderSpace 문단 테두리 아래쪽 간격
     */
    public void setBottomBorderSpace(short bottomBorderSpace) {
        this.bottomBorderSpace = bottomBorderSpace;
    }

    /**
     * 믄단 모양의 속성 2 객체를 반환한다. (5.0.1.7 버전 이상)
     *
     * @return 믄단 모양의 속성 2 객체
     */
    public ParaShapeProperty2 getProperty2() {
        return property2;
    }

    /**
     * 문단 모양의 속성 3 객체를 반환한다. (5.0.2.5 버전 이상)
     *
     * @return 문단 모양의 속성 3 객체
     */
    public ParaShapeProperty3 getProperty3() {
        return property3;
    }

    /**
     * 줄 간격을 반환한다. (5.0.2.5 버전 이상)
     *
     * @return 줄 간격
     */
    public long getLineSpace2() {
        return lineSpace2;
    }

    /**
     * 줄 간격을 설정한다. (5.0.2.5 버전 이상)
     *
     * @param lineSpace2 줄 간격
     */
    public void setLineSpace2(long lineSpace2) {
        this.lineSpace2 = lineSpace2;
    }

    /**
     * 개요 수준을 반환한다. (5.1.0.0 버전 이상)
     *
     * @return 개요 수준 (5.1.0.0 버전 이상)
     */
    public long getParaLevel() {
        return paraLevel;
    }

    /**
     * 개요 수준을 설정한다. (5.1.0.0 버전 이상)
     *
     * @param paraLevel 개요 수준
     */
    public void setParaLevel(long paraLevel) {
        this.paraLevel = paraLevel;
    }

    public ParaShape clone() {
        ParaShape cloned = new ParaShape();
        cloned.property1.copy(property1);
        cloned.leftMargin = leftMargin;
        cloned.rightMargin = rightMargin;
        cloned.indent = indent;
        cloned.topParaSpace = topParaSpace;
        cloned.bottomParaSpace = bottomParaSpace;
        cloned.lineSpace = lineSpace;
        cloned.tabDefId = tabDefId;
        cloned.paraHeadId = paraHeadId;
        cloned.borderFillId = borderFillId;
        cloned.leftBorderSpace = leftBorderSpace;
        cloned.rightBorderSpace = rightBorderSpace;
        cloned.topBorderSpace = topBorderSpace;
        cloned.bottomBorderSpace = bottomBorderSpace;
        cloned.property2.copy(property2);
        cloned.property3.copy(property3);
        cloned.lineSpace2 = lineSpace2;
        cloned.paraLevel = paraLevel;
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PARA_SHAPE, getSize(sw.getFileVersion()));
        
        sw.writeUInt4(this.getProperty1().getValue());
        sw.writeSInt4(this.getLeftMargin());
        sw.writeSInt4(this.getRightMargin());
        sw.writeSInt4(this.getIndent());
        sw.writeSInt4(this.getTopParaSpace());
        sw.writeSInt4(this.getBottomParaSpace());
        sw.writeSInt4(this.getLineSpace());
        sw.writeUInt2(this.getTabDefId());
        sw.writeUInt2(this.getParaHeadId());
        sw.writeUInt2(this.getBorderFillId());
        sw.writeSInt2(this.getLeftBorderSpace());
        sw.writeSInt2(this.getRightBorderSpace());
        sw.writeSInt2(this.getTopBorderSpace());
        sw.writeSInt2(this.getBottomBorderSpace());
        if (sw.getFileVersion().isGreaterEqual(5, 0, 1, 7)) {
            sw.writeUInt4(this.getProperty2().getValue());
        }
        if (sw.getFileVersion().isGreaterEqual(5, 0, 2, 5)) {
            sw.writeUInt4(this.getProperty3().getValue());
            sw.writeUInt4(this.getLineSpace2());
        }
        if (sw.getFileVersion().isGreaterEqual(5, 0, 255, 255)) {
            sw.writeUInt4(this.getParaLevel());
        }
    }

    /**
     * 문단 모양 레코드의 크기를 반환한다.
     *
     * @param version 파일 버전
     * @return 문단 모양 레코드의 크기
     */
    public final int getSize(FileVersion version) {
        int size = 0;
        size += 42;
        if (version.isGreaterEqual(5, 0, 1, 7)) {
            size += 4;
        }
        if (version.isGreaterEqual(5, 0, 2, 5)) {
            size += 8;
        }
        if (version.isGreaterEqual(5, 0, 255, 255)) {
            size += 4;
        }

        return size;
    }

    // 사용할지 모르겠음...
    // hash 생성으로 들어가는 자원소비가 성능에 영향을 미칠 수 있을 것이라고 생각됨.
    @Override
    public int hashCode() {
        return Objects.hash(this.property1,
                this.leftMargin,
                this.rightMargin,
                this.indent,
                this.topParaSpace,
                this.bottomParaSpace,
                this.lineSpace,
                this.tabDefId,
                this.paraHeadId,
                this.borderFillId,
                this.leftBorderSpace,
                this.rightBorderSpace,
                this.topBorderSpace,
                this.bottomBorderSpace,
                this.property2,
                this.property3,
                this.lineSpace2,
                this.paraLevel);
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode()==o.hashCode();
    }
}
