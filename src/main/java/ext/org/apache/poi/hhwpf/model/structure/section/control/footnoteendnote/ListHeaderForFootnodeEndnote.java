package ext.org.apache.poi.hhwpf.model.structure.section.control.footnoteendnote;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.ListHeaderProperty;

import java.io.IOException;

/**
 * 미주/각주을 위한 문단 리스트 헤더 레코드
 *
 * @author neolord
 */
public class ListHeaderForFootnodeEndnote implements StreamWritable {
    /**
     * 문단 개수
     */
    private int paraCount;
    /**
     * 속성
     */
    private ListHeaderProperty property;

    /**
     * 생성자
     */
    public ListHeaderForFootnodeEndnote() {
        property = new ListHeaderProperty();
    }

    public ListHeaderForFootnodeEndnote(StreamReader sr) throws IOException {
        this.paraCount = sr.readSInt4();
        this.property = new ListHeaderProperty(sr.readUInt4());
        sr.readRestOfDataRecordData();
    }

    /**
     * 문단 개수를 반환한다.
     *
     * @return 문단 개수
     */
    public int getParaCount() {
        return paraCount;
    }

    /**
     * 문단 개수를 설정한다.
     *
     * @param paraCount 문단 개수
     */
    public void setParaCount(int paraCount) {
        this.paraCount = paraCount;
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public ListHeaderProperty getProperty() {
        return property;
    }

    public void copy(ListHeaderForFootnodeEndnote from) {
        paraCount = from.paraCount;
        property.copy(from.property);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_LIST_HEADER, 16);

        sw.writeSInt4(this.getParaCount());
        sw.writeUInt4(this.getProperty().getValue());
        sw.writeZero(8);
    }
}
