package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.columndefine;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 하나의 단에 대한 정보를 나태내는 객체
 *
 * @author neolord
 */
public class ColumnInfo implements StreamWritable {
    /**
     * 다단의 폭
     */
    private int width;
    /**
     * 다음 단과의 간격
     */
    private int gap;

    /**
     * 생성자
     */
    public ColumnInfo() {
    }
    public ColumnInfo(StreamReader sr) throws IOException {
        this.width = sr.readUInt2();
        this.gap = sr.readUInt2();
    }
    /**
     * 다단의 폭을 반환한다.
     *
     * @return 다단의 폭
     */
    public int getWidth() {
        return width;
    }

    /**
     * 다단의 폭을 설정한다.
     *
     * @param width 다단의 폭
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 다음 단과의 간격을 반환한다.
     *
     * @return 다음 단과의 간격
     */
    public int getGap() {
        return gap;
    }

    /**
     * 다음 단과의 간격을 설정한다.
     *
     * @param gap 다음 단과의 간격
     */
    public void setGap(int gap) {
        this.gap = gap;
    }

    public ColumnInfo clone() {
        ColumnInfo cloned = new ColumnInfo();
        cloned.width = width;
        cloned.gap = gap;
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.getWidth());
        sw.writeUInt2(this.getGap());
    }
}
