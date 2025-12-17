package ext.org.apache.poi.hhwpf.model.etc;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.datarecord.Style;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 알려지지 않은 레코드
 * TODO rename to DataRecordUnknownData
 *
 * @author neolord
 */
public class UnknownRecord {
    private static final Logger logger = LoggerFactory.getLogger(UnknownRecord.class);
    /**
     * 레코드 헤더
     */
    private DataRecordHeader header;
    /**
     * 레코드 데이터
     */
    private byte[] body;

    private long size;
    /**
     * 생성자
     * DataRecord 의 Data 부분을 보관하기 위한 구조
     */
    public UnknownRecord(StreamReader sr, long size) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.body = sr.readBytes((int)size);
        this.size = size;
    }

    /**
     * 생성자
     *
     * @param header 레코드 헤더
     */
    public UnknownRecord(DataRecordHeader header) {
        this.header = header.clone();
    }

    /**
     * 레코드 헤더를 반환한다.
     *
     * @return 레코드 헤더
     */
    public DataRecordHeader getHeader() {
        return header;
    }

    /**
     * 레코드 헤더를 설정한다.
     *
     * @param header 레코드 헤더
     */
    public void setHeader(DataRecordHeader header) {
        this.header = header.clone();
    }

    /**
     * 레코드 데이터를 반환한다.
     *
     * @return 레코드 데이터
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * 레코드 데이터를 설정한다.
     *
     * @param body 레코드 데이터
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

    public long getSize() {
        return this.size;
    }
    public void setSize(long size) {
        this.size = size;
    }

    public void write(short tagID, StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(tagID,this.getBody().length);
        sw.writeBytes(this.getBody());
    }

}
