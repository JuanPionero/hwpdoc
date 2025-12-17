package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.curve.CurveSegmentType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.polygon.PositionXY;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 곡선 개체 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentCurve implements StreamWritable {
    /**
     * 좌표 리스트
     */
    private ArrayList<PositionXY> positionList;
    /**
     * segment type 리스트
     */
    private ArrayList<CurveSegmentType> segmentTypeList;

    /**
     * 생성자
     */
    public ShapeComponentCurve() {
        positionList = new ArrayList<PositionXY>();
        segmentTypeList = new ArrayList<CurveSegmentType>();
    }

    public ShapeComponentCurve(StreamReader sr) throws IOException {
        positionList = new ArrayList<PositionXY>();
        segmentTypeList = new ArrayList<CurveSegmentType>();
        int positionCount = sr.readSInt4();
        for (int index = 0; index < positionCount; index++) {
            this.positionList.add( new PositionXY(sr.readSInt4(), sr.readSInt4()) );
        }
        for (int index = 0; index < positionCount - 1; index++) {
            this.segmentTypeList.add( CurveSegmentType.valueOf((byte) sr.readUInt1()) );
        }
        sr.readBytes(4);
    }
    /**
     * 새로운 좌표 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 좌료 객체
     */
    public PositionXY addNewPosition() {
        PositionXY p = new PositionXY();
        positionList.add(p);
        return p;
    }

    /**
     * 좌표 리스트를 반환한다.
     *
     * @return 좌표 리스트
     */
    public ArrayList<PositionXY> getPositionList() {
        return positionList;
    }

    /**
     * segment type을 리스트에 추가한다.
     *
     * @param cst segment type
     */
    public void addCurveSegmentType(CurveSegmentType cst) {
        segmentTypeList.add(cst);
    }

    /**
     * segment type 리스트를 반환한다.
     *
     * @return segment type 리스트
     */
    public ArrayList<CurveSegmentType> getSegmentTypeList() {
        return segmentTypeList;
    }

    public void copy(ShapeComponentCurve from) {
        positionList.clear();

        for (PositionXY positionXY : from.positionList) {
            positionList.add(positionXY.clone());
        }

        segmentTypeList.clear();
        for (CurveSegmentType curveSegmentType : from.segmentTypeList) {
            segmentTypeList.add(curveSegmentType);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT_CURVE, this.getSize());
        int positionCount = this.getPositionList().size();
        sw.writeSInt4(positionCount);
        for (PositionXY p : this.getPositionList()) {
            p.write(sw);
        }
        for (int index = 0; index < positionCount - 1; index++) {
            CurveSegmentType cst = this.getSegmentTypeList().get(index);
            sw.writeUInt1(cst.getValue());
        }
        sw.writeZero(4);
    }

    /**
     * 곡선 개체 속성 레코드의 크기를 반환한다.
     *
     * @return 곡선 개체 속성 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 4;
        size += this.getPositionList().size() * 8;
        size += this.getPositionList().size() - 1;
        size += 4;
        return size;
    }
}
