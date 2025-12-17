package ext.org.apache.poi.hhwpf.model.structure.section.paragraph.rangetag;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.Section;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 문단의 영역 태그에 대한 레코드
 *
 * @author neolord
 */
public class ParaRangeTag {
    private static final Logger logger = LoggerFactory.getLogger(ParaRangeTag.class);
    /**
     * 영역 태그 정보에 대한 객체의 리스트
     */
    private ArrayList<RangeTagItem> rangeTagItemList;

    /**
     * 생성자
     */
    public ParaRangeTag() {
        rangeTagItemList = new ArrayList<RangeTagItem>();
    }

    public ParaRangeTag(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.rangeTagItemList = new ArrayList<RangeTagItem>();
        long recordDataSize = sr.getCurrentDataRecordHeader().getSize();
        if(recordDataSize!= 0) {
            long tagCount = recordDataSize / 12;
            for (long index = 0; index < tagCount; index++) {
                this.rangeTagItemList.add( new RangeTagItem( sr ));
            }
        }
    }

    /**
     * 새로운 영역 태그 정보의 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 영역 태그 정보에 대한 객체
     */
//    public RangeTagItem addNewRangeTagItem() {
//        RangeTagItem rt = new RangeTagItem();
//        rangeTagItemList.add(rt);
//        return rt;
//    }

    /**
     * 영역 태그 정보에 대한 객체의 리스트를 반환한다.
     *
     * @return 영역 태그 정보에 대한 객체의 리스트
     */
    public ArrayList<RangeTagItem> getRangeTagItemList() {
        return rangeTagItemList;
    }

    public ParaRangeTag clone() {
        ParaRangeTag cloned = new ParaRangeTag();

        for (RangeTagItem rangeTagItem : rangeTagItemList) {
            cloned.rangeTagItemList.add(rangeTagItem.clone());
        }

        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PARA_RANGE_TAG, this.getSize());
        for (RangeTagItem rti : this.getRangeTagItemList()) {
            sw.writeUInt4(rti.getRangeStart());
            sw.writeUInt4(rti.getRangeEnd());
            sw.writeBytes(rti.getData(), 3);
            sw.writeUInt1(rti.getSort());
        }
    }

    /**
     * 문서의 영역 테그 레코드의 크기를 반환한다.
     *
     * @return 문서의 영역 테그 레코드의 크기
     */
    public int getSize() {
        return this.getRangeTagItemList().size() * 12;
    }
}
