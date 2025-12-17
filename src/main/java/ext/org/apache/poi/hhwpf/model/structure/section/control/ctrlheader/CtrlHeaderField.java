package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.field.FieldHeaderProperty;

import java.io.IOException;

/**
 * 필드 컨트롤를 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderField extends CtrlHeader {
    /**
     * 속성
     */
    private FieldHeaderProperty property;
    /**
     * 기타 속성
     */
    private short etcProperty;
    /**
     * command
     */
    private HWPString command;
    /**
     * id(문서 내 고유 아이디)
     */
    private long instanceId;
    /**
     * 메모 인덱스
     */
    private int memoIndex;

    /**
     * 생성자
     */
    public CtrlHeaderField() {
        this(ControlType.FIELD_UNKNOWN.getCtrlId());
    }

    /**
     * 생성자
     *
     * @param ctrlId 컨트롤 아이디(필드 아이디)
     */
    public CtrlHeaderField(long ctrlId) {
        super(ctrlId);

        property = new FieldHeaderProperty();
        command = new HWPString();
    }

    public CtrlHeaderField(long ctrlId, StreamReader sr) throws IOException, IllegalAccessException {
        super(ctrlId);

        this.property = new FieldHeaderProperty(sr.readUInt4());
        this.etcProperty = sr.readUInt1();

        this.command = new HWPString(sr.readHWPString());
        this.instanceId = sr.readUInt4();

        if(sr.isEndOfDataRecord()) {
            return;
        }
        if(ctrlId == ControlType.FIELD_UNKNOWN.getCtrlId()) {
            this.memoIndex = sr.readSInt4();
        }
        if(sr.isEndOfDataRecord()) {
            return;
        }
        sr.readRestOfDataRecordData();
    }

    /**
     * 컨트롤 아이디(필드 아이디)를 설정한다.
     *
     * @param ctrlId 컨트롤 아이디
     */
    public void setCtrlId(long ctrlId) {
        this.ctrlId = ctrlId;
    }

    /**
     * 필드 컨트롤의 속성 객체를 반환한다.
     *
     * @return 필드 컨트롤의 속성 객체
     */
    public FieldHeaderProperty getProperty() {
        return property;
    }

    /**
     * 기타 속성을 반환한다.
     *
     * @return 기타 속성
     */
    public short getEtcProperty() {
        return etcProperty;
    }

    /**
     * 기타 속성을 설정한다.
     *
     * @param etcProperty 기타 속성
     */
    public void setEtcProperty(short etcProperty) {
        this.etcProperty = etcProperty;
    }

    /**
     * 필드 command를 반환한다.
     *
     * @return 필드 command
     */
    public HWPString getCommand() {
        return command;
    }

    /**
     * 문서 내 고유 아이디를 반환한다.
     *
     * @return 문서 내 고유 아이디
     */
    public long getInstanceId() {
        return instanceId;
    }

    /**
     * 문서 내 고유 아이디를 설정한다.
     *
     * @param instanceId 문서 내 고유 아이디
     */
    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * 메모 인덱스를 반환한다.
     *
     * @return 메모 인덱스
     */
    public int getMemoIndex() {
        return memoIndex;
    }

    /**
     * 메모 인덱스를 설정한다.
     *
     * @param memoIndex 메모 인덱스
     */
    public void setMemoIndex(int memoIndex) {
        this.memoIndex = memoIndex;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderField from2 = (CtrlHeaderField) from;
        property.copy(from2.property);
        etcProperty = from2.etcProperty;
        command.copy(from2.command);
        instanceId = from2.instanceId;
        memoIndex = from2.memoIndex;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, this.getSize());
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getProperty().getValue());
        sw.writeUInt1(this.getEtcProperty());
        sw.writeHWPString(this.getCommand());
        sw.writeUInt4(this.getInstanceId());

        if (this.getCtrlId() == ControlType.FIELD_UNKNOWN.getCtrlId()) {
            sw.writeSInt4(this.getMemoIndex());
        } else {
            sw.writeZero(4);
        }
    }

    /**
     * 컨트롤 헤더 레코드의 크기를 반환한다.
     *
     * @return 컨트롤 헤더 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 9;
        size += this.getCommand().getWCharsSize();
        size += 8;
        return size;
    }
}
