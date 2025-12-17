package ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;

import java.io.IOException;

/**
 * 임의 데이터 레코드
 *
 * @author neolord
 */
public class CtrlData implements StreamWritable {
    /**
     * 파라미터 셋
     */
    private ParameterSet parameterSet;

    /**
     * 생성자
     */
    public CtrlData() {
        this.parameterSet = new ParameterSet();
    }
    public CtrlData(StreamReader sr) throws IOException {
        this.parameterSet = new ParameterSet(sr);
    }

    /**
     * 파라미터 셋을 반환한다.
     *
     * @return 파라미터 셋
     */
    public ParameterSet getParameterSet() {
        return parameterSet;
    }

    public void copy(CtrlData from) {
        parameterSet.copy(from.parameterSet);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_DATA, this.getSize());
        this.parameterSet.write(sw);
    }

    /**
     * 임의 데이터 레코드의 크기를 반환한다.
     *
     * @return 임의 데이터 레코드의 크기
     */
    public int getSize() {
        return this.parameterSet.getSize();
    }
}
