package ext.org.apache.poi.hhwpf;


import ext.org.apache.poi.hhwpf.model.Section;
import ext.org.apache.poi.hhwpf.model.UnexpectedFileFormatException;
import ext.org.apache.poi.hhwpf.model.datarecord.bindata.BinDataCompress;
import ext.org.apache.poi.hhwpf.model.storage.Scripts;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.FileHeader;
import ext.org.apache.poi.hhwpf.model.storage.BinData;
import ext.org.apache.poi.hhwpf.model.storage.BodyText;
import ext.org.apache.poi.hhwpf.model.structure.bindata.EmbeddedBinaryData;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.memo.Memo;
import ext.org.apache.poi.hhwpf.util.initializer.DocInfoInitializer;
import ext.org.apache.poi.hhwpf.util.initializer.FirstestParagraphInitializer;
import ext.org.apache.poi.hpsf.HwpPropertySet;
import ext.org.apache.poi.hpsf.HwpSummaryInformation;
import org.apache.poi.hpsf.ClassID;
import org.apache.poi.hpsf.NoPropertySetStreamException;
import org.apache.poi.hpsf.UnexpectedPropertySetTypeException;
import org.apache.poi.hpsf.WritingNotSupportedException;
import org.apache.poi.poifs.filesystem.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ext.org.apache.poi.hhwpf.Specification.*;


/**
 * 한글과 컴퓨터의 hwp 파일을 위한 apache.poi의 문서 모델.
 * HHWPF : Horrible Hangul Word Processing Format
 * @author Seung Hoon Lee (juanlee0@naver.com)
 */
public class HHWPFDocument extends POIDocumentLikeForHWP {
    private static final Logger logger = LoggerFactory.getLogger(HHWPFDocument.class);
    private FileHeader fileHeader;
    private DocInfo docInfo;
    private BodyText bodyText;
    private HwpSummaryInformation hwpSummaryInformation;
    private BinData binData;
    private Scripts scripts;

    /**
     * Section(Paragraph)이 등록되지 않은 구조임. 바로 저장하면 유효하지 않은 한글문서파일이 됨.
     */
    public HHWPFDocument() {
        this.fileHeader = new FileHeader();
        this.docInfo = new DocInfo();
        // 문서 요약 부분은 다시 써야 함.
        try {
            HwpPropertySet propertySet = new HwpPropertySet();
            propertySet.getSections().get(0).setFormatID(new ClassID(DOCUMENT_SUMMARY_INFORMATION_ID_STR));
            this.hwpSummaryInformation = new HwpSummaryInformation(propertySet);
            this.hwpSummaryInformation.setAuthor( DEFAULT_AUTHOR_FOR_SUMMARY  );
        } catch (UnexpectedPropertySetTypeException e) {
            this.hwpSummaryInformation = null;
        }
        this.bodyText = new BodyText();
        this.binData = new BinData();
        this.scripts = new Scripts(COMPRESSED_JS_VERSION, COMPRESSED_DEFAULT_JS_VERSION);
    }

    public HHWPFDocument(Initializer<DocInfo> docInfoInitializer, Initializer<Paragraph> paragraphInitializer) throws Exception {
        this.fileHeader = new FileHeader();
        this.docInfo = new DocInfo(docInfoInitializer);
        // 문서 요약 부분은 다시 써야 함.
        try {
            HwpPropertySet propertySet = new HwpPropertySet();
            propertySet.getSections().get(0).setFormatID(new ClassID(DOCUMENT_SUMMARY_INFORMATION_ID_STR));
            this.hwpSummaryInformation = new HwpSummaryInformation(propertySet);
            this.hwpSummaryInformation.setAuthor( DEFAULT_AUTHOR_FOR_SUMMARY  );
        } catch (UnexpectedPropertySetTypeException e) {
            this.hwpSummaryInformation = null;
        }
        this.bodyText = new BodyText();
        // BodyText > Section > Paragraph
        Section section = new Section();
        Paragraph paragraph = new Paragraph(paragraphInitializer);
        section.appendParagraph(paragraph);
        this.bodyText.appendSection(section);
        this.binData = new BinData();
        this.scripts = new Scripts(COMPRESSED_JS_VERSION, COMPRESSED_DEFAULT_JS_VERSION);
    }

    public HHWPFDocument(String filePath) throws Exception {
        this(Paths.get(filePath).toFile());
    }

    public HHWPFDocument(File file) throws Exception {
        try(InputStream is = new FileInputStream(file); POIFSFileSystem fs = new POIFSFileSystem(is)) {
            this.read(fs.getRoot());
        }
    }

    public HHWPFDocument(InputStream is) throws Exception {
        try (POIFSFileSystem fs = new POIFSFileSystem(is)) {
            this.read(fs.getRoot());
        };

    }


    // =====================
    // == Getter
    // =====================
    public FileHeader getFileHeader() {
        return fileHeader;
    }

    public DocInfo getDocInfo() {
        return docInfo;
    }

    public BodyText getBodyText() {
        return bodyText;
    }

    public HwpSummaryInformation getHwpSummaryInformation() {
        return hwpSummaryInformation;
    }

    public BinData getBinData() {
        return binData;
    }

    public Scripts getScripts() {
        return scripts;
    }



    // =====================
    // == Reader
    // =====================
    private void read(DirectoryNode storage) throws Exception {
        readFileHeader(storage);
        readDocInfo(storage);
        if(this.fileHeader.isRestricted()) {
            /**
             * 배포용인 경우 여러가지 제한 옵션을 걸 수 있음.
             */
            this.readDocBody((DirectoryNode) storage.getEntry(StorageID.POIFS_STORAGE_VIEW_TEXT));
        } else {
            this.readDocBody((DirectoryNode) storage.getEntry(StorageID.POIFS_STORAGE_BODY_TEXT));
        }
        readSummaryInformation(storage);

        if(storage.hasEntry(StorageID.POIFS_STORAGE_BIN_DATA)) {
            this.readBinData((DirectoryNode) storage.getEntry(StorageID.POIFS_STORAGE_BIN_DATA));
        } else {
            this.binData = new BinData();
        }

        if(storage.hasEntry(StorageID.POIFS_STORAGE_SCRIPTS)) {
            this.readScripts((DirectoryNode) storage.getEntry(StorageID.POIFS_STORAGE_SCRIPTS));
        } else {
            this.scripts = new Scripts(COMPRESSED_JS_VERSION, COMPRESSED_DEFAULT_JS_VERSION);
        }

    }

    private void readFileHeader(DirectoryNode storage) throws IOException {
        try( InputStream is = this.getDocumentStream(storage, StreamID.POIFS_STREAM_FILE_HEADER) ) {
            this.fileHeader = new FileHeader(is);
        }
    }

    private void readDocInfo(DirectoryNode storage) throws IOException, IllegalAccessException {
        try( InputStream is = this.getDocumentStream(storage, StreamID.POIFS_STREAM_DOC_INFO,
                this.fileHeader.isCompressed()) ) {
            this.docInfo = new DocInfo(is, this.fileHeader);
        }
    }

    private void readDocBody(DirectoryNode storage) throws Exception {
        this.bodyText = new BodyText(storage, this.fileHeader, this.docInfo);
    }

    private void readSummaryInformation(DirectoryNode storage) {
        try( InputStream is = this.getDocumentStream(storage, StreamID.POIFS_STREAM_SUMMARY_INFORMATION) ) {
            if(is != null) {
                HwpPropertySet propertySet = new HwpPropertySet(is);
                this.hwpSummaryInformation = new HwpSummaryInformation(propertySet);
            }
        } catch (UnexpectedPropertySetTypeException | NoPropertySetStreamException e) {
            throw new UnexpectedFileFormatException(String.format("Unexpected SummaryInformation structure: %s", e.getMessage()));
        } catch (IOException e) {
            // passthrough 없을 수 있기 때문임.
        }
    }

    private void readBinData(DirectoryNode storage) throws IOException {
        // storage 에 존재하는 모든 entry 불러와 목록화
        this.binData = new BinData(storage, this.fileHeader, this.docInfo);
    }

    private void readScripts(DirectoryNode storage) throws IOException {
        this.scripts = new Scripts(storage);
    }



    // =============================
    // == Writter
    // =============================
    public void writeTo(Path filePath) throws Exception {
        this.writeTo(filePath.toString());
    }

    public void writeTo(String filePath) throws Exception {
        try(OutputStream os = new FileOutputStream(filePath);) {
            this.writeTo(os);
        }
    }

    public void writeTo(File file) throws Exception {
        try(OutputStream os = new FileOutputStream(file)) {
            this.writeTo(os);
        }
    }

    public void writeTo(OutputStream os) throws Exception {
        try (POIFSFileSystem fs = new POIFSFileSystem()) {
            this.writeTo(fs.getRoot());
            fs.writeFilesystem(os);
        }
    }

    private void writeTo(DirectoryNode storage) throws Exception {
        if (this.getFileHeader().isEnclosed()) {
            throw new Exception("Files enclosed with passwords are not supported.");
        }
        this.updateState(); // autoset 참조하여 재작성.
        this.writeFileHeader(storage);
        this.writeDocInfo(storage);
        this.writeBodyText(storage);
        this.writeBinData(storage);
        this.writeSummaryInformation(storage);
        this.writeScripts(storage);
        this.writeDocOptions(storage);
    }


    /**
     * Section, DocInfo, IDMappings... 의 속성들을 동기화 (최신정보로 세팅)
     */
    private void updateState() {
        InstanceID iid = new InstanceID();
        this.docInfo.getDocumentProperties().setSectionCount(this.bodyText.getSectionList().size());
        this.docInfo.getIdMappings().updateState(this.docInfo);
        for(Section s : this.bodyText.getSectionList()) {
            s.updateState(iid);
        }
    }

    private void writeFileHeader(DirectoryNode storage) throws IOException {
        try(StreamWriter sw = new StreamWriter(StreamID.POIFS_STREAM_FILE_HEADER,false, this.fileHeader.getFileVersion())) {
            this.fileHeader.write(sw);
            try(InputStream is = sw.getDataStream()) {
                storage.createDocument(StreamID.POIFS_STREAM_FILE_HEADER, is);
            }
        }
    }

    private void writeDocInfo(DirectoryNode storage) throws Exception {
        try(StreamWriter sw = new StreamWriter(StreamID.POIFS_STREAM_DOC_INFO, this.fileHeader.isCompressed(), this.fileHeader.getFileVersion())) {
            this.docInfo.write(sw);
            try(InputStream is = sw.getDataStream()) {
                storage.createDocument(StreamID.POIFS_STREAM_DOC_INFO, is);
            }
        }
    }

    private void writeBodyText(DirectoryNode parentNode) throws IOException {
        DirectoryEntry storage = parentNode.createDirectory(StorageID.POIFS_STORAGE_BODY_TEXT);
        int index = 0;
        for (Section section : this.bodyText.getSectionList()) {
            try(StreamWriter sw = new StreamWriter(StreamID.POIFS_STREAM_SECTION + index, this.fileHeader.isCompressed(), this.fileHeader.getFileVersion(), this.docInfo)) {
                section.write(sw);
                if(this.bodyText.getSectionList().size()==index+1) {
                    if(this.bodyText.getMemoList()!=null) {
                        for(Memo memo : this.bodyText.getMemoList()) {
                            memo.write(sw);
                        }
                    }
                }
                try(InputStream is = sw.getDataStream()) {
                    storage.createDocument(sw.getName(), is);
//                    logger.info("Section created and saved");
                }
            }
            index++;
        }

    }

    private void writeBinData(DirectoryNode parentNode) throws IOException {
        if(this.getBinData().getEmbeddedBinaryDataList().size()==0) {
            return;
        }
        DirectoryEntry storage = parentNode.createDirectory(StorageID.POIFS_STORAGE_BIN_DATA);
        for(EmbeddedBinaryData ebd : this.getBinData().getEmbeddedBinaryDataList()) {
            try(StreamWriter sw = new StreamWriter(ebd.getName(), this.isCompressedBinData(ebd.getCompressMethod()), this.fileHeader.getFileVersion())) {
                sw.writeBytes(ebd.getData());
                try(InputStream is = sw.getDataStream()) {
                    storage.createDocument(sw.getName(), is);
                }
            }
        }
    }

    private boolean isCompressedBinData(BinDataCompress method) {
        switch (method) {
            case ByStorageDefault:
                return this.fileHeader.isCompressed();
            case Compress:
                return true;
            case NoCompress:
                return false;
        }
        return false;
    }

    private void writeSummaryInformation(DirectoryNode storage) throws IOException {
        if(this.getHwpSummaryInformation() == null) {
            return;
        }
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            this.getHwpSummaryInformation().write(bos);
            if (bos.size() > 0) {
                try(InputStream is = new ByteArrayInputStream(bos.toByteArray())) {
                    storage.createDocument(StreamID.POIFS_STREAM_SUMMARY_INFORMATION, is);
                }
            }
        } catch (WritingNotSupportedException e) {
            throw new IOException(e);
        }
    }

    private void writeScripts(DirectoryNode parentNode) throws IOException {
        DirectoryEntry storage = parentNode.createDirectory(StorageID.POIFS_STORAGE_SCRIPTS);
        if(this.getScripts().getDefaultJScript() != null) {
            try(StreamWriter sw = new StreamWriter(StreamID.POIFS_STREAM_DEFAULT_JSCRIPT, this.fileHeader.isCompressed(), this.fileHeader.getFileVersion())) {
                sw.writeBytes(this.getScripts().getDefaultJScript());
                try(InputStream is = sw.getDataStream()) {
                    storage.createDocument(sw.getName(), is);
                }
            }
        }
        if(this.getScripts().getJScriptVersion() != null) {
            try(StreamWriter sw = new StreamWriter(StreamID.POIFS_STREAM_JSCRIPT_VERSION, this.fileHeader.isCompressed(), this.fileHeader.getFileVersion())) {
                sw.writeBytes(this.getScripts().getJScriptVersion());
                try(InputStream is = sw.getDataStream()) {
                    storage.createDocument(sw.getName(), is);
                }
            }
        }
    }

    private void writeDocOptions(DirectoryNode parentNode) throws IOException {
        DirectoryEntry storage = parentNode.createDirectory(StorageID.POIFS_STORAGE_DOC_OPTIONS);
    }







}
