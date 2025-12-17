package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentOLE;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_SHAPE_COMPONENT_OLE;

/**
 * OLE 개체 컨트롤
 *
 * @author neolord
 */
public class ControlOLE extends GsoControl implements StreamWritable {
    /**
     * OLE 개체 속성
     */
    private ShapeComponentOLE shapeComponentOLE;

    /**
     * 생성자
     */
    public ControlOLE() {
        this(new CtrlHeaderGso());
    }

    public ControlOLE(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.OLE.getId());
        // setGsoId(GsoControlType.OLE.getId());
        if(sr.readDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_OLE) {
            shapeComponentOLE = new ShapeComponentOLE(sr);
        }
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlOLE(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.OLE.getId());

    }

    /**
     * OLE 개체의 속성 객체을 반환한다.
     *
     * @return OLE 개체의 속성 객체
     */
    public ShapeComponentOLE getShapeComponentOLE() {
        return shapeComponentOLE;
    }

    @Override
    public Control clone() {
        ControlOLE cloned = new ControlOLE();
        cloned.copyGsoControlPart(this);
        cloned.shapeComponentOLE.copy(shapeComponentOLE);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        super.write(sw);
        // writeRest - start
        sw.upRecordLevel();

        this.getShapeComponentOLE().write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.
    }
}
