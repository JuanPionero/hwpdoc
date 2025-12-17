package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentEllipse;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.TextBox;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 타원 개체 컨트롤
 *
 * @author neolord
 */
public class ControlEllipse extends GsoControl implements StateUpdatable, StreamWritable {
    /**
     * 글상자
     */
    private TextBox textBox;
    /**
     * 타원 개체 속성
     */
    private ShapeComponentEllipse shapeComponentEllipse;

    /**
     * 생성자
     */
    public ControlEllipse() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlEllipse(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Ellipse.getId());

        textBox = null;
        shapeComponentEllipse = new ShapeComponentEllipse();
    }

    public ControlEllipse(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Ellipse.getId());
//        setGsoId(GsoControlType.Ellipse.getId());
//        DataRecordHeader rh =
        sr.readDataRecordHeader();
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.textBox = new TextBox(sr);
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_ELLIPSE) {
            this.shapeComponentEllipse = new ShapeComponentEllipse(sr);
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
     * 타원 개체의 속성 객체을 반환한다.
     *
     * @return 타원 개체의 속성 객체
     */
    public ShapeComponentEllipse getShapeComponentEllipse() {
        return shapeComponentEllipse;
    }

    @Override
    public Control clone() {
        ControlEllipse cloned = new ControlEllipse();
        cloned.copyGsoControlPart(this);

        if (textBox != null) {
            cloned.createTextBox();
            cloned.textBox.copy(textBox);
        }

        cloned.shapeComponentEllipse.copy(shapeComponentEllipse);
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
        if(this.textBox != null) {
            this.textBox.write(sw);
        }
        this.getShapeComponentEllipse().write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.

    }
}
