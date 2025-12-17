package ext.org.apache.poi.hhwpf.model.structure.section.control.gso.shapecomponent.renderingnfo;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;

import java.io.IOException;

/**
 * 행렬을 나타내는 객체. 각 행렬는 원소가 double로 표현되는 3 X 3 matrix로 구현된다. 마지막 행은 항상 [0,0,1]이기
 * 떄문에 생략되어 실제 6개의 원소만 저장한다.
 *
 * @author neolord
 */
public class Matrix implements StreamWritable {
    /**
     * 행렬의 원소를 저장하는 배열
     */
    private double[] values;

    /**
     * 생성자
     */
    public Matrix() {
        values = new double[6];
    }

    public Matrix(StreamReader sr) throws IOException {
        values = new double[6];
        for (int index = 0; index < 6; index++) {
            this.values[index] = sr.readDouble();
        }
    }

    /**
     * 행렬의 원소를 반환한다.
     *
     * @param index 원소의 인덱스
     * @return 행렬의 원소
     */
    public double getValue(int index) {
        return values[index];
    }

    /**
     * 행렬의 원소를 설정한다.
     *
     * @param index 원소의 인덱스
     * @param value 행렬의 원소
     */
    public void setValue(int index, double value) {
        values[index] = value;
    }

    /**
     * 행렬의 원소를 저장하는 배열을 반홚한다.
     *
     * @return 행렬의 원소를 저장하는 배열
     */
    public double[] getValues() {
        return values;
    }

    public void copy(Matrix from) {
        values = from.values.clone();
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
         for (int index = 0; index < 6; index++) {
            sw.writeDouble(this.getValue(index));
        }
    }
}
