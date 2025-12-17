package ext.org.apache.poi.hhwpf.model.datarecord.documentproperties;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 각종 시작번호에 대한 정보
 *
 * @author neolord
 */
public class StartNumber {
    /**
     * 페이지 시작 번호
     */
    private int page;
    /**
     * 각주 시작 번호
     */
    private int footnote;
    /**
     * 미주 시작 번호
     */
    private int endnote;
    /**
     * 그림 시작 번호
     */
    private int picture;
    /**
     * 표 시작 번호
     */
    private int table;
    /**
     * 수식 시작 번호
     */
    private int equation;

    /**
     * 생성자
     */
    public StartNumber(StreamReader sr) throws IOException {
        this.page = sr.readUInt2();
        this.footnote = sr.readUInt2();
        this.endnote = sr.readUInt2();
        this.picture = sr.readUInt2();
        this.table = sr.readUInt2();
        this.equation = sr.readUInt2();
    }

    public StartNumber() {
        this.page = 1;
        this.footnote = 1;
        this.endnote = 1;
        this.picture = 1;
        this.table = 1;
        this.equation = 1;
    }

    /**
     * 페이지 시작 번호를 반환한다.
     *
     * @return 페이지 시작 번호
     */
    public int getPage() {
        return page;
    }

    /**
     * 페이지 시작 번호를 설정한다.
     *
     * @param page 페이지 시작 번호
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * 각주 시작 번호를 반환한다.
     *
     * @return 각주 시작 번호
     */
    public int getFootnote() {
        return footnote;
    }

    /**
     * 각주 시작 번호를 설정한다.
     *
     * @param footnote 각주 시작 번호
     */
    public void setFootnote(int footnote) {
        this.footnote = footnote;
    }

    /**
     * 미주 시작 번호를 반환한다.
     *
     * @return 미주 시작 번호
     */
    public int getEndnote() {
        return endnote;
    }

    /**
     * 미주 시작 번호를 설정한다.
     *
     * @param endnote 미주 시작 번호
     */
    public void setEndnote(int endnote) {
        this.endnote = endnote;
    }

    /**
     * 그림 시작 번호를 반환한다.
     *
     * @return 그림 시작 번호
     */
    public int getPicture() {
        return picture;
    }

    /**
     * 그림 시작 번호를 설정한다.
     *
     * @param picture 그림 시작 번호
     */
    public void setPicture(int picture) {
        this.picture = picture;
    }

    /**
     * 표 시작 번호를 반환한다.
     *
     * @return 표 시작 번호
     */
    public int getTable() {
        return table;
    }

    /**
     * 표 시작 번호를 설정한다.
     *
     * @param table 표 시작 번호
     */
    public void setTable(int table) {
        this.table = table;
    }

    /**
     * 수식 시작 번호를 반환한다.
     *
     * @return 수식 시작 번호
     */
    public int getEquation() {
        return equation;
    }

    /**
     * 수식 시작 번호를 설정한다.
     *
     * @param equation 수식 시작 번호
     */
    public void setEquation(int equation) {
        this.equation = equation;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.page);
        sw.writeUInt2(this.footnote);
        sw.writeUInt2(this.endnote);
        sw.writeUInt2(this.picture);
        sw.writeUInt2(this.table);
        sw.writeUInt2(this.equation);
    }
}
