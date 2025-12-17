package ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 파라미터 셋 객체
 *
 * @author neolord
 */
public class ParameterSet implements StreamWritable {
    /**
     * 파라미터 셋 id
     */
    private int id;
    /**
     * 파라미터 아이탬 리스트
     */
    private ArrayList<ParameterItem> parameterItemList;

    /**
     * 생성자
     */
    public ParameterSet() {
        id = 0;
        parameterItemList = new ArrayList<ParameterItem>();
    }
    public ParameterSet(StreamReader sr) throws IOException {
        this.id = sr.readUInt2();
        short parameterCount = sr.readSInt2();
        sr.readBytes(2);
        parameterItemList = new ArrayList<ParameterItem>();
        for (int parameterIndex = 0; parameterIndex < parameterCount; parameterIndex++) {
            parameterItemList.add( new ParameterItem(sr) );
        }
    }

    public ParameterSet(String name) {
        this.id = 0x21b;
        this.parameterItemList = new ArrayList<ParameterItem>();
        this.parameterItemList.add( new ParameterItem(0x4000, ParameterType.String, name) );

    }

    /**
     * 파라미터 셋 id를 반환한다.
     *
     * @return 파라미터 셋 id
     */
    public int getId() {
        return id;
    }

    /**
     * 파라미터 셋 id를 설정한다.
     *
     * @param id 파라미터 셋 id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 파라미터 아이탬을 생성하고 리스트에 추가한다.
     *
     * @return 새로 생성된 파라미터 아이탬
     */
    public ParameterItem addNewParameterItem() {
        ParameterItem pi = new ParameterItem();
        parameterItemList.add(pi);
        return pi;
    }

    /**
     * 파라미터 아이탬 리스트를 반환한다.
     *
     * @return 파라미터 아이탬 리스트
     */
    public ArrayList<ParameterItem> getParameterItemList() {
        return parameterItemList;
    }

    /**
     * 아이디가 id인 파라미터 아이탬을 반환한다.
     *
     * @param id 파리미터 아이탬의 아이디
     * @return 아이디가 id인 파라미터 아이탬
     */
    public ParameterItem getParameterItem(int id) {
        for (ParameterItem pi : parameterItemList) {
            if (pi.getId() == id) {
                return pi;
            }
        }
        return null;
    }

    /**
     * 필드 이름을 위한 파라미터 셋 객체를 만든다.
     *
     * @param fieldName 필드 이름
     * @return 필드 이름을 위한 파라미터 셋 객체
     */
    public static ParameterSet createForFieldName(String fieldName) {
        if (fieldName == null || fieldName.length() == 0) {
            return null;
        }

        ParameterSet ps = new ParameterSet();
        ps.setId(0x21b);
        ParameterItem pi = ps.addNewParameterItem();
        pi.setId(0x4000);
        pi.setType(ParameterType.String);
        pi.setValue_BSTR(fieldName);
        return ps;
    }

    public void copy(ParameterSet from) {
        id = from.id;

        for (ParameterItem parameterItem : from.parameterItemList) {
            parameterItemList.add(parameterItem.clone());
        }
    }

    public int getSize() {
        int size = 0;
        size += 6;
        size += this.parameterItemList.stream().mapToInt(ParameterItem::getSize).sum();
        return size;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2(this.id );
        short parameterCount = (short) this.parameterItemList.size();
        sw.writeSInt2(parameterCount);
        sw.writeZero(2);
        for (ParameterItem pi : this.parameterItemList) {
            pi.write(sw);
//            parameterItem(pi, sw);
        }
    }
}
