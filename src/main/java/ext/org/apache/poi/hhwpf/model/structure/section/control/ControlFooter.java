 package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderFooter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.headerfooter.ListHeaderForHeaderFooter;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

 /**
 * 꼬리말 컨트롤
 *
 * @author neolord
 */
public class ControlFooter extends Control implements StateUpdatable, StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(ControlFooter.class);
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
    public ControlFooter() {
        super(new CtrlHeaderFooter());

        listHeader = new ListHeaderForHeaderFooter();
        paragraphList = new ParagraphList();
    }

    public ControlFooter(StreamReader sr) throws Exception {
        super(new CtrlHeaderFooter(sr));
        this.listHeader = new ListHeaderForHeaderFooter(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 꼬리말용 컨트롤 헤더를 반환한다.
     *
     * @return 꼬리말용 컨트롤 헤더
     */
    public CtrlHeaderFooter getHeader() {
        return (CtrlHeaderFooter) header;
    }

    /**
     * 머리말/꼬리말용 리스트 헤더를 반환한다.
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
        ControlFooter cloned = new ControlFooter();
        cloned.copyControlPart(this);
        cloned.listHeader.copy(listHeader);
        cloned.paragraphList.copy(paragraphList);
        return cloned;
    }

     @Override
     public void write(StreamWriter sw) throws IOException {
         this.getHeader().write(sw);

         sw.upRecordLevel();
         this.listHeader.write(sw);
         this.paragraphList.write(sw);

         sw.downRecordLevel();
     }

     @Override
    public void updateState(InstanceID iid) {
        this.getListHeader().setParaCount(this.paragraphList.getParagraphCount());
        this.paragraphList.updateState(iid);
    }
}
