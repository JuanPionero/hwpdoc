package ext.org.apache.poi.hhwpf.model.storage;


import ext.org.apache.poi.hhwpf.POIDocumentLikeForHWP;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.FileHeader;
import ext.org.apache.poi.hhwpf.model.datarecord.bindata.BinDataCompress;
import ext.org.apache.poi.hhwpf.model.structure.bindata.EmbeddedBinaryData;
import ext.org.apache.poi.hhwpf.model.structure.section.paragraph.Paragraph;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

/**
 * 바이너리 데이터를 나타내는 객체 HWP파일 내의 "BinData" storage에 저장된다.
 *
 * @author neolord
 */
public class BinData extends POIDocumentLikeForHWP {
    private static final Logger logger = LoggerFactory.getLogger(BinData.class);
    /**
     * HWP 파일 속에 첨부된 바이너리 데이터(이미지 등)의 리스트
     */
    private ArrayList<EmbeddedBinaryData> embeddedBinaryDataList;

    /**
     * 생성자
     */
    public BinData() {
        this.embeddedBinaryDataList = new ArrayList<EmbeddedBinaryData>();
    }

    public BinData(DirectoryNode storage, FileHeader fileHeader, DocInfo docInfo) throws IOException {
        this.embeddedBinaryDataList = new ArrayList<EmbeddedBinaryData>();
        Set<String> entryNameSet = storage.getEntryNames();
        for (String name : entryNameSet) {
            // 16진수 형태의 스트링을 정수로
            int id = Integer.parseInt(name.substring(3, 7), 16);

            ext.org.apache.poi.hhwpf.model.datarecord.BinData binDataMeta = null;
            BinDataCompress compressMethod = BinDataCompress.ByStorageDefault;

            try {
                binDataMeta = docInfo.getBinDataList().get(id - 1);
                if (binDataMeta != null) {
                    compressMethod = binDataMeta.getProperty().getCompress();
                }
            } catch (Exception e) {
                // ignore it
            }

            try (InputStream is = this.getDocumentStream(storage, name,
                    isCompressBinData(compressMethod, fileHeader))) {
                StreamReader sr = new StreamReader(is, fileHeader, docInfo);
                byte[] data = sr.readRestOfStream();
                embeddedBinaryDataList.add(new EmbeddedBinaryData(name, data, compressMethod));
            }
        }
    }

    private boolean isCompressBinData(BinDataCompress compressMethod, FileHeader fileHeader) {
        return switch (compressMethod) {
            case ByStorageDefault -> fileHeader.isCompressed();
            case Compress -> true;
            case NoCompress -> false;
        };
    }

    /**
     * 새로운 첨부된 바이너리 데이터 객체를 생성하고 list에 추가합니다.
     *
     * @return 새로 생성된 첨부된 바이너리 데이터 객체
     */
    public EmbeddedBinaryData addNewEmbeddedBinaryData() {
        EmbeddedBinaryData ebd = new EmbeddedBinaryData();
        embeddedBinaryDataList.add(ebd);
        return ebd;
    }

    /**
     * 첨부된 바이너리 데이터의 리스트를 반환한다.
     *
     * @return 첨부된 바이너리 데이터의 리스트;
     */
    public ArrayList<EmbeddedBinaryData> getEmbeddedBinaryDataList() {
        return embeddedBinaryDataList;
    }

    /**
     * 새로운 첨부된 바이너리 데이터 객체를 생성하여 list에 추가합니다.
     *
     * @param name           새로운 첨부된 바이너리 데이터 객체의 이름
     * @param data           새로운 첨부된 바이너리 데이터 객체의 데이터
     * @param compressMethod 암축 방법
     */
    public void addNewEmbeddedBinaryData(String name, byte[] data, BinDataCompress compressMethod) {
        EmbeddedBinaryData ebd = addNewEmbeddedBinaryData();
        ebd.setName(name);
        ebd.setData(data);
        ebd.setCompressMethod(compressMethod);
    }

    public void copy(BinData from, boolean deepCopyImage) {
        embeddedBinaryDataList.clear();
        for (EmbeddedBinaryData embeddedBinaryData : from.embeddedBinaryDataList) {
            embeddedBinaryDataList.add(embeddedBinaryData.clone(deepCopyImage));
        }
    }
}
