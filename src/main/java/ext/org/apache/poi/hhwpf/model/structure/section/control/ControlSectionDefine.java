package ext.org.apache.poi.hhwpf.model.structure.section.control;


import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderThickness;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderType;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.EachBorder;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderSectionDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine.BatangPageInfo;
import ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine.FootEndNoteShape;
import ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine.PageBorderFill;
import ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine.PageDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 구역 정의 컨트롤
 *
 * @author neolord
 */
public class ControlSectionDefine extends Control implements StateUpdatable, StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(ControlSectionDefine.class);
    /**
     * 용지설정 정보
     */
    private PageDef pageDef;
    /**
     * 각주 모양 정보
     */
    private FootEndNoteShape footNoteShape;
    /**
     * 미주 모양 정보
     */
    private FootEndNoteShape endNoteShape;
    /**
     * 쪽 테두리/배경 정보 - 양 쪽
     */
    private PageBorderFill bothPageBorderFill;
    /**
     * 쪽 테두리/배경 정보 - 짝수 쪽
     */
    private PageBorderFill evenPageBorderFill;
    /**
     * 쪽 테두리/배경 정보 - 홀수 쪽
     */
    private PageBorderFill oddPageBorderFill;
    /**
     * 바탕쪽 정보(양 쪽, 짝수 쪽, 홀수 쪽) 리스트
     */
    private ArrayList<BatangPageInfo> batangPageInfoList;

    /**
     * 생성자
     */
    public ControlSectionDefine() {
        super(new CtrlHeaderSectionDefine());

        pageDef = new PageDef();
        footNoteShape = new FootEndNoteShape();
        endNoteShape = new FootEndNoteShape();
        bothPageBorderFill = new PageBorderFill();
        evenPageBorderFill = new PageBorderFill();
        oddPageBorderFill = new PageBorderFill();
        batangPageInfoList = new ArrayList<BatangPageInfo>();
    }

    // SectionDefineAdder.add 대체
    public ControlSectionDefine(CtrlHeaderSectionDefine header) {
        super(header);

        pageDef = new PageDef(59528, 84188, 8504, 8504, 5668, 4252, 4252, 4252, 0, 0);
        footNoteShape = new FootEndNoteShape(0,"\u0000","\u0000",")",1,-1,850,567,283,new EachBorder(BorderType.Solid, BorderThickness.MM0_12, new Color4Byte(0L)), 0);
        endNoteShape = new FootEndNoteShape(0,"\u0000","\u0000",")",1,14692344,850,567,0,new EachBorder(BorderType.Solid, BorderThickness.MM0_12, new Color4Byte(0L)), 0);

        bothPageBorderFill = new PageBorderFill(1,1417,1417,1417,1417,1);
        evenPageBorderFill = new PageBorderFill(1,1417,1417,1417,1417,1);
        oddPageBorderFill = new PageBorderFill(1,1417,1417,1417,1417,1);
        batangPageInfoList = new ArrayList<BatangPageInfo>();
    }

    public ControlSectionDefine(StreamReader sr) throws Exception {
        super(new CtrlHeaderSectionDefine(sr));
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.batangPageInfoList = new ArrayList<BatangPageInfo>();
        int ctrlHeaderLevel = sr.getCurrentDataRecordHeader().getLevel();
        int endFootnoteShapeIndex = 0;
        int pageBorderFillIndex = 0;

        while(sr.isAvailable()) {
            // 내부적으로 dataRecordHeader 를 읽은 다음에 level 차이로 인해 내부 loop를 벗어나는 경우가 있다.
            // 이를 대비해서
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
            if (ctrlHeaderLevel >= sr.getCurrentDataRecordHeader().getLevel()) {
                break;
            }
            switch (sr.getCurrentDataRecordHeader().getTagID()) {
                case TagID.HWPTAG_PAGE_DEF:
//                    pageDef();
                    this.pageDef = new PageDef(sr);
                    break;
                case TagID.HWPTAG_FOOTNOTE_SHAPE:
//                    endFootnoteShapes();
                    if(endFootnoteShapeIndex==0) {
                        this.footNoteShape = new FootEndNoteShape(sr);
                    } else if(endFootnoteShapeIndex==1) {
                        this.endNoteShape = new FootEndNoteShape(sr);
                    }
                    endFootnoteShapeIndex++;
                    break;
                case TagID.HWPTAG_PAGE_BORDER_FILL:
//                    pageBorderFills();
                    if (pageBorderFillIndex == 0) {
                        this.bothPageBorderFill = new PageBorderFill(sr);
//                        bothPageBorderFill();
                    } else if (pageBorderFillIndex == 1) {
                        this.evenPageBorderFill = new PageBorderFill(sr);
//                        evenPageBorderFill();
                    } else if (pageBorderFillIndex == 2) {
                        this.oddPageBorderFill = new PageBorderFill(sr);
//                        oddPageBorderFill();
                    }
                    pageBorderFillIndex++;
                    break;
                case TagID.HWPTAG_LIST_HEADER:
//                    batangPageInfo();
                    this.batangPageInfoList.add( new BatangPageInfo(sr) );
                    break;
                case TagID.HWPTAG_CTRL_DATA:
//                    ctrlData();
                    this.ctrlData = new CtrlData(sr);
                    break;
            }
        }
        logger.trace("{} has been read", this.getClass().getSimpleName());

//        pageDef = new PageDef();
//        footNoteShape = new FootEndNoteShape();
//        endNoteShape = new FootEndNoteShape();
//        bothPageBorderFill = new PageBorderFill();
//        evenPageBorderFill = new PageBorderFill();
//        oddPageBorderFill = new PageBorderFill();
//        batangPageInfoList = new ArrayList<BatangPageInfo>();
    }

    /**
     * 구역 정의 컨트롤 용 컨트롤 헤더를 반환한다.
     *
     * @return 구역 정의 컨트롤 용 컨트롤 헤더
     */
    public CtrlHeaderSectionDefine getHeader() {
        return (CtrlHeaderSectionDefine) header;
    }

    /**
     * 용지설정 정보를 반환한다.
     *
     * @return 용지설정 정보
     */
    public PageDef getPageDef() {
        return pageDef;
    }

    /**
     * 각주 모양 정보를 반환한다.
     *
     * @return 각주 모양 정보
     */
    public FootEndNoteShape getFootNoteShape() {
        return footNoteShape;
    }

    /**
     * 미주 모양 정보를 반환한다.
     *
     * @return 미주 모양 정보
     */
    public FootEndNoteShape getEndNoteShape() {
        return endNoteShape;
    }

    /**
     * 쪽 테두리/배경 정보(양 쪽)를 반환한다.
     *
     * @return 쪽 테두리/배경 정보(양 쪽)
     */
    public PageBorderFill getBothPageBorderFill() {
        return bothPageBorderFill;
    }

    /**
     * 쪽 테두리/배경 정보(짝수 쪽)를 반환한다.
     *
     * @return 쪽 테두리/배경 정보(짝수 쪽)
     */
    public PageBorderFill getEvenPageBorderFill() {
        return evenPageBorderFill;
    }

    /**
     * 쪽 테두리/배경 정보(홀수 쪽)를 반환한다.
     *
     * @return 쪽 테두리/배경 정보(홀수 쪽)
     */
    public PageBorderFill getOddPageBorderFill() {
        return oddPageBorderFill;
    }

    /**
     * 새로운 바탕 쪽 정보 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 바탕 쪽 정보 객체
     */
    public BatangPageInfo addNewBatangPageInfo() {
        BatangPageInfo bpi = new BatangPageInfo();
        batangPageInfoList.add(bpi);
        return bpi;
    }

    /**
     * 바탕쪽 정보 리스트를 반환한다.
     *
     * @return 바탕쪽 정보 리스트
     */
    public ArrayList<BatangPageInfo> getBatangPageInfoList() {
        return batangPageInfoList;
    }

    @Override
    public Control clone() {
        ControlSectionDefine cloned = new ControlSectionDefine();
        cloned.copyControlPart(this);
        cloned.pageDef.copy(pageDef);
        cloned.footNoteShape.copy(footNoteShape);
        cloned.endNoteShape.copy(endNoteShape);
        cloned.bothPageBorderFill.copy(bothPageBorderFill);
        cloned.evenPageBorderFill.copy(evenPageBorderFill);
        cloned.oddPageBorderFill.copy(oddPageBorderFill);

        for (BatangPageInfo batangPageInfo : batangPageInfoList) {
            cloned.batangPageInfoList.add(batangPageInfo.clone());
        }

        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        for (BatangPageInfo bpi : this.batangPageInfoList) {
            bpi.getListHeader().setParaCount(bpi.getParagraphList().getParagraphCount());
            bpi.getParagraphList().updateState(iid);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        int size = (sw.getFileVersion().isGreaterEqual(5, 0, 1, 2)) ? 38 : 36;
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, size);
        this.getHeader().write(sw);

        sw.upRecordLevel();
        if(this.ctrlData!=null) {
            this.ctrlData.write(sw);
        }
        if(this.pageDef!=null) {
            this.pageDef.write(sw);
        }

        if(this.footNoteShape!=null) {
            this.footNoteShape.write(sw);
        }
        if(this.endNoteShape!=null) {
            this.endNoteShape.write(sw);
        }
        if(this.bothPageBorderFill!=null) {
            this.bothPageBorderFill.write(sw);
        }
        if(this.evenPageBorderFill!=null) {
            this.evenPageBorderFill.write(sw);
        }
        if(this.oddPageBorderFill!=null) {
            this.oddPageBorderFill.write(sw);
        }

        for (BatangPageInfo bpi : this.batangPageInfoList) {
            bpi.write(sw);
//            ForBatangPageInfo.write(bpi, sw);
        }

        sw.downRecordLevel();
    }
}
