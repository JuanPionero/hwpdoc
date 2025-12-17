package ext.org.apache.poi.hhwpf.util.extractor;


import ext.org.apache.poi.hhwpf.HHWPFDocument;
import ext.org.apache.poi.hhwpf.model.Section;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPChar;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPCharNormal;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.ParaText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static ext.org.apache.poi.hhwpf.Specification.PARAGRAPH_CHAR_INDEX_HIGHEST;
import static ext.org.apache.poi.hhwpf.Specification.PARAGRAPH_CHAR_INDEX_LOWEST;
import static ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPCharType.ControlExtend;

abstract public class BaseExtractor {
    private static final Logger logger = LoggerFactory.getLogger(BaseExtractor.class);
    private HHWPFDocument doc;
    private TextExtractMethod method;
    /**
     * 평문에서 각종 콘트롤 문자를 적용할 것인가를 결정
     */
    private boolean withControlChar;
    /**
     * 줄바꿈에 LF를 삽입할 것인가를 결정
     */
    private boolean appendEndingLF;
    /**
     * 문단 머릿말?
     */
    private boolean insertParaHead;


    public BaseExtractor(HHWPFDocument doc) {
        this.doc = doc;
        this.method = TextExtractMethod.InsertControlTextBetweenParagraphText;
        this.withControlChar = false;
        this.appendEndingLF = true;
        this.insertParaHead = true;
    }

    public void extract(StringBuffer sb) throws UnsupportedEncodingException {
        for(Section section : this.doc.getBodyText().getSectionList()) {
            this.extract(sb,section, 0, PARAGRAPH_CHAR_INDEX_LOWEST, section.getParagraphCount()-1, PARAGRAPH_CHAR_INDEX_HIGHEST );
        }
    }

    public void extract(StringBuffer sb, int sectionIndex, int startParaIndex,
                        int startCharIndex,
                        int endParaIndex,
                        int endCharIndex) throws UnsupportedEncodingException {
        Section section = this.doc.getBodyText().getSectionList().get(sectionIndex);
        this.extract(sb, section, startParaIndex, startCharIndex, endParaIndex, endCharIndex);
    }

    public void extract(StringBuffer sb, Section section, int startParaIndex,
                        int startCharIndex,
                        int endParaIndex,
                        int endCharIndex) throws UnsupportedEncodingException {
        this.extract(sb, section.getParagraphList(), startParaIndex, startCharIndex, endParaIndex, endCharIndex);
    }

    public void extract(StringBuffer sb, ArrayList<Paragraph> paragraphList, int startParaIndex,
                        int startCharIndex,
                        int endParaIndex,
                        int endCharIndex) throws UnsupportedEncodingException {
        if(startParaIndex==endParaIndex) {
            this.extract(sb, paragraphList.get(startParaIndex));
        } else {
            this.extract(sb, paragraphList.get(startParaIndex), startCharIndex, PARAGRAPH_CHAR_INDEX_HIGHEST);
            for(int paraIndex = startParaIndex + 1; paraIndex < endParaIndex; paraIndex++) {
                this.extract(sb, paragraphList.get(paraIndex));
            }
            this.extract(sb, paragraphList.get(endParaIndex), PARAGRAPH_CHAR_INDEX_LOWEST, endCharIndex);
        }
    }

    public void extract(StringBuffer sb, Paragraph p) throws UnsupportedEncodingException {
        this.extract(sb, p, PARAGRAPH_CHAR_INDEX_LOWEST, PARAGRAPH_CHAR_INDEX_HIGHEST);
    }
    public void extract(StringBuffer sb, Paragraph p, int startIndex, int endIndex) throws UnsupportedEncodingException {
        logger.trace("ControlList={}", p.getControlList());
        ArrayList<Control> controlList = new ArrayList<>();
        ParaText paraText = p.getParaText();
        if(paraText !=null) {

            int controlIndex = 0;
            int charIndex = Math.max(Math.max(startIndex, PARAGRAPH_CHAR_INDEX_LOWEST), 0);
            int highestIndex = Math.min(Math.min(endIndex, PARAGRAPH_CHAR_INDEX_HIGHEST), paraText.getCharList().size()-1);
            for(int _prev=0;_prev< charIndex;_prev++) {
                HWPChar ch = paraText.getCharList().get(_prev);
                if (ch.getType() == ControlExtend) {
                    controlIndex ++;
                }
            }
            logger.trace("ControlIndex={}",controlIndex);
            logger.trace("highestCharacterIndex={}",highestIndex);
            logger.trace("charIndex={}",charIndex);
//            logger.trace("controlCount={}",p.getControlList().size());

            for(;charIndex < highestIndex; charIndex++) {
                HWPChar ch = paraText.getCharList().get(charIndex);
                logger.trace("HWPChar={}", ch);
                switch (ch.getType()) {
                    case Normal : this.processNormalChar((HWPCharNormal)ch, sb); break;
                    case ControlChar:
                    case ControlInline:
                        // 옵션을 통해 삽입해야 하는 경우를 구분해야 할 때도 있을 것임.
                        if(this.withControlChar) {
                            this.processControlChar(ch, sb);
                        }
                        break;
                    case ControlExtend:
                        if(this.method == TextExtractMethod.InsertControlTextBetweenParagraphText) {
                            sb.append("\n");
                            logger.trace("ControlExtend[{}]={}", controlIndex, p.getControlList().get(controlIndex) );
                            this.processControlExtendChar(p.getControlList().get(controlIndex), sb);
                        } else {
                            controlList.add(p.getControlList().get(controlIndex));
                        }
                        controlIndex ++;
                        break;
                }
            }
        }

        if(this.appendEndingLF) {
            sb.append("\n");
        }

        if(this.method == TextExtractMethod.AppendControlTextAfterParagraphText) {
            for (Control c : controlList) {
                this.processControlExtendChar(c, sb);
            }
        }
    }

    protected void parseParaText(StringBuffer sb, ParaText paraText) {

    }

    public TextExtractMethod getMethod() {
        return method;
    }

    public void setMethod(TextExtractMethod method) {
        this.method = method;
    }

    public boolean isWithControlChar() {
        return withControlChar;
    }

    public void setWithControlChar(boolean withControlChar) {
        this.withControlChar = withControlChar;
    }

    public boolean isAppendEndingLF() {
        return appendEndingLF;
    }

    public void setAppendEndingLF(boolean appendEndingLF) {
        this.appendEndingLF = appendEndingLF;
    }

    public boolean isInsertParaHead() {
        return insertParaHead;
    }

    public void setInsertParaHead(boolean insertParaHead) {
        this.insertParaHead = insertParaHead;
    }

    abstract protected void processNormalChar(HWPCharNormal ch, StringBuffer sb) throws UnsupportedEncodingException;

    abstract protected void processControlChar(HWPChar ch, StringBuffer sb);

    abstract protected void processControlExtendChar(Control c, StringBuffer sb) throws UnsupportedEncodingException;
}
