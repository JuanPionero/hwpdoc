package ext.org.apache.poi.hhwpf.model.structure.section.control;


import ext.org.apache.poi.hhwpf.Initializer;
import ext.org.apache.poi.hhwpf.InstanceID;
import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark.CtrlData;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeader;
import ext.org.apache.poi.hhwpf.model.structure.section.control.ctrlheader.CtrlHeaderGso;
import ext.org.apache.poi.hhwpf.model.structure.section.control.gso.caption.Caption;
import ext.org.apache.poi.hhwpf.model.structure.section.control.table.Cell;
import ext.org.apache.poi.hhwpf.model.structure.section.control.table.Row;
import ext.org.apache.poi.hhwpf.model.structure.section.control.table.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

import static ext.org.apache.poi.hhwpf.model.datarecord.TagID.*;

/**
 * 표 컨트롤
 *
 * @author neolord
 */
public class ControlTable extends Control implements StateUpdatable {
    private static final Logger logger = LoggerFactory.getLogger(ControlTable.class);
    /**
     * 캡션
     */
    private Caption caption;
    /**
     * 표 정보
     */
    private Table table;
    /**
     * 행 리스트
     */
    private ArrayList<Row> rowList;

    /**
     * 생성자
     */
    public ControlTable() {
        this(new CtrlHeaderGso(ControlType.Table));

    }

    public ControlTable(StreamReader sr) throws Exception {
        this(new CtrlHeaderGso(sr, ControlType.Table));

        sr.readDataRecordHeader();

        if(sr.getCurrentDataRecordHeader().getTagID()== HWPTAG_CTRL_DATA) {
            this.ctrlData = new CtrlData(sr);
//            sr.readDataRecordHeader();
        }

        if (sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_LIST_HEADER) {
            if(sr.isEndOfDataRecord()) {
                sr.readDataRecordHeader();
            }
            this.caption = new Caption( sr );
        }
        this.rowList = new ArrayList<Row>();

        if(sr.isEndOfDataRecord()) {
            sr.readDataRecordHeader();
        }
        if(sr.getCurrentDataRecordHeader().getTagID() == HWPTAG_TABLE) {
            this.table = new Table( sr );
            ArrayList<Integer> cellCountOfRow = this.table.getCellCountOfRowList();
            for (int rowIndex = 0; rowIndex < this.table.getRowCount(); rowIndex++) {
                int cellCount = cellCountOfRow.get(rowIndex);
                this.rowList.add( new Row(cellCount, sr) );
            }
        }

    }

    public ControlTable(Initializer initializer) throws Exception {
        this(new CtrlHeaderGso(ControlType.Table));
        initializer.init(this); // header, table, rowList, caption 등 필요한 내용 설정
    }
    /**
     * 생성자
     *
     * @param header 컨트롤 헤더
     */
    public ControlTable(CtrlHeader header) {
        super(header);

        caption = null;
        table = new Table();
        rowList = new ArrayList<Row>();
    }

    /**
     * 그리기 객체 용 컨트롤 헤더를 반환한다.
     *
     * @return 그리기 객체 용 컨트롤 헤더
     */
    public CtrlHeaderGso getHeader() {
        return (CtrlHeaderGso) header;
    }

    /**
     * 캡션 객체를 생성한다.
     */
    public void createCaption() {
        caption = new Caption();
    }

    /**
     * 캡션 객체를 삭제한다.
     */
    public void deleteCaption() {
        caption = null;
    }

    /**
     * 캡션 객체를 반환한다.
     *
     * @return 캡션 객체
     */
    public Caption getCaption() {
        return caption;
    }

    /**
     * 표 정보 객체를 반환한다.
     *
     * @return 표 정보 객체
     */
    public Table getTable() {
        return table;
    }

    /**
     * 새로운 행 객체를 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 행 객체
     */
    public Row addNewRow() {
        Row r = new Row();
        rowList.add(r);
        return r;
    }

    public void setRowList(ArrayList<Row> rowList) {
        this.rowList = rowList;
    }

    /**
     * 행 리스트를 반환한다.
     *
     * @return 행 리스트
     */
    public ArrayList<Row> getRowList() {
        return rowList;
    }

    @Override
    public Control clone() {
        ControlTable cloned = new ControlTable();
        cloned.copyControlPart(this);

        if (caption != null) {
            cloned.createCaption();
            cloned.caption.copy(caption);
        } else {
            cloned.caption = null;
        }

        cloned.table.copy(table);

        for (Row row : rowList) {
            cloned.rowList.add(row.clone());
        }

        return cloned;
    }

    @Override
    public void updateState(InstanceID iid) {
        CtrlHeaderGso h = this.getHeader();
        if(h==null) {
            return;
        }
        h.setInstanceId(iid.get());
        h.getProperty().setHasCaption( this.caption != null );

        // ForControlTable.autoSet 참조
        Table tbl = this.getTable();
        tbl.setRowCount(this.getRowList().size());
        tbl.getCellCountOfRowList().clear();
        for (Row r : this.getRowList()) {
            tbl.getCellCountOfRowList().add(r.getCellList().size());
        }
        for (Row r : this.getRowList()) {
            for (Cell c : r.getCellList()) {
                c.getListHeader().setParaCount(c.getParagraphList().getParagraphCount());
                c.getParagraphList().updateState(iid);
            }
        }

    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        this.getHeader().write(sw);

        sw.upRecordLevel();
        if(this.caption!=null) {
            this.caption.write(sw);
        }
        this.table.write(sw);
        for (Row r : this.getRowList()) {
            for (Cell c : r.getCellList()) {
                c.write(sw);
//                ForCell.write(c, sw);
            }
        }

        sw.downRecordLevel();

    }
}
