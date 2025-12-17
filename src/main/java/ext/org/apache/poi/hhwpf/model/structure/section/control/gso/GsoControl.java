package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.caption.Caption;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.ShapeComponent;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.ShapeComponentContainer;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.ShapeComponentNormal;

import java.io.IOException;

/**
 * 그리기 개체 컨트롤
 *
 * @author neolord
 */
public abstract class GsoControl extends Control implements StateUpdatable, StreamWritable {
    /**
     * 캡션 정보
     */
    private Caption caption;
    /**
     * 그리기 개체의 공통 요소
     */
    protected ShapeComponent shapeComponent;

    /**
     * 생성자
     */
    public GsoControl() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public GsoControl(CtrlHeaderGso header) {
        super(header);
        shapeComponent = new ShapeComponentNormal();
        caption = null;
    }

    public GsoControl(StreamReader sr, CtrlHeaderGso header, long objectId) throws Exception {
        super(header);
        // ControlContainer 의 child 일 경우는 header = null
//        if(header != null) {
//            while(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_SHAPE_COMPONENT) {
//                if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_LIST_HEADER &&
//                        sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_CTRL_DATA ) {
//                    // 규칙, 규격에 맞지 않는 것임. 예외상황으로 봐야 함.
//                    throw new UnexpectedFileFormatException("Unexpected Gso Record Structure");
//                }
//                if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
//                    this.caption = new Caption(sr);
//                } else {
//                    this.ctrlData = new CtrlData(sr);
//                }
//                sr.readRestOfDataRecordData();
//            }
//        }
//
//        if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_SHAPE_COMPONENT  ) {
//            // 규칙, 규격에 맞지 않는 것임. 예외상황으로 봐야 함.
//            throw new UnexpectedFileFormatException("Unexpected Gso (SHAPE_COMPONENT) Record Structure");
//        }
//
//        // GSO ID == object control id 객체 컨트롤 id
//        long objectId = sr.readUInt4();
//        sr.readBytes(4);

        GsoControlType gsoControlType = GsoControlType.idOf(objectId);

        if(gsoControlType == GsoControlType.Container) {
            shapeComponent = new ShapeComponentContainer(sr, objectId);
        } else {
            shapeComponent = new ShapeComponentNormal(sr, objectId);
        }


    }

    /**
     * 그리기 개체를 위한 컨트롤 헤더 객체를 반환한다.
     *
     * @return 그리기 개체를 위한 컨트롤 헤더 객체
     */
    public CtrlHeaderGso getHeader() {
        return (CtrlHeaderGso) header;
    }

    /**
     * 그리기 개체 아이디를 반환환다.
     *
     * @return 그리기 개체 아이디
     */
    public long getGsoId() {
        return shapeComponent.getGsoId();
    }

    /**
     * 그리기 개체 아이디를 설정한다.
     *
     * @param gsoId 그리기 개체 아이디
     */
    protected void setGsoId(long gsoId) {
        shapeComponent.setGsoId(gsoId);
    }

    /**
     * 그리기 개체 타입을 반환한다.
     *
     * @return 그리기 개체 타입
     */
    public GsoControlType getGsoType() {
        return GsoControlType.idOf(getGsoId());
    }

    /**
     * 캡션 객체를 생성한다.
     */
    public void createCaption() {
        caption = new Caption();
    }

    /**
     * 캡션 객체를 삭제한다.
     */
    public void deleteCaption() {
        caption = null;
    }

    /**
     * 캡션 정보 객체를 반환한다.
     *
     * @return 캡션 정보 객체
     */
    public Caption getCaption() {
        return caption;
    }

    /**
     * 캡션 정보 객체을 설정한다.
     *
     * @param caption 캡션 정보 객체
     */
    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    /**
     * 그리기 개체의 공통 요소을 나태내는 객체를 반환한다.
     *
     * @return 그리기 개체의 공통 요소을 나태내는 객체
     */
    public ShapeComponent getShapeComponent() {
        return shapeComponent;
    }

    public void copyGsoControlPart(GsoControl from) {
        copyControlPart(from);

        if (from.caption != null) {
            createCaption();
            caption.copy(from.caption);
        }

        shapeComponent.copy(from.shapeComponent);
    }

    public void updateState(InstanceID iid) {
        CtrlHeaderGso h = this.getHeader();
        if(h==null) {
            return;
        }
        h.setInstanceId(iid.get());
        h.getProperty().setHasCaption( this.caption != null );
        if(this.caption !=null) {
            this.caption.updateState(iid);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
        sw.upRecordLevel(); // sw.down은 세부 Control (예: ControlLine에서 실행 될 것임.)
        if(this.caption != null) {
            this.caption.write(sw);
        }
        this.shapeComponent.write(sw);
    }

    @Override
    public void writeInContainer(StreamWriter sw) throws IOException {
        sw.upRecordLevel(); // sw.down은 세부 Control (예: ControlLine에서 실행 될 것임.)
        this.shapeComponent.writeInContainer(sw);
    }
}
