package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.pagehide.PageHideHeaderProperty;

import java.io.IOException;

/**
 * 감추기 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderPageHide extends CtrlHeader implements StreamWritable {
    /**
     * 속성
     */
    private PageHideHeaderProperty property;

    /**
     * 생성자
     */
    public CtrlHeaderPageHide() {
        super(ControlType.PageHide.getCtrlId());

        property = new PageHideHeaderProperty();
    }
    public CtrlHeaderPageHide(StreamReader sr) throws IOException {
        super(ControlType.PageHide.getCtrlId());

        this.property = new PageHideHeaderProperty(sr.readUInt4());
    }


    /**
     * 감추기 컨트롤의 속성 객체를 반환한다.
     *
     * @return 감추기 컨트롤의 속성 객체
     */
    public PageHideHeaderProperty getProperty() {
        return property;
    }

    public void copy(CtrlHeaderPageHide from) {
        property.copy(from.property);
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderPageHide from2 = (CtrlHeaderPageHide) from;
        property.copy(from2.property);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 8);
        sw.writeUInt4(this.getCtrlId());
        sw.writeUInt4(this.getProperty().getValue());
    }
}
