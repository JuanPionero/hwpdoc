package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.caption;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 캡션 정보을 나타내는 객체
 *
 * @author neolord
 */
public class Caption implements StateUpdatable, StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(Caption.class);
    /**
     * 문단 리스트 헤더
     */
    private ListHeaderForCaption listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public Caption() {

    }
    public Caption(StreamReader sr) throws Exception {
        this.listHeader = new ListHeaderForCaption(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 문단 리스트 헤더를 반환한다.
     *
     * @return 문단 리스트 헤더
     */
    public ListHeaderForCaption getListHeader() {
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

    public void copy(Caption from) {
        listHeader.copy(from.listHeader);
        paragraphList.copy(from.paragraphList);
    }

    @Override
    public String toString() {
        return "Caption{" +
                "listHeader=" + listHeader +
                ", paragraphList=" + paragraphList +
                '}';
    }

    @Override
    public void updateState(InstanceID iid) {
        this.getListHeader().setParaCount(this.paragraphList.getParagraphCount());
        this.paragraphList.updateState(iid);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_LIST_HEADER, 30);
        this.listHeader.write(sw);
        this.paragraphList.write(sw);
    }
}
