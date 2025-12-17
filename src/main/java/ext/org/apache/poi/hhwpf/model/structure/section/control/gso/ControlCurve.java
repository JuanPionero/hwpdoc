package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentCurve;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.TextBox;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 곡선 개체 컨트롤
 *
 * @author neolord
 */
public class ControlCurve extends GsoControl implements StateUpdatable, StreamWritable {
    /**
     * 글상자
     */
    private TextBox textBox;
    /**
     * 곡선 개체 속성
     */
    private ShapeComponentCurve shapeComponentCurve;

    /**
     * 생성자
     */
    public ControlCurve() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlCurve(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Curve.getId());

        textBox = null;
        shapeComponentCurve = new ShapeComponentCurve();
    }

    public ControlCurve(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Curve.getId());
        // setGsoId(GsoControlType.Curve.getId());
//        DataRecordHeader rh =
        sr.readDataRecordHeader();
        if (sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.textBox = new TextBox(sr);
            if (sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }
        if (sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_CURVE) {
            this.shapeComponentCurve = new ShapeComponentCurve(sr);
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
     * 곡선 개체의 속성 객체를 반환한다.
     *
     * @return 곡선 개체의 속성 객체
     */
    public ShapeComponentCurve getShapeComponentCurve() {
        return shapeComponentCurve;
    }

    @Override
    public Control clone() {
        ControlCurve cloned = new ControlCurve();
        cloned.copyGsoControlPart(this);

        if (textBox != null) {
            cloned.createTextBox();
            cloned.textBox.copy(textBox);
        }

        cloned.shapeComponentCurve.copy(shapeComponentCurve);
        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        super.updateState(iid);
        if (this.textBox == null) {
            return;
        }
        this.textBox.updateState(iid);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        super.write(sw);
        // writeRest - start
        sw.upRecordLevel();
        if(this.textBox != null) {
            this.textBox.write(sw);
        }
        this.shapeComponentCurve.write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.

    }

}
