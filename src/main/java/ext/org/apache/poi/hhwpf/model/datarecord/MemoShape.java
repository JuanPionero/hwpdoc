package ext.org.apache.poi.hhwpf.model.datarecord;


import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.etc.Color4Byte;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderThickness;
import ext.org.apache.poi.hhwpf.model.datarecord.borderfill.BorderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MemoShape {
    private static final Logger logger = LoggerFactory.getLogger(MemoShape.class);

    private long width;
    private BorderType lineType;
    private BorderThickness lineWidth;
    private Color4Byte lineColor;
    private Color4Byte fillColor;
    private Color4Byte activeColor;
    private long unknown;


    public MemoShape() {
        lineType = BorderType.None;
        lineWidth = BorderThickness.MM0_1;
        lineColor = new Color4Byte();
        fillColor = new Color4Byte();
        activeColor = new Color4Byte();
        unknown = 0;
    }

    public MemoShape(StreamReader sr) throws IOException {
        logger.trace("Reading From Stream");
        this.width = sr.readUInt4();
        this.lineType = BorderType.valueOf(sr.readSInt1());
        this.lineWidth = BorderThickness.valueOf(sr.readSInt1());
        this.lineColor = new Color4Byte(sr);
        this.fillColor = new Color4Byte(sr);
        this.activeColor = new Color4Byte(sr);
        this.unknown = sr.readUInt4();
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public BorderType getLineType() {
        return lineType;
    }

    public void setLineType(BorderType lineType) {
        this.lineType = lineType;
    }

    public BorderThickness getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(BorderThickness lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color4Byte getLineColor() {
        return lineColor;
    }

    public Color4Byte getFillColor() {
        return fillColor;
    }

    public Color4Byte getActiveColor() {
        return activeColor;
    }

    public long getUnknown() {
        return unknown;
    }

    public void setUnknown(long unknown) {
        this.unknown = unknown;
    }

    public MemoShape clone() {
        MemoShape cloned = new MemoShape();
        cloned.width = this.width;
        cloned.lineType = this.lineType;
        cloned.lineWidth = this.lineWidth;
        cloned.lineColor.copy(this.lineColor);
        cloned.fillColor.copy(this.fillColor);
        cloned.activeColor.copy(this.activeColor);
        cloned.unknown = this.unknown;
        return cloned;
    }

    public void write(StreamWriter sw) throws IOException {
        sw.writeDataRecordHeader(TagID.HWPTAG_MEMO_SHAPE, 22);

        sw.writeUInt4(this.width);
        sw.writeSInt1(this.lineType.getValue());
        sw.writeSInt1(this.lineWidth.getValue());
        sw.writeUInt4(this.lineColor.getValue());
        sw.writeUInt4(this.fillColor.getValue());
        sw.writeUInt4(this.activeColor.getValue());
        sw.writeUInt4(this.unknown);
    }
}
