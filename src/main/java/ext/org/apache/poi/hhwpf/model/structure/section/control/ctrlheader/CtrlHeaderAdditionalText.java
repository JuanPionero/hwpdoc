package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.parashape.Alignment;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.additionaltext.AdditionalTextPosition;

import java.io.IOException;

/**
 * 덧말 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderAdditionalText extends CtrlHeader implements StreamWritable {
    
    /**
     * main text
     */
    private HWPString mainText;
    /**
     * sub text
     */
    private HWPString subText;
    /**
     * 덧말 위치
     */
    private AdditionalTextPosition position;
    /**
     * 폰트 크기 비율(??)
     */
    private long fsizeratio;
    /**
     * 옵션(??)
     */
    private long option;
    /**
     * Style Number
     */
    private long styleId;
    /**
     * 정렬 기준
     */
    private Alignment alignment;

    /**
     * 생성자
     */
    public CtrlHeaderAdditionalText() {
        super(ControlType.AdditionalText.getCtrlId());
        mainText = new HWPString();
        subText = new HWPString();
    }

    public CtrlHeaderAdditionalText(StreamReader sr) throws IOException {
        super(ControlType.AdditionalText.getCtrlId());
        this.mainText = new HWPString(sr.readHWPString());
        this.subText = new HWPString(sr.readHWPString());
        this.position = AdditionalTextPosition.valueOf((byte) sr.readUInt4());
        this.fsizeratio = sr.readUInt4();
        this.option = sr.readUInt4();
        this.styleId = sr.readUInt4();
        this.alignment = Alignment.valueOf((byte) sr.readUInt4());
    }

    /**
     * main text를 반환한다.
     *
     * @return main text
     */
    public HWPString getMainText() {
        return mainText;
    }

    /**
     * sub text를 반환한다.
     *
     * @return sub text
     */
    public HWPString getSubText() {
        return subText;
    }

    /**
     * 덧말 위치 을 반환한다.
     *
     * @return 덧말 위치
     */
    public AdditionalTextPosition getPosition() {
        return position;
    }

    /**
     * 덧말 위치 를 설정한다.
     *
     * @param position 덧말 위치
     */
    public void setPosition(AdditionalTextPosition position) {
        this.position = position;
    }

    /**
     * 폰트 크기 비율(??)을 반환한다.
     *
     * @return 폰트 크기 비율(??)
     */
    public long getFsizeratio() {
        return fsizeratio;
    }

    /**
     * 폰트 크기 비율(??)을 설정한다.
     *
     * @param fsizeratio 폰트 크기 비율(??)
     */
    public void setFsizeratio(long fsizeratio) {
        this.fsizeratio = fsizeratio;
    }

    /**
     * 옵션(??)을 반환한다.
     *
     * @return 옵션(? ?)
     */
    public long getOption() {
        return option;
    }

    /**
     * 옵션(??)을 설정한다.
     *
     * @param option
     */
    public void setOption(long option) {
        this.option = option;
    }

    /**
     * Style Number를 반환한다.
     *
     * @return Style Number
     */
    public long getStyleId() {
        return styleId;
    }

    /**
     * Style Number를 설정한다.
     *
     * @param styleId Style Number
     */
    public void setStyleId(long styleId) {
        this.styleId = styleId;
    }

    /**
     * 정령 기준을 반환한다.
     *
     * @return 정령 기준
     */
    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * 정령 기준을 설정한다.
     *
     * @param alignment 정령 기준
     */
    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderAdditionalText from2 = (CtrlHeaderAdditionalText) from;
        mainText.copy(from2.mainText);
        subText.copy(from2.subText);
        position = from2.position;
        fsizeratio = from2.fsizeratio;
        option = from2.option;
        styleId = from2.styleId;
        alignment = from2.alignment;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, this.getSize());
        sw.writeUInt4(this.getCtrlId());
        sw.writeHWPString(this.getMainText());
        sw.writeHWPString(this.getSubText());
        sw.writeUInt4(this.getPosition().getValue());
        sw.writeUInt4(this.getFsizeratio());
        sw.writeUInt4(this.getOption());
        sw.writeUInt4(this.getStyleId());
        sw.writeUInt4(this.getAlignment().getValue());
    }
    
    /**
     * 컨트롤 헤더 레코드의 크기를 반환한다.
     *
     * @return 컨트롤 헤더 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 4;
        size += this.getMainText().getWCharsSize();
        size += this.getSubText().getWCharsSize();
        size += 20;
        return size;
    }
}
