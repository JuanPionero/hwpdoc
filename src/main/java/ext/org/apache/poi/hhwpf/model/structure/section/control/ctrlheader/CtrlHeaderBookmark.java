package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_CTRL_DATA;

/**
 * 책갈피 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderBookmark extends CtrlHeader implements StreamWritable {
    /**
     * 생성자
     */
    public CtrlHeaderBookmark() {
        super(ControlType.Bookmark.getCtrlId());
    }

    public CtrlHeaderBookmark(StreamReader sr) throws IOException {
        super(ControlType.Bookmark.getCtrlId());
    }

    @Override
    public void copy(CtrlHeader from) {
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 4);
        sw.writeUInt4(this.getCtrlId());
    }
}
