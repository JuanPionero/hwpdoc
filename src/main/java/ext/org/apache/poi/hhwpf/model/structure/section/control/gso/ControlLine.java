package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentLine;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_LIST_HEADER;

/**
 * 선 개체 컨트롤
 *
 * @author neolord
 */
public class ControlLine extends GsoControl implements StreamWritable {
    /**
     * 선 개체 속성
     */
    private ShapeComponentLine shapeComponentLine;

    /**
     * 생성자
     */
    public ControlLine() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlLine(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Line.getId());

        shapeComponentLine = new ShapeComponentLine();
    }

    public ControlLine(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Line.getId());
//        setGsoId(GsoControlType.Line.getId());
//        DataRecordHeader rh =
        sr.readDataRecordHeader();
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.shapeComponentLine = new ShapeComponentLine(sr);
        }
    }
    /**
     * 선 개체의 속성 객체를 반환한다.
     *
     * @return 선 개체의 속성 객체
     */
    public ShapeComponentLine getShapeComponentLine() {
        return shapeComponentLine;
    }

    @Override
    public Control clone() {
        ControlLine cloned = new ControlLine();
        cloned.copyGsoControlPart(this);
        cloned.shapeComponentLine.copy(shapeComponentLine);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        super.write(sw);
        // writeRest - start
        sw.upRecordLevel();

        this.getShapeComponentLine().write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.
    }
}
