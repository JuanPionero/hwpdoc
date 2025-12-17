package ext.org.apache.poi.hhwpf.model;


import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.*;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.FillInfo;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.FillType;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.PatternFill;
import ext.org.apache.poi.hhwpf.model.datarecord.compatibledocument.CompatibleDocumentSort;
import ext.org.apache.poi.hhwpf.model.etc.UnknownRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

public class DocInfo {
    private static final Logger logger = LoggerFactory.getLogger(DocInfo.class);
    /**
     * 문서 속성
     */
    private DocumentProperties documentProperties;
    /**
     * 아이디 매핑 헤더
     */
    private IDMappings idMappings = null;
    /**
     * 바이너리 데이터 리스트
     */
    private ArrayList<BinData> binDataList;
    /**
     * 한글 글꼴 리스트
     */
    private ArrayList<FaceName> hangulFaceNameList;
    /**
     * 영어 글꼴 리스트
     */
    private ArrayList<FaceName> englishFaceNameList;
    /**
     * 한자 글꼴 리스트
     */
    private ArrayList<FaceName> hanjaFaceNameList;
    /**
     * 일어 글꼴 리스트
     */
    private ArrayList<FaceName> japaneseFaceNameList;
    /**
     * 기타 글꼴 리스트
     */
    private ArrayList<FaceName> etcFaceNameList;
    /**
     * 기호 글꼴 리스트
     */
    private ArrayList<FaceName> symbolFaceNameList;
    /**
     * 사용자 글꼴 리스트
     */
    private ArrayList<FaceName> userFaceNameList;
    /**
     * 테두리/배경 리스트
     */
    private ArrayList<BorderFill> borderFillList;
    /**
     * 글자 모양 리스트
     */
    private ArrayList<CharShape> charShapeList;
    /**
     * 탭 정의 리스트
     */
    private ArrayList<TabDef> tabDefList;
    /**
     * 문단 번호 리스트
     */
    private ArrayList<Numbering> numberingList;
    /**
     * 글머리표 리스트
     */
    private ArrayList<Bullet> bulletList;
    /**
     * 문단 모양 리스트
     */
    private ArrayList<ParaShape> paraShapeList;
    /**
     * 스타일 리스트
     */
    private ArrayList<Style> styleList;
    /**
     * 문서 임의의 데이터
     */
    private UnknownRecord docData;
    /**
     * 배포용 문서 데이터
     */
    private UnknownRecord distributeDocData;
    /**
     * 호환 문서
     */
    private CompatibleDocument compatibleDocument;
    /**
     * 레이아웃 호환성
     */
    private LayoutCompatibility layoutCompatibility;
    /**
     * 변경 추적 정보
     */
    private UnknownRecord trackChange;
    /**
     * 메모 모양 리스트
     */
    private ArrayList<MemoShape> memoShapeList;
    /**
     * 금칙처리 문자
     */
    private UnknownRecord forbiddenChar;
    /**
     * 변경 추적 내용 및 모양
     */
    private ArrayList<UnknownRecord> trackChange2List;
    /**
     * 변경 추적 작성자
     */
    private ArrayList<UnknownRecord> trackChangeAuthorList;

    /**
     * Create default(~=empty docinfo for empty document) DocInfo
     */
    public DocInfo() {
        this.documentProperties = new DocumentProperties();
        this.idMappings = new IDMappings();
        this.binDataList = new ArrayList<BinData>();

        // 기본 폰트 목록 (기존 FaceNameAdder 대체)
        hangulFaceNameList = new ArrayList<FaceName>();
        englishFaceNameList = new ArrayList<FaceName>();
        hanjaFaceNameList = new ArrayList<FaceName>();
        japaneseFaceNameList = new ArrayList<FaceName>();
        etcFaceNameList = new ArrayList<FaceName>();
        symbolFaceNameList = new ArrayList<FaceName>();
        userFaceNameList = new ArrayList<FaceName>();
//        this.initFaceNameLists();

        // BorderFillAdder.add 대체
        borderFillList = new ArrayList<BorderFill>();
//        this.initBorderFillLists();

        // CharShapeAdder.add 대체
        charShapeList = new ArrayList<CharShape>();
//        this.initCharShapeLists();

        tabDefList = new ArrayList<TabDef>();
//        this.initTabDefLists();

        numberingList = new ArrayList<Numbering>();
//        this.initNumberingLists();

        bulletList = new ArrayList<Bullet>();

        paraShapeList = new ArrayList<ParaShape>();
//        this.initParaShapeLists();

        styleList = new ArrayList<Style>();
//        this.initStyleLists();

        docData = null;
        distributeDocData = null;

        compatibleDocument = new CompatibleDocument(CompatibleDocumentSort.HWPCurrent);
        layoutCompatibility = new LayoutCompatibility();

        trackChange = null;
        memoShapeList = new ArrayList<MemoShape>();
        forbiddenChar = null;
        trackChange2List = new ArrayList<UnknownRecord>();
        trackChangeAuthorList = new ArrayList<UnknownRecord>();

    }

    public DocInfo(Initializer<DocInfo> initializer) throws Exception {
        this();
        initializer.init(this);
    }

//    protected void initFaceNameLists() {
//        Stream.<ArrayList<FaceName>>of(hangulFaceNameList, englishFaceNameList, hanjaFaceNameList,
//                japaneseFaceNameList, etcFaceNameList, symbolFaceNameList, userFaceNameList).forEach(list -> {
//            list.add(new FaceName("함초롬돋움", "HCR Dotum"));
//            list.add(new FaceName("함초롬바탕", "HCR Batang"));
//        });
//    }
//
//    protected void initBorderFillLists() {
//        borderFillList.add(new BorderFill());
//        FillType fillType = new FillType();
//        fillType.setPatternFill(true);
//        PatternFill patternFill = new PatternFill();
//        FillInfo fillInfo = new FillInfo(fillType, patternFill);
//        borderFillList.add(new BorderFill(fillInfo));
//    }
//
//    protected void initCharShapeLists() {
//        charShapeList.add(new CharShape(1, (short) 100, (byte) 0, (short) 100, (byte) 0,
//                1000, 0L, (byte) 10, (byte) 10,
//                0L,0L,-1L,11711154L,
//                2,0));
//
//        charShapeList.add(new CharShape(0, (short) 100, (byte) 0, (short) 100, (byte) 0,
//                1000, 0L, (byte) 10, (byte) 10,
//                0L,0L,-1L,11711154L,
//                2,0));
//
//        charShapeList.add(new CharShape(0, (short) 100, (byte) 0, (short) 100, (byte) 0,
//                900, 0L, (byte) 10, (byte) 10,
//                0L,0L,-1L,11711154L,
//                2,0));
//
//        charShapeList.add(new CharShape(1, (short) 100, (byte) 0, (short) 100, (byte) 0,
//                900, 0L, (byte) 10, (byte) 10,
//                0L,0L,-1L,11711154L,
//                2,0));
//
//        charShapeList.add(new CharShape(0, (short) 100, (byte) -5, (short) 100, (byte) 0,
//                900, 0L, (byte) 10, (byte) 10,
//                0L,0L,-1L,11711154L,
//                2,0));
//    }
//
//    protected void initTabDefLists() {
//        tabDefList.add(new TabDef(0));
//        tabDefList.add(new TabDef(1));
//    }
//
//    /**
//     * 기본으로 Numbering객은 한개만 생성. 이후 버전에서 추가를 하게되면, 생성 함수도 이에 대응하여 변견되어야 함.
//     */
//    protected void initNumberingLists() {
//        numberingList.add(new Numbering());
//    }
//
//    protected void initParaShapeLists() {
//        paraShapeList.add(new ParaShape(200L, 0,0,0,
//                0,0,130,0,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 130L));
//
//        paraShapeList.add(new ParaShape(384L, 0,0,-2620,
//                0,0,130,0,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 130L));
//
//        paraShapeList.add(new ParaShape(256L, 0,0,0,
//                0,0,150,0,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 150L));
//
//        paraShapeList.add(new ParaShape(384L, 0,0,0,
//                0,0,160,0,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//
//        paraShapeList.add(new ParaShape(209725824L, 14000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//        // 6th
//        paraShapeList.add(new ParaShape(176171392L, 12000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//        paraShapeList.add(new ParaShape(142616960L, 10000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//        paraShapeList.add(new ParaShape(109062528L, 8000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//        paraShapeList.add(new ParaShape(75508096L, 6000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//        paraShapeList.add(new ParaShape(41953664L, 4000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//
//        // 11th
//        paraShapeList.add(new ParaShape(8399232L, 2000,0,0,
//                0,0,160,1,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//        paraShapeList.add(new ParaShape(384L, 3000,0,0,
//                0,0,160,0,0,
//                2, (short) 0,(short)0,(short)0,(short)0,
//                0,0, 160L));
//    }
//
//    protected void initStyleLists() {
//        // 1st
//        styleList.add(new Style("바탕글", "Normal", (short) 0, (short) 0, (short) 1042, 3, 0));
//        styleList.add(new Style("본문", "Body", (short) 0, (short) 1, (short) 1042, 11, 0));
//        styleList.add(new Style("개요 1", "Outline 1", (short) 0, (short) 2, (short) 1042, 10, 0));
//        styleList.add(new Style("개요 2", "Outline 2", (short) 0, (short) 3, (short) 1042, 9, 0));
//        styleList.add(new Style("개요 3", "Outline 3", (short) 0, (short) 4, (short) 1042, 8, 0));
//        // 6th
//        styleList.add(new Style("개요 4", "Outline 4", (short) 0, (short) 5, (short) 1042, 7, 0));
//        styleList.add(new Style("개요 5", "Outline 5", (short) 0, (short) 6, (short) 1042, 6, 0));
//        styleList.add(new Style("개요 6", "Outline 6", (short) 0, (short) 7, (short) 1042, 5, 0));
//        styleList.add(new Style("개요 7", "Outline 7", (short) 0, (short) 8, (short) 1042, 4, 0));
//        styleList.add(new Style("쪽 번호", "Page Number", (short) 0, (short) 9, (short) 1042, 3, 1));
//        // 11th
//        styleList.add(new Style("머리말", "Header", (short) 0, (short) 10, (short) 1042, 2, 2));
//        styleList.add(new Style("각주", "Footnote", (short) 0, (short) 11, (short) 1042, 1, 3));
//        styleList.add(new Style("미주", "Endnote", (short) 0, (short) 12, (short) 1042, 1, 3));
//        styleList.add(new Style("메모", "Memo", (short) 0, (short) 13, (short) 1042, 0, 4));
//
//    }



    public DocInfo(InputStream inpStr, FileHeader fileHeader) throws IOException, IllegalAccessException {
        this();
        StreamReader sr = new StreamReader(inpStr, fileHeader, this);
        // InputStream의 size는 모를 수 있다.
        // available() 로도 정확하게 알 수 없을 경우도 있다. 특히 network 환경에서.

//        boolean finished = false;

        while(sr.isAvailable()) {
            // DataRecord의 header (헤더)
            // 기존 코드에서 예외는 없음을 확인. 항상 dataRecordHeader 그 다음 바로 body 파징이 이어짐.
            DataRecordHeader dataRecordHeader = sr.readDataRecordHeader();

            switch ( dataRecordHeader.getTagID() ) {
                case HWPTAG_DOCUMENT_PROPERTIES :
                    this.documentProperties = new DocumentProperties(sr);
                    break;

                case HWPTAG_ID_MAPPINGS :
                    this.idMappings = new IDMappings(sr);
                    break;

                case HWPTAG_BIN_DATA :
                    // 참고: binData 개수: this.idMappings.getBinDataCount()
                    this.binDataList.add(new BinData(sr));
                    break;

                case HWPTAG_FACE_NAME :
                    // 참고: 언어별 글꼴 개수도 this.idMappings 에서 구할 수 있음.
                    // 예: this.idMappings.getHangulFaceNameCount()
                    this.registerFontFaceName(new FaceName(sr));
                    break;

                case HWPTAG_BORDER_FILL :
                    this.borderFillList.add(new BorderFill(sr));
                    break;

                case HWPTAG_CHAR_SHAPE :
                    this.charShapeList.add(new CharShape(sr));
                    break;

                case HWPTAG_TAB_DEF :
                    this.tabDefList.add(new TabDef(sr));
                    break;

                case HWPTAG_NUMBERING :
                    this.numberingList.add(new Numbering(sr));
                    break;

                case HWPTAG_BULLET :
                    this.bulletList.add(new Bullet(sr));
                    break;

                case HWPTAG_PARA_SHAPE :
                    this.paraShapeList.add(new ParaShape(sr));
                    break;

                case HWPTAG_STYLE :
                    this.styleList.add(new Style(sr));
                    break;

                case HWPTAG_DOC_DATA :
                    this.docData = new UnknownRecord(sr, dataRecordHeader.getSize());
                    break;

                case HWPTAG_FORBIDDEN_CHAR :
                    this.forbiddenChar = new UnknownRecord(sr, dataRecordHeader.getSize());
                    break;

                case HWPTAG_DISTRIBUTE_DOC_DATA :
                    this.distributeDocData = new UnknownRecord(sr, dataRecordHeader.getSize());
                    break;

                case HWPTAG_COMPATIBLE_DOCUMENT :
                    this.compatibleDocument = new CompatibleDocument(sr);
                    break;

                case HWPTAG_LAYOUT_COMPATIBILITY :
                    this.layoutCompatibility = new LayoutCompatibility(sr);
                    break;

                case HWPTAG_TRACKCHANGE :
                    this.trackChange = new UnknownRecord(sr, dataRecordHeader.getSize());
                    break;

                case HWPTAG_MEMO_SHAPE :
                    this.memoShapeList.add( new MemoShape(sr) );
                    break;

                case HWPTAG_TRACK_CHANGE :
                    this.trackChange2List.add( new UnknownRecord(sr, dataRecordHeader.getSize()));
                    break;

                case HWPTAG_TRACK_CHANGE_AUTHOR :
                    this.trackChangeAuthorList.add( new UnknownRecord(sr, dataRecordHeader.getSize()));
                    break;
            }

//            try {
//                logger.debug("is End Of Data Record Data = {}", sr.isEndOfDataRecord());
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    /**
     * this.idMappings 에 등록된 언어별 글꼴 개수 리스트에 맞춰
     * 등록되는 글꼴이 어떤 언어의 글꼴인지 파악.
     * @param fn
     */
    private void registerFontFaceName(FaceName fn) {
        if(this.hangulFaceNameList.size() < this.idMappings.getHangulFaceNameCount()) {
            this.hangulFaceNameList.add(fn);
        } else if (this.englishFaceNameList.size() < this.idMappings.getEnglishFaceNameCount()) {
            this.englishFaceNameList.add(fn);
        } else if (this.hanjaFaceNameList.size() < this.idMappings.getHanjaFaceNameCount()) {
            this.hanjaFaceNameList.add(fn);
        } else if (this.japaneseFaceNameList.size() < this.idMappings.getJapaneseFaceNameCount()) {
            this.japaneseFaceNameList.add(fn);
        } else if (this.etcFaceNameList.size() < this.idMappings.getEtcFaceNameCount()) {
            this.etcFaceNameList.add(fn);
        } else if (this.symbolFaceNameList.size() < this.idMappings.getSymbolFaceNameCount()) {
            this.symbolFaceNameList.add(fn);
        } else if (this.userFaceNameList.size() < this.idMappings.getUserFaceNameCount()) {
            this.userFaceNameList.add(fn);
        } else {
            throw new UnexpectedFileFormatException(String.format("Unexpected FontName %s", fn.getName()));
        }
    }

    public DocumentProperties getDocumentProperties() {
        return documentProperties;
    }

    public IDMappings getIdMappings() {
        return idMappings;
    }

    public ArrayList<BinData> getBinDataList() {
        return binDataList;
    }

    public ArrayList<FaceName> getHangulFaceNameList() {
        return hangulFaceNameList;
    }

    public ArrayList<FaceName> getEnglishFaceNameList() {
        return englishFaceNameList;
    }

    public ArrayList<FaceName> getHanjaFaceNameList() {
        return hanjaFaceNameList;
    }

    public ArrayList<FaceName> getJapaneseFaceNameList() {
        return japaneseFaceNameList;
    }

    public ArrayList<FaceName> getEtcFaceNameList() {
        return etcFaceNameList;
    }

    public ArrayList<FaceName> getSymbolFaceNameList() {
        return symbolFaceNameList;
    }

    public ArrayList<FaceName> getUserFaceNameList() {
        return userFaceNameList;
    }

    public ArrayList<BorderFill> getBorderFillList() {
        return borderFillList;
    }

    public ArrayList<CharShape> getCharShapeList() {
        return charShapeList;
    }

    public ArrayList<TabDef> getTabDefList() {
        return tabDefList;
    }

    public ArrayList<Numbering> getNumberingList() {
        return numberingList;
    }

    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }

    public ArrayList<ParaShape> getParaShapeList() {
        return paraShapeList;
    }

    public ArrayList<Style> getStyleList() {
        return styleList;
    }

    public UnknownRecord getDocData() {
        return docData;
    }

    public UnknownRecord getDistributeDocData() {
        return distributeDocData;
    }

    public CompatibleDocument getCompatibleDocument() {
        return compatibleDocument;
    }

    public LayoutCompatibility getLayoutCompatibility() {
        return layoutCompatibility;
    }

    public UnknownRecord getTrackChange() {
        return trackChange;
    }

    public ArrayList<MemoShape> getMemoShapeList() {
        return memoShapeList;
    }

    public UnknownRecord getForbiddenChar() {
        return forbiddenChar;
    }

    public ArrayList<UnknownRecord> getTrackChange2List() {
        return trackChange2List;
    }

    public ArrayList<UnknownRecord> getTrackChangeAuthorList() {
        return trackChangeAuthorList;
    }

    public void write(StreamWriter sw) throws Exception {
        if(this.documentProperties != null) {
            this.documentProperties.write(sw);
        }
        if(this.idMappings != null) {
            this.idMappings.write(sw);
        }

        sw.upRecordLevel();
        for (BinData binData : this.binDataList) {
            binData.write(sw);
        }
        // faceName
        faceNameList(this.hangulFaceNameList, sw);
        faceNameList(this.englishFaceNameList, sw);
        faceNameList(this.hanjaFaceNameList,sw);
        faceNameList(this.japaneseFaceNameList, sw);
        faceNameList(this.etcFaceNameList, sw);
        faceNameList(this.symbolFaceNameList, sw);
        faceNameList(this.userFaceNameList, sw);

        // borderFill
        for (BorderFill borderFill : this.borderFillList) {
            borderFill.write(sw);
        }

//        charShape();
        for (CharShape cs : this.charShapeList) {
            cs.write(sw);
        }
//        tabDef();
        for (TabDef td : this.tabDefList) {
            td.write(sw);
        }
//        numbering();
        for (Numbering n : this.numberingList) {
            n.write(sw);
        }
//        bullet();
        for (Bullet b : this.bulletList) {
            b.write(sw);
        }
//         paraShape();
        for (ParaShape paraShape : this.paraShapeList) {
            paraShape.write(sw);
        }
//        style();
        for (Style s : this.styleList) {
            s.write(sw);
        }
//        memoShape();
        for (MemoShape memoShape : this.memoShapeList) {
            memoShape.write(sw);
        }
//        trackChangeAuthor();
        for (UnknownRecord trackChangeAuthor : this.trackChangeAuthorList) {
            trackChangeAuthor.write(TagID.HWPTAG_TRACK_CHANGE_AUTHOR, sw);
        }
//        trackChange2();
        for (UnknownRecord trackChange2 : this.trackChange2List) {
            trackChange2.write(TagID.HWPTAG_TRACK_CHANGE, sw);
        }

        sw.downRecordLevel();
//        docData();
        if(this.docData != null) {
            this.docData.write(TagID.HWPTAG_DOC_DATA, sw);
        }
//        forbiddenChar();
        if(this.forbiddenChar != null) {
            this.forbiddenChar.write(TagID.HWPTAG_FORBIDDEN_CHAR, sw);
        }
//        compatibleDocument();
        if(this.compatibleDocument != null) {
            this.compatibleDocument.write(sw);
        }

        sw.upRecordLevel();
//        layoutCompatibility();
        if (this.layoutCompatibility != null) {
            this.layoutCompatibility.write(sw);
        }

        sw.downRecordLevel();
//        distri...
        if (this.distributeDocData != null) {
            this.distributeDocData.write(TagID.HWPTAG_DISTRIBUTE_DOC_DATA, sw);
        }

        sw.upRecordLevel();
//        trackchs...
        if (this.trackChange != null) {
            this.trackChange.write(TagID.HWPTAG_TRACKCHANGE, sw);
        }

        sw.downRecordLevel();
    }

    private void faceNameList(ArrayList<FaceName> faceNameList, StreamWriter sw)
            throws IOException {
        for (FaceName faceName : faceNameList) {
            faceName.write(sw);
        }
    }
}
