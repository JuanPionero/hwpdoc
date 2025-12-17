package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.*;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.equation.EQEdit;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.caption.Caption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 수식 컨트롤
 *
 * @author neolord
 */
public class ControlEquation extends Control implements StateUpdatable, StreamWritable {
    private static final Logger logger = LoggerFactory.getLogger(ControlEquation.class);
    /**
     * 캡션
     */
    private Caption caption;
    /**
     * 수식 정보
     */
    private EQEdit eqEdit;

    /**
     * 생성자
     */
    public ControlEquation() {
        super(new CtrlHeaderGso(ControlType.Equation));

        eqEdit = new EQEdit();
    }
    public ControlEquation(StreamReader sr) throws Exception {
        super(new CtrlHeaderGso(sr, ControlType.Equation));
        int ctrlHeaderLevel = sr.getCurrentDataRecordHeader().getLevel();

        if (sr.readDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            this.caption = new Caption( sr );
        }

        logger.trace("data header = {}", sr.getCurrentDataRecordHeader());
        logger.trace("data header remain = {}", sr.getRemainedDataRecordDataCount());


        // 1회만 반복 할 것으로 보임. !!
        // 이것이 맞다면, 아래 내용처럼 여러 조건 검사는 불필요함.
        logger.trace("Start to read EqEdit; stream is available={}", sr.isAvailable());
        while(sr.isAvailable()) {
            logger.trace("Check 1");
            if (sr.isEndOfDataRecord()) {
                logger.trace("ready to read other data record header");
                sr.readDataRecordHeader();
            }
            logger.trace("Check 2");
            if (ctrlHeaderLevel >= sr.getCurrentDataRecordHeader().getLevel()) {
                break;
            }
            logger.trace("Check 3, id={}", sr.getCurrentDataRecordHeader().getTagID());
            if (sr.getCurrentDataRecordHeader().getTagID() != HWPTAG_EQEDIT) {
                throw new UnexpectedFileFormatException("Unexpected Equation Control Structure");
            }
            logger.trace("Start to generate EQEdit");
            this.eqEdit = new EQEdit(sr);
        }
    }

    public ControlEquation(Initializer<ControlEquation> initializer) throws Exception {
        super(new CtrlHeaderGso(ControlType.Equation));
        eqEdit = new EQEdit();
        initializer.init(this);
    }

    /**
     * 그리기 객체용 컨트롤 헤더를 반환한다.
     *
     * @return 그리기 객체용 컨트롤 헤더
     */
    public CtrlHeaderGso getHeader() {
        return (CtrlHeaderGso) header;
    }

    /**
     * 캡션 객체를 생성한다.
     */
    public void createCaption() {
        caption = new Caption();
    }

    /**
     * 캡션 객체를 삭제한다.
     */
    public void deleteCaption() {
        caption = null;
    }

    /**
     * 캡션 객체를 반환한다.
     *
     * @return 캡션 객체
     */
    public Caption getCaption() {
        return caption;
    }

    /**
     * 수식 정보 객체를 반환한다.
     *
     * @return 수식 정보 객체
     */
    public EQEdit getEQEdit() {
        return eqEdit;
    }

    @Override
    public Control clone() {
        ControlEquation cloned = new ControlEquation();
        cloned.copyControlPart(this);

        if (caption != null) {
            cloned.createCaption();
            cloned.caption.copy(caption);
        } else {
            cloned.caption = null;
        }

        cloned.eqEdit.copy(eqEdit);

        return cloned;
    }

    @Override
    public String toString() {
        return "ControlEquation{" +
                "caption=" + caption +
                ", eqEdit=" + eqEdit +
                ", header=" + header +
                ", ctrlData=" + ctrlData +
                '}';
    }

    @Override
    public void updateState(InstanceID iid) {
        CtrlHeaderGso h = this.getHeader();
        if(h==null) {
            return;
        }
        h.setInstanceId(iid.get());
        h.getProperty().setHasCaption( this.caption != null );
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        if(this.caption!=null) {
            this.caption.write(sw);
        }
        this.eqEdit.write(sw);

        sw.downRecordLevel();
    }
}
