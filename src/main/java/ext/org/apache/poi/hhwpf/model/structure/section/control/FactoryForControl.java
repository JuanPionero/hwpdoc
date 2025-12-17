package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.*;

import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.caption.Caption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 컨트롤을 생성하는 객체
 *
 * @author neolord
 */
public class FactoryForControl {
    private static final Logger logger = LoggerFactory.getLogger(FactoryForControl.class);
    /**
     * 컨트롤 id에 해당되는 컨트롤을 생성한다.
     *
     * @param ctrlId 컨트롤 id
     * @return 새로 생성된 컨트롤
     */
    public static Control createControlExtend(long ctrlId) {
        if (ctrlId == ControlType.SectionDefine.getCtrlId()) {
            return new ControlSectionDefine();
        } else if (ctrlId == ControlType.ColumnDefine.getCtrlId()) {
            return new ControlColumnDefine();
        } else if (ctrlId == ControlType.Table.getCtrlId()) {
            return new ControlTable();
        } else if (ctrlId == ControlType.Equation.getCtrlId()) {
            return new ControlEquation();
        } else if (ctrlId == ControlType.Header.getCtrlId()) {
            return new ControlHeader();
        } else if (ctrlId == ControlType.Footer.getCtrlId()) {
            return new ControlFooter();
        } else if (ctrlId == ControlType.Footnote.getCtrlId()) {
            return new ControlFootnote();
        } else if (ctrlId == ControlType.Endnote.getCtrlId()) {
            return new ControlEndnote();
        } else if (ctrlId == ControlType.AutoNumber.getCtrlId()) {
            return new ControlAutoNumber();
        } else if (ctrlId == ControlType.NewNumber.getCtrlId()) {
            return new ControlNewNumber();
        } else if (ctrlId == ControlType.PageHide.getCtrlId()) {
            return new ControlPageHide();
        } else if (ctrlId == ControlType.PageOddEvenAdjust.getCtrlId()) {
            return new ControlPageOddEvenAdjust();
        } else if (ctrlId == ControlType.PageNumberPosition.getCtrlId()) {
            return new ControlPageNumberPosition();
        } else if (ctrlId == ControlType.IndexMark.getCtrlId()) {
            return new ControlIndexMark();
        } else if (ctrlId == ControlType.Bookmark.getCtrlId()) {
            return new ControlBookmark();
        } else if (ctrlId == ControlType.OverlappingLetter.getCtrlId()) {
            return new ControlOverlappingLetter();
        } else if (ctrlId == ControlType.AdditionalText.getCtrlId()) {
            return new ControlAdditionalText();
        } else if (ctrlId == ControlType.HiddenComment.getCtrlId()) {
            return new ControlHiddenComment();
        } else if (ControlType.isField(ctrlId)) {
            return new ControlField(ctrlId);
        }
        throw new UnexpectedFileFormatException( String.format("Unexpected ControlExtend Id : %s (0x%08x)", ctrlId, ctrlId));
    }

    public static Control createControlExtend(long ctrlId, StreamReader sr) throws Exception {
        if (ctrlId == ControlType.SectionDefine.getCtrlId()) {
            return new ControlSectionDefine(sr);
        } else if (ctrlId == ControlType.ColumnDefine.getCtrlId()) {
            ControlColumnDefine t = new ControlColumnDefine(sr);
            return t;
        } else if (ctrlId == ControlType.Table.getCtrlId()) {
            return new ControlTable(sr);
        } else if (ctrlId == ControlType.Equation.getCtrlId()) {
//            logger.trace("generating Equation");
            return new ControlEquation(sr);
        } else if (ctrlId == ControlType.Header.getCtrlId()) {
            return new ControlHeader(sr);
        } else if (ctrlId == ControlType.Footer.getCtrlId()) {
            return new ControlFooter(sr);
        } else if (ctrlId == ControlType.Footnote.getCtrlId()) {
            return new ControlFootnote(sr);
        } else if (ctrlId == ControlType.Endnote.getCtrlId()) {
            return new ControlEndnote(sr);
        } else if (ctrlId == ControlType.AutoNumber.getCtrlId()) {
            return new ControlAutoNumber(sr);
        } else if (ctrlId == ControlType.NewNumber.getCtrlId()) {
            return new ControlNewNumber(sr);
        } else if (ctrlId == ControlType.PageHide.getCtrlId()) {
            return new ControlPageHide(sr);
        } else if (ctrlId == ControlType.PageOddEvenAdjust.getCtrlId()) {
            return new ControlPageOddEvenAdjust(sr);
        } else if (ctrlId == ControlType.PageNumberPosition.getCtrlId()) {
            return new ControlPageNumberPosition(sr);
        } else if (ctrlId == ControlType.IndexMark.getCtrlId()) {
            return new ControlIndexMark(sr);
        } else if (ctrlId == ControlType.Bookmark.getCtrlId()) {
            return new ControlBookmark(sr);
        } else if (ctrlId == ControlType.OverlappingLetter.getCtrlId()) {
            return new ControlOverlappingLetter(sr);
        } else if (ctrlId == ControlType.AdditionalText.getCtrlId()) {
            return new ControlAdditionalText(sr);
        } else if (ctrlId == ControlType.HiddenComment.getCtrlId()) {
            return new ControlHiddenComment(sr);
        } else if (ControlType.isField(ctrlId)) {
            return new ControlField(ctrlId, sr);
        }
        throw new UnexpectedFileFormatException( String.format("Unexpected ControlExtend Id : %s (0x%08x)", ctrlId, ctrlId));
    }

    public static ControlForm createFormControl(CtrlHeaderGso header) {
        return new ControlForm(header);
    }

    public static ControlForm createFormControl(StreamReader sr) throws IOException, IllegalAccessException {
        // id에 따라, 공통 속성 : 가이드 표 5, 64. 69, 70 참고.
        // 공통속성이 끝나면 캡션 정보가 뒤 따를 수 있다. 가이드 표 71 (캡션 리스트), 72(캡션), 73(캡션 속성) 참고.
        CtrlHeaderGso ctrlHeader = new CtrlHeaderGso(sr, ControlType.Form);

        // 원 코드에서 ForGwoControl.captionAndCtrlData() 부분 변환 하고 있는 중
        // 공통 속성을 이어, 콘트롤의 데이터 레코드가 옴 (이런 레코드로는 HWPTAG_LIST_HEADER, HWPTAG_CTRL_DATA 가 있음)
        DataRecordHeader dataRecordHeader = sr.readDataRecordHeader();

        if(dataRecordHeader.getTagID() != HWPTAG_FORM_OBJECT ) {
            // 규칙, 규격에 맞지 않는 것임. 예외상황으로 봐야 함.
            throw new UnexpectedFileFormatException("Unexpected FormControl Record Structure");
        }

        return new ControlForm(sr, ctrlHeader);
    }

    /**
     * 그리기 객체 아이디(gsoId)에 해당되는 그리기 객체 컨트롤를 새로 생성한다.
     *
     * @param gsoId  그리기 객체 아이디
     * @param header 그리기 객체용 컨트롤 헤더
     * @return 새로 생성된 그리기 객체 컨트롤
     */
    public static GsoControl createGso(long gsoId, CtrlHeaderGso header) {
        return _createGso(gsoId, header);
    }

    public static GsoControl createGso(StreamReader sr) throws Exception {

        // id에 따라, 공통 속성 : 가이드 표 5, 64, 69, 70 참고.
        // 공통속성이 끝나면 캡션 정보가 뒤 따를 수 있다. 가이드 표 71 (캡션 리스트), 72(캡션), 73(캡션 속성) 참고.
        logger.trace("Just before CtrlHeaderGso creation");
        CtrlHeaderGso header = new CtrlHeaderGso(sr, ControlType.Gso);
        logger.trace("CtrlHeaderGso creation - finished");

        //===== Key Point =======
        // Gso 는 component를 가진다.
        // 각 Gso component는 고유의 id (gso object id)를 가진다.
        // 이 고유 id를 얻기 전에 Caption, ctrlData를 먼저 구해야 한다.
        //=====================

        // 원 코드에서 ForGsoControl.captionAndCtrlData() 부분 변환 하고 있는 중
        // 공통 속성을 이어, 콘트롤의 데이터 레코드가 옴 (이런 레코드로는 HWPTAG_LIST_HEADER, HWPTAG_CTRL_DATA 가 있음)
        sr.readDataRecordHeader();

        Caption caption = null;
        CtrlData ctrlData = null;
        while(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_SHAPE_COMPONENT) {
            if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_LIST_HEADER &&
                    sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_CTRL_DATA ) {
                // 규칙, 규격에 맞지 않는 것임. 예외상황으로 봐야 함.
                throw new UnexpectedFileFormatException("Unexpected Gso Record Structure");
            }
            if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
                caption = new Caption(sr);
            } else {
                ctrlData = new CtrlData(sr);
            }
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
        }

        logger.trace("Caption= {}, CtrlData= {}", caption, ctrlData);

        if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_SHAPE_COMPONENT  ) {
            // 규칙, 규격에 맞지 않는 것임. 예외상황으로 봐야 함.
            throw new UnexpectedFileFormatException("Unexpected Gso (SHAPE_COMPONENT) Record Structure");
        }
        logger.trace("remainedDataRecordData = {}", sr.getRemainedDataRecordDataCount());

        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        // GSO : GenShapeObject 가이드문서 #40 페이지 마지막 부분. 4.3.9.2.1 개체 요소
        // GSO ID == object control id 객체 컨트롤 id
        long objectId = sr.readUInt4();
        sr.readBytes(4); // id2
        logger.trace("GSO object Id = {} {}", String.format("0x%08x", objectId), CtrlID.make(objectId));
        GsoControl gc = _createGso(sr, header, objectId);
        // Rectangle에서는 ctrlData가 내부적으로 다시 생성되는 듯 함.
        gc.setCaption(caption);
        if (objectId != GsoControlType.Rectangle.getId()) {
            gc.setCtrlData(ctrlData);
        }
//        _generateShapeComponent(sr, gc);
//        _complementControl(objectId, header);
        return gc;

    }

//    private static GsoControl _generateShapeComponent(StreamReader sr, GsoControl gc) throws IOException, IllegalAccessException {
//        _processCommonPart(sr, gc.getShapeComponent());
//        if(gc.getGsoType() == GsoControlType.Container) {
//            _processContainerChildInfo(sr, (ShapeComponentContainer) gc.getShapeComponent());
//            if(!sr.isEndOfDataRecord()) {
//                ((ShapeComponentContainer) gc.getShapeComponent()).setInstid(sr.readUInt4());
//            }
//        } else {
//
//        }
//    }
//    private static void _processCommonPart(StreamReader sr, ShapeComponent sc) throws IOException {
//        sc.setOffsetX(sr.readSInt4());
//        sc.setOffsetY(sr.readSInt4());
//        sc.setGroupingCount(sr.readUInt2());
//        sc.setLocalFileVersion(sr.readUInt2());
//        sc.setWidthAtCreate(sr.readSInt4());
//        sc.setHeightAtCreate(sr.readSInt4());
//        sc.setWidthAtCurrent(sr.readSInt4());
//        sc.setHeightAtCurrent(sr.readSInt4());
//        sc.getProperty().setValue(sr.readUInt4());
//        sc.setRotateAngle(sr.readUInt2());
//        sc.setRotateXCenter(sr.readSInt4());
//        sc.setRotateYCenter(sr.readSInt4());
//    }
//    private static void _processRenderingInfo(StreamReader sr, )
//    private static void _processContainerChildInfo(StreamReader sr, ShapeComponentContainer scc) throws IOException {
//        int count = sr.readUInt2();
//        for (int index = 0; index < count; index++) {
//            long childId = sr.readUInt4();
//            scc.addChildControlId(childId);
//        }
//    }
//    private static ShapeComponentContainer _generateShapeComponentContainer(StreamReader sr) {
//
//    }

    private static GsoControl _createGso(long gsoId, CtrlHeaderGso header) {
        if (gsoId == GsoControlType.Line.getId()) {
            return new ControlLine(header);
        } else if (gsoId == GsoControlType.Rectangle.getId()) {
            return new ControlRectangle(header);
        } else if (gsoId == GsoControlType.Ellipse.getId()) {
            return new ControlEllipse(header);
        } else if (gsoId == GsoControlType.Polygon.getId()) {
            return new ControlPolygon(header);
        } else if (gsoId == GsoControlType.Arc.getId()) {
            return new ControlArc(header);
        } else if (gsoId == GsoControlType.Curve.getId()) {
            return new ControlCurve(header);
        } else if (gsoId == GsoControlType.Picture.getId()) {
            return new ControlPicture(header);
        } else if (gsoId == GsoControlType.OLE.getId()) {
            return new ControlOLE(header);
        } else if (gsoId == GsoControlType.Container.getId()) {
            return new ControlContainer(header);
        } else if (gsoId == GsoControlType.ObjectLinkLine.getId()) {
            return new ControlObjectLinkLine(header);
        } else if (gsoId == GsoControlType.TextArt.getId()) {
            return new ControlTextArt(header);
        } else {
            return new ControlUnknown(header);
        }
    }

    public static GsoControl _createGso(StreamReader sr, CtrlHeaderGso header, long gsoId) throws Exception {
        if (gsoId == GsoControlType.Line.getId()) {
            return new ControlLine(sr, header);
        } else if (gsoId == GsoControlType.Rectangle.getId()) {
            return new ControlRectangle(sr, header);
        } else if (gsoId == GsoControlType.Ellipse.getId()) {
            return new ControlEllipse(sr, header);
        } else if (gsoId == GsoControlType.Polygon.getId()) {
            return new ControlPolygon(sr, header);
        } else if (gsoId == GsoControlType.Arc.getId()) {
            return new ControlArc(sr, header);
        } else if (gsoId == GsoControlType.Curve.getId()) {
            return new ControlCurve(sr, header);
        } else if (gsoId == GsoControlType.Picture.getId()) {
            return new ControlPicture(sr, header);
        } else if (gsoId == GsoControlType.OLE.getId()) {
            return new ControlOLE(sr, header);
        } else if (gsoId == GsoControlType.Container.getId()) {
            return new ControlContainer(sr, header);
        } else if (gsoId == GsoControlType.ObjectLinkLine.getId()) {
            return new ControlObjectLinkLine(sr, header);
        } else if (gsoId == GsoControlType.TextArt.getId()) {
            return new ControlTextArt(sr, header);
        } else {
            return new ControlUnknown(sr, header);
        }
    }
}
