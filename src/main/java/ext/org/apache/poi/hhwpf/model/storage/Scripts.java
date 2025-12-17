package ext.org.apache.poi.hhwpf.model.storage;

import ext.org.apache.poi.hhwpf.POIDocumentLikeForHWP;
import ext.org.apache.poi.hhwpf.StreamReader;
import org.apache.poi.poifs.filesystem.DirectoryNode;

import java.io.IOException;
import java.io.InputStream;

import static ext.org.apache.poi.hhwpf.StreamID.POIFS_STREAM_DEFAULT_JSCRIPT;
import static ext.org.apache.poi.hhwpf.StreamID.POIFS_STREAM_JSCRIPT_VERSION;

public class Scripts extends POIDocumentLikeForHWP {
    private byte[] defaultJScript;
    private byte[] jScriptVersion;

    public Scripts() {
        defaultJScript = null;
        jScriptVersion = null;
    }
    public Scripts(byte[] jScriptVersion, byte[] defaultJScript) {
        this.defaultJScript = defaultJScript;
        this.jScriptVersion = jScriptVersion;
    }
    public Scripts(DirectoryNode storage) throws IOException {
        try (InputStream is = this.getDocumentStream(storage, POIFS_STREAM_DEFAULT_JSCRIPT)) {
            StreamReader sr = new StreamReader(is);
            byte[] data = sr.readRestOfStream();
            this.defaultJScript = data;
        }
        try (InputStream is = this.getDocumentStream(storage, POIFS_STREAM_JSCRIPT_VERSION)) {
            StreamReader sr = new StreamReader(is);
            byte[] data = sr.readRestOfStream();
            this.jScriptVersion = data;
        }
    }

    public byte[] getDefaultJScript() {
        return defaultJScript;
    }

    public void setDefaultJScript(byte[] defaultJScript) {
        this.defaultJScript = defaultJScript;
    }

    public byte[] getJScriptVersion() {
        return jScriptVersion;
    }

    public void setJScriptVersion(byte[] jScriptVersion) {
        this.jScriptVersion = jScriptVersion;
    }

    public void copy(Scripts from) {
        this.defaultJScript = from.defaultJScript;
        this.jScriptVersion = from.jScriptVersion;
    }
}
