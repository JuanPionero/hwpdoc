package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentLineForObjectLinkLine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentTextArt;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_SHAPE_COMPONENT_LINE;
import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_SHAPE_COMPONENT_TEXTART;

/**
 * 객체 연결선 컨트롤
 *
 * @author neolord
 */
public class ControlObjectLinkLine extends GsoControl implements StreamWritable {
    /**
     * 선 개체 속성
     */
    private ShapeComponentLineForObjectLinkLine shapeComponentLine;

    /**
     * 생성자
     */
    public ControlObjectLinkLine() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlObjectLinkLine(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.ObjectLinkLine.getId());

        shapeComponentLine = new ShapeComponentLineForObjectLinkLine();
    }

    public ControlObjectLinkLine(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.ObjectLinkLine.getId());

//        setGsoId(GsoControlType.ObjectLinkLine.getId());
        if(sr.readDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_LINE) {
            this.shapeComponentLine = new ShapeComponentLineForObjectLinkLine(sr);
        }

    }

    /**
     * 선 개체의 속성 객체를 반환한다.
     *
     * @return 선 개체의 속성 객체
     */
    public ShapeComponentLineForObjectLinkLine getShapeComponentLine() {
        return shapeComponentLine;
    }

    @Override
    public Control clone() {
        ControlObjectLinkLine cloned = new ControlObjectLinkLine();
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
