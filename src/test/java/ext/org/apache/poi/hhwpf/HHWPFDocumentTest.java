package ext.org.apache.poi.hhwpf;

import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.storage.BodyText;
import ext.org.apache.poi.hhwpf.util.factory.HHWPDocumentFactory;
import ext.org.apache.poi.hpsf.HwpSummaryInformation;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class HHWPFDocumentTest {
    static HHWPFDocument hwpDoc;
    static Path tempFilePath = Paths.get("result", "blank_temp.hwp");

    @BeforeAll
    static void setUp() {
        try {
            hwpDoc = HHWPDocumentFactory.create();
            if(Files.exists(tempFilePath)) {
                Files.delete(tempFilePath);
            }
        } catch (Exception e) {
            // 정상적인 테스트 진행 불가 상황.
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void afterAll() {
        hwpDoc = null;
        if(Files.exists(tempFilePath)) {
            try {
                Files.delete(tempFilePath);
            } catch (IOException e) {
            }
        }
    }

    @Test
    void getDocInfo() {
        final DocInfo docInfo = hwpDoc.getDocInfo();
        assertNotNull(docInfo, "Blank HWP Doc must have its DocInfo block");
    }

    @Test
    void getBodyText() {
        final BodyText bodyText = hwpDoc.getBodyText();
        assertNotNull( bodyText , "Blank HWP Doc must have its BodyText block");
        assertTrue( null != bodyText.getSectionList() && bodyText.getSectionList().size() > 0,
                "Blank HWP Doc must have at least one text section");
        assertNotNull( bodyText.getSectionList().get(0).getParagraph(0) ,
                "Blank HWP Doc must have at least one text paragraph at the first section");
    }

    @Test
    void getHwpSummaryInformation() {
        final HwpSummaryInformation summaryInformation = hwpDoc.getHwpSummaryInformation();
        assertNotNull( summaryInformation, "Blank HWP Doc must have its Summary Information");
    }

    @Test
    void writeTo() {
        try {
            hwpDoc.writeTo(tempFilePath);
            assertTrue( Files.exists(tempFilePath), "Could not write HWP file");
        } catch (Exception e) {
            assertTrue(false, "Could not write HWP file");
        }
    }
}