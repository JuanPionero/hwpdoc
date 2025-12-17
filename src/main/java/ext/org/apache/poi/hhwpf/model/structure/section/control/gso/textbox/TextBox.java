package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

/**
 * 글상자을 나타내는 객체
 *
 * @author neolord
 */
public class TextBox implements StateUpdatable, StreamWritable {
    /**
     * 문단 리스트 헤더
     */
    private ListHeaderForTextBox listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public TextBox() {
        listHeader = new ListHeaderForTextBox();
        paragraphList = new ParagraphList();
    }

    public TextBox(StreamReader sr) throws Exception {
        this.listHeader = new ListHeaderForTextBox(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 문단 리스트 헤더 객체를 반환한다.
     *
     * @return 문단 리스트 헤더 객체
     */
    public ListHeaderForTextBox getListHeader() {
        return listHeader;
    }

    /**
     * 문단 리스트 객체를 반환한다.
     *
     * @return 문단 리스트 객체
     */
    public ParagraphList getParagraphList() {
        return paragraphList;
    }

    public void copy(TextBox from) {
        listHeader.copy(from.listHeader);
        paragraphList.copy(from.paragraphList);
    }

    @Override
    public void updateState(InstanceID iid) {
        this.listHeader.setParaCount(this.paragraphList.getParagraphCount());
        this.paragraphList.updateState(iid);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
//        listHeader(tb.getListHeader(), sw);
        if(this.listHeader!=null) {
            this.getListHeader().write(sw);
        }
        this.paragraphList.write(sw);
    }
}
