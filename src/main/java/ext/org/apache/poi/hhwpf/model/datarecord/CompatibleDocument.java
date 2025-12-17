package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.compatibledocument.CompatibleDocumentSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 호환 문서에 대한 레코드
 *
 * @author neolord
 */
public class CompatibleDocument {
    private static final Logger logger = LoggerFactory.getLogger(CompatibleDocument.class);
    /**
     * 대상 프로그램의 종류
     */
    private CompatibleDocumentSort targetProgram;

    public CompatibleDocument() {
    }

    public CompatibleDocument(CompatibleDocumentSort targetProgram) {
        this.targetProgram = targetProgram;
    }

    public CompatibleDocument(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.targetProgram = CompatibleDocumentSort.valueOf((byte) sr.readUInt4());
    }

    /**
     * 대상 프로그램의 종류를 반환한다.
     *
     * @return 대상 프로그램의 종류
     */
    public CompatibleDocumentSort getTargetProgram() {
        return targetProgram;
    }

    /**
     * 대상 프로그램의 종류를 설정한다.
     *
     * @param targetProgram 대상 프로그램의 종류
     */
    public void setTargetProgram(CompatibleDocumentSort targetProgram) {
        this.targetProgram = targetProgram;
    }

    public CompatibleDocument clone() {
        CompatibleDocument cloned = new CompatibleDocument();
        cloned.targetProgram = targetProgram;
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_COMPATIBLE_DOCUMENT, 4);
        sw.writeUInt4(this.targetProgram.getValue());
    }
}
