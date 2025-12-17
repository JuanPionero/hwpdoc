package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderIndexMark;

import java.io.IOException;

/**
 * 찾아보기 표식 컨트롤
 *
 * @author neolord
 */
public class ControlIndexMark extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlIndexMark() {
        super(new CtrlHeaderIndexMark());
    }

    public ControlIndexMark(StreamReader sr) throws IOException, IllegalAccessException {
        super(new CtrlHeaderIndexMark(sr));
    }

    /**
     * 찾아보기 표식용 컨트롤 헤더를 반환한다.
     *
     * @return 찾아보기 표식용 컨트롤 헤더
     */
    public CtrlHeaderIndexMark getHeader() {
        return (CtrlHeaderIndexMark) header;
    }

    @Override
    public Control clone() {
        ControlIndexMark cloned = new ControlIndexMark();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}