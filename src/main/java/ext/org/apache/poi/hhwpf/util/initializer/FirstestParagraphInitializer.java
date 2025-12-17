package ext.org.apache.poi.hhwpf.util.initializer;

import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderThickness;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderType;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.EachBorder;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlColumnDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ControlSectionDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderColumnDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderSectionDefine;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.charshape.ParaCharShape;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.header.ParaHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.lineseg.LineSegItem;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.lineseg.ParaLineSeg;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.ParaText;

public class FirstestParagraphInitializer implements Initializer<Paragraph> {
    @Override
    public void init(Paragraph target) throws Exception {
        target.setParaHeader( new ParaHeader(true, 17, 4L, 3,
                (short) 0, (short) 3, 1, 0, 1, 0 , 0));
        ParaText paraText = target.getParaText(); // = new ParaText();
        paraText.addExtendCharForSectionDefine();
        paraText.addExtendCharForColumnDefine();

        ParaCharShape paraCharShape = target.getParaCharShape();
        paraCharShape.addCharShape(0, 0);

        ParaLineSeg paraLineSeg = target.getParaLineSeg();
        paraLineSeg.addLineSegItem(new LineSegItem(0,0,1000,1000,850,600,0,43520,39216));


        CtrlHeaderSectionDefine ctrlHeaderSectionDefine = new CtrlHeaderSectionDefine(0,1134,0,0,8000,1,0,0,0,0,0);
        target.getControlList().add(  new ControlSectionDefine(ctrlHeaderSectionDefine) );

        CtrlHeaderColumnDefine ctrlHeaderColumnDefine = new CtrlHeaderColumnDefine(4100, 0, 0, new EachBorder(BorderType.None, BorderThickness.MM0_1, new Color4Byte(0)));
        target.getControlList().add( new ControlColumnDefine(ctrlHeaderColumnDefine) );
    }

}
