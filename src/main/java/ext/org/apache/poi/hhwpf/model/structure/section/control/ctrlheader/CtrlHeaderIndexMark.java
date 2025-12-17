package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;

import java.io.IOException;

/**
 * 찾아보기 표식 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderIndexMark extends CtrlHeader implements StreamWritable {
    /**
     * 키워드 1
     */
    private HWPString keyword1;
    /**
     * 키워드 2
     */
    private HWPString keyword2;

    /**
     * 생성자
     */
    public CtrlHeaderIndexMark() {
        super(ControlType.IndexMark.getCtrlId());
        keyword1 = new HWPString();
        keyword2 = new HWPString();
    }

    public CtrlHeaderIndexMark(StreamReader sr) throws IllegalAccessException, IOException {
        super(ControlType.IndexMark.getCtrlId());
        this.keyword1 = new HWPString(sr.readHWPString());
        this.keyword2 = new HWPString(sr.readHWPString());
        if(sr.isEndOfDataRecord()) {
            return;
        }
        sr.readRestOfDataRecordData();
    }

    /**
     * 키워드 1을 반환한다.
     *
     * @return 키워드 1
     */
    public HWPString getKeyword1() {
        return keyword1;
    }

    /**
     * 키워드 2을 반환한다.
     *
     * @return 키워드 2
     */
    public HWPString getKeyword2() {
        return keyword2;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderIndexMark from2 = (CtrlHeaderIndexMark) from;
        keyword1.copy(from2.keyword1);
        keyword2.copy(from2.keyword2);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, this.getSize());
        sw.writeUInt4(this.getCtrlId());

        sw.writeHWPString(this.getKeyword1());
        sw.writeHWPString(this.getKeyword2());
    }
    
    /**
     * 컨트롤 헤더 레코드의 크기를 반환한다.
     *
     * @return 컨트롤 헤더 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 4;
        size += this.getKeyword1().getWCharsSize();
        size += this.getKeyword2().getWCharsSize();
        return size;
    }
    
    
}
