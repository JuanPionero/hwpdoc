package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.memo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.ParagraphList;

import java.io.IOException;

public class Memo implements StreamWritable {
    /**
     * 메모 리스트 레코드
     */
    private MemoList memoList;
    /**
     * 메모를 위한 리스트 헤더 레코드
     */
    private ListHeaderForMemo listHeader;
    /**
     * 문단 리스트
     */
    private ParagraphList paragraphList;

    /**
     * 생성자
     */
    public Memo() {
        memoList = new MemoList();
        listHeader = new ListHeaderForMemo();
        paragraphList = new ParagraphList();
    }

    public Memo(StreamReader sr) throws Exception {
        this.memoList = new MemoList(sr);
        this.listHeader = new ListHeaderForMemo(sr);
        this.paragraphList = new ParagraphList(sr);
    }

    /**
     * 메모 리스트 레코드를 반환한다.
     *
     * @return 메모 리스트 레코드
     */
    public MemoList getMemoList() {
        return memoList;
    }

    /**
     * 메모를 위한 리스트 헤더 레코드를 반환한다.
     *
     * @return 메모를 위한 리스트 헤더 레코드
     */
    public ListHeaderForMemo getListHeader() {
        return listHeader;
    }

    /**
     * 문단 리스트를 반환한다.
     *
     * @return 문단 리스트
     */
    public ParagraphList getParagraphList() {
        return paragraphList;
    }

    public Memo clone() {
        Memo cloned = new Memo();
        cloned.memoList.copy(memoList);
        cloned.listHeader.copy(listHeader);
        cloned.paragraphList.copy(paragraphList);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getMemoList().write(sw);
        this.getListHeader().write(sw);
        this.getParagraphList().write(sw);
    }
}
