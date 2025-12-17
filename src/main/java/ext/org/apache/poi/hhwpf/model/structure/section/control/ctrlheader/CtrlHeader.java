package ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader;

import ext.org.apache.poi.hhwpf.StreamWritable;

/**
 * 컨트롤 헤더 객체들을 위한 부모 클래스
 *
 * @author neolord
 */
public abstract class CtrlHeader implements StreamWritable {
    /**
     * 컨트롤 아이디
     */
    protected long ctrlId;

    /**
     * 생성자
     *
     * @param ctrlId 컨트롤 아이디
     */
    public CtrlHeader(long ctrlId) {
        this.ctrlId = ctrlId;
    }

    /**
     * 컨트롤 아이디를 반환한다.
     *
     * @return 컨트롤 아이디
     */
    public long getCtrlId() {
        return ctrlId;
    }

    public abstract void copy(CtrlHeader from);
}
