package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.*;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.ShapeComponentPicture;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_CTRL_DATA;
import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_SHAPE_COMPONENT_PICTURE;

/**
 * 그림 개체 컨트롤
 *
 * @author neolord
 */
public class ControlPicture extends GsoControl implements StreamWritable {
    /**
     * 그림 개체 속성
     */
    private ShapeComponentPicture shapeComponentPicture;

    /**
     * 생성자
     */
    public ControlPicture() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlPicture(CtrlHeaderGso header) {
        super(header);
        setGsoId(GsoControlType.Picture.getId());

        shapeComponentPicture = new ShapeComponentPicture();
    }

    public ControlPicture(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Picture.getId());
//        setGsoId(GsoControlType.Picture.getId());
//        DataRecordHeader rh =
        sr.readDataRecordHeader();
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_CTRL_DATA) {
            this.ctrlData = new CtrlData(sr);
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_SHAPE_COMPONENT_PICTURE) {
            this.shapeComponentPicture = new ShapeComponentPicture(sr);
        }
    }

    public ControlPicture(Initializer<ControlPicture> initializer) throws Exception {
        this();
        setGsoId(GsoControlType.Picture.getId());
        shapeComponentPicture = new ShapeComponentPicture();
        initializer.init(this);
    }

    /**
     * 그림 개체의 속성 객체를 반환한다.
     *
     * @return 그림 개체의 속성 객체
     */
    public ShapeComponentPicture getShapeComponentPicture() {
        return shapeComponentPicture;
    }

    @Override
    public Control clone() {
        ControlPicture cloned = new ControlPicture();
        cloned.copyGsoControlPart(this);
        cloned.shapeComponentPicture.copy(shapeComponentPicture);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        super.write(sw);
        // writeRest - start
        sw.upRecordLevel();
        if(this.ctrlData!=null) {
            this.ctrlData.write(sw);
        }
        this.shapeComponentPicture.write(sw);
        sw.downRecordLevel();
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl)에서 sw.upRecordLevel() 이 한번 실행되었음.

    }

    @Override
    public void updateState(InstanceID iid) {
        super.updateState(iid);
        this.shapeComponentPicture.setInstanceId(iid.get());
    }

    @Override
    public String toString() {
        return "ControlPicture{" +
                "shapeComponentPicture=" + shapeComponentPicture +
                ", shapeComponent=" + shapeComponent +
                ", header=" + header +
                ", ctrlData=" + ctrlData +
                '}';
    }
}
