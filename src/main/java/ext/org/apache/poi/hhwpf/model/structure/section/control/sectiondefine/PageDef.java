package ext.org.apache.poi.hhwpf.model.structure.section.control.sectiondefine;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.text.ParaText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 용지 설정에 대한 레코드
 *
 * @author neolord
 */
public class PageDef implements StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(PageDef.class);
    /**
     * 용지 가로 크기
     */
    private long paperWidth;
    /**
     * 용지 세로 크기
     */
    private long paperHeight;
    /**
     * 용지 왼쪽 여백
     */
    private long leftMargin;
    /**
     * 용지 오른쪽 여백
     */
    private long rightMargin;
    /**
     * 용지 위쪽 여백
     */
    private long topMargin;
    /**
     * 용지 아래쪽 여백
     */
    private long bottomMargin;
    /**
     * 머리말 여백
     */
    private long headerMargin;
    /**
     * 꼬리말 여백
     */
    private long footerMargin;
    /**
     * 제본 여백
     */
    private long gutterMargin;
    /**
     * 속성
     */
    private PageDefProperty property;

    /**
     * 생상자
     */
    public PageDef() {
        property = new PageDefProperty();
    }

    public PageDef(int paperWidthValue,
                   int paperHeightValue,
                   int leftMarginValue,
                   int rightMarginValue,
                   int topMarginValue,
                   int bottomMarginValue,
                   int headerMarginValue,
                   int footerMarginValue,
                   int gutterMarginValue,
                   int propertyValue) {
        this.paperWidth = paperWidthValue;
        this.paperHeight = paperHeightValue;
        this.leftMargin = leftMarginValue;
        this.rightMargin = rightMarginValue;
        this.topMargin = topMarginValue;
        this.bottomMargin = bottomMarginValue;
        this.headerMargin = headerMarginValue;
        this.footerMargin = footerMarginValue;
        this.gutterMargin = gutterMarginValue;
        this.property = new PageDefProperty(propertyValue);
    }

    public PageDef(StreamReader sr) throws IOException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        this.paperWidth = sr.readUInt4();
        this.paperHeight = sr.readUInt4();
        this.leftMargin = sr.readUInt4();
        this.rightMargin = sr.readUInt4();
        this.topMargin = sr.readUInt4();
        this.bottomMargin = sr.readUInt4();
        this.headerMargin = sr.readUInt4();
        this.footerMargin = sr.readUInt4();
        this.gutterMargin = sr.readUInt4();
        this.property = new PageDefProperty( sr.readUInt4() );
    }

    /**
     * 용지 가로 크기를 반환한다.
     *
     * @return 용지 가로 크기
     */
    public long getPaperWidth() {
        return paperWidth;
    }

    /**
     * 용지 가로 크기를 설정한다.
     *
     * @param paperWidth 용지 가로 크기
     */
    public void setPaperWidth(long paperWidth) {
        this.paperWidth = paperWidth;
    }

    /**
     * 용지 세로 크기를 반환한다.
     *
     * @return 용지 세로 크기
     */
    public long getPaperHeight() {
        return paperHeight;
    }

    /**
     * 용지 세로 크기를 설정한다.
     *
     * @param paperHeight 용지 세로 크기
     */
    public void setPaperHeight(long paperHeight) {
        this.paperHeight = paperHeight;
    }

    /**
     * 용지 왼쪽 여백의 크기를 반환한다.
     *
     * @return 용지 왼쪽 여백의 크기
     */
    public long getLeftMargin() {
        return leftMargin;
    }

    /**
     * 용지 왼쪽 여백의 크기를 설정한다.
     *
     * @param leftMargin 용지 왼쪽 여백의 크기
     */
    public void setLeftMargin(long leftMargin) {
        this.leftMargin = leftMargin;
    }

    /**
     * 용지 오른쪽 여백의 크기를 반환한다.
     *
     * @return 용지 오른쪽 여백의 크기
     */
    public long getRightMargin() {
        return rightMargin;
    }

    /**
     * 용지 오른쪽 여백의 크기를 설정한다.
     *
     * @param rightMargin 용지 오른쪽 여백의 크기
     */
    public void setRightMargin(long rightMargin) {
        this.rightMargin = rightMargin;
    }

    /**
     * 용지 위쪽 여백의 크기를 반환한다.
     *
     * @return 용지 위쪽 여백의 크기
     */
    public long getTopMargin() {
        return topMargin;
    }

    /**
     * 용지 위쪽 여백의 크기를 설정한다.
     *
     * @param topMargin 용지 위쪽 여백의 크기
     */
    public void setTopMargin(long topMargin) {
        this.topMargin = topMargin;
    }

    /**
     * 용지 아래쪽 여백의 크기를 반환한다.
     *
     * @return 용지 아래쪽 여백의 크기
     */
    public long getBottomMargin() {
        return bottomMargin;
    }

    /**
     * 용지 아래쪽 여백의 크기를 설정한다.
     *
     * @param bottomMargin 용지 아래쪽 여백의 크기
     */
    public void setBottomMargin(long bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    /**
     * 머리말 여백의 크기를 반환한다.
     *
     * @return 머리말 여백의 크기
     */
    public long getHeaderMargin() {
        return headerMargin;
    }

    /**
     * 머리말 여백의 크기를 설정한다.
     *
     * @param headerMargin 머리말 여백의 크기
     */
    public void setHeaderMargin(long headerMargin) {
        this.headerMargin = headerMargin;
    }

    /**
     * 꼬리말 여백의 크기를 반환한다.
     *
     * @return 꼬리말 여백의 크기
     */
    public long getFooterMargin() {
        return footerMargin;
    }

    /**
     * 꼬리말 여백의 크기를 설정한다.
     *
     * @param footerMargin 꼬리말 여백의 크기
     */
    public void setFooterMargin(long footerMargin) {
        this.footerMargin = footerMargin;
    }

    /**
     * 제본 여백의 크기를 반환한다.
     *
     * @return 제본 여백의 크기
     */
    public long getGutterMargin() {
        return gutterMargin;
    }

    /**
     * 제본 여백의 크기를 설정한다.
     *
     * @param gutterMargin 제본 여백의 크기
     */
    public void setGutterMargin(long gutterMargin) {
        this.gutterMargin = gutterMargin;
    }

    /**
     * 속성 객체를 반환한다.
     *
     * @return 속성 객체
     */
    public PageDefProperty getProperty() {
        return property;
    }

    public void copy(PageDef from) {
        paperWidth = from.paperWidth;
        paperHeight = from.paperHeight;
        leftMargin = from.leftMargin;
        rightMargin = from.rightMargin;
        topMargin = from.topMargin;
        bottomMargin = from.bottomMargin;
        headerMargin = from.headerMargin;
        footerMargin = from.footerMargin;
        gutterMargin = from.gutterMargin;
        property.copy(from.property);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_PAGE_DEF, 40);
        sw.writeUInt4(this.getPaperWidth());
        sw.writeUInt4(this.getPaperHeight());
        sw.writeUInt4(this.getLeftMargin());
        sw.writeUInt4(this.getRightMargin());
        sw.writeUInt4(this.getTopMargin());
        sw.writeUInt4(this.getBottomMargin());
        sw.writeUInt4(this.getHeaderMargin());
        sw.writeUInt4(this.getFooterMargin());
        sw.writeUInt4(this.getGutterMargin());
        sw.writeUInt4(this.getProperty().getValue());
    }

    @Override
    public String toString() {
        return "PageDef{" +
                "paperWidth=" + paperWidth +
                ", paperHeight=" + paperHeight +
                ", leftMargin=" + leftMargin +
                ", rightMargin=" + rightMargin +
                ", topMargin=" + topMargin +
                ", bottomMargin=" + bottomMargin +
                ", headerMargin=" + headerMargin +
                ", footerMargin=" + footerMargin +
                ", gutterMargin=" + gutterMargin +
                ", property=" + property.getValue() +
                '}';
    }

    public long getWritableWidth() {
        return this.paperWidth - this.leftMargin - this.rightMargin - this.gutterMargin;
    }

    public long getWritableHeight() {
        return this.paperHeight - this.topMargin - this.bottomMargin - this.headerMargin - this.footerMargin;
    }

}
