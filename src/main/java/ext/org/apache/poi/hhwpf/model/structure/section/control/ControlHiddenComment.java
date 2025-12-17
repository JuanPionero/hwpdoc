package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderDefault;
import ext.org.apache.poi.hhwpf.model.structure.section.control.hiddencomment.ListHeaderForHiddenComment;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_LIST_HEADER;

/**
 * 숨은 설명 컨트롤
 *
 * @author neolord
 */
public class ControlHiddenComment extends Control implements StateUpdatable, StreamWritable {
    /**
     * 숨은 설명 용 리스트 헤더
     */
    private ListHeaderForHiddenComment listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public ControlHiddenComment() {
        super(new CtrlHeaderDefault(ControlType.HiddenComment.getCtrlId()));

        listHeader = new ListHeaderForHiddenComment();
        paragraphList = new ParagraphList();
    }

    public ControlHiddenComment(StreamReader sr) throws Exception {
        super(new CtrlHeaderDefault(ControlType.HiddenComment.getCtrlId()));
        DataRecordHeader dataRecordHeader = sr.readDataRecordHeader();
        if (dataRecordHeader.getTagID() != HWPTAG_LIST_HEADER) {
            throw new UnexpectedFileFormatException("Unexpected DataRecordHeader in ControlHiddenComment");
        }
        listHeader = new ListHeaderForHiddenComment(sr);
        paragraphList = new ParagraphList(sr);
    }

    /**
     * 컨트롤 헤더를 반환한다.
     *
     * @return 컨트롤 헤더
     */
    public CtrlHeader getHeader() {
        return header;
    }

    /**
     * 숨은 설명 용 리스트 헤더를 반환한다.
     *
     * @return 숨은 설명 용 리스트 헤더
     */
    public ListHeaderForHiddenComment getListHeader() {
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
        ControlHiddenComment cloned = new ControlHiddenComment();
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
        // 여기서 header는 CtrlHeader로 abstract

        ((CtrlHeaderDefault)this.getHeader()).write(sw);

        sw.upRecordLevel();

//        ForListHeaderForHiddenComment.write(hc.getListHeader(), sw);
        this.listHeader.write(sw);
        
//        ForParagraphList.write(hc.getParagraphList(), sw);
        this.paragraphList.write(sw);

        sw.downRecordLevel();

    }
}
