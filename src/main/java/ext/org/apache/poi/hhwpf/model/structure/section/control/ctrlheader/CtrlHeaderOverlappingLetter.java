package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 글자 겸침 컨트롤을 위한 컨트롤 헤더 레코드
 *
 * @author neolord
 */
public class CtrlHeaderOverlappingLetter extends CtrlHeader implements StreamWritable {
    /**
     * 겹침 글자 리스트
     */
    private ArrayList<HWPString> overlappingLetterList;
    /**
     * 테두리 타입
     */
    private short borderType;
    /**
     * 내부 글자 크기
     */
    private byte internalFontSize;
    /**
     * 테두리 내부 글자 펼침
     */
    private short expendInsideLetter;
    /**
     * 테두리 내부 글자의 글자모양 id 리스트
     */
    private ArrayList<Long> charShapeIdList;

    /**
     * 생성자
     */
    public CtrlHeaderOverlappingLetter() {
        super(ControlType.OverlappingLetter.getCtrlId());

        overlappingLetterList = new ArrayList<HWPString>();
        charShapeIdList = new ArrayList<Long>();
    }
    public CtrlHeaderOverlappingLetter(StreamReader sr) throws IOException, IllegalAccessException {
        super(ControlType.OverlappingLetter.getCtrlId());

        this.overlappingLetterList = new ArrayList<HWPString>();
        int count = sr.readUInt2();
        for (int index = 0; index < count; index++) {
            overlappingLetterList.add(new HWPString(sr.readWChar()));
        }

        if(sr.isEndOfDataRecord()) {
            return;
        }

        this.borderType = sr.readUInt1();
        this.internalFontSize = sr.readSInt1();
        this.expendInsideLetter = sr.readUInt1();

        this.charShapeIdList = new ArrayList<Long>();
        short count2 = sr.readUInt1();
        for (short i = 0; i < count2; i++) {
            charShapeIdList.add( sr.readUInt4() );
        }
    }
    /**
     * 겹쳐지는 글자를 리스트에 추가한다.
     *
     * @param overlappingLetter 겹쳐지는 글자
     */
    public void addOverlappingLetter(HWPString overlappingLetter) {
        overlappingLetterList.add(overlappingLetter);
    }

    /**
     * 겹침 글자 리스트를 반환한다.
     *
     * @return 겹침 글자 리스트
     */
    public ArrayList<HWPString> getOverlappingLetterList() {
        return overlappingLetterList;
    }

    /**
     * 테두리 타입을 반환한다.
     *
     * @return 테두리 타입
     */
    public short getBorderType() {
        return borderType;
    }

    /**
     * 테두리 타입를 설정한다.
     *
     * @param borderType 테두리 타입
     */
    public void setBorderType(short borderType) {
        this.borderType = borderType;
    }

    /**
     * 내부 글자 크기를 반환한다.
     *
     * @return 내부 글자 크기
     */
    public byte getInternalFontSize() {
        return internalFontSize;
    }

    /**
     * 내부 글자 크기를 설정한다.
     *
     * @param internalFontSize 내부 글자 크기
     */
    public void setInternalFontSize(byte internalFontSize) {
        this.internalFontSize = internalFontSize;
    }

    /**
     * 테두리 내부 글자 펼침을 반환한다.
     *
     * @return 테두리 내부 글자 펼침
     */
    public short getExpendInsideLetter() {
        return expendInsideLetter;
    }

    /**
     * 테두리 내부 글자 펼침을 설정한다.
     *
     * @param expendInsideLetter 테두리 내부 글자 펼침
     */
    public void setExpendInsideLetter(short expendInsideLetter) {
        this.expendInsideLetter = expendInsideLetter;
    }

    /**
     * 테두리 내부 글자의 글자모양 id를 리스트에 추가한다.
     *
     * @param charShapeId 테두리 내부 글자의 글자모양 id
     */
    public void addCharShapeId(long charShapeId) {
        charShapeIdList.add(charShapeId);
    }

    /**
     * 테두리 내부 글자의 글자모양 id 리스트를 반환한다.
     *
     * @return 테두리 내부 글자의 글자모양 id 리스트
     */
    public ArrayList<Long> getCharShapeIdList() {
        return charShapeIdList;
    }

    @Override
    public void copy(CtrlHeader from) {
        CtrlHeaderOverlappingLetter from2 = (CtrlHeaderOverlappingLetter) from;
        overlappingLetterList.clear();
        for (HWPString str : from2.overlappingLetterList) {
            overlappingLetterList.add(str.clone());
        }

        borderType = from2.borderType;
        internalFontSize = from2.internalFontSize;
        expendInsideLetter = from2.expendInsideLetter;

        charShapeIdList.clear();
        for (Long lng : from2.charShapeIdList) {
            charShapeIdList.add(lng);
        }
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_CTRL_HEADER, this.getSize());
        sw.writeUInt4(this.getCtrlId());

//        overlappingLetters(h, sw);
        sw.writeUInt2(this.getOverlappingLetterList().size());
        for (HWPString letter : this.getOverlappingLetterList()) {
            sw.writeWChar(letter.getBytes());
        }

        sw.writeUInt1(this.getBorderType());
        sw.writeSInt1(this.getInternalFontSize());
        sw.writeUInt1(this.getExpendInsideLetter());

//        charShapeIds(h, sw);
        sw.writeUInt1((short) this.getCharShapeIdList().size());
        for (long charShapeId : this.getCharShapeIdList()) {
            sw.writeUInt4(charShapeId);
        }
    }

    /**
     * 컨트롤 헤더 레코드의 크기를 반환한다.
     *
     * @return 컨트롤 헤더 레코드의 크기
     */
    public int getSize() {
        int size = 0;
        size += 4;

        size += 2;
        size += this.getOverlappingLetterList().size() * 2;

        size += 3;

        size += 1;
        size += this.getCharShapeIdList().size() * 4;

        return size;
    }
}
