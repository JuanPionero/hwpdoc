package ext.org.apache.poi.hhwpf.model.structure.section.control.gso;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.FactoryForControl;
import ext.org.apache.poi.hhwpf.model.structure.section.control.StateUpdatable;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.ShapeComponentContainer;

import java.io.IOException;
import java.util.ArrayList;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_SHAPE_COMPONENT;

/**
 * 묶음 개체 컨트롤
 *
 * @author neolord
 */
public class ControlContainer extends GsoControl implements StateUpdatable, StreamWritable {
    /**
     * 차일드 컨트롤 리스트
     */
    private ArrayList<GsoControl> childControlList;

    /**
     * 생성자
     */
    public ControlContainer() {
        this(new CtrlHeaderGso());
    }

    /**
     * 생성자
     *
     * @param header 그리기 개체를 위한 컨트롤 헤더
     */
    public ControlContainer(CtrlHeaderGso header) {
        super(header);
        shapeComponent = new ShapeComponentContainer();

        setGsoId(GsoControlType.Container.getId());

        childControlList = new ArrayList<GsoControl>();
    }

    public ControlContainer(StreamReader sr, CtrlHeaderGso header) throws Exception {
        super(sr, header, GsoControlType.Container.getId());
        // GsoControl 에서 생성
        // this.shapeComponent = new ShapeComponentContainer(sr);
        // GsoControl 에서 읽고 shapeComponent 생성시에 주입.
        // setGsoId(GsoControlType.Container.getId());

        childControlList = new ArrayList<GsoControl>();
        int childCount = ((ShapeComponentContainer) this.shapeComponent).getChildControlIdList().size();
        // ForControlContainer.readRest
        for (int index = 0; index < childCount; index++) {//
             // ForGsoControl.readInControl
            sr.readDataRecordHeader();
            if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_SHAPE_COMPONENT) {
                throw new UnexpectedFileFormatException("Unexpected ControlContainer Child");
            }
            long gsoIdOfChild = sr.readUInt4();
            GsoControl gc = FactoryForControl._createGso(sr, null, gsoIdOfChild);
        }
    }

    public GsoControl addNewChildControl(GsoControlType gsoType) {
        GsoControl gc = FactoryForControl.createGso(gsoType.getId(), null);
        childControlList.add(gc);
        return gc;
    }

    /**
     * 차일드 컨트롤을 리스트에 추가한다.
     *
     * @param childControl 차일드 컨트롤
     */
    public void addChildControl(GsoControl childControl) {
        childControlList.add(childControl);
    }

    /**
     * 차일드 컨트롤의 리스트를 반환한다.
     *
     * @return 차일드 컨트롤의 리스트
     */
    public ArrayList<GsoControl> getChildControlList() {
        return childControlList;
    }

    @Override
    public Control clone() {
        ControlContainer cloned = new ControlContainer();
        cloned.copyGsoControlPart(this);

        for (GsoControl childControl : childControlList) {
            cloned.childControlList.add((GsoControl) (childControl.clone()));
        }

        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        super.updateState(iid);
        ShapeComponentContainer scc = (ShapeComponentContainer) this.getShapeComponent();
        scc.getChildControlIdList().clear();
        for(GsoControl child : this.getChildControlList()) {
            scc.addChildControlId(child.getGsoId());
        }

        for (GsoControl child : this.getChildControlList()) {
            child.updateState(iid);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        // sw.upRecordLevel, sw.downRecorLevel 이 짝을 이루어 실행됨.
        // 다른 control 에서와 유사하게 진행되도록 sw.upRecordLevel 을 super에서 진행함.
        super.writeInContainer(sw);
        // writeRest - start
        for(GsoControl child : this.getChildControlList()) {
            child.writeInContainer(sw);
        }
        // writeRest - end
        sw.downRecordLevel(); // super(=GsoControl.writeInContainer)에서 sw.upRecordLevel() 이 한번 실행되었음.
    }
}
