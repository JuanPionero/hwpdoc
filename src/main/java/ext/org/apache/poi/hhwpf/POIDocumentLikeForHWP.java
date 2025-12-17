package ext.org.apache.poi.hhwpf;

import ext.org.apache.poi.hhwpf.util.binary.Compressor;
import org.apache.poi.poifs.filesystem.*;

import java.io.*;

/**
 * POIDocument 와 DirectoryNode의 기능들 중 필요한 것들만 모음.
 * TODO storage 의 경우도 Closeable이 되어야  할 수 있으니, 추가 분석 후 어떻게 처리할지 고민해야 함.
 */
abstract public class POIDocumentLikeForHWP {
    protected DocumentEntry getDocumentEntry(DirectoryNode storage, String name) throws IOException {
        Entry entry = storage.getEntry(name);
        if(entry==null) {
            throw new IOException("No Entry for '" + name + "'");
        } else if (!entry.isDocumentEntry()) {
            throw new IOException("Entry '" + entry.getName()
                    + "' is not a DocumentEntry");
        }
        return (DocumentEntry) entry;

    }

    protected InputStream getDocumentStream(DirectoryNode storage, String name) throws IOException {
        return new DocumentInputStream(this.getDocumentEntry(storage, name));
    }

    protected InputStream getDocumentStream(DirectoryNode storage, String name, boolean compress) throws IOException {

        InputStream is = this.getDocumentStream(storage, name);

        if(compress) {
            try {
                byte[] decompressed = Compressor.decompressedBytes(is);
                return new ByteArrayInputStream(decompressed);
            } finally {

            }
        }
        return is;
    }

    protected InputStream getDocumentStream(DirectoryNode storage, String name, boolean compress, boolean restricted) throws Exception {

        InputStream is = restricted ? this.getDocumentStream(storage, name)
                : StreamDecryptor.decryptStream(this.getDocumentStream(storage, name));

        if(compress) {
            try {
                byte[] decompressed = Compressor.decompressedBytes(is);
                return new ByteArrayInputStream(decompressed);
            } finally {

            }
        }
        return is;
    }


}
