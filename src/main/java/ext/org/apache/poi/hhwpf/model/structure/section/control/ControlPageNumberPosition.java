package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderPageNumberPosition;

import java.io.IOException;

/**
 * 쪽 번호 위치 컨트롤
 *
 * @author neolord
 */
public class ControlPageNumberPosition extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlPageNumberPosition() {
        super(new CtrlHeaderPageNumberPosition());
    }

    public ControlPageNumberPosition(StreamReader sr) throws IOException {
        super(new CtrlHeaderPageNumberPosition(sr));
    }

    /**
     * 쪽 번호 위치 컨트롤 용 컨트롤 헤더를 반환한다.
     *
     * @return 쪽 번호 위치 컨트롤 용 컨트롤 헤더
     */
    public CtrlHeaderPageNumberPosition getHeader() {
        return (CtrlHeaderPageNumberPosition) header;
    }

    @Override
    public Control clone() {
        ControlPageNumberPosition cloned = new ControlPageNumberPosition();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
