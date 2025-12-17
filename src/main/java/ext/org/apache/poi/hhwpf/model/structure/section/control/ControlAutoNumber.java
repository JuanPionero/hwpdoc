package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderAutoNumber;

import java.io.IOException;

/**
 * 자동번호 컨트롤에 대한 객체
 *
 * @author neolord
 */
public class ControlAutoNumber extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlAutoNumber() {
        super(new CtrlHeaderAutoNumber());
    }

    public ControlAutoNumber(StreamReader sr) throws IOException {
        super(new CtrlHeaderAutoNumber(sr));
    }

    /**
     * 자동번호 컨트롤용 컨트롤 헤더를 반환한다.
     *
     * @return 자동번호 컨트롤용 컨트롤 헤더
     */
    public CtrlHeaderAutoNumber getHeader() {
        return (CtrlHeaderAutoNumber) header;
    }

    @Override
    public Control clone() {
        ControlAutoNumber cloned = new ControlAutoNumber();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
