package ext.org.apache.poi.hhwpf.model.datarecord;

import ext.org.apache.poi.hhwpf.Specification;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;

import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;



/**
 * 아이디 매핑 헤더를 나타내는 레코드. "DocInfo" stream 안에 있는 다른 객체들의 개수를 저전한다.
 *
 * @author neolord
 */
public class IDMappings {
    private static final Logger logger = LoggerFactory.getLogger(IDMappings.class);
    /**
     * 바이너리 데이터의 개수
     */
    private int binDataCount;
    /**
     * 한글 글꼴의 개수
     */
    private int hangulFaceNameCount;
    /**
     * 영어 글꼴의 개수
     */
    private int englishFaceNameCount;
    /**
     * 한자 글꼴의 개수
     */
    private int hanjaFaceNameCount;
    /**
     * 일본어 글꼴의 개수
     */
    private int japaneseFaceNameCount;
    /**
     * 기타 글꼴의 개수
     */
    private int etcFaceNameCount;
    /**
     * 기호 글꼴의 개수
     */
    private int symbolFaceNameCount;
    /**
     * 사용자 글꼴의 개수
     */
    private int userFaceNameCount;
    /**
     * 테두리/배경의 개수
     */
    private int borderFillCount;
    /**
     * 글자 모양의 개수
     */
    private int charShapeCount;
    /**
     * 탭 정의의 개수
     */
    private int tabDefCount;
    /**
     * 문단 번호의 개수
     */
    private int numberingCount;
    /**
     * 글머리표의 개수
     */
    private int bulletCount;
    /**
     * 문단 모양의 개수
     */
    private int paraShapeCount;
    /**
     * 스타일의 개수
     */
    private int styleCount;
    /**
     * 메모 모양의 개수(5.0.2.1 이상)
     */
    private int memoShapeCount;
    /**
     * 변경 추적의 개수(5.0.3.2 이상)
     */
    private int trackChangeCount;
    /**
     * 변경추적 사용자의 개수 (5.0.3.2 이상)
     */
    private int trackChangeAuthorCount;

    /**
     * 생성자
     */
    public IDMappings() {
    }
    public IDMappings(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.binDataCount = sr.readSInt4(); // 0
        this.hangulFaceNameCount = sr.readSInt4(); // 1
        this.englishFaceNameCount = sr.readSInt4(); // 2
        this.hanjaFaceNameCount = sr.readSInt4(); // 3
        this.japaneseFaceNameCount = sr.readSInt4(); // 4
        this.etcFaceNameCount = sr.readSInt4(); // 5
        this.symbolFaceNameCount = sr.readSInt4(); // 6
        this.userFaceNameCount = sr.readSInt4(); // 7
        this.borderFillCount = sr.readSInt4(); // 8
        this.charShapeCount = sr.readSInt4(); // 9
        this.tabDefCount = sr.readSInt4(); // 10
        this.numberingCount = sr.readSInt4(); // 11
        this.bulletCount = sr.readSInt4(); // 12
        this.paraShapeCount = sr.readSInt4(); // 13
        this.styleCount = sr.readSInt4(); // 14

        // 버전에 따라 사용 여부가 결정되는 항목들
        this.memoShapeCount = 0; // 15
        this.trackChangeCount = 0; // 16
        this.trackChangeAuthorCount = 0; // 17
        final FileVersion fileVersion = sr.getFileHeader().getFileVersion();
        // 5.0.2.1 이상부터만 예외 발생 시키지 않음
        if(sr.isAvailable() && fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 2, (short) 1)) {
            this.memoShapeCount = sr.readSInt4(); // 15
        }
            // 5.0.3.2 이상부터만 예외 발생 시키지 않음
        if(sr.isAvailable() && fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 3, (short) 2)) {
            this.trackChangeCount = sr.readSInt4(); // 16
            this.trackChangeAuthorCount = sr.readSInt4(); // 17
        }

    }
    /**
     * 바이너리 데이터 객체의 개수를 반환한다.
     *
     * @return 바이너리 데이터 객체의 개수
     */
    public int getBinDataCount() {
        return binDataCount;
    }

    /**
     * 바이너리 데이터 객체의 개수를 설정한다.
     *
     * @param binDataCount 바이너리 데이터 객체의 개수
     */
    public void setBinDataCount(int binDataCount) {
        this.binDataCount = binDataCount;
    }

    /**
     * 한글 글꼴 객체의 개수를 반환한다.
     *
     * @return 한글 글꼴 객체의 개수
     */
    public int getHangulFaceNameCount() {
        return hangulFaceNameCount;
    }

    /**
     * 한글 글꼴 객체의 개수를 설정한다.
     *
     * @param hangulFaceNameCount 한글 글꼴 객체의 개수
     */
    public void setHangulFaceNameCount(int hangulFaceNameCount) {
        this.hangulFaceNameCount = hangulFaceNameCount;
    }

    /**
     * 영어 글꼴 객체의 개수를 반환한다.
     *
     * @return 영어 글꼴 객체의 개수
     */
    public int getEnglishFaceNameCount() {
        return englishFaceNameCount;
    }

    /**
     * 영어 글꼴 객체의 개수를 설정한다.
     *
     * @param englishFaceNameCount 영어 글꼴 객체의 개수
     */
    public void setEnglishFaceNameCount(int englishFaceNameCount) {
        this.englishFaceNameCount = englishFaceNameCount;
    }

    /**
     * 한자 글꼴 객체의 개수를 반환한다.
     *
     * @return 한자 글꼴 객체의 개수
     */
    public int getHanjaFaceNameCount() {
        return hanjaFaceNameCount;
    }

    /**
     * 한자 글꼴 객체의 개수를 설정한다.
     *
     * @param hanjaFaceNameCount 한자 글꼴 객체의 개수
     */
    public void setHanjaFaceNameCount(int hanjaFaceNameCount) {
        this.hanjaFaceNameCount = hanjaFaceNameCount;
    }

    /**
     * 일본어 글꼴 객체의 개수를 반환한다.
     *
     * @return 일본어 글꼴 객체의 개수
     */
    public int getJapaneseFaceNameCount() {
        return japaneseFaceNameCount;
    }

    /**
     * 일본어 글꼴 객체의 개수를 설정한다.
     *
     * @param japaneseFaceNameCount 일본어 글꼴 객체의 개수
     */
    public void setJapaneseFaceNameCount(int japaneseFaceNameCount) {
        this.japaneseFaceNameCount = japaneseFaceNameCount;
    }

    /**
     * 기타 글꼴 객체의 개수를 반환한다.
     *
     * @return 기타 글꼴 객체의 개수
     */
    public int getEtcFaceNameCount() {
        return etcFaceNameCount;
    }

    /**
     * 기타 글꼴 객체의 개수를 설정한다.
     *
     * @param etcFaceNameCount 기타 글꼴 객체의 개수
     */
    public void setEtcFaceNameCount(int etcFaceNameCount) {
        this.etcFaceNameCount = etcFaceNameCount;
    }

    /**
     * 기호 글꼴 객체의 개수를 반환한다.
     *
     * @return 기호 글꼴 객체의 개수
     */
    public int getSymbolFaceNameCount() {
        return symbolFaceNameCount;
    }

    /**
     * 기호 글꼴 객체의 개수를 설정한다.
     *
     * @param symbolFaceNameCount 기호 글꼴 객체의 개수
     */
    public void setSymbolFaceNameCount(int symbolFaceNameCount) {
        this.symbolFaceNameCount = symbolFaceNameCount;
    }

    /**
     * 사용자 글꼴 객체의 개수를 반환한다.
     *
     * @return 사용자 글꼴 객체의 개수
     */
    public int getUserFaceNameCount() {
        return userFaceNameCount;
    }

    /**
     * 사용자 글꼴 객체의 개수를 설정한다.
     *
     * @param userFaceNameCount 사용자 글꼴 객체의 개수
     */
    public void setUserFaceNameCount(int userFaceNameCount) {
        this.userFaceNameCount = userFaceNameCount;
    }

    /**
     * 배경/테두리 객체의 개수를 반환한다.
     *
     * @return 배경/테두리 객체의 개수
     */
    public int getBorderFillCount() {
        return borderFillCount;
    }

    /**
     * 배경/테두리 객체의 개수를 설정한다.
     *
     * @param borderFillCount 배경/테두리 객체의 개수
     */
    public void setBorderFillCount(int borderFillCount) {
        this.borderFillCount = borderFillCount;
    }

    /**
     * 글자 모양 객체의 개수를 반환한다.
     *
     * @return 글자 모양 객체의 개수
     */
    public int getCharShapeCount() {
        return charShapeCount;
    }

    /**
     * 글자 모양 객체의 개수를 설정한다.
     *
     * @param charShapeCount 글자 모양 객체의 개수
     */
    public void setCharShapeCount(int charShapeCount) {
        this.charShapeCount = charShapeCount;
    }

    /**
     * 탭 정의 객체의 개수를 반환한다.
     *
     * @return 탭 정의 객체의 개수
     */
    public int getTabDefCount() {
        return tabDefCount;
    }

    /**
     * 탭 정의 객체의 개수를 설정한다.
     *
     * @param tabDefCount 탭 정의 객체의 개수
     */
    public void setTabDefCount(int tabDefCount) {
        this.tabDefCount = tabDefCount;
    }

    /**
     * 문단 번호 객체의 개수를 반환한다.
     *
     * @return 문단 번호 객체의 개수
     */
    public int getNumberingCount() {
        return numberingCount;
    }

    /**
     * 문단 번호 객체의 개수를 설정한다.
     *
     * @param numberingCount 문단 번호 객체의 개수
     */
    public void setNumberingCount(int numberingCount) {
        this.numberingCount = numberingCount;
    }

    /**
     * 글머리표 객체의 개수를 반환한다.
     *
     * @return 글머리표 객체의 개수
     */
    public int getBulletCount() {
        return bulletCount;
    }

    /**
     * 글머리표 객체의 개수를 설정한다.
     *
     * @param bulletCount 글머리표 객체의 개수
     */
    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    /**
     * 믄단 모양 객체의 개수를 반환한다.
     *
     * @return 믄단 모양 객체의 개수
     */
    public int getParaShapeCount() {
        return paraShapeCount;
    }

    /**
     * 믄단 모양 객체의 개수를 설정한다.
     *
     * @param paraShapeCount 믄단 모양 객체의 개수
     */
    public void setParaShapeCount(int paraShapeCount) {
        this.paraShapeCount = paraShapeCount;
    }

    /**
     * 스타일 객체의 개수를 반환한다.
     *
     * @return 스타일 객체의 개수
     */
    public int getStyleCount() {
        return styleCount;
    }

    /**
     * 스타일 객체의 개수를 설정한다.
     *
     * @param styleCount 스타일 객체의 개수
     */
    public void setStyleCount(int styleCount) {
        this.styleCount = styleCount;
    }

    /**
     * 메모 모양 객체의 개수를 반환한다. (5.0.2.1 이상)
     *
     * @return 메모 모양 객체의 개수
     */
    public int getMemoShapeCount() {
        return memoShapeCount;
    }

    /**
     * 메모 모양 객체의 개수를 설정한다. (5.0.2.1 이상)
     *
     * @param memoShapeCount 메모 모양 객체의 개수
     */
    public void setMemoShapeCount(int memoShapeCount) {
        this.memoShapeCount = memoShapeCount;
    }

    /**
     * 변경 추적 객체의 개수를 반환한다. (5.0.3.2 이상)
     *
     * @return 변경 추적 객체의 개수
     */
    public int getTrackChangeCount() {
        return trackChangeCount;
    }

    /**
     * 변경 추적 객체의 개수를 설정한다. (5.0.3.2 이상)
     *
     * @param trackChangeCount 변경 추적 객체의 개수
     */
    public void setTrackChangeCount(int trackChangeCount) {
        this.trackChangeCount = trackChangeCount;
    }

    /**
     * 변경추적 사용자 객체의 개수를 반환한다. (5.0.3.2 이상)
     *
     * @return 변경추적 사용자 객체의 개수
     */
    public int getTrackChangeAuthorCount() {
        return trackChangeAuthorCount;
    }

    /**
     * 변경추적 사용자 객체의 개수를 설정한다. (5.0.3.2 이상)
     *
     * @param trackChangeAuthorCount 변경추적 사용자 객체의 개수
     */
    public void setTrackChangeAuthorCount(int trackChangeAuthorCount) {
        this.trackChangeAuthorCount = trackChangeAuthorCount;
    }

    public void copy(IDMappings from) {
        binDataCount = from.binDataCount;
        hangulFaceNameCount = from.hangulFaceNameCount;
        englishFaceNameCount = from.englishFaceNameCount;
        hanjaFaceNameCount = from.hanjaFaceNameCount;
        japaneseFaceNameCount = from.japaneseFaceNameCount;
        etcFaceNameCount = from.etcFaceNameCount;
        symbolFaceNameCount = from.symbolFaceNameCount;
        userFaceNameCount = from.userFaceNameCount;
        borderFillCount = from.borderFillCount;
        charShapeCount = from.charShapeCount;
        tabDefCount = from.tabDefCount;
        numberingCount = from.numberingCount;
        bulletCount = from.bulletCount;
        paraShapeCount = from.paraShapeCount;
        styleCount = from.styleCount;
        memoShapeCount = from.memoShapeCount;
        trackChangeCount = from.trackChangeCount;
        trackChangeAuthorCount = from.trackChangeAuthorCount;
    }

    public void updateState(DocInfo docInfo) {
        this.binDataCount = docInfo.getBinDataList().size() ;
        this.hangulFaceNameCount = docInfo.getHangulFaceNameList().size() ;
        this.englishFaceNameCount = docInfo.getEnglishFaceNameList().size() ;
        this.hanjaFaceNameCount = docInfo.getHanjaFaceNameList().size();
        this.japaneseFaceNameCount = docInfo.getJapaneseFaceNameList().size();
        this.etcFaceNameCount = docInfo.getEtcFaceNameList().size();
        this.symbolFaceNameCount = docInfo.getSymbolFaceNameList().size();
        this.userFaceNameCount = docInfo.getUserFaceNameList().size();
        this.borderFillCount = docInfo.getBorderFillList().size();
        this.charShapeCount = docInfo.getCharShapeList().size();
        this.tabDefCount = docInfo.getTabDefList().size();
        this.numberingCount = docInfo.getNumberingList().size();
        this.bulletCount = docInfo.getBulletList().size();
        this.paraShapeCount = docInfo.getParaShapeList().size();
        this.styleCount = docInfo.getStyleList().size();
        this.memoShapeCount = docInfo.getMemoShapeList().size();
        this.trackChangeCount = docInfo.getTrackChange2List().size();
        this.trackChangeAuthorCount = docInfo.getTrackChangeAuthorList().size();
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_ID_MAPPINGS, getSize(sw.getFileVersion()));

        sw.writeSInt4(this.getBinDataCount()); // 0
        sw.writeSInt4(this.getHangulFaceNameCount()); // 1
        sw.writeSInt4(this.getEnglishFaceNameCount()); // 2
        sw.writeSInt4(this.getHanjaFaceNameCount()); // 3
        sw.writeSInt4(this.getJapaneseFaceNameCount()); // 4
        sw.writeSInt4(this.getEtcFaceNameCount()); // 5
        sw.writeSInt4(this.getSymbolFaceNameCount()); // 6
        sw.writeSInt4(this.getUserFaceNameCount()); // 7
        sw.writeSInt4(this.getBorderFillCount()); // 8
        sw.writeSInt4(this.getCharShapeCount()); // 9
        sw.writeSInt4(this.getTabDefCount()); // 10
        sw.writeSInt4(this.getNumberingCount()); // 11
        sw.writeSInt4(this.getBulletCount()); // 12
        sw.writeSInt4(this.getParaShapeCount()); // 13
        sw.writeSInt4(this.getStyleCount()); // 14
        if (sw.getFileVersion().isGreaterEqual(5, 0, 2, 1)) {
            sw.writeSInt4(this.getMemoShapeCount()); // 15
        }
        if (sw.getFileVersion().isGreaterEqual(5, 0, 3, 2)) {
            sw.writeSInt4(this.getTrackChangeCount()); // 16
            sw.writeSInt4(this.getTrackChangeAuthorCount()); // 17
        }
    }

    /**
     * 아이디 매핑 레코드의 크기를 반환한다.
     *
     * @param version 파일 버전
     * @return 아이디 매핑 레코드
     */
    public final int getSize(FileVersion version) {
        if (version.isGreaterEqual(5, 0, 3, 2)) {
            return 72;
        } else if (version.isGreaterEqual(5, 0, 2, 1)) {
            return 64;
        } else {
            return 60;
        }
    }
}
