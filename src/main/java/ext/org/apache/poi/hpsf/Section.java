package ext.org.apache.poi.hpsf;

import org.apache.poi.hpsf.Property;
import org.apache.poi.hpsf.Variant;

import java.io.UnsupportedEncodingException;

public class Section extends org.apache.poi.hpsf.Section {

    public Section() {
        super();
    }

    public Section(final org.apache.poi.hpsf.Section s) {
        super(s);
    }

    public Section(final byte[] src, final int offset) throws UnsupportedEncodingException {
        super(src, offset);
    }
    public void setProperty(final int id, final String value) {
        setProperty(id, Variant.VT_LPWSTR, value);
    }
}
