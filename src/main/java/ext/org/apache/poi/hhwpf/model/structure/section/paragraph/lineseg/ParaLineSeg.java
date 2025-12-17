package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.lineseg;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.header.ParaHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 문단의 레이아웃 레코드
 *
 * @author neolord
 */
public class ParaLineSeg {
    private static final Logger logger = LoggerFactory.getLogger(ParaLineSeg.class);
    /**
     * 각 줄의 align 정보의 리스트
     */
    private ArrayList<LineSegItem> lineSegItemList;

    /**
     * 생성자
     */
    public ParaLineSeg() {
        lineSegItemList = new ArrayList<LineSegItem>();
    }

    public ParaLineSeg(LineSegItem lineSegItem) {
        this();
        this.lineSegItemList.add(lineSegItem);
    }

    public ParaLineSeg(StreamReader sr, ParaHeader ph) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        lineSegItemList = new ArrayList<LineSegItem>();
        int lineAlignCount = ph.getLineAlignCount();
        for(int index =0;index < lineAlignCount; index++) {
            lineSegItemList.add( new LineSegItem( sr ) );
        }
    }

    /**
     * 각 줄의 align 정보에 대한 객체를 새로 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 각 줄의 align 정보에 대한 객체
     */
//    public LineSegItem addNewLineSegItem() {
//        LineSegItem plsi = new LineSegItem();
//        lineSegItemList.add(plsi);
//        return plsi;
//    }

    /**
     * 각 줄의 align 정보의 리스트를 반환한다.
     *
     * @return 각 줄의 align 정보의 리스트
     */
    public ArrayList<LineSegItem> getLineSegItemList() {
        return lineSegItemList;
    }

    public void addLineSegItem(LineSegItem item) {
        this.lineSegItemList.add( item );
    }

    public ParaLineSeg clone() {
        ParaLineSeg cloned = new ParaLineSeg();

        for (LineSegItem lineSegItem : lineSegItemList) {
            cloned.lineSegItemList.add(lineSegItem.clone());
        }

        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PARA_LINE_SEG, this.getSize());
        for (LineSegItem lsi : this.getLineSegItemList()) {
            sw.writeUInt4(lsi.getTextStartPosition());
            sw.writeSInt4(lsi.getLineVerticalPosition());
            sw.writeSInt4(lsi.getLineHeight());
            sw.writeSInt4(lsi.getTextPartHeight());
            sw.writeSInt4(lsi.getDistanceBaseLineToLineVerticalPosition());
            sw.writeSInt4(lsi.getLineSpace());
            sw.writeSInt4(lsi.getStartPositionFromColumn());
            sw.writeSInt4(lsi.getSegmentWidth());
            sw.writeUInt4(lsi.getTag().getValue());
        }
    }

    /**
     * 문단의 레이아웃 레코드의 크기를 반환한다.
     *
     * @return 문단의 레이아웃 레코드의 크기
     */
    public int getSize() {
        return this.getLineSegItemList().size() * 36;
    }
}
