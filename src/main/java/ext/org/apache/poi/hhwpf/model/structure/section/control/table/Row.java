package ext.org.apache.poi.hhwpf.model.structure.section.control.table;

import ext.org.apache.poi.hhwpf.StreamReader;

import java.util.ArrayList;

/**
 * 표의 행을 나타내는 객체
 *
 * @author neolord
 */
public class Row {
    /**
     * 셀 리스트
     */
    private ArrayList<Cell> cellList;

    /**
     * 생성자
     */
    public Row() {
        cellList = new ArrayList<Cell>();
    }

    public Row(int cellCount, StreamReader sr) throws Exception {
        cellList = new ArrayList<Cell>();
        for (int cellIndex = 0; cellIndex < cellCount; cellIndex++) {
            this.cellList.add( new Cell(sr) );
        }
    }

    public Row( ArrayList<Cell> cellList) {
        this.cellList = cellList;
    }

    /**
     * 새로운 셀 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 셀 객체
     */
    public Cell addNewCell() {
        Cell c = new Cell();
        cellList.add(c);
        return c;
    }

    /**
     * 셀 리스트를 반환한다.
     *
     * @return 셀 리스트
     */
    public ArrayList<Cell> getCellList() {
        return cellList;
    }

    public Row clone() {
        Row cloned = new Row();
        for (Cell cell : cellList) {
            cloned.cellList.add(cell.clone());
        }
        return cloned;
    }

    public int estimateColumnCount() {
        int accumulator = 0;
        for(Cell cell : cellList) {
            accumulator += cell.getListHeader().getColSpan() <= 1 ? 1 : cell.getListHeader().getColSpan();
        }
        return accumulator;
    }

}
