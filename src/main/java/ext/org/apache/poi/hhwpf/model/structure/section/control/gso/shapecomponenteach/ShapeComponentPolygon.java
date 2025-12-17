package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.polygon.PositionXY;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 다각형 개체 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentPolygon implements StreamWritable {
    /**
     * 좌표 리스트
     */
    private ArrayList<PositionXY> positionList;

    /**
     * 생성자
     */
    public ShapeComponentPolygon() {
        positionList = new ArrayList<PositionXY>();
    }

    public ShapeComponentPolygon(StreamReader sr) throws IOException, IllegalAccessException {
        this.positionList = new ArrayList<PositionXY>();
        int positionCount = sr.readSInt4();
        for (int index = 0; index < positionCount; index++) {
            this.positionList.add(new PositionXY(sr.readSInt4(), sr.readSInt4()));
        }
        if (!sr.isEndOfDataRecord()) {
            sr.readRestOfDataRecordData();
        }
    }

    /**
     * 새로운 좌표 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 좌표 객체
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

    public void copy(ShapeComponentPolygon from) {
        positionList.clear();
        for (PositionXY positionXY : from.positionList) {
            positionList.add(positionXY.clone());
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT_POLYGON, this.getSize());
        sw.writeSInt4(this.getPositionList().size());
        for (PositionXY p : this.getPositionList()) {
            p.write(sw);

        }
        sw.writeZero(4);
    }

    /**
     * 다각형 개체 속성 레코드의 크기를 반환한다.
     *
     * @return 다각형 개체 속성 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 4;
        size += 8 * this.getPositionList().size();
        size += 4;
        return size;
    }
}
