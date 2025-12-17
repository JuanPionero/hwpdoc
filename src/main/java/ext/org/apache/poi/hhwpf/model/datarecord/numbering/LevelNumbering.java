package ext.org.apache.poi.hhwpf.model.datarecord.numbering;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;

import java.io.IOException;

/**
 * 각 수준(1~7)에 해당하는 문단 번호 정보
 *
 * @author neolord
 */
public class LevelNumbering {
    /**
     * 문단 머리 정보
     */
    private ParagraphHeadInfo paragraphHeadInfo;
    /**
     * 번호 형식
     */
    private HWPString numberFormat;
    /**
     * 수준별 시작번호 (5.0.2.5 이상)
     */
    private long startNumber;

    /**
     * 생성자
     */
    public LevelNumbering() {
        paragraphHeadInfo = new ParagraphHeadInfo();
        numberFormat = new HWPString();
    }

    public LevelNumbering(int levelNum) {
        if(levelNum>10 || levelNum<1) {
            throw new IllegalStateException("Unexpected input argument: " + levelNum);
        }
        this.startNumber = levelNum<8?1:0;
        this.paragraphHeadInfo = switch (levelNum) {
            case 1, 3, 5 -> new ParagraphHeadInfo(12, 0,50, -1);
            case 2, 4, 6 -> new ParagraphHeadInfo(268, 0,50, -1);
            case 7 -> new ParagraphHeadInfo(44, 0,50, -1);
            case 8, 9, 10 -> new ParagraphHeadInfo(0, 0,0, 0);
            default -> null;
        };

        this.numberFormat = new HWPString(switch (levelNum) {
            case 1 -> "^1.";
            case 2 -> "^2.";
            case 3 -> "^3)";
            case 4 -> "^4)";
            case 5 -> "(^5)";
            case 6 -> "(^6)";
            case 7 -> "^7";
            case 8,9,10-> null;
            default -> null;
        });

    }

    public LevelNumbering(StreamReader sr) throws IOException {
        this.paragraphHeadInfo = new ParagraphHeadInfo(sr);
        this.numberFormat = new HWPString( sr );
    }

    /**
     * 문단 머리 정보에 대한 객체를 반환한다.
     *
     * @return 문단 머리 정보에 대한 객체
     */
    public ParagraphHeadInfo getParagraphHeadInfo() {
        return paragraphHeadInfo;
    }

    /**
     * 번호 형식을 반환한다.
     *
     * @return 번호 형식
     */
    public HWPString getNumberFormat() {
        return numberFormat;
    }

    /**
     * 수준별 시작번호를 반환한다.
     *
     * @return 수준별 시작번호
     */
    public long getStartNumber() {
        return startNumber;
    }

    /**
     * 수준별 시작번호를 설정한다.
     *
     * @param startNumber 수준별 시작번호
     */
    public void setStartNumber(long startNumber) {
        this.startNumber = startNumber;
    }

    public void copy(LevelNumbering from) {
        paragraphHeadInfo.copy(from.paragraphHeadInfo);
        numberFormat.copy(from.numberFormat);
        startNumber = from.startNumber;
    }
}
