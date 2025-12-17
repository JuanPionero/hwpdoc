package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.autonumber.AutoNumberHeaderProperty;

import java.io.IOException;

/**
 * 자동번호 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderAutoNumber extends CtrlHeader implements StreamWritable {
    /**
     * 속성
     */
    private AutoNumberHeaderProperty property;
    /**
     * 번호
     */
    private int number;
    /**
     * 사용자 기호
     */
    private HWPString userSymbol;
    /**
     * 앞 장식 문자
     */
    private HWPString beforeDecorationLetter;
    /**
     * 뒤 장식 문자
     */
    private HWPString afterDecorationLetter;

    /**
     * 생성자
     */
    public CtrlHeaderAutoNumber() {
        super(ControlType.AutoNumber.getCtrlId());

        property = new AutoNumberHeaderProperty();
        userSymbol = new HWPString();
        beforeDecorationLetter = new HWPString();
        afterDecorationLetter = new HWPString();
    }

    public CtrlHeaderAutoNumber(StreamReader sr) throws IOException {
        super(ControlType.AutoNumber.getCtrlId());

        this.property = new AutoNumberHeaderProperty(sr.readUInt4());
        this.number = sr.readUInt2();
        this.userSymbol = new HWPString(sr.readWChar());
        this.beforeDecorationLetter = new HWPString(sr.readWChar());
        this.afterDecorationLetter = new HWPString(sr.readWChar());
    }

    /**
     * 자동번호 컨트롤의 속성 객체를 반환한다.
     *
     * @return 자동번호 컨트롤의 속성 객체
     */
    public AutoNumberHeaderProperty getProperty() {
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

    /**
     * 사용자 기호를 반환한다.
     *
     * @return 사용자 기호
     */
    public HWPString getUserSymbol() {
        return userSymbol;
    }

    /**
     * 앞 장식 문자를 반환한다.
     *
     * @return 앞 장식 문자
     */
    public HWPString getBeforeDecorationLetter() {
        return beforeDecorationLetter;
    }

    /**
     * 뒤 장식 문자를 반환한다.
     *
     * @return 뒤 장식 문자
     */
    public HWPString getAfterDecorationLetter() {
        return afterDecorationLetter;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderAutoNumber from2 = (CtrlHeaderAutoNumber) from;
        property.copy(from2.property);
        number = from2.number;
        userSymbol.copy(from2.userSymbol);
        beforeDecorationLetter.copy(from2.beforeDecorationLetter);
        afterDecorationLetter.copy(from2.afterDecorationLetter);

    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 16);
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt2(this.getNumber());
        sw.writeWChar(this.getUserSymbol().getBytes());
        sw.writeWChar(this.getBeforeDecorationLetter().getBytes());
        sw.writeWChar(this.getAfterDecorationLetter().getBytes());
    }
}
