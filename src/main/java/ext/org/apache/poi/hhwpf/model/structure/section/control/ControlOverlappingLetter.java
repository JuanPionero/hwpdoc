package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderOverlappingLetter;

import java.io.IOException;

/**
 * 글자 겹침 컨트롤
 *
 * @author neolord
 */
public class ControlOverlappingLetter extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlOverlappingLetter() {
        super(new CtrlHeaderOverlappingLetter());
    }

    public ControlOverlappingLetter(StreamReader sr) throws IOException, IllegalAccessException {
        super(new CtrlHeaderOverlappingLetter(sr));
    }

    /**
     * 글자 겹침 용 컨트롤 헤더를 반환한다.
     *
     * @return 글자 겹침 용 컨트롤 헤더
     */
    public CtrlHeaderOverlappingLetter getHeader() {
        return (CtrlHeaderOverlappingLetter) header;
    }

    @Override
    public Control clone() {
        ControlOverlappingLetter cloned = new ControlOverlappingLetter();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
