package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


/**
 * 문단의 텍스트 레코드
 *
 * @author neolord
 */
public class ParaText {
    private static final Logger logger = LoggerFactory.getLogger(ParaText.class);
    /**
     * 글자(Character) 리스트
     */
    private ArrayList<HWPChar> charList;

    /**
     * 생성자
     */
    public ParaText() {
        charList = new ArrayList<HWPChar>();
    }

    public ParaText(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
       //  long dataSize = dataRecordHeader.getSize();
        charList = new ArrayList<HWPChar>();
        // long readByteCount = 0;
        while(sr.isReadingDataRecordData()) {
            readHwpChar(sr);
        }
    }

    private void readHwpChar(StreamReader sr) throws IOException {
        int code = sr.readUInt2();
        switch(HWPChar.type(code)) {
            case Normal : charList.add( new HWPCharNormal(code) ); break;
            case ControlChar : charList.add( new HWPCharControlChar(code) ); break;
            case ControlExtend : charList.add( new HWPCharControlExtend(sr) ); break;
            case ControlInline : charList.add( new HWPCharControlInline(sr) ); break;
        }
    }

    /**
     * 새로운 [일반 Character]를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 [일반 Character]
     */
    public HWPCharNormal addNewNormalChar() {
        HWPCharNormal nc = new HWPCharNormal();
        charList.add(nc);
        return nc;
    }

    public HWPCharNormal insertNewNormalChar(int position) {
        HWPCharNormal nc = new HWPCharNormal();
        charList.add(position, nc);
        return nc;
    }

    public void appendNormalChar(int code) {
        this.charList.add( new HWPCharNormal(code) );
    }

    /**
     * 새로운 [문자 컨트롤 Character]를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 [문자 컨트롤 Character]
     */
    public HWPCharControlChar addNewCharControlChar() {
        HWPCharControlChar ccc = new HWPCharControlChar();
        charList.add(ccc);
        return ccc;
    }

    public void appendControlChar(int code) {
        this.charList.add( new HWPCharControlChar(code) );
    }

    /**
     * 추가로 인해 문장 내에 이미 등록된 글자들의 인덱스가 조정되면 안되는 점 꼭 기억해야 함.
     * 기존 코드에서는 뭔가를 추가할 때마다 chr(13)을 마지막으로 옮겼음. (이 방법이 사실 더 맞을 듯 하기도 함.)
     * \n = 10 : line feed
     * \r = 13 : carriage return  = 0x0d
     */
    public void arrangeParagraphEnd() {
        for (HWPChar ch : charList) {
            if (ch.getCode() == 0x0d/* para break */) {
                charList.remove(ch);
                break;
            }
        }
        this.charList.add( new HWPCharControlChar(0x0d) );
    }

    /**
     * 새로운 [인라인 컨트롤 Character]를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 [인라인 컨트롤 Character]
     */
    public HWPCharControlInline addNewInlineControlChar() {
        HWPCharControlInline icc = new HWPCharControlInline();
        charList.add(icc);
        return icc;
    }

    /**
     * 새로운 [확장 컨트롤 Character]를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 [확장 컨트롤 Character]
     */
    public HWPCharControlExtend addNewExtendControlChar() {
        HWPCharControlExtend ecc = new HWPCharControlExtend();
        charList.add(ecc);
        return ecc;
    }

    /**
     * 글자(Character) 리스트를 반환한다.
     *
     * @return 글자(Character) 리스트
     */
    public ArrayList<HWPChar> getCharList() {
        return charList;
    }

    /**
     * 확장 컨트롤 Character 순번에 해당하는 글자의 문단 내의 순번을 반환한다.
     *
     * @param extendCharIndex 확장 컨트롤 Character 순번
     * @return 확장 컨트롤 Character 순번에 해당하는 글자의 문단 내의 순번
     */
    public int getCharIndexFromExtendCharIndex(int extendCharIndex) {
        int extendCharIndex2 = 0;
        int count = charList.size();
        for (int index = 0; index < count; index++) {
            if (charList.get(index).getType() == HWPCharType.ControlExtend) {
                if (extendCharIndex == extendCharIndex2) {
                    return index;
                }
                extendCharIndex2++;
            }
        }
        return -1;
    }

    /**
     * startIndex 순번부터 코드가 charCode인 인라인 컨트롤 character의 순번을 반환한다.
     *
     * @param startIndex 검색을 시작할 순번
     * @param charCode   찾을 인라인 컨트롤 character의 코드
     * @return 인라인 컨트롤 character의 순번
     */
    public int getInlineCharIndex(int startIndex, short charCode) {
        int count = charList.size();
        for (int index = startIndex; index < count; index++) {
            HWPChar ch = charList.get(index);
            if (ch.getType() == HWPCharType.ControlInline
                    && ch.getCode() == charCode) {
                return index;
            }
        }
        return -1;
    }

    /**
     * startIndex 순번 부터 endIndex 순번 까지의 일반 Character의 문자열을 반환한다.
     *
     * @param startIndex 시작 순번
     * @param endIndex   끝 순번
     * @return startIndex 순번 부터 endIndex 순번 까지의 일반 Character의 문자열
     * @throws UnsupportedEncodingException
     */
    public String getNormalString(int startIndex, int endIndex) throws UnsupportedEncodingException {
        if (startIndex == endIndex) {
            return "";
        }
        if (startIndex > endIndex) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int index = startIndex; index <= endIndex; index++) {
            HWPChar ch = charList.get(index);
            if (ch.getType() == HWPCharType.Normal) {
                HWPCharNormal chn = (HWPCharNormal) ch;
                sb.append(chn.getCh());
            }
        }
        return sb.toString();
    }

    /**
     * startIndex 순번 부터 끝까지의 일반 Character의 문자열을 반환한다
     *
     * @param startIndex 시작 순번
     * @return startIndex 순번 부터 끝까지의 일반 Character의 일반 Character의 문자열
     * @throws UnsupportedEncodingException
     */
    public String getNormalString(int startIndex) throws UnsupportedEncodingException {
        return getNormalString(startIndex, charList.size() - 1);
    }

    /**
     * 문자열을 추가한다.
     *
     * @param str 추가할 문자열
     * @throws UnsupportedEncodingException
     */
    public void addString(String str) throws UnsupportedEncodingException {
        int len = str.length();
        for (int index = 0; index < len; index++) {
            HWPCharNormal ch = addNewNormalChar();
            ch.setCode((short) str.codePointAt(index));
        }
        processEndOfParagraph();
    }

    /**
     * TODO addString 대체할 것임.
     * @param str
     */
    public void appendString(String str) {
        int len = str.length();
        for (int index = 0; index < len; index++) {
            this.charList.add(new HWPCharNormal( str.codePointAt(index) ));
        }
    }

    /**
     * 문자열을 추가한다.
     *
     * @param str 추가할 문자열
     * @throws UnsupportedEncodingException
     */
    public int insertString(int position, String str) throws UnsupportedEncodingException {
        int oldCharSize = charSizeBaseLength();

        int len = str.length();
        for (int index = 0; index < len; index++) {
            HWPCharNormal ch = insertNewNormalChar(position + index);
            ch.setCode((short) str.codePointAt(index));
        }
        processEndOfParagraph();

        return charSizeBaseLength() - oldCharSize;
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     *
     * 구역 정의 컨트롤를 추가하기 위한 확장 컨트롤 문자를 추가한다.
     */
    @Deprecated
    public void addExtendCharForSectionDefine() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x0002);
        byte[] addition = new byte[12];
        addition[3] = 's';
        addition[2] = 'e';
        addition[1] = 'c';
        addition[0] = 'd';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    /**
     * 문단 끝을 나타내는 문자를 찾아서 마지막으로 보낸다.
     */
    private void processEndOfParagraph() {
        for (HWPChar ch : charList) {
            if (ch.getCode() == 0x0d/* para break */) {
                charList.remove(ch);
                break;
            }
        }

        HWPCharNormal ch2 = addNewNormalChar();
        ch2.setCode((short) 0x0d);
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     *
     * 단 정의 컨트롤를 추가하기 위한 확장 컨트롤 문자를 추가한다.
     */
    @Deprecated
    public void addExtendCharForColumnDefine() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x0002);
        byte[] addition = new byte[12];
        addition[3] = 'c';
        addition[2] = 'o';
        addition[1] = 'l';
        addition[0] = 'd';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     *
     * 표 컨트롤를 추가하기 위한 확장 컨트롤 문자를 추가한다.
     */
    @Deprecated
    public void addExtendCharForTable() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x000b);
        byte[] addition = new byte[12];
        addition[3] = 't';
        addition[2] = 'b';
        addition[1] = 'l';
        addition[0] = ' ';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    public void appendExtendCharForTable() {
        this.appendExtendChar(0x000b, "tbl ");
    }

    /**
     * 그리기 개체 컨트롤를 추가하기 위한 확장 컨트롤 문자를 추가한다.
     *
     * @Deprecated in favor of ControlExtendCharFactory
     */
    @Deprecated
    public void addExtendCharForGSO() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x000b);
        byte[] addition = new byte[12];
        addition[3] = 'g';
        addition[2] = 's';
        addition[1] = 'o';
        addition[0] = ' ';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     */
    @Deprecated
    public void appendExtendCharForGSO() {
        this.appendExtendChar(0x000b, "gso ");
    }

    /**
     *
     * @Deprecated in favor of ControlExtendCharFactory
     * @param code
     * @param str
     */
    @Deprecated
    public void appendExtendChar(int code, String str) {
        byte[] addition = new byte[12];
        addition[3] = (byte) str.codePointAt(0);
        addition[2] = (byte) str.codePointAt(1);
        addition[1] = (byte) str.codePointAt(2);
        addition[0] = (byte) str.codePointAt(3);
        HWPCharControlExtend ecc = new HWPCharControlExtend((short) code, addition);
        // logger.debug("is Equation? = {}",ecc.isEquation());
        charList.add(ecc);
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     * 필드의 시작
     *
     * 하이퍼 링크의 시작을 위한 확장 컨트롤 문자를 추가한다.
     * %hlk 는 ControlInline 일 가능성이 더 많음.
     */
    @Deprecated
    public void addExtendCharForHyperlinkStart() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x0003);
        byte[] addition = new byte[12];
        addition[3] = '%';
        addition[2] = 'h';
        addition[1] = 'l';
        addition[0] = 'k';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    /**
     * @Deprecated in favor of ControlInlineCharFactory
     * 필드 끝
     * 하이퍼 링크의 끝을 위한 확장 컨트롤 문자를 추가한다.
     */
    public void addExtendCharForHyperlinkEnd() {
        HWPCharControlInline chExtend = addNewInlineControlChar();
        chExtend.setCode((short) 0x0004);
        byte[] addition = new byte[12];
        addition[3] = '%';
        addition[2] = 'h';
        addition[1] = 'l';
        addition[0] = 'k';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     * 머리말을 위한 확장 컨트롤 문자를 추가한다.
     */
    @Deprecated
    public void addExtendCharForHeader() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x0010);
        byte[] addition = new byte[12];
        addition[3] = 'h';
        addition[2] = 'e';
        addition[1] = 'a';
        addition[0] = 'd';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        processEndOfParagraph();
    }

    /**
     * @Deprecated in favor of ControlExtendCharFactory
     * 머리말을 위한 확장 컨트롤 문자를 추가한다.
     */
    @Deprecated
    public void addExtendCharForFooter() {
        HWPCharControlExtend chExtend = addNewExtendControlChar();
        chExtend.setCode((short) 0x0010);
        byte[] addition = new byte[12];
        addition[3] = 'f';
        addition[2] = 'o';
        addition[1] = 'o';
        addition[0] = 't';
        try {
            chExtend.setAddition(addition);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        processEndOfParagraph();
    }

    public ParaText clone() {
        ParaText cloned = new ParaText();
        for (HWPChar hwpChar : charList) {
            cloned.charList.add(hwpChar.clone());
        }
        return cloned;
    }

    /**
     * Length of chars in wchar(2bytes)
     * legacy name is getCharSize
     * @return
     */
    public int charSizeBaseLength() {
        int length = 0;
        for (HWPChar hwpChar : charList) {
            length += hwpChar.getCharSize();
        }
        return length;
    }

    public void write(StreamWriter sw, int charBufferSize) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PARA_TEXT, charBufferSize);

        for (HWPChar hc : this.getCharList()) {
            // ForParaText.write 의 끝부분
            if(hc instanceof StreamWritable) {
                // getType 후 비교 HWPCharNormal, HWPCharControlChar, HWPCharControlInline, HWPCharControlExtend
                // 대신 아래코드 사용.
                ((StreamWritable) hc).write(sw);
            }
        }
    }


}
