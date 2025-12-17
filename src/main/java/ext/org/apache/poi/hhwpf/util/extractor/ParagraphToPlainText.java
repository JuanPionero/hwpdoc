package ext.org.apache.poi.hhwpf.util.extractor;

import ext.org.apache.poi.hhwpf.HHWPFDocument;
import ext.org.apache.poi.hhwpf.model.structure.section.control.*;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.*;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.textbox.TextBox;
import ext.org.apache.poi.hhwpf.model.structure.section.control.table.Cell;
import ext.org.apache.poi.hhwpf.model.structure.section.control.table.Row;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPChar;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.HWPCharNormal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class ParagraphToPlainText extends BaseExtractor {
    private static final Logger logger = LoggerFactory.getLogger(ParagraphToPlainText.class);
    public ParagraphToPlainText(HHWPFDocument doc) {
        super(doc);
    }

    protected void processNormalChar(HWPCharNormal ch, StringBuffer sb) throws UnsupportedEncodingException {
        sb.append(ch.getCh());
    }

    protected void processControlChar(HWPChar ch, StringBuffer sb) {
        switch (ch.getCode()) {
            case 9:
                sb.append("\t");
                break;
            case 10:
                sb.append("\n");
                break;
            case 24:
                sb.append("_");
                break;
        }
    }

    protected void processControlExtendChar(Control control, StringBuffer sb) throws UnsupportedEncodingException {
        if (control.isField()) {
        } else {
            switch (control.getType()) {
                case Table:
                    table((ControlTable) control, sb);
                    break;
                case Gso:
                    gso((GsoControl) control,  sb);
                    break;
                case Equation:
                    equation((ControlEquation) control, sb);
                    break;
                case Header:
                    header((ControlHeader) control, sb);
                    break;
                case Footer:
                    footer((ControlFooter) control, sb);
                    break;
                case Footnote:
                    footnote((ControlFootnote) control, sb);
                    break;
                case Endnote:
                    endnote((ControlEndnote) control, sb);
                    break;
                case AdditionalText:
                    additionalText((ControlAdditionalText) control, sb);
                    break;
                case HiddenComment:
                    hiddenComment((ControlHiddenComment) control, sb);
                    break;
                case SectionDefine:
                    break;
                case ColumnDefine:
                    break;
                case AutoNumber:
                    break;
                case NewNumber:
                    break;
                case PageHide:
                    break;
                case PageOddEvenAdjust:
                    break;
                case PageNumberPosition:
                    break;
                case IndexMark:
                    break;
                case Bookmark:
                    break;
                case OverlappingLetter:
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 표 컨트롤에서 텍스트를 추출한다
     *
     * @param table         표 컨트롤
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void table(ControlTable table,
            StringBuffer sb) throws UnsupportedEncodingException {
        for (Row r : table.getRowList()) {
            for (Cell c : r.getCellList()) {
                for (Paragraph paragraph : c.getParagraphList()) {
                    this.extract(sb, paragraph);
                }
            }
        }
    }



    /**
     * 수식 컨트롤에서 텍스트를 추출한다
     *
     * @param equation 수식 컨트롤 객체
     * @param sb       추출된 텍스트를 저정할 StringBuffer 객체
     */
    private void equation(ControlEquation equation, StringBuffer sb) {
        logger.trace("eqEdit={}", equation.getEQEdit());
        sb.append(equation.getEQEdit().getScript().toUTF16LEString()).append("\n");
    }

    /**
     * 머리말 컨트롤에서 텍스트를 추출한다.
     *
     * @param header        머리말 컨트롤
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void header(ControlHeader header,
            StringBuffer sb) throws UnsupportedEncodingException {
        for(Paragraph paragraph : header.getParagraphList()) {
            this.extract(sb, paragraph);
        }
    }

    /**
     * 꼬리말 컨트롤에서 텍스트를 추출한다.
     *
     * @param footer        꼬리말 컨트롤
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void footer(ControlFooter footer,
            StringBuffer sb) throws UnsupportedEncodingException {
        for(Paragraph paragraph : footer.getParagraphList()) {
            this.extract(sb, paragraph);
        }
    }

    /**
     * 각주 컨트롤에서 텍스트를 추출한다.
     *
     * @param footnote      각주 컨트롤
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void footnote(ControlFootnote footnote,
            StringBuffer sb) throws UnsupportedEncodingException {
        for(Paragraph paragraph : footnote.getParagraphList()) {
            this.extract(sb, paragraph);
        }
    }

    /**
     * 미주 컨트롤에서 텍스트를 추출한다.
     *
     * @param endnote       미주 컨트롤
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void endnote(ControlEndnote endnote,
                                StringBuffer sb) throws UnsupportedEncodingException {
        for(Paragraph paragraph : endnote.getParagraphList()) {
            this.extract(sb, paragraph);
        }
    }

    /**
     * 덧말 컨트롤에서 텍스트를 추출한다.
     *
     * @param additionalText 덧말 컨트롤
     * @param sb             추출된 텍스트를 저정할 StringBuffer 객체
     */
    private static void additionalText(ControlAdditionalText additionalText,
                                       StringBuffer sb) {
        sb.append(additionalText.getHeader().getMainText().toUTF16LEString()).append("\n");
        sb.append(additionalText.getHeader().getSubText().toUTF16LEString()).append("\n");
    }

    /**
     * 숨은 설명 컨트롤에서 텍스트를 추출한다.
     *
     * @param hiddenComment 숨은 설명 컨트롤
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void hiddenComment(ControlHiddenComment hiddenComment,
                                      StringBuffer sb) throws UnsupportedEncodingException {
        for(Paragraph paragraph : hiddenComment.getParagraphList()) {
            this.extract(sb, paragraph);
        }
    }

    private void gso(GsoControl gc, StringBuffer sb) throws UnsupportedEncodingException {
        switch (gc.getGsoType()) {
            case Line:
                break;
            case Rectangle:
                rectangle((ControlRectangle) gc, sb);
                break;
            case Ellipse:
                ellipse((ControlEllipse) gc, sb);
                break;
            case Arc:
                arc((ControlArc) gc, sb);
                break;
            case Polygon:
                polygon((ControlPolygon) gc,  sb);
                break;
            case Curve:
                curve((ControlCurve) gc, sb);
                break;
            case Picture:
                break;
            case OLE:
                break;
            case Container:
                container((ControlContainer) gc, sb);
                break;
            default:
                break;
        }
    }

    /**
     * 사각형 개체에서 텍스트를 추출한다.
     *
     * @param rectangle     사각형 개체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void rectangle(ControlRectangle rectangle,
                           StringBuffer sb) throws UnsupportedEncodingException {
        textBox(rectangle.getTextBox(), sb);
    }

    /**
     * 글상자 객체에서 텍스트를 추출한다.
     *
     * @param textBox       글상자 객체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void textBox(TextBox textBox,
                         StringBuffer sb) throws UnsupportedEncodingException {
        if (textBox == null) {
            return;
        }
        for(Paragraph paragraph : textBox.getParagraphList()) {
            this.extract(sb, paragraph);
        }
    }

    /**
     * 타원 개체에서 텍스트를 추출한다.
     *
     * @param ellipse       타원 개체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void ellipse(ControlEllipse ellipse,
                         StringBuffer sb) throws UnsupportedEncodingException {
        textBox(ellipse.getTextBox(), sb);
    }

    /**
     * 호 개체에서 텍스트를 추출한다.
     *
     * @param arc           호 개체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void arc(ControlArc arc,
                     StringBuffer sb) throws UnsupportedEncodingException {
        textBox(arc.getTextBox(), sb);
    }

    /**
     * 다각형 개체에서 텍스트를 추출한다.
     *
     * @param polygon       다각형 개체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void polygon(ControlPolygon polygon,
                         StringBuffer sb) throws UnsupportedEncodingException {
        textBox(polygon.getTextBox(), sb);
    }

    /**
     * 곡선 개체에서 텍스트를 추출한다.
     *
     * @param curve         곡선 개체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void curve(ControlCurve curve,
                              StringBuffer sb) throws UnsupportedEncodingException {
        textBox(curve.getTextBox(), sb);
    }

    /**
     * 묶음 개체에서 텍스트를 추출한다.
     *
     * @param container     묶음 개체
     * @param sb            추출된 텍스트를 저정할 StringBuffer 객체
     * @throws UnsupportedEncodingException
     */
    private void container(ControlContainer container,
                                  StringBuffer sb) throws UnsupportedEncodingException {
        for (GsoControl child : container.getChildControlList()) {
            gso(child, sb);
        }
    }
}
