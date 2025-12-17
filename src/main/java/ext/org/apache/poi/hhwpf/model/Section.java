package ext.org.apache.poi.hhwpf.model;



import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.DataRecordHeader;
import ext.org.apache.poi.hhwpf.model.storage.BodyText;
import ext.org.apache.poi.hhwpf.model.structure.section.ParagraphListInterface;
import ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine.BatangPageInfo;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.memo.Memo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_LIST_HEADER;
import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_MEMO_LIST;

/**
 * 문단 구역(섹션)를 나타내는 객체. HWP 파일내의 "BodyText" storage 안에 "Section[번호]" stream에
 * 저장된다.
 *
 * @author neolord
 */
public class Section implements ParagraphListInterface {
    private static final Logger logger = LoggerFactory.getLogger(Section.class);
    /**
     * 문단 리스트
     */
    private ArrayList<Paragraph> paragraphList;
    /**
     * 마지막 바탕쪽 정보
     */
    private BatangPageInfo lastBatangPageInfo;
    /**
     * 생성자
     */
    public Section() {
        paragraphList = new ArrayList<Paragraph>();

        lastBatangPageInfo = null;
        anyBatangPageInfo = null;

    }

    /**
     * 임의의 바탕쪽 정보
     */
    private BatangPageInfo anyBatangPageInfo;

    public Section(InputStream inpStr, FileHeader fileHeader, DocInfo docInfo, BodyText bodyText, boolean isLastSection) throws Exception {

        logger.trace("Constructing the {} stream", this.getClass().getSimpleName());
        StreamReader sr = new StreamReader(inpStr, fileHeader, docInfo);
        // 구역정의 데이터 레코드 헤더

        this.paragraphList = new ArrayList<Paragraph>();
        DataRecordHeader dataRecordHeader = sr.readDataRecordHeader();
        while (sr.isAvailable()) {
//            logger.trace("Just before paragraph parsing");
            Paragraph _paragraph = new Paragraph(sr);
//            logger.trace("after Paragraph in Section");
            this.paragraphList.add(_paragraph);
//            logger.trace("Paragraph list just has been modified");
            if(_paragraph.getHeader().isLastInList()) {
                logger.trace("isLastInList in Section");
                break;
            }
        }

        // Last BatangPageInfo
        if(sr.isAvailable() && sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.lastBatangPageInfo = new BatangPageInfo(sr);
        }

        // Any BatangPageInfo
        if(sr.isAvailable() && sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.anyBatangPageInfo = new BatangPageInfo(sr);
        }

        if(isLastSection) {
            while(sr.isAvailable()) {
                if(sr.isEndOfDataRecord()) {
                    sr.readDataRecordHeader();
                }
                if(sr.getCurrentDataRecordHeader().getTagID()==HWPTAG_MEMO_LIST) {
                    bodyText.getMemoList().add(new Memo(sr));
                }
            }
        }
    }

    /**
     * 새로운 문단를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 문단
     */
    @Override
    public Paragraph addNewParagraph() {
        Paragraph p = new Paragraph();
        paragraphList.add(p);
        return p;
    }

    public void appendParagraph(Paragraph paragraph) {
        this.paragraphList.add(paragraph);
    }

    /**
     * 문단 개수를 반환한다.
     *
     * @return 문단 개수
     */
    @Override
    public int getParagraphCount() {
        return paragraphList.size();
    }

    /**
     * index 번째의 문단을 반환한다.
     *
     * @param index 찾고자 하는 문단의 순번
     * @return index 번째의 문단
     */
    @Override
    public Paragraph getParagraph(int index) {
        return paragraphList.get(index);
    }

    @Override
    public Paragraph[] getParagraphs() {
        return paragraphList.toArray(Paragraph.Zero_Array);
    }

    /**
     * index 번째의 문단을 삭제한다.
     *
     * @param index 삭제할 문단의 순번
     */
    @Override
    public void deleteParagraph(int index) {
        paragraphList.remove(index);
    }

    @Override
    public void deleteAllParagraphs() {
        paragraphList.clear();
    }

    @Override
    public void insertParagraph(int index, Paragraph para) {
        paragraphList.add(index, para);
    }

    @Override
    public Paragraph insertNewParagraph(int index) {
        Paragraph p = new Paragraph();
        paragraphList.add(index, p);
        return p;
    }

    /**
     * Iterator<Paragraph> 객체를 반환한다.
     *
     * @return Iterator<Paragraph> 객체
     */
    @Override
    public Iterator<Paragraph> iterator() {
        return paragraphList.iterator();
    }

    public Paragraph getLastParagraph() {
        if (paragraphList.size() > 0) {
            return paragraphList.get(paragraphList.size() - 1);
        }
        return null;
    }

    /**
     * 마지막 바탕쪽 정보 객체를 생성한다.
     */
    public void createLastBatangPageInfo() {
        lastBatangPageInfo = new BatangPageInfo();
    }

    /**
     * 마지막 바탕쪽 정보를 리턴한다.
     *
     * @return 마지막 바탕쪽 정보
     */
    public BatangPageInfo getLastBatangPageInfo() {
        return lastBatangPageInfo;
    }

    /**
     * 임의의 바탕쪽 정보 객체를 생성한다.
     */
    public void createAnyBatangPageInfo() {
        anyBatangPageInfo = new BatangPageInfo();
    }

    /**
     * 임의의 바탕쪽 정보를 리턴한다.
     *
     * @return 마지막 바탕쪽 정보
     */
    public BatangPageInfo getAnyBatangPageInfo() {
        return anyBatangPageInfo;
    }

    public Section clone() {
        Section cloned = new Section();

        cloned.paragraphList.clear();
        for (Paragraph paragraph : paragraphList) {
            cloned.paragraphList.add(paragraph.clone());
        }

        if (lastBatangPageInfo != null) {
            cloned.lastBatangPageInfo = lastBatangPageInfo.clone();
        } else {
            cloned.lastBatangPageInfo = null;
        }

        if (anyBatangPageInfo != null) {
            cloned.anyBatangPageInfo = anyBatangPageInfo.clone();
        } else {
            cloned.anyBatangPageInfo = null;
        }

        return cloned;
    }

    public ArrayList<Paragraph> getParagraphList() {
        return paragraphList;
    }

    @Override
    public void updateState(InstanceID iid) {
        int count = this.getParagraphCount();
        for (int index = 0; index < count; index++) {
            this.paragraphList.get(index).updateParagraphState(index == count - 1, iid);
        }
    }

    public void write(StreamWriter sw) throws IOException {
        for(Paragraph p : this.paragraphList) {
            p.write(sw);
        }

        if (this.getLastBatangPageInfo() != null) {
            sw.upRecordLevel();
            this.getLastBatangPageInfo().write(sw);
            sw.downRecordLevel();
        }
    }
}
