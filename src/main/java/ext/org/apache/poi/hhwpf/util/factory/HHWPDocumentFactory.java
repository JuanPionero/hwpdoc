package ext.org.apache.poi.hhwpf.util.factory;

import ext.org.apache.poi.hhwpf.HHWPFDocument;
import ext.org.apache.poi.hhwpf.util.initializer.DocInfoInitializer;
import ext.org.apache.poi.hhwpf.util.initializer.FirstestParagraphInitializer;

public class HHWPDocumentFactory {
    private HHWPDocumentFactory() {

    }

    public static HHWPFDocument create() throws Exception {
        return new HHWPFDocument(new DocInfoInitializer(), new FirstestParagraphInitializer());
    }
}
