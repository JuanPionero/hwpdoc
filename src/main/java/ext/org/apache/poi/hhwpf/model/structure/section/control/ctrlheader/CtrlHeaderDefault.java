package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;

import java.io.IOException;

public class CtrlHeaderDefault extends CtrlHeader implements StreamWritable {
    /**
     * 생성자
     *
     * @param ctrlId 컨트롤 아이디
     */
    public CtrlHeaderDefault(long ctrlId) {
        super(ctrlId);
    }

    @Override
    public void copy(CtrlHeader from) {
        // nothing
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 4);
        sw.writeUInt4(this.getCtrlId());
    }
}
