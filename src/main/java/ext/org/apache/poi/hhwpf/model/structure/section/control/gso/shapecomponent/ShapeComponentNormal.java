package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.FillInfo;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.lineinfo.LineInfo;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.shadowinfo.ShadowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 일반 컨트롤을 위한 객체 공통 속성 레코드
 *
 * @author neolord
 */
public class ShapeComponentNormal extends ShapeComponent implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(ShapeComponentNormal.class);
    /**
     * 테두리 선 정보
     */
    private LineInfo lineInfo;
    /**
     * 채움 정보
     */
    private FillInfo fillInfo;
    /**
     * 그림자 정보
     */
    private ShadowInfo shadowInfo;

    /**
     * 생성자
     */
    public ShapeComponentNormal() {
        lineInfo = null;
        fillInfo = null;
        shadowInfo = null;
    }

    public ShapeComponentNormal(StreamReader sr, long gsoId) throws IOException, IllegalAccessException {
        super(sr, gsoId);

        if(sr.isEndOfDataRecord()) {
            return;
        }
        lineInfo = new LineInfo(sr);
        logger.trace("++++++++++++ ShapeComponentNormal LineInfo has been completed ++++++++");

        if(sr.isEndOfDataRecord()) {
            return;
        }
        fillInfo = new FillInfo(sr);
        logger.trace("++++++++++++ ShapeComponentNormal FillInfo has been completed ++++++++");
        if(sr.isEndOfDataRecord()) {
            return;
        }
        shadowInfo = new ShadowInfo(sr);
        logger.trace("++++++++++++ ShapeComponentNormal shadowInfo has been completed ++++++++");

        if(sr.isEndOfDataRecord()) {
            return;
        }

        logger.trace("++++++++++++ ShapeComponentNormal instance id, skip and transparency 지정 시작 ++++++++");
        this.setInstid(sr.readUInt4());
        sr.readBytes(1);
        this.shadowInfo.setTransparent(sr.readUInt1());
        logger.trace("++++++++++++ ShapeComponentNormal instance id, skip and transparency 지정 END ++++++++");
    }

    /**
     * 테두리 선 정보 객체를 생성한다.
     */
    public void createLineInfo() {
        lineInfo = new LineInfo();
    }

    /**
     * 테두리 선 정보 객체를 삭제한다.
     */
    public void deleteLineInfo() {
        lineInfo = null;
    }

    /**
     * 테두리 선 정보 객체를 반환한다.
     *
     * @return 테두리 선 정보 객체
     */
    public LineInfo getLineInfo() {
        return lineInfo;
    }

    /**
     * 채움 정보 객체를 생성한다.
     */
    public void createFillInfo() {
        fillInfo = new FillInfo();
    }

    /**
     * 채움 정보 객체를 삭제한다.
     */
    public void deleteFillInfo() {
        fillInfo = null;
    }

    /**
     * 채움 정보 객체를 반환한다.
     *
     * @return 채움 정보 객체
     */
    public FillInfo getFillInfo() {
        return fillInfo;
    }

    /**
     * 그림자 정보 객체를 생성한다.
     */
    public void createShadowInfo() {
        shadowInfo = new ShadowInfo();
    }

    /***
     * 그림자 정보 객체를 삭제한다.
     */
    public void deleteShadowInfo() {
        shadowInfo = null;
    }

    /**
     * 그림자 정보 객체를 반환한다.
     *
     * @return 그림자 정보 객체
     */
    public ShadowInfo getShadowInfo() {
        return shadowInfo;
    }

    @Override
    public void copy(ShapeComponent from) {
        copyShapeComponent(from);
        ShapeComponentNormal from2 = (ShapeComponentNormal) from;

        if (from2.lineInfo != null) {
            createLineInfo();
            lineInfo.copy(from2.lineInfo);
        } else {
            lineInfo = null;
        }

        if (from2.fillInfo != null) {
            createFillInfo();
            fillInfo.copy(from2.fillInfo);
        } else {
            fillInfo = null;
        }

        if (from2.shadowInfo != null) {
            createShadowInfo();
            shadowInfo.copy(from2.shadowInfo);
        } else {
            shadowInfo = null;
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT, getSize());
        sw.writeUInt4(this.getGsoId());
        sw.writeUInt4(this.getGsoId());
        this.writeCommon(sw);
    }

    private void writeCommon(StreamWriter sw) throws IOException {

//        CommonPart.write ...
        super.write(sw);

//        lineInfo(scn.getLineInfo(), sw);
        if(this.lineInfo != null) {
            this.lineInfo.write(sw);
        }
//        fillInfo(scn.getFillInfo(), sw);
        if(this.fillInfo  != null) {
            this.fillInfo.write(sw);
        }
//        shadowInfo(scn.getShadowInfo(), sw);
        if(this.shadowInfo != null) {
            this.shadowInfo.write(sw);
        }
//        rest(scn, sw);
        if(this.shadowInfo != null) {
            sw.writeUInt4(this.getInstid());
            sw.writeZero(1);
            sw.writeUInt1(this.getShadowInfo().getTransparent());
        }
    }

    /**
     * 객체 공통 속성 레코드의 크기를 반환한다.
     *
     * @return 객체 공통 속성 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 8;
        size += super.getSize(); // CommonPart.getSize(scn);
        if (this.getLineInfo() != null) {
            size += 13;
        }
        if (this.getFillInfo() != null) {
            size += this.getFillInfo().getSize(); // ForFillInfo.getSize(scn.getFillInfo());
        }
        if (this.getShadowInfo() != null) {
            size += 22;
        }

        return size;
    }

    @Override
    public void writeInContainer(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_SHAPE_COMPONENT, getSize() - 4);
        sw.writeUInt4(this.getGsoId());

        this.writeCommon(sw);
    }

    @Override
    public String toString() {
        return "ShapeComponentNormal{" +
                "lineInfo=" + lineInfo +
                ", fillInfo=" + fillInfo +
                ", shadowInfo=" + shadowInfo +
                '}';
    }
}
