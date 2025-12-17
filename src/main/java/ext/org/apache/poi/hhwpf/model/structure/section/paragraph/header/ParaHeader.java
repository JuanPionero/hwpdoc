package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.header;


import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPChar;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.ParaText;

import java.io.IOException;

/**
 * 문단 헤더 레코드
 *
 * @author neolord
 */
public class ParaHeader {
    /**
     * 문단 리스트에서 이 문단이 마지막 문단인지 여부
     */
    private boolean lastInList;
    /**
     * 텍스트가 가지고 있는 문자의 객수
     */
    private long characterCount;
    /**
     * control mask
     */
    private ControlMask controlMask;
    /**
     * 참조된 문단 모양 id
     * TODO rename to shapeId
     */
    private int paraShapeId;
    /**
     * 참조된 스타일 id
     */
    private short styleId;
    /**
     * 단 나누기 종류
     */
    private DivideSort divideSort;
    /**
     * 글자 모양 정보의 개수. ParaCharShape에 글자 위치-글자 모양 쌍의 개수
     */
    private int charShapeCount;
    /**
     * range tag 정보의 개수. ParaRangeTag의 영역 태그의 개수
     */
    private int rangeTagCount;
    /**
     * 각 줄에 대한 align에 대한 정보의 개수. ParaLineSeg의 정보의 객수
     */
    private int lineAlignCount;
    /**
     * 문단 Instance ID (unique ID)
     */
    private long instanceID;
    /**
     * 변경추적 병합 문단여부. (5.0.3.2 버전 이상)
     */
    private int isMergedByTrack;

    /**
     * 생성자
     */
    public ParaHeader() {
        controlMask = new ControlMask();
        divideSort = new DivideSort();
    }

    public ParaHeader(boolean lastInListValue,
                      int characterCountValue,
                      long controlMaskValue,
                      int paraShapeIdValue,
                      short styleIdValue,
                      short divideSortValue,
                      int charShapeCountValue,
                      int rangeTagCountValue,
                      int lineAlignCountValue,
                      int instanceIDValue,
                      int isMergedByTrackValue) {
        this.lastInList = lastInListValue;
        this.characterCount = characterCountValue;
        this.controlMask = new ControlMask(controlMaskValue);;
        this.paraShapeId = paraShapeIdValue;
        this.styleId = styleIdValue;
        this.divideSort = new DivideSort(divideSortValue);
        this.charShapeCount = charShapeCountValue;
        this.rangeTagCount = rangeTagCountValue;
        this.lineAlignCount = lineAlignCountValue;
        this.instanceID = instanceIDValue;
        this.isMergedByTrack = isMergedByTrackValue;
    }


    public ParaHeader(StreamReader sr) throws IOException {

        long value = sr.readUInt4();
        if ((value & 0x80000000L) == 0x80000000L) {
            this.lastInList = true;
            this.characterCount = value & 0x7fffffffL;
        } else {
            this.lastInList = false;
            this.characterCount = value;
        }
        this.controlMask = new ControlMask(sr.readUInt4());
        this.paraShapeId = sr.getCorrectedParaShapeId(sr.readUInt2());
        this.styleId = sr.readUInt1();
        this.divideSort = new DivideSort(sr.readUInt1());
        this.charShapeCount = sr.readUInt2();
        this.rangeTagCount = sr.readUInt2();
        this.lineAlignCount = sr.readUInt2();
        this.instanceID = sr.readUInt4();
        FileVersion fileVersion = sr.getFileHeader().getFileVersion();
        // v >= 5.0.3.2
        if(sr.isReadingDataRecordData() && fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 3, (short) 2)) {
            this.isMergedByTrack = sr.readUInt2();
        }

        if(sr.isReadingDataRecordData()) {
            sr.readRestOfDataRecordData();
        }
    }

    /**
     * 문단 리스트에서 이 문단이 마지막 문단인지 여부를 반환한다.
     *
     * @return 문단 리스트에서 이 문단이 마지막 문단인지 여부
     */
    public boolean isLastInList() {
        return lastInList;
    }

    /**
     * 문단 리스트에서 이 문단이 마지막 문단인지 여부를 설정한다.
     *
     * @param lastInList 문단 리스트에서 이 문단이 마지막 문단인지 여부
     */
    public void setLastInList(boolean lastInList) {
        this.lastInList = lastInList;
    }

    /**
     * 텍스트가 가지고 있는 문자의 객수를 반환한다.
     *
     * @return 텍스트가 가지고 있는 문자의 객수
     */
    public long getCharacterCount() {
        return characterCount;
    }

    /**
     * 텍스트가 가지고 있는 문자의 객수를 설정한다.
     *
     * @param characterCount 텍스트가 가지고 있는 문자의 객수
     */
    public void setCharacterCount(long characterCount) {
        this.characterCount = characterCount;
    }

    /**
     * control mask 객체를 반환한다.
     *
     * @return control mask 객체
     */
    public ControlMask getControlMask() {
        return controlMask;
    }

    /**
     * 참조된 문단 모양 객체의 id릎 반환한다.
     * TODO rename to getShapeId
     * @return 참조된 문단 모양 객체의 id
     */
    public int getParaShapeId() {
        return paraShapeId;
    }

    /**
     * 참조된 문단 모양 객체의 id를 설정한다.
     * TODO rename to setShapeId
     * @param paraShapeId 참조된 문단 모양 객체의 id
     */
    public void setParaShapeId(int paraShapeId) {
        this.paraShapeId = paraShapeId;
    }

    /**
     * 참조된 스타일 객체의 Id를 반환한다.
     *
     * @return 참조된 스타일 객체의 Id
     */
    public short getStyleId() {
        return styleId;
    }

    /**
     * 참조된 스타일 객체의 Id를 설정한다.
     *
     * @param styleId 참조된 스타일 객체의 Id
     */
    public void setStyleId(short styleId) {
        this.styleId = styleId;
    }

    /**
     * 단 나누기 종류 객체를 반환한다.
     * 구역, 다단, 쪽, 단 중 한가지의 방법의 나누기
     * @return 단 나누기 종류 객체
     */
    public DivideSort getDivideSort() {
        return divideSort;
    }

    /**
     * 글자 모양 정보의 개수를 반환한다.
     *
     * @return 글자 모양 정보의 개수
     */
    public int getCharShapeCount() {
        return charShapeCount;
    }

    /**
     * 글자 모양 정보의 개수를 설정한다.
     *
     * @param charShapeCount 글자 모양 정보의 개수
     */
    public void setCharShapeCount(int charShapeCount) {
        this.charShapeCount = charShapeCount;
    }

    /**
     * range tag 정보의 개수를 반환한다.
     *
     * @return range tag 정보의 개수
     */
    public int getRangeTagCount() {
        return rangeTagCount;
    }

    /**
     * range tag 정보의 개수를 설정한다.
     *
     * @param rangeTagCount range tag 정보의 개수
     */
    public void setRangeTagCount(int rangeTagCount) {
        this.rangeTagCount = rangeTagCount;
    }

    /**
     * 각 줄에 대한 align에 대한 정보의 개수를 반환한다.
     *
     * @return 각 줄에 대한 align에 대한 정보의 개수
     */
    public int getLineAlignCount() {
        return lineAlignCount;
    }

    /**
     * 각 줄에 대한 align에 대한 정보의 개수를 설정한다.
     *
     * @param lineAlignCount 각 줄에 대한 align에 대한 정보의 개수.
     */
    public void setLineAlignCount(int lineAlignCount) {
        this.lineAlignCount = lineAlignCount;
    }

    /**
     * 문단의 instance id를 반환한다.
     *
     * @return 문단의 instance id
     */
    public long getInstanceID() {
        return instanceID;
    }

    /**
     * 문단의 instance id를 설정한다.
     *
     * @param instanceID 문단의 instance id
     */
    public void setInstanceID(long instanceID) {
        this.instanceID = instanceID;
    }

    /**
     * 변경추적 병합 문단여부를 반환한다. (5.0.3.2 버전 이상)
     *
     * @return 변경추적 병합 문단여부
     */
    public int getIsMergedByTrack() {
        return isMergedByTrack;
    }

    /**
     * 변경추적 병합 문단여부를 설정한다.
     *
     * @param isMergedByTrack 변경추적 병합 문단여부
     */
    public void setIsMergedByTrack(int isMergedByTrack) {
        this.isMergedByTrack = isMergedByTrack;
    }

    public void copy(ParaHeader from) {
        lastInList = from.lastInList;
        characterCount = from.characterCount;
        controlMask.copy(from.controlMask);
        paraShapeId = from.paraShapeId;
        styleId = from.styleId;
        divideSort.copy(from.divideSort);
        charShapeCount = from.charShapeCount;
        rangeTagCount = from.rangeTagCount;
        lineAlignCount = from.lineAlignCount;
        instanceID = from.instanceID;
        isMergedByTrack = from.isMergedByTrack;
    }

    public void updateState(boolean isLastParaphInList, Paragraph p) {
        ParaText paraText = p.getParaText();
        this.lastInList = isLastParaphInList;
        this.characterCount = paraText == null ? 1 : paraText.getCharList().stream().map(ch-> ch.getCharSize())
                .reduce(0, (a, b)-> a+ b);
        // ForParagraph.setControlMask 참조
        this.controlMask.setValue(0);
        if(paraText!=null) {
            for (HWPChar ch : paraText.getCharList()) {
                switch (ch.getCode()) {
                    case 2:
                        controlMask.setHasSectColDef(true);
                        break;
                    case 3:
                        controlMask.setHasFieldStart(true);
                        break;
                    case 4:
                        controlMask.setHasFieldEnd(true);
                        break;
                    case 8:
                        controlMask.setHasTitleMark(true);
                    case 9:
                        controlMask.setHasTab(true);
                        break;
                    case 10:
                        controlMask.setHasLineBreak(true);
                        break;
                    case 11:
                        controlMask.setHasGsoTable(true);
                        break;
                    case 15:
                        controlMask.setHasHiddenComment(true);
                        break;
                    case 16:
                        controlMask.setHasHeaderFooter(true);
                        break;
                    case 17:
                        controlMask.setHasFootnoteEndnote(true);
                        break;
                    case 18:
                        controlMask.setHasAutoNumber(true);
                        break;
                    case 21:
                        controlMask.setHasPageControl(true);
                        break;
                    case 22:
                        controlMask.setHasBookmark(true);
                        break;
                    case 23:
                        controlMask.setHasAdditionalTextOverlappingLetter(true);
                        break;
                    case 24:
                        controlMask.setHasHyphen(true);
                        break;
                    case 30:
                        controlMask.setHasBundleBlank(true);
                        break;
                    case 31:
                        controlMask.setHasFixWidthBlank(true);
                        break;
                }
            }
        }

        this.charShapeCount = p.getParaCharShape() != null ? p.getParaCharShape().getPositonShapeIdPairList().size() : 0;
        this.rangeTagCount = p.getParaRangeTag() != null ? p.getParaRangeTag().getRangeTagItemList().size() : 0;
        this.lineAlignCount = p.getParaLineSeg() != null ? p.getParaLineSeg().getLineSegItemList().size() : 0;
        this.instanceID = 0;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PARA_HEADER, getSize(sw.getFileVersion()));

        lastInList_TextCount(sw);
        sw.writeUInt4(this.controlMask.getValue());
        sw.writeUInt2(sw.getCorrectedParaShapeId(this.paraShapeId));
        sw.writeUInt1(this.styleId);
        sw.writeUInt1(this.divideSort.getValue());
        sw.writeUInt2(this.charShapeCount);
        sw.writeUInt2(this.rangeTagCount);
        sw.writeUInt2(this.lineAlignCount);
        sw.writeUInt4(this.instanceID);
        if (sw.getFileVersion().isGreaterEqual(5, 0, 3, 2)) {
            sw.writeUInt2(this.isMergedByTrack);
        }
    }

    /**
     * 문단 헤더 레코드의 크기를 반환한다.
     *
     * @param version 파일 버전
     * @return 문단 헤더 레코드의 크기
     */
    public final int getSize(FileVersion version) {
        int size = 0;
        size += 22;
        if (version.isGreaterEqual(5, 0, 3, 2)) {
            size += 2;
        }
        return size;
    }

    /**
     * 문단 리스트에서 마지막 문단여부와 문자수를 쓴다.
     *
     * @param sw 스트림 라이터
     * @throws IOException
     */
    private void lastInList_TextCount(StreamWriter sw)
            throws IOException {
        long value = 0;
        if (this.isLastInList()) {
            value += 0x80000000;
        }
        value += this.getCharacterCount() & 0x7fffffff;
        sw.writeUInt4(value);
    }
}
