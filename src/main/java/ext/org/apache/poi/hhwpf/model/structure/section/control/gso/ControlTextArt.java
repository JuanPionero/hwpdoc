package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentTextArt;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_SHAPE_COMPONENT_TEXTART;

/**
 * 글맵시 컨트롤
 */
public class ControlTextArt extends GsoControl implements StreamWritable {
    /**
     * 글맵시 개체 속성
     */
    private ShapeComponentTextArt shapeComponentTextArt;

    /**
     * 생성자
     */
    public ControlTextArt() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlTextArt(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.TextArt.getId());
        shapeComponentTextArt = new ShapeComponentTextArt();
    }

    public ControlTextArt(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.TextArt.getId());
//        setGsoId(GsoControlType.TextArt.getId());
        if(sr.readDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_TEXTART) {
            this.shapeComponentTextArt = new ShapeComponentTextArt(sr);
        }
    }

    public ShapeComponentTextArt getShapeComponentTextArt() {
        return shapeComponentTextArt;
    }

    @Override
    public Control clone() {
        ControlTextArt cloned = new ControlTextArt();
        cloned.copyGsoControlPart(this);

        cloned.shapeComponentTextArt.copy(shapeComponentTextArt);

        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        super.write(sw);
        // writeRest - start
        sw.upRecordLevel();

        this.getShapeComponentTextArt().write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.
    }
}
