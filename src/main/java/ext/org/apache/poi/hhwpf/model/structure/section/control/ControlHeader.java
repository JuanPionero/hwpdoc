package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.headerfooter.ListHeaderForHeaderFooter;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_LIST_HEADER;

/**
 * 머리말 컨트롤
 *
 * @author neolord
 */
public class ControlHeader extends Control implements StateUpdatable, StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(ControlHeader.class);
    /**
     * 머리말/꼬리말용 리스트 헤더
     */
    private ListHeaderForHeaderFooter listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public ControlHeader() {
        super(new CtrlHeaderHeader());

        listHeader = new ListHeaderForHeaderFooter();
        paragraphList = new ParagraphList();
    }

    public ControlHeader(StreamReader sr) throws Exception {
        super(new CtrlHeaderHeader(sr));
        this.listHeader = new ListHeaderForHeaderFooter(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 머리말용 컨트롤 헤더를 반환한다.
     *
     * @return 머리말용 컨트롤 헤더
     */
    public CtrlHeaderHeader getHeader() {
        return (CtrlHeaderHeader) header;
    }

    /**
     * 머리말/꼬리말용리스트 헤더를 반환한다.
     *
     * @return 머리말/꼬리말용 리스트 헤더
     */
    public ListHeaderForHeaderFooter getListHeader() {
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

    @Override
    public Control clone() {
        ControlHeader cloned = new ControlHeader();
        cloned.copyControlPart(this);
        cloned.listHeader.copy(listHeader);
        cloned.paragraphList.copy(paragraphList);
        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        this.listHeader.setParaCount(this.paragraphList.getParagraphCount());
        this.paragraphList.updateState(iid);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        this.listHeader.write(sw);
        this.paragraphList.write(sw);

        sw.downRecordLevel();
    }
}
