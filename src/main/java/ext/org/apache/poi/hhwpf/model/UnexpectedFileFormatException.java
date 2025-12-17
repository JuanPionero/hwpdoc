package ext.org.apache.poi.hhwpf.model;

import org.apache.poi.UnsupportedFileFormatException;

public class UnexpectedFileFormatException extends UnsupportedFileFormatException {
    public UnexpectedFileFormatException() {
        super("Unexpected File Format");
    }
    public UnexpectedFileFormatException(String message) {
        super(message);
    }
    public UnexpectedFileFormatException(Throwable cause) {
        super("Unexpected File Format", cause);
    }
}
