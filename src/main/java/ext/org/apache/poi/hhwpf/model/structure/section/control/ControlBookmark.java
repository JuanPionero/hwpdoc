package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderBookmark;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_CTRL_DATA;

/**
 * 책갈피 컨트롤에 대한 객체
 *
 * @author neolord
 */
public class ControlBookmark extends Control implements StreamWritable {
    /**
     * 생성자
     */
    public ControlBookmark() {
        super(new CtrlHeaderBookmark());
    }

    public ControlBookmark(StreamReader sr) throws IOException {
        super(new CtrlHeaderBookmark(sr));
        DataRecordHeader dataRecordHeader = sr.readDataRecordHeader();
        if (dataRecordHeader.getTagID() == HWPTAG_CTRL_DATA) {
            this.ctrlData = new CtrlData(sr);
        }
    }

    /**
     * 책갈피용 컨트롤 헤더를 반환한다.
     *
     * @return 책갈피용 컨트롤 헤더
     */
    public CtrlHeaderBookmark getHeader() {
        return (CtrlHeaderBookmark) header;
    }

    @Override
    public Control clone() {
        ControlBookmark cloned = new ControlBookmark();
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        this.ctrlData.write(sw);

        sw.downRecordLevel();
    }
}
