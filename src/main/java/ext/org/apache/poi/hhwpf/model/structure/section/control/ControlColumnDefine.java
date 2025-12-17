package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderColumnDefine;

import java.io.IOException;

/**
 * 단 정의 컨트롤에 대한 객체
 *
 * @author neolord
 */
public class ControlColumnDefine extends Control implements StateUpdatable, StreamWritable {
    /**
     * 생상자
     */
    public ControlColumnDefine() {
        super(new CtrlHeaderColumnDefine());
    }
    public ControlColumnDefine(CtrlHeaderColumnDefine header) {
        super(header);

    }
    public ControlColumnDefine(StreamReader sr) throws IOException, IllegalAccessException {
        super(new CtrlHeaderColumnDefine(sr));
    }

    /**
     * 단 정의용 컨트롤 헤더를 반환한다.
     *
     * @return 단 정의용 컨트롤 헤더
     */
    public CtrlHeaderColumnDefine getHeader() {
        return (CtrlHeaderColumnDefine) header;
    }

    @Override
    public Control clone() {
        ControlColumnDefine cloned = new ControlColumnDefine();
        cloned.copyControlPart(this);
        return cloned;
    }

    /**
     *
     * @param iid InstanceID or null
     */
    @Override
    public void updateState(InstanceID iid) {
        CtrlHeaderColumnDefine h = this.getHeader();

        if (h.getProperty().isSameWidth() == false) {
            if (h.getColumnInfoList().size() > 1) {
                h.getProperty() .setColumnCount((short) h.getColumnInfoList().size());
            } else {
                h.getProperty().setColumnCount((short) 1);
            }
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        if(this.ctrlData!=null) {
            this.ctrlData.write(sw);
        }

        sw.downRecordLevel();
    }
}
