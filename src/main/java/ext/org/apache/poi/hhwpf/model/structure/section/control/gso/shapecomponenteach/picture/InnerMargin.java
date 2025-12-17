package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.picture;

import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 안쪽 여백 정보
 *
 * @author neolord
 */
public class InnerMargin implements StreamWritable {
    /**
     * 왼쪽 여백
     */
    private int left;
    /**
     * 오른쪽 여백
     */
    private int right;
    /**
     * 위쪽 여백
     */
    private int top;
    /**
     * 아래쪽 여백
     */
    private int bottom;

    /**
     * 생성자
     */
    public InnerMargin() {
    }

    public InnerMargin(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * 왼쪽 여백의 크기를 반환한다.
     *
     * @return 왼쪽 여백의 크기
     */
    public int getLeft() {
        return left;
    }

    /**
     * 왼쪽 여백의 크기를 설정한다.
     *
     * @param left 왼쪽 여백의 크기
     */
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * 오른쪽 여백의 크기를 반환한다.
     *
     * @return 오른쪽 여백의 크기
     */
    public int getRight() {
        return right;
    }

    /**
     * 오른쪽 여백의 크기를 설정한다.
     *
     * @param right 오른쪽 여백의 크기
     */
    public void setRight(int right) {
        this.right = right;
    }

    /**
     * 위쪽 여백의 크기를 반환한다.
     *
     * @return 위쪽 여백의 크기
     */
    public int getTop() {
        return top;
    }

    /**
     * 위쪽 여백의 크기를 설정한다.
     *
     * @param top 위쪽 여백의 크기
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * 아래쪽 여백의 크기를 반환한다.
     *
     * @return 아래쪽 여백의 크기
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * 아래쪽 여백의 크기를 설정한다.
     *
     * @param bottom 아래쪽 여백의 크기
     */
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void copy(InnerMargin from) {
        left = from.left;
        right = from.right;
        top = from.top;
        bottom = from.bottom;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.getLeft());
        sw.writeUInt2(this.getRight());
        sw.writeUInt2(this.getTop());
        sw.writeUInt2(this.getBottom());
    }

    @Override
    public String toString() {
        return "InnerMargin{" +
                "left=" + left +
                ", right=" + right +
                ", top=" + top +
                ", bottom=" + bottom +
                '}';
    }
}
