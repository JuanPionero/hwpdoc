package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.form.FormObject;

import java.io.IOException;

/**
 * 양식 개체 컨트롤
 *
 * @author neolord
 */
public class ControlForm extends Control {
    private FormObject formObject;

    /**
     * 생성자
     */
    public ControlForm() {
        this(new CtrlHeaderGso(ControlType.Form));
    }

    /**
     * 생상자
     *
     * @param header 양식 개체를 위한 컨트롤 헤더
     */
    public ControlForm(CtrlHeaderGso header) {
        super(header);
        formObject = new FormObject();
    }

    public ControlForm(StreamReader sr, CtrlHeaderGso header) throws IOException {
        super(header);
        this.formObject = new FormObject(sr);
    }

    public CtrlHeaderGso getHeader() {
        return (CtrlHeaderGso) header;
    }

    public FormObject getFormObject() {
        return formObject;
    }

    @Override
    public Control clone() {
        ControlForm cloned = new ControlForm();
        cloned.copyControlPart(this);

        cloned.formObject.copy(formObject);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        this.formObject.write(sw);
    }

}
