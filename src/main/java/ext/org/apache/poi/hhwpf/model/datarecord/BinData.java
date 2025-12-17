package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.datarecord.bindata.BinDataType;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.model.datarecord.bindata.BinDataProperty;
import ext.org.apache.poi.hhwpf.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 바이너리 데이터를 나타내는 레코드
 *
 * @author neolord
 */
public class BinData {
    private static final Logger logger = LoggerFactory.getLogger(BinData.class);
    /**
     * 속성
     */
    private BinDataProperty property;




    /**
     * Type이 "LINK"일 때, 연결 파일의 절대 경로
     */
    private String absolutePathForLink;
    /**
     * Type이 "LINK"일 때, 연결 파일의 상대 경로
     */
    private String relativePathForLink;
    /**
     * Type이 "EMBEDDING"이거나 "STORAGE"일 때, BINDATASTORAGE에 저장된 바이너리 데이터의 아이디
     */
    private int binDataID;
    /**
     * Type이 "EMBEDDING"일 때 extension("." 제외)
     */
    private String extensionForEmbedding;

    /**
     * 생성자
     */
    public BinData() {
        property = new BinDataProperty();
    }

    public BinData(StreamReader sr) throws IOException {
        logger.trace("Reading From Stream");
        this.property = new BinDataProperty(sr);
        if (this.getProperty().getType() == BinDataType.Link) {
            this.setAbsolutePathForLink(sr.readUTF16LEString());
            this.setRelativePathForLink(sr.readUTF16LEString());
        }
        if (this.getProperty().getType() == BinDataType.Embedding
                || this.getProperty().getType() == BinDataType.Storage) {
            this.setBinDataID(sr.readUInt2());
            this.setExtensionForEmbedding(sr.readUTF16LEString());
        }
    }

    /**
     * 바이너리 데이터의 속성 객체를 반환한다.
     *
     * @return 바이너리 데이터의 속성 객체
     */
    public BinDataProperty getProperty() {
        return property;
    }

    /**
     * Type이 "LINK"일 때, 연결 파일의 절대 경로를 반환한다.
     *
     * @return 연결 파일의 절대 경로
     */
    public String getAbsolutePathForLink() {
        return absolutePathForLink;
    }

    /**
     * Type이 "LINK"일 때, 연결 파일의 절대 경로를 설정한다.
     *
     * @param absolutePathForLink 연결 파일의 절대 경로
     */
    public void setAbsolutePathForLink(String absolutePathForLink) {
        this.absolutePathForLink = absolutePathForLink;
    }

    /**
     * Type이 "LINK"일 때, 연결 파일의 상대 경로를 반환한다.
     *
     * @return 연결 파일의 상대 경로
     */
    public String getRelativePathForLink() {
        return relativePathForLink;
    }

    /**
     * Type이 "LINK"일 때, 연결 파일의 상대 경로를 설정한다.
     *
     * @param relativePathForLink 연결 파일의 상대 경로
     */
    public void setRelativePathForLink(String relativePathForLink) {
        this.relativePathForLink = relativePathForLink;
    }

    /**
     * Type이 "EMBEDDING"이거나 "STORAGE"일 때, 바이너리 데이터의 아이디를 반환한다.
     *
     * @return 바이너리 데이터의 아이디
     */
    public int getBinDataID() {
        return binDataID;
    }

    /**
     * Type이 "EMBEDDING"이거나 "STORAGE"일 때, 바이너리 데이터의 아이디를 설정한다.
     *
     * @param binDataID 바이너리 데이터의 아이디
     */
    public void setBinDataID(int binDataID) {
        this.binDataID = binDataID;
    }

    /**
     * Type이 "EMBEDDING"일 때, 파일의 extension을 반환한다.
     *
     * @return 파일의 extension
     */
    public String getExtensionForEmbedding() {
        return extensionForEmbedding;
    }

    /**
     * Type이 "EMBEDDING"일 때, 파일의 extension을 설정한다.
     *
     * @param extensionForEmbedding 파일의 extension
     */
    public void setExtensionForEmbedding(String extensionForEmbedding) {
        this.extensionForEmbedding = extensionForEmbedding;
    }

    public BinData clone() {
        BinData cloned = new BinData();
        cloned.property.copy(property);
        cloned.absolutePathForLink = absolutePathForLink;
        cloned.relativePathForLink = relativePathForLink;
        cloned.binDataID = binDataID;
        cloned.extensionForEmbedding = extensionForEmbedding;
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_BIN_DATA, getSize());
        sw.writeUInt2(this.getProperty().getValue());
        if (this.getProperty().getType() == BinDataType.Link) {
            sw.writeUTF16LEString(this.getAbsolutePathForLink());
            sw.writeUTF16LEString(this.getRelativePathForLink());
        }
        if (this.getProperty().getType() == BinDataType.Embedding
                || this.getProperty().getType() == BinDataType.Storage) {
            sw.writeUInt2(this.getBinDataID());
            sw.writeUTF16LEString(this.getExtensionForEmbedding());
        }
    }

    public final int getSize() {
        int size = 0;
        size += 2;
        if (this.getProperty().getType() == BinDataType.Link) {
            size += StringUtil.getUTF16LEStringSize(this.getAbsolutePathForLink());
            size += StringUtil.getUTF16LEStringSize(this.getRelativePathForLink());
        }
        if (this.getProperty().getType() == BinDataType.Embedding
                || this.getProperty().getType() == BinDataType.Storage) {
            size += 2;
            size += StringUtil.getUTF16LEStringSize(this.getExtensionForEmbedding());
        }
        return size;
    }

    @Override
    public String toString() {
        return "BinData{" +
                "property=" + property.getValue() +
                ", absolutePathForLink='" + absolutePathForLink + '\'' +
                ", relativePathForLink='" + relativePathForLink + '\'' +
                ", binDataID=" + binDataID +
                ", extensionForEmbedding='" + extensionForEmbedding + '\'' +
                '}';
    }
}
