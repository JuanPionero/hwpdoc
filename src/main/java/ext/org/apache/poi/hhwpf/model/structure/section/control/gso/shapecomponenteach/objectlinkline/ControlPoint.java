package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponenteach.objectlinkline;

import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

public class ControlPoint implements StreamWritable {
    private long x;
    private long y;
    private int type;

    public ControlPoint() {
    }

    public ControlPoint(long x, long y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void copy(ControlPoint from) {
        x = from.x;
        y = from.y;
        type = from.type;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt4(this.getX());
        sw.writeUInt4(this.getY());
        sw.writeUInt2(this.getType());
    }
}