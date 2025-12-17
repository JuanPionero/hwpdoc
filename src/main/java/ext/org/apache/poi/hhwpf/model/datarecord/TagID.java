package ext.org.apache.poi.hhwpf.model.datarecord;

/**
 * Record의 테그 리스트
 *
 * @author juanlee0@naver.com
 */
public final class TagID {
    private TagID() {
    }

    /**
     * tag 시작 값
     */
    private final static short HWPTAG_BEGIN = 0x10;

    // for "DocInfo" stream
    /**
     * 문서 속성 tag
     */
    public final static short HWPTAG_DOCUMENT_PROPERTIES = HWPTAG_BEGIN;
    /**
     * 아이디 매핑 헤더 tag
     */
    public final static short HWPTAG_ID_MAPPINGS = HWPTAG_BEGIN + 1;
    /**
     * BinData tag
     */
    public final static short HWPTAG_BIN_DATA = HWPTAG_BEGIN + 2;
    /**
     * Typeface Name tag
     */
    public final static short HWPTAG_FACE_NAME = HWPTAG_BEGIN + 3;
    /**
     * 테두리/배경 tag
     */
    public final static short HWPTAG_BORDER_FILL = HWPTAG_BEGIN + 4;
    /**
     * 글자 모양 tag
     */
    public final static short HWPTAG_CHAR_SHAPE = HWPTAG_BEGIN + 5;
    /**
     * 탭 정의 tag
     */
    public final static short HWPTAG_TAB_DEF = HWPTAG_BEGIN + 6;
    /**
     * 번호 정의 tag
     */
    public final static short HWPTAG_NUMBERING = HWPTAG_BEGIN + 7;
    /**
     * 글머리표 tag
     */
    public final static short HWPTAG_BULLET = HWPTAG_BEGIN + 8;
    /**
     * 문단 모양 tag
     */
    public final static short HWPTAG_PARA_SHAPE = HWPTAG_BEGIN + 9;
    /**
     * 스타일 tag
     */
    public final static short HWPTAG_STYLE = HWPTAG_BEGIN + 10;
    /**
     * 문서의 임의의 데이터 tag
     */
    public final static short HWPTAG_DOC_DATA = HWPTAG_BEGIN + 11;
    /**
     * 배포용 문서 데이터 tag
     */
    public final static short HWPTAG_DISTRIBUTE_DOC_DATA = HWPTAG_BEGIN + 12;

    // HWPTAG_BEGIN + 13 은 예약된 값으로 추가 정의하지 않는다.

    /**
     * 호환 문서 tag
     */
    public final static short HWPTAG_COMPATIBLE_DOCUMENT = HWPTAG_BEGIN + 14;
    /**
     * 레이아웃 호환성 tag
     */
    public final static short HWPTAG_LAYOUT_COMPATIBILITY = HWPTAG_BEGIN + 15;
    /**
     * 변경 추적 정보 tag
     */
    public final static short HWPTAG_TRACKCHANGE = HWPTAG_BEGIN + 16;
    /**
     * 메모 모양 tag
     */
    public final static short HWPTAG_MEMO_SHAPE = HWPTAG_BEGIN + 76;
    /**
     * 금칙처리 문자 tag
     */
    public final static short HWPTAG_FORBIDDEN_CHAR = HWPTAG_BEGIN + 78;
    /**
     * 변경 추적 내용 및 모양 tag
     */
    public final static short HWPTAG_TRACK_CHANGE = HWPTAG_BEGIN + 80;
    /**
     * 변경 추적 작성자 tag
     */
    public final static short HWPTAG_TRACK_CHANGE_AUTHOR = HWPTAG_BEGIN + 81;

    // for "BodyText" storages
    /**
     * 문단 헤더 tag
     */
    public final static short HWPTAG_PARA_HEADER = HWPTAG_BEGIN + 50;
    /**
     * 문단의 텍스트 tag
     */
    public final static short HWPTAG_PARA_TEXT = HWPTAG_BEGIN + 51;
    /**
     * 문단의 글자 모양 tag
     */
    public final static short HWPTAG_PARA_CHAR_SHAPE = HWPTAG_BEGIN + 52;
    /**
     * 문단의 레이아웃 tag
     */
    public final static short HWPTAG_PARA_LINE_SEG = HWPTAG_BEGIN + 53;
    /**
     * 문단의 영역 태그 tag
     */
    public final static short HWPTAG_PARA_RANGE_TAG = HWPTAG_BEGIN + 54;
    /**
     * 컨트롤 헤더 tag (개체 컨트롤: 가이드 4.3.9 참고)
     */
    public final static short HWPTAG_CTRL_HEADER = HWPTAG_BEGIN + 55;
    /**
     * 문단 리스트 헤더 tag
     */
    public final static short HWPTAG_LIST_HEADER = HWPTAG_BEGIN + 56;
    /**
     * 용지 설정 tag
     */
    public final static short HWPTAG_PAGE_DEF = HWPTAG_BEGIN + 57;
    /**
     * 각주/미주 모양 tag
     */
    public final static short HWPTAG_FOOTNOTE_SHAPE = HWPTAG_BEGIN + 58;
    /**
     * 쪽 테두리/배경 tag
     */
    public final static short HWPTAG_PAGE_BORDER_FILL = HWPTAG_BEGIN + 59;
    /**
     * 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT = HWPTAG_BEGIN + 60;
    /**
     * 표 개체 tag
     */
    public final static short HWPTAG_TABLE = HWPTAG_BEGIN + 61;
    /**
     * 직선 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_LINE = HWPTAG_BEGIN + 62;
    /**
     * 사각형 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_RECTANGLE = HWPTAG_BEGIN + 63;
    /**
     * 타원 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_ELLIPSE = HWPTAG_BEGIN + 64;
    /**
     * 호 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_ARC = HWPTAG_BEGIN + 65;
    /**
     * 다각형 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_POLYGON = HWPTAG_BEGIN + 66;
    /**
     * 곡선 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_CURVE = HWPTAG_BEGIN + 67;
    /**
     * OLE 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_OLE = HWPTAG_BEGIN + 68;
    /**
     * 그림 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_PICTURE = HWPTAG_BEGIN + 69;
    /**
     * 컨테이너 개체 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_CONTAINER = HWPTAG_BEGIN + 70;
    /**
     * 컨트롤 임의의 데이터 tag
     */
    public final static short HWPTAG_CTRL_DATA = HWPTAG_BEGIN + 71;
    /**
     * 수식 개체 tag
     */
    public final static short HWPTAG_EQEDIT = HWPTAG_BEGIN + 72;
    /**
     * 글맵시 tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_TEXTART = HWPTAG_BEGIN + 74;
    /**
     * 양식 개체 tag
     */
    public final static short HWPTAG_FORM_OBJECT = HWPTAG_BEGIN + 75;
    /**
     * 메모 리스트 헤더 tag
     */
    public final static short HWPTAG_MEMO_LIST = HWPTAG_BEGIN + 77;
    /**
     * 차트 데이터 tag
     */
    public final static short HWPTAG_CHART_DATA = HWPTAG_BEGIN + 79;
    /**
     * 비디오 데이터 tag
     */
    public final static short HWPTAG_VIDEO_DATA = HWPTAG_BEGIN + 82;
    /**
     * Unknown tag
     */
    public final static short HWPTAG_SHAPE_COMPONENT_UNKNOWN = HWPTAG_BEGIN + 99;
}
