package ext.org.apache.poi.hhwpf.model.datarecord;



import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.datarecord.tabdef.TabDefProperty;
import ext.org.apache.poi.hhwpf.model.datarecord.tabdef.TabInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 탭 정의에 대한 레코드
 *
 * @author neolord
 */
public class TabDef {
    private static final Logger logger = LoggerFactory.getLogger(TabDef.class);
    /**
     * 속성
     */
    private TabDefProperty property;
    /**
     * 탭 정보 리스트
     */
    private ArrayList<TabInfo> tabInfoList;

    /**
     * 생성자
     */
    public TabDef() {
        property = new TabDefProperty();
        tabInfoList = new ArrayList<TabInfo>();
    }

    public TabDef(long propertyValue) {
        property = new TabDefProperty(propertyValue);
        tabInfoList = new ArrayList<TabInfo>();
    }

    public TabDef(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.property = new TabDefProperty(sr.readUInt4());
        this.tabInfoList = new ArrayList<TabInfo>();
        long tabInfoCount = sr.readUInt4();
        if (tabInfoCount > 0) {
            this.tabInfoList.add( new TabInfo(sr) );
        }

    }

    /**
     * 탭 정의의 속성 객체를 반환한다.
     *
     * @return 탭 정의의 속성 객체
     */
    public TabDefProperty getProperty() {
        return property;
    }

    /**
     * 새로운 탭 정보를 생성하고 리스트에 추가한다.
     *
     * @return 세로 생성된 탭 정보
     */
//    public TabInfo addNewTabInfo() {
//        TabInfo ti = new TabInfo();
//        tabInfoList.add(ti);
//        return ti;
//    }

    /**
     * 탭 정보 리스트를 반환한다.
     *
     * @return 탭 정보 리스트
     */
    public ArrayList<TabInfo> getTabInfoList() {
        return tabInfoList;
    }

    public TabDef clone() {
        TabDef cloned = new TabDef();
        cloned.property.copy(property);

        cloned.tabInfoList.clear();
        for (TabInfo tabInfo : tabInfoList) {
            cloned.tabInfoList.add(tabInfo.clone());
        }

        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_TAB_DEF, getSize());

        sw.writeUInt4(this.getProperty().getValue());

        long tabInfoCount = this.getTabInfoList().size();
        sw.writeUInt4(tabInfoCount);
        if (tabInfoCount > 0) {
            for (TabInfo ti : this.getTabInfoList()) {
                sw.writeUInt4(ti.getPosition());
                sw.writeUInt1(ti.getTabSort().getValue());
                sw.writeUInt1(ti.getFillSort().getValue());
                sw.writeZero(2);
            }
        }
    }

    /**
     * 탭 정의 레코드의 크기를 반환한다.
     *
     * @return 탭 정의 레코드의 크기
     */
    public final int getSize() {
        int size = 0;
        size += 8;
        size += 8 * this.getTabInfoList().size();
        return size;
    }
}
