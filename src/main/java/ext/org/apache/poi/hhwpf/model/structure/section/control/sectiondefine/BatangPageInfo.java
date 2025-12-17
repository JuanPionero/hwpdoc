package ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

/**
 * 바탕 페이지 정보를 나타내는 객체
 *
 * @author neolord
 */
public class BatangPageInfo implements StreamWritable {
    /**
     * 문단 리스트 헤더
     */
    private ListHeaderForBatangPage listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public BatangPageInfo() {
        this.listHeader = new ListHeaderForBatangPage();
        this.paragraphList = new ParagraphList();
    }

    public BatangPageInfo(StreamReader sr) throws Exception {
        this.listHeader = new ListHeaderForBatangPage(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 문단 리스트 헤더를 반환한다.
     *
     * @return 문단 리스트 헤더
     */
    public ListHeaderForBatangPage getListHeader() {
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

    public BatangPageInfo clone() {
        BatangPageInfo cloned = new BatangPageInfo();
        cloned.listHeader.copy(listHeader);
        cloned.paragraphList.copy(paragraphList);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_LIST_HEADER, 34);
        this.getListHeader().write(sw);
        this.paragraphList.write(sw);
    }
}
