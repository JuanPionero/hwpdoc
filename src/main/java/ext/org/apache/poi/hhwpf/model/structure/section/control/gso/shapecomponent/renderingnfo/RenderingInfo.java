package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.renderingnfo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Rendering 정보
 *
 * @author neolord
 */
public class RenderingInfo implements StreamWritable {
    /**
     * 이동을 위한 행렬
     */
    private Matrix translationMatrix;
    /**
     * 확장/회전을 위한 행령의 쌍에 대한 리스트
     */
    private ArrayList<ScaleRotateMatrixPair> scaleRotateMatrixPairList;

    /**
     * 생성자
     */
    public RenderingInfo() {
        translationMatrix = new Matrix();
        scaleRotateMatrixPairList = new ArrayList<ScaleRotateMatrixPair>();
    }

    public RenderingInfo(StreamReader sr) throws IOException {
        int scaleRotateMatrixCount = sr.readUInt2();
        this.translationMatrix = new Matrix(sr);
        this.scaleRotateMatrixPairList = new ArrayList<ScaleRotateMatrixPair>();
        for (int index = 0; index < scaleRotateMatrixCount; index++) {
            scaleRotateMatrixPairList.add( new ScaleRotateMatrixPair(sr) );
        }
    }

    /**
     * 이동을 위한 행렬 객체를 반환한다.
     *
     * @return 이동을 위한 행렬 객체
     */
    public Matrix getTranslationMatrix() {
        return translationMatrix;
    }

    /**
     * 새로운 확장/회전을 위한 행령의 쌍을 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 확장/회전을 위한 행령의 쌍
     */
    public ScaleRotateMatrixPair addNewScaleRotateMatrixPair() {
        ScaleRotateMatrixPair srmp = new ScaleRotateMatrixPair();
        scaleRotateMatrixPairList.add(srmp);
        return srmp;
    }

    /**
     * 확장/회전을 위한 행령의 쌍에 대한 리스트를 반환한다.
     *
     * @return 확장/회전을 위한 행령의 쌍에 대한 리스트
     */
    public ArrayList<ScaleRotateMatrixPair> getScaleRotateMatrixPairList() {
        return scaleRotateMatrixPairList;
    }

    public void copy(RenderingInfo from) {
        translationMatrix.copy(from.translationMatrix);

        scaleRotateMatrixPairList.clear();
        for (ScaleRotateMatrixPair matrixPair : from.scaleRotateMatrixPairList) {
            scaleRotateMatrixPairList.add(matrixPair.clone());
        }

    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        int scaleRotateMatrixCount = this.getScaleRotateMatrixPairList().size();
        sw.writeUInt2(scaleRotateMatrixCount);
        this.getTranslationMatrix().write(sw);
        for (ScaleRotateMatrixPair srmp : this.getScaleRotateMatrixPairList()) {
            srmp.write(sw);
        }
    }
}
