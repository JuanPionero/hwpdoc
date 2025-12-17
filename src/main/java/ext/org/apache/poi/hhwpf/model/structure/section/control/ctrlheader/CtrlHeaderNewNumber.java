package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.newnumber.NewNumberHeaderProperty;

import java.io.IOException;

/**
 * 새 번호 지정 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderNewNumber extends CtrlHeader implements StreamWritable {
    /**
     * 속성
     */
    private NewNumberHeaderProperty property;
    /**
     * 번호
     */
    private int number;

    /**
     * 생성자
     */
    public CtrlHeaderNewNumber() {
        super(ControlType.NewNumber.getCtrlId());

        property = new NewNumberHeaderProperty();
    }

    public CtrlHeaderNewNumber(StreamReader sr) throws IOException {
        super(ControlType.NewNumber.getCtrlId());

        this.property = new NewNumberHeaderProperty(sr.readUInt4());
        this.number = sr.readUInt2();
    }

    /**
     * 새 번호 지정 컨트롤의 속성 객체를 반환한다.
     *
     * @return 새 번호 지정 컨트롤의 속성 객체
     */
    public NewNumberHeaderProperty getProperty() {
        return property;
    }

    /**
     * 번호를 반환한다.
     *
     * @return 번호
     */
    public int getNumber() {
        return number;
    }

    /**
     * 번호를 설정한다.
     *
     * @param number 번호
     */
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderNewNumber from2 = (CtrlHeaderNewNumber) from;
        property.copy(from2.property);
        number = from2.number;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 10);
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt2(this.getNumber());
    }
}
