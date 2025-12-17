package ext.org.apache.poi.hhwpf;

import ext.org.apache.poi.hhwpf.model.structure.FileVersion;

public final class Specification {
    // HWP Document File
    public static final byte[] HWP_DOCUMENT_FILE =  {
                0x48, 0x57, 0x50, 0x20, 0x44, 0x6f, 0x63, 0x75,
                0x6d, 0x65, 0x6e, 0x74, 0x20, 0x46, 0x69, 0x6c,
                0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    public static final long DEFAULT_FILE_VERSION_LONG = 0x05000304;

    public static final String HWP_DOCUMENT_FILE_STR = "HWP Document File";

    public static final byte[] COMPRESSED_JS_VERSION = { 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };

    public static final byte[] COMPRESSED_DEFAULT_JS_VERSION = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
            , 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF };

    /**
     * 원작자의 코드를 그대로 따라하여 -1로 설정함.
     * 하지만, -1 대신 0으로 설정하는 것이 더 좋을 듯 함.
     * TODO 좀더 살펴보고 결정 할 것임
     */
    public static final int PARAGRAPH_CHAR_INDEX_LOWEST = -1;
    public static final int PARAGRAPH_CHAR_INDEX_HIGHEST = 0xffff;


    public static final String DOCUMENT_SUMMARY_INFORMATION_ID_STR = "{9FA2B660-1061-11D4-B4C6-006097C09D8C}";
    public static final String DEFAULT_AUTHOR_FOR_SUMMARY = "HWPDOC LIB Ver 0.0.1";
    public static final String BIN_DATA_STREAM_NAME_PREFIX = "Bin";
    public static final String EQUATION_VERSION = "Equation Version 60"; // 웹에서 사용하는 에디터에서 추출.
    private Specification() {

    }
}
