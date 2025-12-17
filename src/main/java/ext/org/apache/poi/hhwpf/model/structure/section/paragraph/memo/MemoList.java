package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.memo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

/**
 * 메모 리스트 레코드
 *
 * @author neolord
 */
public class MemoList implements StreamWritable {
    /**
     * 메모 인덱스
     */
    private long memoIndex;

    /**
     * 생성자
     */
    public MemoList() {
        memoIndex = 0;
    }

    public MemoList(StreamReader sr) throws IOException {
        this.memoIndex = sr.readUInt4();
    }

    /**
     * 메모 인덱스를 반환한다.
     *
     * @return 메모 인덱스
     */
    public long getMemoIndex() {
        return memoIndex;
    }

    /**
     * 메모 인덱스를 설정한다.
     *
     * @param memoIndex 메모 인덱스
     */
    public void setMemoIndex(long memoIndex) {
        this.memoIndex = memoIndex;
    }

    public void copy(MemoList from) {
        memoIndex = from.memoIndex;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
    }
}
