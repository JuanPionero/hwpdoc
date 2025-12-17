package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.ParameterItem;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.ParameterType;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderField;

import java.io.IOException;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.HWPTAG_CTRL_DATA;

/**
 * 필드 컨트롤
 *
 * @author neolord
 */
public class ControlField extends Control implements StateUpdatable {
    /**
     * 생성자
     *
     * @param ctrlId : ctrl header의 ctrl-id.
     */
    public ControlField(long ctrlId) {
        super(new CtrlHeaderField(ctrlId));
    }

    public ControlField(long ctrlId, StreamReader sr) throws IOException, IllegalAccessException {
        super(new CtrlHeaderField(ctrlId, sr));
        // Control is abstract class => additional definition would be made here
        sr.readDataRecordHeader();
        if(sr.getCurrentDataRecordHeader().getTagID()== HWPTAG_CTRL_DATA) {
            this.ctrlData = new CtrlData(sr);
        }
    }

    /**
     * 필드용 컨트롤 헤더를 반환한다.
     *
     * @return 필드용 컨트롤 헤더
     */
    public CtrlHeaderField getHeader() {
        return (CtrlHeaderField) header;
    }

    /**
     * 필드 컨트롤의 이름응 반환한다.
     *
     * @return 필드 컨트롤의 이름
     */
    public String getName() {
        if (ctrlData != null) {
            if (ctrlData.getParameterSet().getId() == 0x021B) {
                ParameterItem pi = ctrlData.getParameterSet().getParameterItem(0x4000);
                if (pi != null && pi.getType() == ParameterType.String) {
                    if (pi.getValue_BSTR() != null) {
                        return pi.getValue_BSTR();
                    }
                }
            }
        }
        return commandToName(getHeader().getCommand().toUTF16LEString());
    }

    private String commandToName(String command) {
        if (command == null) {
            return null;
        }

        String[] properties = command.split(" ");
        if (properties != null && properties.length >= 1) {
            String[] token = properties[0].split(":");
            if (token != null && token.length >= 1) {
                return token[token.length - 1];
            }
        }
        return null;
    }

    /**
     * 필드 컨트롤의 이름응 설정한다.
     *
     * @param name 필드 이름
     */
    public void setName(String name) {
        if (ctrlData == null) {
            createCtrlData();
            ctrlData.getParameterSet().setId(0x021B);
        }

        ParameterItem pi = ctrlData.getParameterSet().getParameterItem(
                0x4000);
        if (pi == null) {
            pi = ctrlData.getParameterSet().addNewParameterItem();
            pi.setId(0x4000);
        }

        pi.setType(ParameterType.String);
        pi.setValue_BSTR(name);
    }

    @Override
    public Control clone() {
        ControlField cloned = new ControlField(this.header.getCtrlId());
        cloned.copyControlPart(this);
        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        this.getHeader().setInstanceId(iid.get());
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);
        sw.upRecordLevel();
        if(this.ctrlData!=null) {
            this.ctrlData.write(sw);
        }
        sw.downRecordLevel();
    }

}
