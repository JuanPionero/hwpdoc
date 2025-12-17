package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.renderingnfo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 확장/회전을 위한 행렬의 쌍을 나타내는 객체
 *
 * @author neolord
 */
public class ScaleRotateMatrixPair implements StreamWritable {
    /**
     * 확장을 위한 행렬
     */
    private Matrix scaleMatrix;
    /**
     * 회전을 위한 행렬
     */
    private Matrix rotateMatrix;

    /**
     * 생성자
     */
    public ScaleRotateMatrixPair() {
        scaleMatrix = new Matrix();
        rotateMatrix = new Matrix();
    }
    public ScaleRotateMatrixPair(StreamReader sr) throws IOException {
        this.scaleMatrix = new Matrix(sr);
        this.rotateMatrix = new Matrix(sr);
    }

    /**
     * 확장을 위한 행렬을 반환한다.
     *
     * @return 확장을 위한 행렬
     */
    public Matrix getScaleMatrix() {
        return scaleMatrix;
    }

    /**
     * 회전을 위한 행렬을 반환한다.
     *
     * @return 회전을 위한 행렬
     */
    public Matrix getRotateMatrix() {
        return rotateMatrix;
    }

    public ScaleRotateMatrixPair clone() {
        ScaleRotateMatrixPair cloned = new ScaleRotateMatrixPair();
        cloned.scaleMatrix.copy(scaleMatrix);
        cloned.rotateMatrix.copy(rotateMatrix);
        return cloned;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getScaleMatrix().write(sw);
        this.getRotateMatrix().write(sw);
    }
}
