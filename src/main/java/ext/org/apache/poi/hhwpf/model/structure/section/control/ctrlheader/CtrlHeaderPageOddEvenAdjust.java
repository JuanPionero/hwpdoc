package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.pageoddevenadjust.PageOddEvenAdjustHeaderProperty;

import java.io.IOException;

/**
 * 홀/짝수 조정(페이지 번호 제어) 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderPageOddEvenAdjust extends CtrlHeader implements StreamWritable {
    /**
     * 속성
     */
    private PageOddEvenAdjustHeaderProperty property;

    /**
     * 생성자
     */
    public CtrlHeaderPageOddEvenAdjust() {
        super(ControlType.PageOddEvenAdjust.getCtrlId());

        property = new PageOddEvenAdjustHeaderProperty();
    }

    public CtrlHeaderPageOddEvenAdjust(StreamReader sr) throws IOException {
        super(ControlType.PageOddEvenAdjust.getCtrlId());

        this.property = new PageOddEvenAdjustHeaderProperty(sr.readUInt4());
    }

    /**
     * 홀/짝수 조정(페이지 번호 제어) 컨트롤의 속성 객체를 반환한다.
     *
     * @return 홀/짝수 조정(페이지 번호 제어) 컨트롤의 속성 객체
     */
    public PageOddEvenAdjustHeaderProperty getProperty() {
        return property;
    }

    public void copy(CtrlHeaderPageOddEvenAdjust from) {
        property.copy(from.property);
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderPageOddEvenAdjust from2 = (CtrlHeaderPageOddEvenAdjust) from;
        property.copy(from2.property);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 8);
        sw.writeUInt4(this.getCtrlId());
        sw.writeUInt4(this.getProperty().getValue());
    }
}
