package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;


import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentRectangle;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.TextBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 사각형 개체 컨트롤
 *
 * @author neolord
 */
public class ControlRectangle extends GsoControl implements StateUpdatable, StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(ControlRectangle.class);
    /**
     * 글상자
     */
    private TextBox textBox;
    /**
     * 사각형 개체 속성
     */
    private ShapeComponentRectangle shapeComponentRectangle;

    /**
     * 생성자
     */
    public ControlRectangle() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlRectangle(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Rectangle.getId());

        textBox = null;
        shapeComponentRectangle = new ShapeComponentRectangle();
    }

    public ControlRectangle(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Rectangle.getId());
//        setGsoId(GsoControlType.Rectangle.getId());
        logger.trace("remainedDataRecordData = {}", sr.getRemainedDataRecordDataCount());
//        DataRecordHeader rh =
        sr.readDataRecordHeader();
        if (sr.getCurrentDataRecordHeader().getTagID() == TagID.HWPTAG_CTRL_DATA) {
            this.ctrlData = new CtrlData(sr);
            if (sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }

        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.textBox = new TextBox(sr);
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_RECTANGLE) {
            this.shapeComponentRectangle = new ShapeComponentRectangle(sr);
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
     * 사각형 개체의 속성 객체를 반환한다.
     *
     * @return 사각형 개체의 속성 객체
     */
    public ShapeComponentRectangle getShapeComponentRectangle() {
        return shapeComponentRectangle;
    }

    @Override
    public Control clone() {
        ControlRectangle cloned = new ControlRectangle();
        cloned.copyGsoControlPart(this);

        if (textBox != null) {
            cloned.createTextBox();
            cloned.textBox.copy(textBox);
        }

        cloned.shapeComponentRectangle.copy(shapeComponentRectangle);
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
        if(this.ctrlData!=null) {
            this.ctrlData.write(sw);
        }
//        ForTextBox.write(rect.getTextBox(), sw);
        if(this.textBox != null) {
            this.textBox.write(sw);
        }
//        shapeComponentRectangle(rect.getShapeComponentRectangle(), sw);
        this.shapeComponentRectangle.write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.

    }
}
