package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.GsoControlType;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 컨테이너 컨트롤을 위한 객체 공통 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentContainer extends ShapeComponent implements StreamWritable {
    /**
     * 컨테이너에 포함된 컨트롤의 id 리스트
     */
    private ArrayList<Long> childControlIdList;

    /**
     * 생성자
     */
    public ShapeComponentContainer() {
        childControlIdList = new ArrayList<Long>();
    }
    public ShapeComponentContainer(long gsoId) {
        super(gsoId);
        childControlIdList = new ArrayList<Long>();
    }

    public ShapeComponentContainer(StreamReader sr, long gsoId) throws IOException, IllegalAccessException {
        super(sr, gsoId);
        childControlIdList = new ArrayList<Long>();
        int count = sr.readUInt2();
        for (int index = 0; index < count; index++) {
            childControlIdList.add(sr.readUInt4());
        }
        if(sr.isEndOfDataRecord()) {
            return;
        }
        // instid 는 ShapeComponent에 선언되어있음.
        this.setInstid(sr.readUInt4());
    }

    /**
     * 컨테이너에 포함된 컨트롤의 id를 리스트 추가한다.
     *
     * @param id 컨테이너에 포함된 컨트롤의 id
     */
    public void addChildControlId(long id) {
        childControlIdList.add(id);
    }

    /**
     * 컨테이너에 포함된 컨트롤의 id 리스트를 반환한다.
     *
     * @return 컨테이너에 포함된 컨트롤의 id 리스트
     */
    public ArrayList<Long> getChildControlIdList() {
        return childControlIdList;
    }

    @Override
    public void copy(ShapeComponent from) {
        copyShapeComponent(from);
        ShapeComponentContainer from2 = (ShapeComponentContainer) from;

        childControlIdList.clear();
        for (Long childControlId : from2.childControlIdList) {
            childControlIdList.add(childControlId);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT, getSize());
        sw.writeUInt4(this.getGsoId());
        sw.writeUInt4(this.getGsoId());
// CommonPart.write
        super.write(sw);

//        childInfo(scc, sw);
        int count = this.getChildControlIdList().size();
        sw.writeUInt2(count);

        for (long childId : this.getChildControlIdList()) {
            sw.writeUInt4(childId);
        }
        sw.writeUInt4(this.getInstid());

    }

    /**
     * 객체 공통 속성 레코드의 크기를 반환한다.
     *
     * @return객체 공통 속성 레코드의 크기
     */
    public int getSize(ShapeComponentContainer scc) {
        int size = 0;
        size += 8;
        size += super.getSize(); //CommonPart.getSize(scc);

        size += 2;
        size += 4 * this.getChildControlIdList().size();

        size += 4;
        return size;
    }

    @Override
    public void writeInContainer(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT, getSize());
        // write에서와는 달리 한번만
        sw.writeUInt4(this.getGsoId());

        // CommonPart.write
        super.write(sw);

        //        childInfo(scc, sw);
        int count = this.getChildControlIdList().size();
        sw.writeUInt2(count);

        for (long childId : this.getChildControlIdList()) {
            sw.writeUInt4(childId);
        }
        sw.writeZero(4);
    }
}
