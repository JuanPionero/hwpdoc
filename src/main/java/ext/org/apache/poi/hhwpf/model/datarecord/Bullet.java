package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.DocInfo;
import ext.org.apache.poi.hhwpf.model.etc.HWPString;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.fillinfo.PictureInfo;
import ext.org.apache.poi.hhwpf.model.datarecord.numbering.ParagraphHeadInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 글머리표에 대한 레코드
 *
 * @author neolord
 */
public class Bullet {
    private static final Logger logger = LoggerFactory.getLogger(Bullet.class);
    /**
     * 문단 머리의 정보
     */
    private ParagraphHeadInfo paragraphHeadInfo;
    /**
     * 글머리표 문자
     */
    private HWPString bulletChar;
    /**
     * 그림 글머리표 여부
     */
    private boolean imageBullet;
    /**
     * 그림 글머리표 정보
     */
    private PictureInfo imageBulletInfo;
    /**
     * 체크 글머리표 문자
     */
    private HWPString checkBulletChar;

    /**
     * 생성자
     */
    public Bullet() {
        paragraphHeadInfo = new ParagraphHeadInfo();
        bulletChar = new HWPString();
        imageBullet = false;
        imageBulletInfo = new PictureInfo();
        checkBulletChar = new HWPString();
    }

    public Bullet(StreamReader sr) throws IOException, IllegalAccessException {
        logger.trace("Reading From Stream");
        this.paragraphHeadInfo = new ParagraphHeadInfo(sr);
        this.bulletChar = new HWPString(sr.readWChar());
        if(sr.isEndOfDataRecord()) {
            return;
        }
        long imageBulletIndicator = sr.readUInt4();
        this.imageBullet = (imageBulletIndicator==1);
        this.imageBulletInfo = new PictureInfo(sr);

        if(sr.isEndOfDataRecord()) {
            return;
        }
        this.checkBulletChar = new HWPString(sr.readWChar());
    }

    /**
     * 문단 머리의 정보에 대한 객체를 반환한다.
     *
     * @return 문단 머리의 정보에 대한 객체
     */
    public ParagraphHeadInfo getParagraphHeadInfo() {
        return paragraphHeadInfo;
    }

    /**
     * 글머리표 문자를 반환한다.
     *
     * @return 글머리표 문자
     */
    public HWPString getBulletChar() {
        return bulletChar;
    }

    public boolean getImageBullet() {
        return imageBullet;
    }

    public void setImageBullet(boolean imageBullet) {
        this.imageBullet = imageBullet;
    }

    public PictureInfo getImageBulletInfo() {
        return imageBulletInfo;
    }

    public HWPString getCheckBulletChar() {
        return checkBulletChar;
    }

    public Bullet clone() {
        Bullet cloned = new Bullet();
        cloned.paragraphHeadInfo.copy(paragraphHeadInfo);
        cloned.bulletChar.copy(bulletChar);
        cloned.imageBullet = imageBullet;
        cloned.imageBulletInfo.copy(imageBulletInfo);
        cloned.checkBulletChar.copy(checkBulletChar);
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_BULLET, 25);
        this.paragraphHeadInfo.write(sw);
        sw.writeWChar(this.getBulletChar().getBytes());
        sw.writeUInt4(this.imageBullet ? 1 : 0);
        this.imageBulletInfo.write(sw);
        sw.writeWChar(this.checkBulletChar.getBytes());
    }
}
