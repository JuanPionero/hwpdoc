package ext.org.apache.poi.hhwpf.model.structure.section.control.table;

import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_LIST_HEADER;

/**
 * 표의 셀을 나타내는 객체
 *
 * @author neolord
 */
public class Cell implements StreamWritable {
    /**
     * 문단 리스트 헤더
     */
    private ListHeaderForCell listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public Cell() {
        listHeader = new ListHeaderForCell();
        paragraphList = new ParagraphList();
    }

    public Cell(StreamReader sr) throws Exception {
        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_LIST_HEADER) {
            throw new UnexpectedFileFormatException("Unexpected Table Cell Header");
        }
        this.listHeader = new ListHeaderForCell(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    public Cell(Initializer<Cell> initializer) throws Exception {
        this();
        initializer.init(this);
    }

    /**
     * 문단 리스트 헤더를 반환한다.
     *
     * @return 문단 리스트 헤더
     */
    public ListHeaderForCell getListHeader() {
        return listHeader;
    }

    /**
     * 문단 리스트를 반환한다.
     *
     * @return 문단 리스트
     */
    public ParagraphList getParagraphList() {
        return paragraphList;
    }

    public Cell clone() {
        Cell cloned = new Cell();
        cloned.listHeader.copy(listHeader);
        cloned.paragraphList.copy(paragraphList);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.listHeader.write(sw);
        this.paragraphList.write(sw);
    }

    public void setParagraphList(ParagraphList paragraphList) {
        this.paragraphList = paragraphList;
    }
}
