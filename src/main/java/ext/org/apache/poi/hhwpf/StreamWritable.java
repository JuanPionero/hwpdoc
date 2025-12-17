package ext.org.apache.poi.hhwpf;

import java.io.IOException;

public interface StreamWritable {
    void write(StreamWriter sw) throws IOException;
    default void writeInContainer(StreamWriter sr) throws IOException {
        // 일부에서만 사용할 것임.
    }
}
