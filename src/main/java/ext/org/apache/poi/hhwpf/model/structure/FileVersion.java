package ext.org.apache.poi.hhwpf.model.structure;

import static ext.org.apache.poi.hhwpf.Specification.DEFAULT_FILE_VERSION_LONG;

/**
 * hwplib의 원작자 neolord님의 ForFileHeader코드를 다른 방법으로 구현
 * @author Seung Hoon Lee (juanlee0@naver.com)
 */
public class FileVersion {
    /**
     * 파일 버전 - MM
     */
    private final short mm;
    /**
     * 파일 번전 - nn
     */
    private final short nn;
    /**
     * 파일 버전 - PP
     */
    private final short pp;
    /**
     * 파일 버전 - rr
     */
    private final short rr;

    public FileVersion(short mm, short nn, short pp, short rr) {
        this.mm = mm;
        this.nn = nn;
        this.pp = pp;
        this.rr = rr;
    }

//    public FileVersion() {
//        this((short) 5, (short) 0, (short) 3, (short) 4);
//    }
    public FileVersion() {
        this(DEFAULT_FILE_VERSION_LONG);
    }

    public FileVersion(long version) {
        this.mm = (short) ((version & 0xff000000) >> 24);
        this.nn = (short) ((version & 0xff0000) >> 16);
        this.pp = (short) ((version & 0xff00) >> 8);
        this.rr = (short) (version & 0xff);
    }

    public long getVersion() {
        long version = (long) (this.mm & 0xff) << 24;
        version += (this.nn & 0xff) << 16;
        version += (this.pp & 0xff) << 8;
        version += (this.rr & 0xff);
        return version;
    }




    /**
     * 파일 버전 - MM를 반환한다.
     *
     * @return 파일 버전 - MM(0~255)
     */
    public short getMM() {
        return this.mm;
    }

    /**
     * 파일 버전 - nn를 반환한다.
     *
     * @return 파일 버전 - nn(0~255)
     */
    public short getNN() {
        return this.nn;
    }

    /**
     * 파일 버전 - PP를 반환한다.
     *
     * @return 파일 버전 - PP(0~255)
     */
    public short getPP() {
        return this.pp;
    }

    /**
     * 파일 버전 - rr를 반환한다.
     *
     * @return 파일 버전 - rr(0~255)
     */
    public short getRR() {
        return this.rr;
    }



    /**
     * 현재 버전이 비교 버전(mm2,nn2,pp2,rr2)보다 상위 버전인지 여부를 반환한다.
     *
     * @param mm2 비교 버전 - MM
     * @param nn2 비교 버전 - nn
     * @param pp2 비교 버전 - PP
     * @param rr2 비교 버전 - rr
     * @return 현재 버전이 비교 버전(mm2,nn2,pp2,rr2)보다 상위 버전인지 여부
     */
    public boolean isGreaterEqual(short mm2, short nn2, short pp2, short rr2) {
        return (this.mm > mm2)
                || (this.mm == mm2 && this.nn > nn2)
                || (this.mm == mm2 && this.nn == nn2 && this.pp > pp2)
                || (this.mm == mm2 && this.nn == nn2 && this.pp == pp2 && this.rr > rr2)
                || (this.mm == mm2 && this.nn == nn2 && this.pp == pp2 && this.rr == rr2);
    }

    public boolean isGreaterEqual(int mm2, int nn2, int pp2, int rr2) {
        return this.isGreaterEqual((short) mm2, (short) nn2, (short) pp2, (short) rr2);
    }

    /**
     * 버젼 문자열로 반환한다.
     *
     * @return 버젼 문자열
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.mm).append(".").append(this.nn).append(".")
                .append(this.pp).append(".").append(this.rr).append(".");
        return sb.toString();
    }

    public FileVersion clone() {
        return new FileVersion(this.mm, this.nn, this.pp, this.rr);
    }
}
