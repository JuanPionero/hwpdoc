package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.header.HeaderFooterApplyPage;

import java.io.IOException;

/**
 * 꼬리말 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderFooter extends CtrlHeader implements StreamWritable {
    /**
     * 꼬리말이 적용될 범위(페이지 종류)
     */
    private HeaderFooterApplyPage applyPage;
    /**
     * 생성 순서 (??)
     */
    private int createIndex;

    /**
     * 생성자
     */
    public CtrlHeaderFooter() {
        super(ControlType.Footer.getCtrlId());
    }

    public CtrlHeaderFooter(StreamReader sr) throws IOException {
        super(ControlType.Footer.getCtrlId());
        this.applyPage = HeaderFooterApplyPage.valueOf((byte) sr.readUInt4());
        if(sr.isReadingDataRecordData()) {
            this.createIndex = sr.readSInt4();
        }

    }

    /**
     * "
     * 꼬리말이 적용될 범위를 반환한다.
     *
     * @return 꼬리말이 적용될 범위
     */
    public HeaderFooterApplyPage getApplyPage() {
        return applyPage;
    }

    /**
     * 꼬리말이 적용될 범위를 설정한다.
     *
     * @param applyPage 꼬리말이 적용될 범위
     */
    public void setApplyPage(HeaderFooterApplyPage applyPage) {
        this.applyPage = applyPage;
    }

    /**
     * 생성 순서를 반환한다.
     *
     * @return 생성 순서
     */
    public int getCreateIndex() {
        return createIndex;
    }

    /**
     * 생성 순서를 설정한다.
     *
     * @param createIndex 생성 순서
     */
    public void setCreateIndex(int createIndex) {
        this.createIndex = createIndex;
    }

    public void copy(CtrlHeaderFooter from) {
        applyPage = from.applyPage;
        createIndex = from.createIndex;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderFooter from2 = (CtrlHeaderFooter) from;
        applyPage = from2.applyPage;
        createIndex = from2.createIndex;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, 12);
        sw.writeUInt4(this.getCtrlId());

        sw.writeUInt4(this.getApplyPage().getValue());
        sw.writeSInt4(this.getCreateIndex());
    }
}
