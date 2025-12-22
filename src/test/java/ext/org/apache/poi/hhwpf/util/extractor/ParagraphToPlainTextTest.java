package ext.org.apache.poi.hhwpf.util.extractor;

import ext.org.apache.poi.hhwpf.HHWPFDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ParagraphToPlainTextTest {

    static HHWPFDocument hwpDoc;
    static String textSampleHwpName = "text-sample.hwp";

    @BeforeAll
    static void setUp() {
        // Obtain system resource URI
        try {
//            URI resourceURI = ClassLoader.getSystemResource(textSampleHwpName).toURI();
//            Path resourcePath = Paths.get(resourceURI);
            InputStream inputStream = ClassLoader.getSystemResourceAsStream(textSampleHwpName);
//            inputStream = new FileInputStream(resourcePath.toString());
//            inputStream = ClassLoader.getSystemResourceAsStream(textSampleHwpName);
//            inputStream = new ByteArrayInputStream( Files.readAllBytes(resourcePath) );
             hwpDoc = new HHWPFDocument(inputStream);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void extractPlainTextFromAllParagraphsTest() {
        ParagraphToPlainText extractor = new ParagraphToPlainText(hwpDoc);
        extractor.setMethod(TextExtractMethod.InsertControlTextBetweenParagraphText);
        StringBuffer sb = new StringBuffer();
        try {
            extractor.extract(sb);
            assertTrue(sb.toString().contains("이것은 추가된 문자열입니다."),
                "This Hwp format file must contains "+"'이것은 추가된 문자열입니다.'");
        } catch (UnsupportedEncodingException e) {
            assertTrue(false, "Could not extract plain text from the HWP file");
        }
    }
}