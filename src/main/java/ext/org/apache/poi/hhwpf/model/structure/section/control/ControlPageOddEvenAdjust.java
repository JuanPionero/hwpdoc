package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderPageOddEvenAdjust;

import java.io.IOException;

/**
 *
 */
public class ControlPageOddEvenAdjust extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlPageOddEvenAdjust() {
        super(new CtrlHeaderPageOddEvenAdjust());
    }

    public ControlPageOddEvenAdjust(StreamReader sr) throws IOException {
        super(new CtrlHeaderPageOddEvenAdjust(sr));
    }

    /**
     * 홀/짝수 조정(페이지 번호 제어) 컨트롤 용 컨트롤 헤더를 반환한다.
     *
     * @return 홀/짝수 조정(페이지 번호 제어) 컨트롤 헤더
     */
    public CtrlHeaderPageOddEvenAdjust getHeader() {
        return (CtrlHeaderPageOddEvenAdjust) header;
    }

    @Override
    public Control clone() {
        ControlPageOddEvenAdjust cloned = new ControlPageOddEvenAdjust();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
    }
}
