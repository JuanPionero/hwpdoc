package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentArc;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentCurve;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.TextBox;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 호 개체 컨트롤
 *
 * @author neolord
 */
public class ControlArc extends GsoControl implements StateUpdatable, StreamWritable {
    /**
     * 글상자
     */
    private TextBox textBox;
    /**
     * 호 개체 속성
     */
    private ShapeComponentArc shapeComponentArc;

    /**
     * 생성자
     */
    public ControlArc() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlArc(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Arc.getId());

        textBox = null;
        shapeComponentArc = new ShapeComponentArc();
    }

    public ControlArc(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Arc.getId());
        // setGsoId(GsoControlType.Arc.getId());
//        DataRecordHeader rh =
        sr.readDataRecordHeader();
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.textBox = new TextBox(sr);
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_ARC) {
            this.shapeComponentArc = new ShapeComponentArc(sr);
        }
    }

    /**
     * 글상자 객체를 생성한다.
     */
    public void createTextBox() {
        textBox = new TextBox();
    }

    /**
     * 글상자 객체를 삭제한다.
     */
    public void deleteTextBox() {
        textBox = null;
    }

    /**
     * 글상자 객체를 반환한다.
     *
     * @return 글상자 객체
     */
    public TextBox getTextBox() {
        return textBox;
    }

    /**
     * 호 개체의 속성 객체를 반환한다.
     *
     * @return 호 개체의 속성 객체
     */
    public ShapeComponentArc getShapeComponentArc() {
        return shapeComponentArc;
    }

    @Override
    public Control clone() {
        ControlArc cloned = new ControlArc();
        cloned.copyGsoControlPart(this);

        if (textBox != null) {
            cloned.createTextBox();
            cloned.textBox.copy(textBox);
        }

        cloned.shapeComponentArc.copy(shapeComponentArc);
        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        super.updateState(iid);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        super.write(sw);
        // writeRest - start
        sw.upRecordLevel();
        this.textBox.write(sw);
        this.getShapeComponentArc().write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.
    }
}
