package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.objectlinkline.ControlPoint;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.objectlinkline.LinkLineType;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 객체 연결선 컨트롤을 위한 선 개체 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentLineForObjectLinkLine implements StreamWritable {
    /**
     * 시작점 x 좌표
     */
    private int startX;
    /**
     * 시작점 y 좌표
     */
    private int startY;
    /**
     * 끝점 x 좌표
     */
    private int endX;
    /**
     * 끝점 y 좌표
     */
    private int endY;
    private LinkLineType type;
    private long startSubjectID;
    private long startSubjectIndex;
    private long endSubjectID;
    private long endSubjectIndex;
    private ArrayList<ControlPoint> controlPoints;

    /**
     * 생성자
     */
    public ShapeComponentLineForObjectLinkLine() {
        controlPoints = new ArrayList<ControlPoint>();
    }

    public ShapeComponentLineForObjectLinkLine(StreamReader sr) throws IOException, IllegalAccessException {
        this.startX = sr.readSInt4();
        this.startY = sr.readSInt4();
        this.endX = sr.readSInt4();
        this.endY = sr.readSInt4();

        this.type = LinkLineType.valueOf((byte) sr.readUInt4());
        this.startSubjectID = sr.readUInt4();
        this.startSubjectIndex = sr.readUInt4();
        this.endSubjectID = sr.readUInt4();
        this.endSubjectIndex = sr.readUInt4();

        this.controlPoints = new ArrayList<ControlPoint>();
        int countOfCP = (int) sr.readUInt4();
        for (int index = 0; index < countOfCP; index++) {
            controlPoints.add(new ControlPoint(sr.readUInt4(),sr.readUInt4(),sr.readUInt2()));
        }

        if(sr.isEndOfDataRecord()) {
            return;
        }

        sr.readRestOfDataRecordData();

    }

    /**
     * 시작점 x 좌표를 반환한다.
     *
     * @return 시작점 x 좌표
     */
    public int getStartX() {
        return startX;
    }

    /**
     * 시작점 x 좌표를 설정한다.
     *
     * @param startX 시작점 x 좌표
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * 시작점 y 좌표를 반환한다.
     *
     * @return 시작점 y 좌표
     */
    public int getStartY() {
        return startY;
    }

    /**
     * 시작점 y 좌표를 설정한다.
     *
     * @param startY 시작점 y 좌표
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * 끝점 x 좌표를 반환한다.
     *
     * @return 끝점 x 좌표
     */
    public int getEndX() {
        return endX;
    }

    /**
     * 끝점 x 좌표를 설정한다.
     *
     * @param endX 끝점 x 좌표
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * 끝점 y 좌표를 반환한다.
     *
     * @return 끝점 y 좌표
     */
    public int getEndY() {
        return endY;
    }

    /**
     * 끝점 y 좌표를 설정한다.
     *
     * @param endY 끝점 y 좌표
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    public LinkLineType getType() {
        return type;
    }

    public void setType(LinkLineType type) {
        this.type = type;
    }

    public long getStartSubjectID() {
        return startSubjectID;
    }

    public void setStartSubjectID(long startSubjectID) {
        this.startSubjectID = startSubjectID;
    }

    public long getStartSubjectIndex() {
        return startSubjectIndex;
    }

    public void setStartSubjectIndex(long startSubjectIndex) {
        this.startSubjectIndex = startSubjectIndex;
    }

    public long getEndSubjectID() {
        return endSubjectID;
    }

    public void setEndSubjectID(long endSubjectID) {
        this.endSubjectID = endSubjectID;
    }

    public long getEndSubjectIndex() {
        return endSubjectIndex;
    }

    public void setEndSubjectIndex(long endSubjectIndex) {
        this.endSubjectIndex = endSubjectIndex;
    }

    public ControlPoint addNewControlPoint() {
        ControlPoint newCP = new ControlPoint();
        controlPoints.add(newCP);
        return newCP;
    }

    public ArrayList<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public void copy(ShapeComponentLineForObjectLinkLine from) {
        startX = from.startX;
        startY = from.startY;
        endX = from.endX;
        endY = from.endY;
        type = from.type;
        startSubjectID = from.startSubjectID;
        startSubjectIndex = from.startSubjectIndex;
        endSubjectID = from.endSubjectID;
        endSubjectIndex = from.endSubjectIndex;

        for (ControlPoint fromCP : from.controlPoints) {
            addNewControlPoint().copy(fromCP);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT_LINE, this.getSize());
        sw.writeSInt4(this.getStartX());
        sw.writeSInt4(this.getStartY());
        sw.writeSInt4(this.getEndX());
        sw.writeSInt4(this.getEndY());
        sw.writeUInt4(this.getType().getValue());
        sw.writeUInt4(this.getStartSubjectID());
        sw.writeUInt4(this.getStartSubjectIndex());
        sw.writeUInt4(this.getEndSubjectID());
        sw.writeUInt4(this.getEndSubjectIndex());

        sw.writeUInt4(this.getControlPoints().size());
        for (ControlPoint cp : this.getControlPoints()) {
            cp.write(sw);
        }

        sw.writeZero(4);
    }
    
    /**
     * 선 개체 속성 레코드의 크기를 반환한다.
     *
     * @return 선 개체 속성 레코드의 크기
     */
    public int getSize() {
        return 40 + this.getControlPoints().size() * 10 + 4;
    }
    
    
}
