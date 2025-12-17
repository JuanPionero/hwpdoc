package ext.org.apache.poi.hhwpf.model;

import ext.org.apache.poi.hhwpf.Specification;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.FileVersion;
import ext.org.apache.poi.hhwpf.util.binary.BitFlag;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import static ext.org.apache.poi.hhwpf.Specification.DEFAULT_FILE_VERSION_LONG;

public class FileHeader {

    private final byte[] signature;
    private final FileVersion fileVersion;

    public FileHeader(long fileVersion) {
        this.signature = Specification.HWP_DOCUMENT_FILE.clone();
        this.fileVersion = new FileVersion(fileVersion);
        this.compressed = false;
        this.enclosed = false;
        this.restricted = false;
        this.hasScript = false;
        this.drmApplied = false;
        this.hasXMLTemplate = false;
        this.hasDocumentHistory = false;
        this.hasDigitalSignature = false;
        this.certificated = false;
        this.savedForDigitalSigning = false;
        this.secureWithPublicCertificateDRM = false;
        this.hasCreativeCommonLicense = false;
    }

    public FileHeader() {
        this(DEFAULT_FILE_VERSION_LONG);
    }

    public FileHeader(InputStream is) throws IOException {
        StreamReader sr = new StreamReader(is);

        this.signature = sr.readBytes(32);
        if(!Arrays.equals(Specification.HWP_DOCUMENT_FILE, this.signature)) {
            throw new UnexpectedFileFormatException();
        }

        this.fileVersion = new FileVersion( sr.readUInt4() );

        // some flags in FileHeader
        // 명칭
        long flag = sr.readUInt4();
        this.compressed = BitFlag.get(flag, 0);
        this.enclosed = BitFlag.get(flag, 1);
        this.restricted = BitFlag.get(flag, 2);
        this.hasScript = BitFlag.get(flag, 3);
        this.drmApplied = BitFlag.get(flag, 4);
        this.hasXMLTemplate = BitFlag.get(flag, 5);
        this.hasDocumentHistory = BitFlag.get(flag, 6);
        this.hasDigitalSignature = BitFlag.get(flag, 7);
        this.certificated = BitFlag.get(flag, 8);
        this.savedForDigitalSigning = BitFlag.get(flag, 9);
        this.secureWithPublicCertificateDRM = BitFlag.get(flag, 10);
        this.hasCreativeCommonLicense = BitFlag.get(flag, 11);

    }

    public byte[] getSignature() {
        return signature;
    }

    public FileVersion getFileVersion() {
        return fileVersion;
    }



    /**
     * 압축 여부
     */
    private boolean compressed;
    /**
     * 암호 설정 여부
     */
    private boolean enclosed;
    /**
     * 배포용(제한된) 문서 여부
     * 기존 코드에서는 distribution 이라고 사용했었음.
     * 기능 제한을 걸었다를 표시하는 플래그이기 때문임. 제한된 문서의 내부 stream은 복호화를 거쳐야 함.
     */
    private boolean restricted;
    /**
     * 스크립트 저장 여부
     */
    private boolean hasScript;
    /**
     * DRM 보안 문서 여부
     */
    private boolean drmApplied;
    /**
     * XMLTemplate 스토리지 존재 여부
     */
    private boolean hasXMLTemplate;
    /**
     * 문서 이력 관리 존재 여부
     */
    private boolean hasDocumentHistory;
    /**
     * 전자 서명 정보 존재 여부
     */
    private boolean hasDigitalSignature;
    /**
     * 공인 인증서 암호화 여부
     * EncryptPublicCertification
     */
    private boolean certificated;
    /**
     * 전자 서명 예비 저장 여부
     */
    private boolean savedForDigitalSigning;
    /**
     * 공인 인증서 DRM 보안 문서 여부
     * PublicCertificationDRMDocument
     */
    private boolean secureWithPublicCertificateDRM;
    /**
     * CCL 문서 여부
     * CreativeCommonLicense
     */
    private boolean hasCreativeCommonLicense;

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public boolean isEnclosed() {
        return enclosed;
    }

    public void setEnclosed(boolean enclosed) {
        this.enclosed = enclosed;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }

    public boolean hasScript() {
        return hasScript;
    }

    public void setHasScript(boolean hasScript) {
        this.hasScript = hasScript;
    }

    public boolean isDrmApplied() {
        return drmApplied;
    }

    public void setDrmApplied(boolean drmApplied) {
        this.drmApplied = drmApplied;
    }

    public boolean hasXMLTemplate() {
        return hasXMLTemplate;
    }

    public void setHasXMLTemplate(boolean hasXMLTemplate) {
        this.hasXMLTemplate = hasXMLTemplate;
    }

    public boolean hasDocumentHistory() {
        return hasDocumentHistory;
    }

    public void setHasDocumentHistory(boolean hasDocumentHistory) {
        this.hasDocumentHistory = hasDocumentHistory;
    }

    public boolean hasDigitalSignature() {
        return hasDigitalSignature;
    }

    public void setHasDigitalSignature(boolean hasDigitalSignature) {
        this.hasDigitalSignature = hasDigitalSignature;
    }

    public boolean isCertificated() {
        return certificated;
    }

    public void setCertificated(boolean certificated) {
        this.certificated = certificated;
    }

    public boolean isSavedForDigitalSigning() {
        return savedForDigitalSigning;
    }

    public void setSavedForDigitalSigning(boolean savedForDigitalSigning) {
        this.savedForDigitalSigning = savedForDigitalSigning;
    }

    public boolean isSecureWithPublicCertificateDRM() {
        return secureWithPublicCertificateDRM;
    }

    public void setSecureWithPublicCertificateDRM(boolean secureWithPublicCertificateDRM) {
        this.secureWithPublicCertificateDRM = secureWithPublicCertificateDRM;
    }

    public boolean hasCreativeCommonLicense() {
        return hasCreativeCommonLicense;
    }

    public void setHasCreativeCommonLicense(boolean hasCreativeCommonLicense) {
        this.hasCreativeCommonLicense = hasCreativeCommonLicense;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeBytes(this.signature);
        sw.writeUInt4(this.fileVersion.getVersion());
        long properties = 0;
        properties = BitFlag.set(properties, 0, this.isCompressed());
        properties = BitFlag.set(properties, 1, this.isEnclosed());
        properties = BitFlag.set(properties, 2, this.isRestricted());
        properties = BitFlag.set(properties, 3, this.hasScript());
        properties = BitFlag.set(properties, 4, this.isDrmApplied());
        properties = BitFlag.set(properties, 5, this.hasXMLTemplate());
        properties = BitFlag.set(properties, 6, this.hasDocumentHistory());
        properties = BitFlag.set(properties, 7, this.hasDigitalSignature());
        properties = BitFlag.set(properties, 8,
                this.isCertificated());
        properties = BitFlag.set(properties, 9, this.isSavedForDigitalSigning());
        properties = BitFlag.set(properties, 10,
                this.isSecureWithPublicCertificateDRM());
        properties = BitFlag.set(properties, 11, this.hasCreativeCommonLicense());
        sw.writeUInt4(properties);
        sw.writeZero(216);
    }
}
