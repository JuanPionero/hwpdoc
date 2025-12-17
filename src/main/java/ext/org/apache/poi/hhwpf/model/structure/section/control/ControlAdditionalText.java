package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderAdditionalText;

import java.io.IOException;

/**
 * 덧말 컨트롤
 *
 * @author neolord
 */
public class ControlAdditionalText extends Control implements StreamWritable {

    /**
     * 생성자
     */
    public ControlAdditionalText() {
        super(new CtrlHeaderAdditionalText());
    }

    public ControlAdditionalText(StreamReader sr) throws IOException {
        super(new CtrlHeaderAdditionalText(sr));
    }

    /**
     * 덧말 용 컨트롤 헤더를 반환한다.
     *
     * @return 덧말 용 컨트롤 헤더
     */
    public CtrlHeaderAdditionalText getHeader() {
        return (CtrlHeaderAdditionalText) header;
    }

    public Control clone() {
        ControlAdditionalText cloned = new ControlAdditionalText();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
