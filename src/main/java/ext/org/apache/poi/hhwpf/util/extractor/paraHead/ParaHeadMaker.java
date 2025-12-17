package ext.org.apache.poi.hhwpf.util.extractor.paraHead;


import ext.org.apache.poi.hhwpf.HHWPFDocument;
import ext.org.apache.poi.hhwpf.model.Section;
import ext.org.apache.poi.hhwpf.model.datarecord.Bullet;
import ext.org.apache.poi.hhwpf.model.datarecord.Numbering;
import ext.org.apache.poi.hhwpf.model.datarecord.ParaShape;
import ext.org.apache.poi.hhwpf.model.datarecord.Style;
import ext.org.apache.poi.hhwpf.model.datarecord.numbering.LevelNumbering;
import ext.org.apache.poi.hhwpf.model.structure.section.control.Control;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlSectionDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlType;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.util.StringUtil;

public class ParaHeadMaker {
    private HHWPFDocument document;
    private ControlSectionDefine sectionDefine;
    private ParaNumber paraNumberForNumbering;
    private ParaNumber paraNumberForOutline;

    public ParaHeadMaker(HHWPFDocument document) {
        this.document = document;
        setSectionDefine(document.getBodyText().getSectionList().get(0));
        paraNumberForNumbering = new ParaNumber();
    }

    public void startSection(Section section) {
        setSectionDefine(section);
        paraNumberForOutline = new ParaNumber();
    }

    public void endSection() {
        paraNumberForOutline = null;
    }

    private void setSectionDefine(Section section) {
        if (section.getParagraphCount() > 0 && section.getParagraph(0).getControlList().size() > 0) {
            Control firstControl = section.getParagraph(0).getControlList().get(0);
            if (firstControl.getType() == ControlType.SectionDefine) {
                sectionDefine = (ControlSectionDefine) firstControl;
            } else {
                if (section.getParagraph(0).getControlList().size() >= 2) {
                    Control secondControl = section.getParagraph(0).getControlList().get(1);
                    if (secondControl.getType() == ControlType.SectionDefine) {
                        sectionDefine = (ControlSectionDefine) secondControl;
                    }
                }
            }
        }
    }

    public String paraHeadString(Paragraph paragraph) {
        ParaShape paraShape = document.getDocInfo().getParaShapeList().get(paragraph.getHeader().getParaShapeId());
        switch (paraShape.getProperty1().getParaHeadShape()) {
            case None:
                return "";
            case Outline:
                return outline(paragraph.getHeader().getStyleId(),
                        paraShape.getProperty1().getParaLevel());
            case Numbering:
                return numbering(paraShape.getParaHeadId(),
                        paraShape.getProperty1().getParaLevel());
            case Bullet:
                return bullet(paraShape.getParaHeadId(),
                        paraShape.getProperty1().getParaLevel());
        }
        return null;
    }

    private String outline(int styleID, byte paraLevel) {
        Style style = document.getDocInfo().getStyleList().get(styleID);
        ParaShape outlineParaShape = document.getDocInfo().getParaShapeList().get(style.getParaShapeId());

        Numbering numbering = document.getDocInfo().getNumberingList().get(outlineParaShape.getParaHeadId());
        LevelNumbering lv;
        try {
            lv = numbering.getLevelNumbering(paraLevel + 1);
        } catch (Exception e) {
            e.printStackTrace();
            lv = null;
        }

        if (lv != null) {
            if (paraNumberForOutline.changedParaHead(outlineParaShape.getParaHeadId())) {
                paraNumberForOutline.reset(outlineParaShape.getParaHeadId(), paraLevel, (int) lv.getStartNumber());
            } else {
                paraNumberForOutline.increase(paraLevel);
            }

            return numberText(lv, paraNumberForOutline, paraLevel);
        } else {
            return null;
        }
    }

    private String numbering(int paraHeadID, byte paraLevel) {
        Numbering numbering = document.getDocInfo().getNumberingList().get(paraHeadID - 1);

        LevelNumbering lv;
        try {
            lv = numbering.getLevelNumbering(paraLevel + 1);
        } catch (Exception e) {
            e.printStackTrace();
            lv = null;
        }

        if (lv != null) {
            if (paraNumberForNumbering.changedParaHead(paraHeadID)) {
                paraNumberForNumbering.reset(paraHeadID, paraLevel, (int) lv.getStartNumber());
            } else {
                paraNumberForNumbering.increase(paraLevel);
            }
            return numberText(lv, paraNumberForNumbering, paraLevel);
        } else {
            return null;
        }
    }

    private String numberText(LevelNumbering lv, ParaNumber paraNumber, int paraLevel) {
        String format = lv.getNumberFormat().toUTF16LEString();
        String[] tokens = new String[10];
        String[] values = new String[10];
        for (int level = 0; level <= paraLevel; level++) {
            tokens[level] = "^" + (level + 1);
            values[level] = ParaHeadNumber.toString(paraNumber.value(level),
                    lv.getParagraphHeadInfo().getProperty().getParagraphNumberFormat());
        }
        return StringUtil.replaceEach(format, tokens, values);
    }

    private String bullet(int paraHeadId, byte paraLevel) {
        if (paraHeadId > 0) {
            Bullet bullet = document.getDocInfo().getBulletList().get(paraHeadId - 1);
            return bullet.getBulletChar().toUTF16LEString();
        } else {
            return "●";
        }
    }
}
