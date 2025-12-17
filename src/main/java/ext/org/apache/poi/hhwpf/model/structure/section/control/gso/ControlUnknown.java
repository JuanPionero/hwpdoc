package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;

public class ControlUnknown extends GsoControl {
    /**
     * 생성자
     */
    public ControlUnknown() {
        this(new CtrlHeaderGso());
    }

    public ControlUnknown(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Unknown.getId());
    }

    public ControlUnknown(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Unknown.getId());
//        setGsoId(GsoControlType.Unknown.getId());

    }

    @Override
    public Control clone() {
        ControlUnknown cloned = new ControlUnknown();
        cloned.copyGsoControlPart(this);
        return cloned;
    }
}
