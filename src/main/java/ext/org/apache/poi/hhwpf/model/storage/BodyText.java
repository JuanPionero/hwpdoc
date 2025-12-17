package ext.org.apache.poi.hhwpf.model.storage;



import ext.org.apache.poi.hhwpf.POIDocumentLikeForHWP;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.FileHeader;
import ext.org.apache.poi.hhwpf.model.Section;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.memo.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.poifs.filesystem.DirectoryNode;

import java.io.InputStream;
import java.util.ArrayList;

import static ext.org.apache.poi.hhwpf.StreamID.POIFS_STREAM_SECTION;

/**
 * 본문를 나타나는 객체. HWP파일 내에 "BodyText" storage에 저장된다.
 *
 * @author neolord
 */

public class BodyText extends POIDocumentLikeForHWP {
    private static final Logger logger = LoggerFactory.getLogger(BodyText.class);
    /**
     * 문서 영역(섹션) 리스트
     */
    private ArrayList<Section> sectionList;

    /**
     * 메모 리스트
     */
    private ArrayList<Memo> memoList;

    /**
     * 생성자
     */
    public BodyText() {
        this.sectionList = new ArrayList<>();
    }

    public BodyText(DirectoryNode storage, FileHeader fileHeader, DocInfo docInfo) throws Exception {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.sectionList = new ArrayList<Section>();
        this.memoList = new ArrayList<Memo>();
        int sectionCount = docInfo.getDocumentProperties().getSectionCount();
        for (int index = 0; index < sectionCount; index++) {
            // 섹션마다 하나의 STREAM
            try (InputStream is = this.getDocumentStream(storage, POIFS_STREAM_SECTION + index,
                    fileHeader.isCompressed())) {
                sectionList.add(new Section(is, fileHeader, docInfo, this, index==sectionCount-1));

            }
        }

//        for (int index = 0; index < sectionCount; index++) {
//            sectionList.add(new Section(sr, index));
////            section(index);
//        }
    }

//    /**
//     * 새로운 문서 영역(섹션)을 생성하고 리스트에 추가한다.
//     *
//     * @return 새로 생성된 문서 영역(섹션)
//     */
//    public Section addNewSection() {
//        Section s = new Section();
//        sectionList.add(s);
//        return s;
//    }
    public void appendSection(Section section) {
        this.sectionList.add(section);
    }

    public void insertSection(int index, Section section) {
        this.sectionList.add(index, section);
    }

    /**
     * 문서 영역(섹션) 리스트를 반환한다.
     *
     * @return 문서 영역(섹션) 리스트
     */
    public ArrayList<Section> getSectionList() {
        return sectionList;
    }

    public Section getLastSection() {
        if (sectionList.size() == 0) {
            return null;
        }
        return sectionList.get(sectionList.size() - 1);
    }

    /**
     * 새로운 메모을 생성하여 반환한다.
     *
     * @return 새로 생성된 메모
     */
    public Memo addNewMemo() {
        if (memoList == null) {
            memoList = new ArrayList<Memo>();
        }

        Memo m = new Memo();
        memoList.add(m);
        return m;
    }

    /**
     * 메모 리스트를 반환한다.
     *
     * @return 메모 리스트
     */
    public ArrayList<Memo> getMemoList() {
        return memoList;
    }

    public void copy(BodyText from) {
        sectionList.clear();
        for (Section section : from.sectionList) {
            sectionList.add(section.clone());
        }

        if (from.memoList != null) {
            memoList = new ArrayList<Memo>();
            for (Memo memo : from.memoList) {
                memoList.add(memo.clone());
            }
        } else {
            memoList = null;
        }
    }
}
