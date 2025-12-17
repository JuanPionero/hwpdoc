package ext.org.apache.poi.hhwpf.model.datarecord.tabdef;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderType;

import java.io.IOException;

/**
 * 탭 정보에 대한 객체
 *
 * @author neolord
 */
public class TabInfo {
    /**
     * 탭의 위치
     */
    private long position;
    /**
     * 탭의 종류
     */
    private TabSort tabSort;
    /**
     * 채움 종류
     */
    private BorderType fillSort;

    /**
     * 생성자
     */
    public TabInfo() {
    }
    public TabInfo(StreamReader sr) throws IOException {
        this.position = sr.readUInt4();
        this.tabSort = TabSort.valueOf((byte) sr.readUInt1());
        this.fillSort = BorderType.valueOf((byte) sr.readUInt1());
        sr.readBytes(2); // reserved
    }

    /**
     * 탭의 위치를 반환한다.
     *
     * @return 탭의 위치
     */
    public long getPosition() {
        return position;
    }

    /**
     * 탭의 위치를 설정한다.
     *
     * @param position 탭의 위치
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     * 탭의 종류를 반환한다.
     *
     * @return 탭의 종류
     */
    public TabSort getTabSort() {
        return tabSort;
    }

    /**
     * 탭의 종류를 설정한다.
     *
     * @param tabSort 탭의 종류
     */
    public void setTabSort(TabSort tabSort) {
        this.tabSort = tabSort;
    }

    /**
     * 채움 종류를 반환한다.
     *
     * @return 채움 종류
     */
    public BorderType getFillSort() {
        return fillSort;
    }

    /**
     * 채움 종류를 설정한다.
     *
     * @param fillSort 채움 종류
     */
    public void setFillSort(BorderType fillSort) {
        this.fillSort = fillSort;
    }

    public TabInfo clone() {
        TabInfo cloned = new TabInfo();
        cloned.position = position;
        cloned.tabSort = tabSort;
        cloned.fillSort = fillSort;
        return cloned;
    }
}
