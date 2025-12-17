package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.structure.CaretPosition;
import ext.org.apache.poi.hhwpf.model.datarecord.documentproperties.StartNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 문서 속성를 나타내는 레코드
 *
 * @author neolord
 */
public class DocumentProperties {
    private static final Logger logger = LoggerFactory.getLogger(DocumentProperties.class);
    /**
     * 구역 개수
     */
    private int sectionCount;
    /**
     * 문서 내 각종 시작번호에 대한 정보
     */
    private StartNumber startNumber;
    /**
     * 문서 내 캐럿의 위치 정보
     */
    private CaretPosition caretPosition;

    /**
     * 생성자
     */
    public DocumentProperties(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.sectionCount = sr.readUInt2();
        this.startNumber = new StartNumber(sr);
        this.caretPosition = new CaretPosition(sr);
    }

    public DocumentProperties() {
        this.sectionCount = 1;
        this.startNumber = new StartNumber();
        this.caretPosition = new CaretPosition();
    }

    /**
     * 구역 개수를 반환한다.
     *
     * @return 구역 개수
     */
    public int getSectionCount() {
        return sectionCount;
    }

    /**
     * 구역 개수를 설정한다.
     *
     * @param sectionCount 구역 개수
     */
    public void setSectionCount(int sectionCount) {
        this.sectionCount = sectionCount;
    }

    /**
     * 문서 내 각종 시작번호에 대한 정보 객체를 반환한다.
     *
     * @return 문서 내 각종 시작번호에 대한 정보 객체
     */
    public StartNumber getStartNumber() {
        return startNumber;
    }

    /**
     * 문서 내 캐럿의 위치 정보 객체를 반환한다.
     *
     * @return 문서 내 캐럿의 위치 정보 객체
     */
    public CaretPosition getCaretPosition() {
        return caretPosition;
    }


    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_DOCUMENT_PROPERTIES, 26);
        sw.writeUInt2(this.sectionCount);
        this.startNumber.write(sw);
        this.caretPosition.write(sw);
    }
}
