package ext.org.apache.poi.hhwpf;

import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.storage.BodyText;
import ext.org.apache.poi.hhwpf.util.factory.HHWPDocumentFactory;
import ext.org.apache.poi.hpsf.HwpSummaryInformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HHWPFDocumentTest {
    HHWPFDocument hwpDoc;
    @BeforeEach
    void setUp() {
        try {
            hwpDoc = HHWPDocumentFactory.create();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getDocInfo() {
        DocInfo docInfo = hwpDoc.getDocInfo();
        assert null != docInfo : "Blank HWP Doc must have its DocInfo block";
    }

    @Test
    void getBodyText() {
        BodyText bodyText = hwpDoc.getBodyText();
        assert null != bodyText : "Blank HWP Doc must have its BodyText block";
        assert null != bodyText.getSectionList() && bodyText.getSectionList().size() > 0 :
                "Blank HWP Doc must have at least one text section";
        assert null != bodyText.getSectionList().get(0).getParagraph(0) :
                "Blank HWP Doc must have at least one text paragraph at the first section";
    }

    @Test
    void getHwpSummaryInformation() {
        HwpSummaryInformation summaryInformation = hwpDoc.getHwpSummaryInformation();
        assert null != summaryInformation : "Blank HWP Doc must have its Summary Information";
    }
}