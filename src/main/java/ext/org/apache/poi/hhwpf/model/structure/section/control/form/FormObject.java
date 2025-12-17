package ext.org.apache.poi.hhwpf.model.structure.section.control.form;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.TagID;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.structure.section.control.form.properties.PropertySet;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FormObject implements StreamWritable {
    private FormObjectType type;
    private PropertySet properties;

    public FormObject() {
        type = null;
        properties = new PropertySet("");
    }

    public FormObject(StreamReader sr) throws IOException {
        long id = sr.readUInt4();
        this.type = FormObjectType.fromUint4(id);
        sr.readBytes(4);
        this.properties = new PropertySet(sr);
    }

    public FormObjectType getType() {
        return type;
    }

    public void setType(FormObjectType type) {
        this.type = type;
    }

    public PropertySet getProperties() {
        return properties;
    }

    public void copy(FormObject from) {
        type = from.type;
        properties.copy(from.properties);
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        HWPString propertiesString = this.getProperties().toHWPString();
        long strSize = 12 + propertiesString.getWCharsSize();
        sw.writeDataRecordHeader(TagID.HWPTAG_FORM_OBJECT, strSize);
        // id를 두번 등록하는 경우도 있음.
        sw.writeUInt4(this.getType().getId());
        sw.writeUInt4(this.getType().getId());
        sw.writeUInt2(propertiesString.getWCharsSize());
        sw.writeZero(2);
        sw.writeHWPString(propertiesString);

    }
}
