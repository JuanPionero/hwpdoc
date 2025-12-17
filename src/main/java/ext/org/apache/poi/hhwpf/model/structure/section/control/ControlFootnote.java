package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderFootnote;
import ext.org.apache.poi.hhwpf.model.structure.section.control.footnoteendnote.ListHeaderForFootnodeEndnote;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_LIST_HEADER;

/**
 * 각주(FootNote) 컨트롤
 *
 * @author neolord
 */
public class ControlFootnote extends Control implements StateUpdatable, StreamWritable {
    /**
     * 각주/미주용 리스트 헤더
     */
    private ListHeaderForFootnodeEndnote listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public ControlFootnote() {
        super(new CtrlHeaderFootnote());

        listHeader = new ListHeaderForFootnodeEndnote();
        paragraphList = new ParagraphList();
    }

    public ControlFootnote(StreamReader sr) throws Exception {
        super(new CtrlHeaderFootnote(sr));
        DataRecordHeader dataRecordHeader = sr.readDataRecordHeader();
        if(dataRecordHeader.getTagID() != HWPTAG_LIST_HEADER) {
            throw new UnexpectedFileFormatException("Unexpected DataRecordHeader in ControlFootnote");
        }
        this.listHeader = new ListHeaderForFootnodeEndnote(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 각주용 컨트롤 헤더를 반환한다.
     *
     * @return 각주용 컨트롤 헤더
     */
    public CtrlHeaderFootnote getHeader() {
        return (CtrlHeaderFootnote) header;
    }

    /**
     * 각주/미주용 리스트 헤더를 반환한다.
     *
     * @return 각주/미주용 리스트 헤더
     */
    public ListHeaderForFootnodeEndnote getListHeader() {
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
        ControlFootnote cloned = new ControlFootnote();
        cloned.copyControlPart(this);
        cloned.listHeader.copy(listHeader);
        cloned.paragraphList.copy(paragraphList);
        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        this.getHeader().setInstanceId(iid.get());
        this.listHeader.setParaCount(this.paragraphList.getParagraphCount());
        this.paragraphList.updateState(iid);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        this.listHeader.write(sw);
        this.paragraphList.write((sw));

        sw.downRecordLevel();
    }
}
