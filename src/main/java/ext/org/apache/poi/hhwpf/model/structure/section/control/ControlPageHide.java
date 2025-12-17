package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderPageHide;

import java.io.IOException;

/**
 * 감추기 컨트롤
 *
 * @author neolord
 */
public class ControlPageHide extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlPageHide() {
        super(new CtrlHeaderPageHide());
    }

    public ControlPageHide(StreamReader sr) throws IOException {
        super(new CtrlHeaderPageHide(sr));
    }

    /**
     * 감추기 컨트롤 용 컨트롤 헤더를 반환한다.
     *
     * @return 감추기 컨트롤 용 컨트롤 헤더
     */
    public CtrlHeaderPageHide getHeader() {
        return (CtrlHeaderPageHide) header;
    }

    @Override
    public Control clone() {
        ControlPageHide cloned = new ControlPageHide();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
