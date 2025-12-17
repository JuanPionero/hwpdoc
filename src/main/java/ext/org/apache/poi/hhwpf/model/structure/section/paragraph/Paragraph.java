package ext.org.apache.poi.hhwpf.model.structure.section.paragraph;



import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.Section;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderThickness;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderType;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.EachBorder;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import ext.org.apache.poi.hhwpf.model.structure.section.control.*;

import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderColumnDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;

import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderSectionDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.*;

import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.charshape.ParaCharShape;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.header.ParaHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.lineseg.LineSegItem;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.lineseg.ParaLineSeg;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.rangetag.ParaRangeTag;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPChar;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.ParaText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;
import static ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType.Form;
import static ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType.Gso;


/**
 * 하나의 문단을 나타내는 객체
 *
 * @author neolord
 */
public class Paragraph {
    private static final Logger logger = LoggerFactory.getLogger(Paragraph.class);

    public static final Paragraph[] Zero_Array = new Paragraph[0];
    /**
     * 헤더
     */
    private ParaHeader paraHeader;
    /**
     * 텍스트
     */
    private ParaText paraText;
    /**
     * 글자 모양
     */
    private ParaCharShape paraCharShape;
    /**
     * 레이아웃
     */
    private ParaLineSeg paraLineSeg;
    /**
     * 영역 태그
     */
    private ParaRangeTag paraRangeTag;

    /**
     * 컨트롤 리스트
     */
    private ArrayList<Control> controlList;

    public Paragraph() {
        this.paraHeader = new ParaHeader();
        this.controlList = new ArrayList<>();
        this.paraText = new ParaText();
        this.paraCharShape = new ParaCharShape(0,0);
        this.paraLineSeg = new ParaLineSeg();
    }

    /**
     * Create new empty Paragraph for Section
     * 섹션의 첫번째 문장에는 Section Define과 Column Define으로 시작해야 함.
     * TODO Fatory 패턴을 사용하거나, Initializer 를 구현하는 것으로 변경할 계획임.
     * @param section
     */
    public Paragraph(Section section) {
        // EmptyParagraphAdder.add(Section) 대치
        this.paraHeader = new ParaHeader(true, 17, 4L, 3,
                (short) 0, (short) 3, 1, 0, 1, 0 , 0);
        this.paraText = new ParaText();
        this.paraText.addExtendCharForSectionDefine();
        this.paraText.addExtendCharForColumnDefine();

        this.paraCharShape = new ParaCharShape(0,0);

        this.paraLineSeg = new ParaLineSeg(new LineSegItem(0,0,1000,1000,850,600,0,43520,39216));

        this.controlList = new ArrayList<>();
        CtrlHeaderSectionDefine ctrlHeaderSectionDefine = new CtrlHeaderSectionDefine(0,1134,0,0,8000,1,0,0,0,0,0);
        this.controlList.add(  new ControlSectionDefine(ctrlHeaderSectionDefine) );

        CtrlHeaderColumnDefine ctrlHeaderColumnDefine = new CtrlHeaderColumnDefine(4100, 0, 0, new EachBorder(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0)));
        this.controlList.add( new ControlColumnDefine(ctrlHeaderColumnDefine) );

    }
    public Paragraph(Initializer<Paragraph> initializer) throws Exception {
        this();
        initializer.init(this);

    }

    /**
     * Create new empty Paragraph for ParagraphList
     * @param paragraphList
     */
//    public Paragraph(ParagraphList paragraphList) {
//        // EmptyParagraphAdder.add(ParagraphList) 대치
//        this.paraHeader = new ParaHeader(true, 17, 4L, 3,
//                (short) 0, (short) 0, 1, 0, 1, 0 , 0);
//        this.paraCharShape = new ParaCharShape(0,0);
//    }
    /**
     * 생성자
     */
    public Paragraph(StreamReader sr) throws Exception {
        if(sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_PARA_HEADER) {
            throw new UnexpectedFileFormatException("Unexpected Paragraph Header");
        }
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());

        short paraHeaderLevel = sr.getCurrentDataRecordHeader().getLevel();
        this.paraHeader = new ParaHeader(sr); // 원작자의 기존 코드에서는 무조건 DataRecordData 를 모두 읽는다.
        // ParaHeader를 제외하고 나머지는 그 흐름이 유사함.

        if(!sr.isAvailable()) {
            return;
        }
        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_PARA_TEXT) {
            this.paraText = new ParaText(sr);
        }

        if(!sr.isAvailable()) {
            return;
        }
        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_PARA_CHAR_SHAPE) {
            this.paraCharShape = new ParaCharShape(sr, this.paraHeader);
        }
        if(!sr.isAvailable()) {
            return;
        }
        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_PARA_LINE_SEG) {
            this.paraLineSeg = new ParaLineSeg(sr, this.paraHeader);
        }

        if(!sr.isAvailable()) {
            return;
        }
        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_PARA_RANGE_TAG) {
            this.paraRangeTag = new ParaRangeTag(sr);
        }

        // 스트림이 남았는지 체크
        // DataRecordData를 모두 다 읽었으면, DataRecordHeader를 읽기
        // Control 의 경우 추가 조건이 들어감.
        while (sr.isAvailable()) {
            // 내부적으로 dataRecordHeader 를 읽은 다음에 level 차이로 인해 내부 loop를 벗어나는 경우가 있다.
            // 이를 대비해서
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
            // 원작자 코드의 ForParagraph.java 내용 참고 하여 구현.
            if ( paraHeaderLevel >= sr.getCurrentDataRecordHeader().getLevel()
                    || (paraHeaderLevel==0 && sr.getCurrentDataRecordHeader().getLevel()==1
                            && ( sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER
                            || sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_MEMO_LIST ) )) {

                logger.trace("paraHeaderLevel={}", paraHeaderLevel);
                logger.trace("currentLevel={}", sr.getCurrentDataRecordHeader().getLevel());
                logger.trace("currentTagId={}", sr.getCurrentDataRecordHeader().getTagID());
                break;
            }
//            logger.trace("TagID = {}", sr.getCurrentDataRecordHeader().getTagID());
            switch (sr.getCurrentDataRecordHeader().getTagID()) {

                case HWPTAG_CTRL_HEADER : this.parseControl(sr); break;
                default: // 다른 것들은 무시.
                    // DataRecord의 data 를 그냥 읽어 넘기라는 의미.
                    sr.readRestOfDataRecordData();
            }
            logger.trace("Control List {}", this.controlList);
        }
        if(sr.isAvailable()) {
            logger.trace("End Of Paragraph ============== stream remain");
        } else {
            logger.trace("End Of Paragraph ============== stream end");
        }


    }

    private void parseControl(StreamReader sr) throws Exception {
        long controlId = sr.readUInt4(); // HWPTAG_CTRL_HEADER 의 data 4 bytes
        logger.trace("Start to read a Control Component id= {}, {}", String.format("0x%08x",controlId), CtrlID.make(controlId));
        if (this.controlList == null) {
            this.controlList = new ArrayList<Control>();
        }
        if (controlId == Gso.getCtrlId()) { // 가이드의 표 69 참고 !
//            ForGsoControl fgc = new ForGsoControl();
//            fgc.read(paragraph, sr);
            logger.trace("to create ShapeComponent Control (GSO)");
            GsoControl gc = FactoryForControl.createGso(sr);
            this.controlList.add(gc);
        } else if (controlId == Form.getCtrlId()) {
            logger.trace("to create Form Control");
            // ForFormControl ffc = new ForFormControl();
            // ffc.read(paragraph, sr);
            // ForFormControl.read(paragraph, sr);
            ControlForm fc = FactoryForControl.createFormControl(sr);
            this.controlList.add(fc);
        } else {
            // ForControl.read(c, sr);
            logger.trace("to create ControlExtend");
            Control c = FactoryForControl.createControlExtend(controlId, sr);// paragraph.addNewControl(id);
            this.controlList.add(c);
        }
    }

    /**
     * 헤더에 대한 객체를 반환한다.
     *
     * @return 헤더에 대한 객체
     */
    public ParaHeader getHeader() {
        return paraHeader;
    }

    /**
     * 문단 텍스트에 대한 객체를 생성한다.
     */
    public void createText() {
        paraText = new ParaText();
    }

    /**
     * 문단 텍스트에 대한 객체를 삭제한다.
     */
    public void deleteText() {
        paraText = null;
    }

    /**
     * 문단 텍스트에 대한 객체를 반환한다.
     *
     * @return 문단 텍스트에 대한 객체
     */
    public ParaText getParaText() {
        return paraText;
    }

    /**
     * 문단의 글자 모양에 대한 객체를 생성한다.
     */
    public void createCharShape() {
        paraCharShape = new ParaCharShape();
    }

    /**
     * 문단의 글자 모양에 대한 객체를 삭제한다.
     */
    public void deleteCharShape() {
        paraCharShape = null;
    }

    /**
     * 문단의 글자 모양에 대한 객체를 반환한다.
     *
     * @return 문단의 글자 모양에 대한 객체
     */
    public ParaCharShape getParaCharShape() {
        return paraCharShape;
    }

    /**
     * 문단의 레이아웃에 대한 객체를 생성한다.
     */
    public void createLineSeg() {
        paraLineSeg = new ParaLineSeg();
    }

    /**
     * 문단의 레이아웃에 대한 객체를 삭제한다.
     */
    public void deleteLineSeg() {
        paraLineSeg = null;
    }

    /**
     * 문단의 레이아웃에 대한 객체를 반환한다.
     *
     * @return 문단의 레이아웃에 대한 객체
     */
    public ParaLineSeg getParaLineSeg() {
        return paraLineSeg;
    }

    /**
     * 문단의 영역 태그에 대한 객체를 생성한다.
     */
    public void createRangeTag() {
        paraRangeTag = new ParaRangeTag();
    }

    /**
     * 문단의 영역 태그에 대한 객체를 삭제한다.
     */
    public void deleteRangeTag() {
        paraRangeTag = null;
    }

    /**
     * 문단의 영역 태그에 대한 객체를 반환한다.
     *
     * @return 문단의 영역 태그에 대한 객체
     */
    public ParaRangeTag getParaRangeTag() {
        return paraRangeTag;
    }

    /**
     * type에 해당하는 새로운 컨트롤을 생성하고 리스트에 추가한다.
     *
     * @param type 컨트롤 타입
     * @return 새로 생성된 컨트롤 객체
     */
    public Control addNewControl(ControlType type) {
        return addNewControl(type.getCtrlId());
    }

    /**
     * id에 해당하는 새로운 컨트롤을 생성하고 리스트에 추가한다.
     *
     * @param id 컨트롤 id값
     * @return 새로 생성된 컨트롤 객체
     */
    public Control addNewControl(long id) {
        if (controlList == null) {
            controlList = new ArrayList<Control>();
        }
        Control c = FactoryForControl.createControlExtend(id);
        controlList.add(c);
        return c;
    }

    /**
     * gsoType에 해당하는 새로운 GSO 컨트롤(그리기 객체)를 생성하고 리스트에 추가한다.
     *
     * @param gsoType GSO 컨트롤(그리기 객체) 타입
     * @return 새로 생성한 GSO 컨트롤
     */
    public GsoControl addNewGsoControl(GsoControlType gsoType) {
        return addNewGsoControl(gsoType.getId(), new CtrlHeaderGso());
    }

    /**
     * gsoType에 해당하는 새로운 GSO 컨트롤(그리기 객체)를 생성하고 리스트에 추가한다. 새로 생성한 GSO 컨트롤의 헤더를
     * header로 설정한다.
     *
     * @param gsoType GSO 컨트롤(그리기 객체) 타입
     * @param header  컨트롤 헤더 객체
     * @return 새로 생성한 GSO 컨트롤
     */
    public GsoControl addNewGsoControl(GsoControlType gsoType, CtrlHeaderGso header) {
        return addNewGsoControl(gsoType.getId(), header);
    }

    public ControlForm addNewFormControl(CtrlHeaderGso header) {
        if (controlList == null) {
            controlList = new ArrayList<Control>();
        }
        ControlForm fc = FactoryForControl.createFormControl(header);
        controlList.add(fc);
        return fc;
    }

    /**
     * gsoid에 해당하는 새로운 GSO 컨트롤(그리기 객체)를 생성하고 리스트에 추가한다. 새로 생성한 GSO 컨트롤의 헤더를 header로
     * 설정한다.
     *
     * @param gsoId  GSO 컨트롤(그리기 객체)의 id
     * @param header 컨트롤 헤더 객체
     * @return 새로 생성한 GSO 컨트롤
     */
    public GsoControl addNewGsoControl(long gsoId, CtrlHeaderGso header) {
        if (controlList == null) {
            controlList = new ArrayList<Control>();
        }
        GsoControl gc = FactoryForControl.createGso(gsoId, header);
        controlList.add(gc);
        return gc;
    }

    /**
     * 컨트롤 리스트를 반환한다.
     *
     * @return 컨트롤 리스트
     */
    public ArrayList<Control> getControlList() {
        return controlList;
    }

    public void createControlList() {
        controlList = new ArrayList<>();
    }

    /**
     * 컨트롤 리스트에서 컨트롤의 순번을 반환한다.
     *
     * @param c 컨트롤
     * @return 컨트롤의 순번
     */
    public int getControlIndex(Control c) {
        return controlList.indexOf(c);
    }

    /**
     * 문단 내의 일반 문자열을 반환한다.
     *
     * @return 문단 내의 일반 문자열
     * @throws UnsupportedEncodingException
     */
    public String getNormalString() throws UnsupportedEncodingException {
        if (paraText != null) {
            return paraText.getNormalString(0);
        }
        return "";
    }

    public Paragraph clone() {
        Paragraph cloned = new Paragraph();

        cloned.paraHeader.copy(paraHeader);

        if (paraText != null) {
            cloned.paraText = paraText.clone();
        } else {
            cloned.paraText = null;
        }

        if (paraCharShape != null) {
            cloned.paraCharShape = paraCharShape.clone();
        } else {
            cloned.paraCharShape = null;
        }

        if (paraLineSeg != null) {
            cloned.paraLineSeg = paraLineSeg.clone();
        } else {
            cloned.paraLineSeg = null;
        }

        if (paraRangeTag != null) {
            cloned.paraRangeTag = paraRangeTag.clone();
        } else {
            cloned.paraRangeTag = null;
        }

        if (controlList != null) {
            cloned.controlList = new ArrayList<Control>();
            for (Control control : controlList) {
                cloned.controlList.add(control.clone());
            }
        } else {
            cloned.controlList = null;
        }

        return cloned;
    }

    @Override
    public String toString() {
        return "Paragraph{" +
                "paraHeader=" + paraHeader +
                ", paraText=" + paraText +
                ", paraCharShape=" + paraCharShape +
                ", paraLineSeg=" + paraLineSeg +
                ", paraRangeTag=" + paraRangeTag +
                ", controlList=" + controlList +
                '}';
    }

    public void updateParagraphState(boolean isLastParaphInList, InstanceID iid) {

        this.paraHeader.updateState(isLastParaphInList, this);
        // ForControl.autoSet 을 다시 작성
        if(this.controlList != null) {
            for(Control c : this.controlList) {
                if(c instanceof StateUpdatable) {
                    ((StateUpdatable) c).updateState(iid);
                }
            }
        }


    }

    public void write(StreamWriter sw) throws IOException {
        this.paraHeader.write(sw);

        sw.upRecordLevel();
        if(!_emptyText()) {
            long size = this.paraHeader.getCharacterCount() * 2;
            this.paraText.write(sw, (int) size);
        }

        if(this.paraCharShape!=null) {
            this.paraCharShape.write(sw);
        }
        if(this.paraLineSeg!=null) {
            this.paraLineSeg.write(sw);
        }
        if(this.paraRangeTag!=null) {
            this.paraRangeTag.write(sw);
        }
        if(this.controlList!=null) {
            for(Control c: this.controlList) {
                c.write(sw);
            }
        }
        sw.downRecordLevel();
    }

    // TODO 재검토 대상으로, paraText.charList 길이가 1 미만일 경우 .get(0) 은 null 이라는 점을 고려하지 않은 코드임.
    // 그 길이가 1일 때 줄바꿈 하나만 있다면 빈 문자열로 취급하겠다는 의미로 보임.
    // 그렇다면 paraText.write 에 sw 만 보내고, paraText의 charList 길이로 저장 공간 크기 판단하는 것이 가능할 것으로 보임.
    private boolean _emptyText() {
        if (this.paraHeader.getCharacterCount() <= 1) {
            if (this.paraText == null) {
                return true;
            }

            if (this.paraText.getCharList().size() <= 1) {
                HWPChar hwpChar = paraText.getCharList().get(0);
                if (hwpChar.getCode() == 0xd) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setParaHeader(ParaHeader paraHeader) {
        this.paraHeader = paraHeader;
    }

    public void setParaCharShape(ParaCharShape paraCharShape) {
        this.paraCharShape = paraCharShape;
    }

    public void setParaLineSeg(ParaLineSeg paraLineSeg) {
        this.paraLineSeg = paraLineSeg;
    }

    public void close() {
        this.getParaText().arrangeParagraphEnd();
    }
}
