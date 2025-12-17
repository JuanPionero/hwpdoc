package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine.NumberShape;

import java.io.IOException;

/**
 * 각주(Foot Note) 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderFootnote extends CtrlHeader implements StreamWritable {
    /**
     * 각주 번호
     */
    private long number;
    /**
     * 앞 장식 문자
     */
    private HWPString beforeDecorationLetter;
    /**
     * 뒤 장식 문자
     */
    private HWPString afterDecorationLetter;
    /**
     * 번호 모양
     */
    private NumberShape numberShape;
    /**
     * 문서 내 각 개체에 대한 고유 아이디
     */
    private long instanceId;

    /**
     * 생성자
     */
    public CtrlHeaderFootnote() {
        super(ControlType.Footnote.getCtrlId());
        beforeDecorationLetter = new HWPString();
        afterDecorationLetter = new HWPString();
    }

    public CtrlHeaderFootnote(StreamReader sr) throws IOException, IllegalAccessException {
        super(ControlType.Footnote.getCtrlId());
        this.number = sr.readUInt4();

        this.beforeDecorationLetter = new HWPString(sr.readWChar());
        this.afterDecorationLetter = new HWPString(sr.readWChar());
        this.numberShape = NumberShape.valueOf((short) sr.readUInt4());
        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.instanceId = sr.readUInt4();
;    }

    /**
     * 각주 번호를 반환한다.
     *
     * @return 각주 번호
     */
    public long getNumber() {
        return number;
    }

    /**
     * 각주 번호를 설정한다.
     *
     * @param number 각주 번호
     */
    public void setNumber(long number) {
        this.number = number;
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

    /**
     * 번호 모양을 반환한다.
     *
     * @return 번호 모양
     */
    public NumberShape getNumberShape() {
        return numberShape;
    }

    /**
     * 번호 모양을 설정한다.
     *
     * @param numberShape 번호 모양
     */
    public void setNumberShape(NumberShape numberShape) {
        this.numberShape = numberShape;
    }

    /**
     * 문서 내 각 개체에 대한 고유 아이디를 반환한다.
     *
     * @return 문서 내 각 개체에 대한 고유 아이디
     */
    public long getInstanceId() {
        return instanceId;
    }

    /**
     * 문서 내 각 개체에 대한 고유 아이디를 설정한다.
     *
     * @param instanceId 문서 내 각 개체에 대한 고유 아이디
     */
    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderFootnote from2 = (CtrlHeaderFootnote) from;
        number = from2.number;
        beforeDecorationLetter.copy(from2.beforeDecorationLetter);
        afterDecorationLetter.copy(from2.afterDecorationLetter);
        numberShape = from2.numberShape;
        instanceId = from2.instanceId;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 20);
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getNumber());
        sw.writeWChar(this.getBeforeDecorationLetter().getBytes());
        sw.writeWChar(this.getAfterDecorationLetter().getBytes());
        sw.writeUInt4(this.getNumberShape().getValue());
        sw.writeUInt4(this.getInstanceId());
    }
}
