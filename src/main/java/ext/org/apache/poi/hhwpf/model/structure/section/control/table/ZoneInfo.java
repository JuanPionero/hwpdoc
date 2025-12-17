package ext.org.apache.poi.hhwpf.model.structure.section.control.table;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 영역 속성
 *
 * @author neolord
 */
public class ZoneInfo implements StreamWritable {
    /**
     * 시작 열 주소
     */
    private int startColumn;
    /**
     * 시작 행 주소
     */
    private int startRow;
    /**
     * 끝 열 주소
     */
    private int endColumn;
    /**
     * 끝 행 주소
     */
    private int endRow;
    /**
     * 참조된 테두리/배경 Id
     */
    private int borderFillId;

    /**
     * 생성자
     */
    public ZoneInfo() {
    }

    public ZoneInfo(StreamReader sr) throws IOException {
        this.startColumn = sr.readUInt2();
        this.startRow = sr.readUInt2();
        this.endColumn = sr.readUInt2();
        this.endRow = sr.readUInt2();
        this.borderFillId = sr.readUInt2();
    }
    /**
     * 시작 열 주소를 반환한다.
     *
     * @return 시작 열 주소
     */
    public int getStartColumn() {
        return startColumn;
    }

    /**
     * 시작 열 주소를 설정한다.
     *
     * @param startColumn 시작 열 주소
     */
    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    /**
     * 시작 행 주소를 반환한다.
     *
     * @return 시작 행 주소
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * 시작 행 주소를 설정한다.
     *
     * @param startRow 시작 행 주소
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * 끝 열 주소를 반환한다.
     *
     * @return 끝 열 주소
     */
    public int getEndColumn() {
        return endColumn;
    }

    /**
     * 끝 열 주소를 설정한다.
     *
     * @param endColumn 끝 열 주소
     */
    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    /**
     * 끝 행 주소를 반환한다.
     *
     * @return 끝 행 주소
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * 끝 행 주소를 설정한다.
     *
     * @param endRow 끝 행 주소
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     * 참조된 테두리/배경 Id를 반환한다.
     *
     * @return 참조된 테두리/배경 Id
     */
    public int getBorderFillId() {
        return borderFillId;
    }

    /**
     * 참조된 테두리/배경 Id를 설정한다.
     *
     * @param borderFillId 참조된 테두리/배경 Id
     */
    public void setBorderFillId(int borderFillId) {
        this.borderFillId = borderFillId;
    }

    public ZoneInfo clone() {
        ZoneInfo cloned = new ZoneInfo();
        cloned.startColumn = startColumn;
        cloned.startRow = startRow;
        cloned.endColumn = endColumn;
        cloned.endRow = endRow;
        cloned.borderFillId = borderFillId;
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.getStartColumn());
        sw.writeUInt2(this.getStartRow());
        sw.writeUInt2(this.getEndColumn());
        sw.writeUInt2(this.getEndRow());
        sw.writeUInt2(this.getBorderFillId());
    }
}
