package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.datarecord.numbering.ParagraphHeadInfo;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import ext.org.apache.poi.hhwpf.model.datarecord.numbering.LevelNumbering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 문단 번호 레코드
 *
 * @author neolord
 */
public class Numbering {
    private static final Logger logger = LoggerFactory.getLogger(Numbering.class);
    /**
     * 수준(1～7)에 해당하는 문단 번호 정보 객체의 리스트
     */
    private ArrayList<LevelNumbering> levelNumberingList;
    /**
     * 시작 번호
     */
    private int startNumber;

    /**
     * 생성자
     */
    public Numbering() {
        createLevelNumberings();
    }
    public Numbering(StreamReader sr) throws IOException, IllegalAccessException {
        logger.trace("Constructing {} by reading stream", this.getClass().getSimpleName());
        levelNumberingList = new ArrayList<LevelNumbering>();
        // 버전과 무관하게 1~7 은 무조건 등록: arrayList로
        for (int level = 1; level <= 7; level++) {
            levelNumberingList.add(new LevelNumbering(sr));
        }
        this.startNumber = sr.readUInt2();

        final FileVersion fileVersion = sr.getFileHeader().getFileVersion();
        // >= 5.0.2.5
        if (!sr.isEndOfDataRecord() && fileVersion.isGreaterEqual((short) 5, (short) 0, (short) 2, (short) 5)) {
            for (int level = 1; level <= 7; level++) {
                levelNumberingList.get(level - 1).setStartNumber(sr.readUInt4());
            }
        }

        if(sr.isEndOfDataRecord()) {
            for (int level = 8; level <= 10; level++) {
                levelNumberingList.add(new LevelNumbering());
            }
            return;
        }

        for (int level = 8; level <= 10; level++) {
            levelNumberingList.add(new LevelNumbering(sr));
        }
        for (int level = 8; level <= 10; level++) {
            levelNumberingList.get(level - 1).setStartNumber(sr.readUInt4());
        }

    }

    /**
     * 수준(1～10)에 해당하는 문단 번호 정보 객체를 생성한다.
     * 5.0.2.5 이상 부터 8~10 추가됨
     */
    private void createLevelNumberings() {
        levelNumberingList = new ArrayList<LevelNumbering>();
        for (int index = 0; index < 10; index++) {
            LevelNumbering ln = new LevelNumbering(index+1);
            levelNumberingList.add(ln);
        }
    }

    /**
     * level에 해당하는 문단 번호 정보 객체를 반환한다.
     *
     * @param level 문단 번호 정보 객체를 얻고자 하는 수준(1~7)
     * @return level에 해당하는 문단 번호 정보 객체
     * @throws Exception (level &lt; 1 || level &gt; 7) 일떼 발샐한다.
     */
    public LevelNumbering getLevelNumbering(int level) throws Exception {
        if (level >= 1 && level <= 10) {
            return levelNumberingList.get(level - 1);
        } else {
            throw new Exception("invalid level : " + level);
        }
    }

    public ArrayList<LevelNumbering> getLevelNumberingList() {
        return levelNumberingList;
    }

    /**
     * 시작 번호를 반환한다.
     *
     * @return 시작 번호
     */
    public int getStartNumber() {
        return startNumber;
    }

    /**
     * 시작 번호를 설정한다.
     *
     * @param startNumber 시작 번호
     */
    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public Numbering clone() {
        Numbering cloned = new Numbering();

        for (int index = 0; index < 0; index++) {
            cloned.levelNumberingList.get(index).copy(levelNumberingList.get(index));
        }

        cloned.startNumber = startNumber;

        return cloned;
    }

    public void write(StreamWriter sw) throws Exception {
        sw.writeDataRecordHeader(TagID.HWPTAG_NUMBERING, this.getSize(sw.getFileVersion()));

        for (int level = 1; level <= 7; level++) {
            this.writeLevelNumbering(this.getLevelNumbering(level), sw);
        }
        sw.writeUInt2(this.getStartNumber());
        if (sw.getFileVersion().isGreaterEqual(5, 0, 2, 5)) {
            for (int level = 1; level <= 7; level++) {
                sw.writeUInt4(this.getLevelNumbering(level).getStartNumber());
            }

            for (int level = 8; level <= 10; level++) {
                this.writeLevelNumbering(this.getLevelNumbering(level), sw);
            }
            for (int level = 8; level <= 10; level++) {
                sw.writeUInt4(this.getLevelNumbering(level).getStartNumber());
            }
        }
    }

    /**
     * 문단 번호 레코드의 크기를 반환한다.
     *
     * @param version 파일 버전
     * @return 문단 번호 레코드의 크기
     * @throws Exception
     */
    public final int getSize(FileVersion version) throws Exception {
        int size = 0;
        for (int level = 1; level <= 7; level++) {
            LevelNumbering ln = this.getLevelNumbering(level);
            size += 12 + ln.getNumberFormat().getWCharsSize();
        }
        size += 2;
        if (version.isGreaterEqual(5, 0, 2, 5)) {
            size += 4 * 7;

            for (int level = 8; level <= 10; level++) {
                LevelNumbering ln = this.getLevelNumbering(level);
                size += 12 + ln.getNumberFormat().getWCharsSize();
            }
            size += 4 * 3;
        }
        return size;
    }

    /**
     * 하나의 레벨에 해당하는 문단 번호 정보을 쓴다.
     *
     * @param ln 하나의 레벨에 해당하는 문단 번호 정보
     * @param sw 스트림 라이터
     * @throws IOException
     */
    public final void writeLevelNumbering(LevelNumbering ln, StreamWriter sw)
            throws IOException {
        ln.getParagraphHeadInfo().write(sw);
        sw.writeHWPString(ln.getNumberFormat());
    }

}
