package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderNewNumber;

import java.io.IOException;

/**
 * 새 번호 지정 컨트롤
 *
 * @author neolord
 */
public class ControlNewNumber extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlNewNumber() {
        super(new CtrlHeaderNewNumber());
    }

    public ControlNewNumber(StreamReader sr) throws IOException {
        super(new CtrlHeaderNewNumber(sr));
    }

    /**
     * 새 번호 지정용 컨트롤 헤더를 반환한다.
     *
     * @return 새 번호 지정용 컨트롤 헤더
     */
    public CtrlHeaderNewNumber getHeader() {
        return (CtrlHeaderNewNumber) header;
    }

    @Override
    public Control clone() {
        ControlNewNumber cloned = new ControlNewNumber();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
