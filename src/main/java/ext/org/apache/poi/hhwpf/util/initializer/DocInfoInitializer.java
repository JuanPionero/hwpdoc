package ext.org.apache.poi.hhwpf.util.initializer;

import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.datarecord.*;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.FillInfo;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.FillType;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.PatternFill;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.PatternType;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;

import java.util.ArrayList;
import java.util.List;

public class DocInfoInitializer implements Initializer<DocInfo> {
    @Override
    public void init(DocInfo target) throws Exception {
        this.initFaceNameLists(List.of(
                target.getHangulFaceNameList(),
                target.getEnglishFaceNameList(), target.getHanjaFaceNameList(),
                target.getJapaneseFaceNameList(), target.getEtcFaceNameList(),
                target.getSymbolFaceNameList(), target.getUserFaceNameList()));
        this.initBorderFillLists(target.getBorderFillList());
        this.initCharShapeLists(target.getCharShapeList());
        this.initTabDefLists(target.getTabDefList());
        this.initNumberingLists(target.getNumberingList());
        this.initParaShapeLists(target.getParaShapeList());
        this.initStyleLists(target.getStyleList());
    }

    protected void initFaceNameLists(List<ArrayList<FaceName>> faceNameList) {
        faceNameList.forEach(list -> {
            list.add(new FaceName("함초롬돋움", "HCR Dotum"));
            list.add(new FaceName("함초롬바탕", "HCR Batang"));
        });
    }

    protected void initBorderFillLists(List<BorderFill> borderFillList) {

        // BorderFillAdder.borderFill1
        borderFillList.add(new BorderFill());

        // BorderFillAdder.borderFill2
        FillType fillType = new FillType();
        fillType.setPatternFill(true);
        PatternFill patternFill = new PatternFill(new Color4Byte(-1), new Color4Byte(-16777216), PatternType.None);
        FillInfo fillInfo = new FillInfo(fillType, patternFill);
        borderFillList.add(new BorderFill(fillInfo));
    }

    protected void initCharShapeLists(List<CharShape> charShapeList) {
        charShapeList.add(new CharShape(1, (short) 100, (byte) 0, (short) 100, (byte) 0,
                1000, 0L, (byte) 10, (byte) 10,
                0L,0L,-1L,11711154L,
                2,0));

        charShapeList.add(new CharShape(0, (short) 100, (byte) 0, (short) 100, (byte) 0,
                1000, 0L, (byte) 10, (byte) 10,
                0L,0L,-1L,11711154L,
                2,0));

        charShapeList.add(new CharShape(0, (short) 100, (byte) 0, (short) 100, (byte) 0,
                900, 0L, (byte) 10, (byte) 10,
                0L,0L,-1L,11711154L,
                2,0));

        charShapeList.add(new CharShape(1, (short) 100, (byte) 0, (short) 100, (byte) 0,
                900, 0L, (byte) 10, (byte) 10,
                0L,0L,-1L,11711154L,
                2,0));

        charShapeList.add(new CharShape(0, (short) 100, (byte) -5, (short) 100, (byte) 0,
                900, 0L, (byte) 10, (byte) 10,
                0L,0L,-1L,11711154L,
                2,0));
    }

    protected void initTabDefLists(List<TabDef> tabDefList) {
        tabDefList.add(new TabDef(0));
        tabDefList.add(new TabDef(1));
    }

    /**
     * 기본으로 Numbering객은 한개만 생성. 이후 버전에서 추가를 하게되면, 생성 함수도 이에 대응하여 변견되어야 함.
     */
    protected void initNumberingLists(List<Numbering> numberingList) {
        numberingList.add(new Numbering());
    }

    protected void initParaShapeLists(List<ParaShape> paraShapeList) {
        paraShapeList.add(new ParaShape(196L, 0,0,0,
                0,0,130,0,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 130L));

        paraShapeList.add(new ParaShape(384L, 0,0,-2620,
                0,0,130,0,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 130L));

        paraShapeList.add(new ParaShape(256L, 0,0,0,
                0,0,150,0,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 150L));

        paraShapeList.add(new ParaShape(384L, 0,0,0,
                0,0,160,0,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));

        paraShapeList.add(new ParaShape(209725824L, 14000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
        // 6th
        paraShapeList.add(new ParaShape(176171392L, 12000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
        paraShapeList.add(new ParaShape(142616960L, 10000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
        paraShapeList.add(new ParaShape(109062528L, 8000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
        paraShapeList.add(new ParaShape(75508096L, 6000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
        paraShapeList.add(new ParaShape(41953664L, 4000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));

        // 11th
        paraShapeList.add(new ParaShape(8399232L, 2000,0,0,
                0,0,160,1,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
        paraShapeList.add(new ParaShape(384L, 3000,0,0,
                0,0,160,0,0,
                2, (short) 0,(short)0,(short)0,(short)0,
                0,0, 160L));
    }

    protected void initStyleLists(List<Style> styleList) {
        // 1st
        styleList.add(new Style("바탕글", "Normal", (short) 0, (short) 0, (short) 1042, 3, 0));
        styleList.add(new Style("본문", "Body", (short) 0, (short) 1, (short) 1042, 11, 0));
        styleList.add(new Style("개요 1", "Outline 1", (short) 0, (short) 2, (short) 1042, 10, 0));
        styleList.add(new Style("개요 2", "Outline 2", (short) 0, (short) 3, (short) 1042, 9, 0));
        styleList.add(new Style("개요 3", "Outline 3", (short) 0, (short) 4, (short) 1042, 8, 0));
        // 6th
        styleList.add(new Style("개요 4", "Outline 4", (short) 0, (short) 5, (short) 1042, 7, 0));
        styleList.add(new Style("개요 5", "Outline 5", (short) 0, (short) 6, (short) 1042, 6, 0));
        styleList.add(new Style("개요 6", "Outline 6", (short) 0, (short) 7, (short) 1042, 5, 0));
        styleList.add(new Style("개요 7", "Outline 7", (short) 0, (short) 8, (short) 1042, 4, 0));
        styleList.add(new Style("쪽 번호", "Page Number", (short) 0, (short) 9, (short) 1042, 3, 1));
        // 11th
        styleList.add(new Style("머리말", "Header", (short) 0, (short) 10, (short) 1042, 2, 2));
        styleList.add(new Style("각주", "Footnote", (short) 0, (short) 11, (short) 1042, 1, 3));
        styleList.add(new Style("미주", "Endnote", (short) 0, (short) 12, (short) 1042, 1, 3));
        styleList.add(new Style("메모", "Memo", (short) 0, (short) 13, (short) 1042, 0, 4));

    }
}
