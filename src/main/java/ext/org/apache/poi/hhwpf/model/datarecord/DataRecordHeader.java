package ext.org.apache.poi.hhwpf.model.datarecord;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.util.binary.BitFlag;

import java.io.IOException;

public class DataRecordHeader {
    /**
     * 테그 아이디 - 레코드의 종류
     */
    private short tagID;
    /**
     * 레벨 - 트리구조에서 항목의 레벨
     */
    private short level;
    /**
     * 크기
     */
    private long size;

    public DataRecordHeader(StreamReader sr) throws IOException {
        long value = sr.readUInt4();
        this.tagID = (short) BitFlag.get(value, 0, 9);
        this.level= (short) BitFlag.get(value, 10, 19);
        this.size = (short) BitFlag.get(value, 20, 31);
        if (this.size == 4095) {
            this.size = sr.readUInt4();
        }
    }

    public DataRecordHeader(short tagID, short level, long size) {
        this.tagID = tagID;
        this.level = level;
        this.size = size;
    }

    public short getTagID() {
        return tagID;
    }

    public void setTagID(short tagID) {
        this.tagID = tagID;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     * 새로운 레코드 헤더 객체를 생성하고 값을 복사한다.
     *
     * @return 새로 생성된 레코드 헤더 객체
     */
    public DataRecordHeader clone() {
        DataRecordHeader rh = new DataRecordHeader(this.tagID, this.level, this.size);
        return rh;
    }

    @Override
    public String toString() {
        return "DataRecordHeader{" +
                "tagID=" + tagID +
                " [HWPTAG_BEGIN+" + (tagID-16) +
                "] , level=" + level +
                ", size=" + size +
                '}';
    }
}
