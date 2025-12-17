package ext.org.apache.poi.hhwpf.model.structure.section.control.bookmark;

import ext.org.apache.poi.hhwpf.StreamReader;
import ext.org.apache.poi.hhwpf.StreamWritable;
import ext.org.apache.poi.hhwpf.StreamWriter;
import ext.org.apache.poi.hhwpf.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 파라미터 아이탬 객체
 *
 * @author neolord
 */
public class ParameterItem implements StreamWritable {
    /**
     * 파라미터 아이탬 id
     */
    private long id;
    /**
     * 파라미터 아이템 종류
     */
    private ParameterType type;
    /**
     * 파라미터 아이템 값(문자열)
     */
    private String value_BSTR;
    /**
     * 파라미터 아이템 값(1byte 정수)
     */
    private byte value_I1;
    /**
     * 파라미터 아이템 값(2byte 정수)
     */
    private short value_I2;
    /**
     * 파라미터 아이템 값(4byte 정수)
     */
    private int value_I4;
    /**
     * 파라미터 아이템 값(정수)
     */
    private int value_I;
    /**
     * 파라미터 아이템 값(1byte 부호없는 정수)
     */
    private short value_UI1;
    /**
     * 파라미터 아이템 값(2byte 부호없는 정수)
     */
    private int value_UI2;
    /**
     * 파라미터 아이템 값(4byte 부호없는 정수)
     */
    private long value_UI4;
    /**
     * 파라미터 아이템 값(부호없는 정수)
     */
    private long value_UI;
    /**
     * 파라미터 아이템 값(파라미터 셋)
     */
    private ParameterSet value_ParameterSet;
    /**
     * 파라미터 아이템 값(파라미터 배열)
     */
    private ParameterItem[] value_ParameterArray;
    /**
     * 파라미터 아이템 값(binData id)
     */
    private int value_binData;

    /**
     * 생성자
     */
    public ParameterItem() {
        id = 0;
        type = ParameterType.NULL;

        value_BSTR = null;
        value_I1 = 0;
        value_I2 = 0;
        value_I4 = 0;
        value_I = 0;
        value_UI1 = 0;
        value_UI2 = 0;
        value_UI4 = 0;
        value_UI = 0;
        value_ParameterSet = null;
        value_ParameterArray = null;
        value_binData = -1;
    }

    public ParameterItem(StreamReader sr) throws IOException {
        id = sr.readUInt2();
        type = ParameterType.valueOf(sr.readUInt2());

        switch (type) {
            case NULL:
                break;
            case String:
                this.value_BSTR = sr.readUTF16LEString();
                break;
            case Integer1:
                this.value_I1 = (byte) sr.readSInt4();
                break;
            case Integer2:
                this.value_I2 = (short) sr.readSInt4();
                break;
            case Integer4:
                this.value_I4 = sr.readSInt4();
                break;
            case Integer:
                this.value_I = sr.readSInt4();
                break;
            case UnsignedInteger1:
                this.value_UI1 = (short) sr.readUInt4();
                break;
            case UnsignedInteger2:
                this.value_UI2 = (int) sr.readUInt4();
                break;
            case UnsignedInteger4:
                this.value_UI4 = sr.readUInt4();
                break;
            case UnsignedInteger:
                this.value_UI = sr.readUInt4();
                break;
            case ParameterSet:
                // parameterSet(pi, sr);
                this.value_ParameterSet = new ParameterSet(sr);
                break;
            case Array:
//                parameterArray(pi, sr);
                short count = sr.readSInt2();
                if (count > 0) {
//                    pi.createValue_ParameterArray(count);
                    ArrayList<ParameterSet> _pSet = new ArrayList<>();
                    int id = sr.readUInt2();
                    for (int index = 0; index < count; index++) {
                        _pSet.add( new ParameterSet(sr) );
//                        parameterItemForArray(pi.getValue_ParameterArray(index), sr, id);
                    }
                    this.value_ParameterArray = (ParameterItem[]) _pSet.toArray();
                }
                break;
            case BINDataID:
                this.value_binData = sr.readUInt2();
                break;

        }
    }

    public ParameterItem(long id, ParameterType type, String fieldName) {
        this();
        this.id = id;
        this.type = type;
        this.value_BSTR = fieldName;
    }

    /**
     * 파라미터 아이탬 id를 반환한다.
     *
     * @return 파라미터 아이탬 id
     */
    public long getId() {
        return id;
    }

    /**
     * 파라미터 아이탬 id를 설정한다.
     *
     * @param id 파라미터 아이탬 id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 파라미터 아이템 종류를 반환한다.
     *
     * @return 파라미터 아이템 종류
     */
    public ParameterType getType() {
        return type;
    }

    /**
     * 파라미터 아이템 종류를 설정한다.
     *
     * @param type 파라미터 아이템 종류
     */
    public void setType(ParameterType type) {
        this.type = type;
    }

    /**
     * 파라미터 아이템 값(문자열)을 반환한다.
     *
     * @return 파라미터 아이템 값(문자열)
     */
    public String getValue_BSTR() {
        return value_BSTR;
    }

    /**
     * 파라미터 아이템 값(문자열)을 설정한다.
     *
     * @param value_BSTR 파라미터 아이템 값(문자열)
     */
    public void setValue_BSTR(String value_BSTR) {
        this.value_BSTR = value_BSTR;
    }

    /**
     * 파라미터 아이템 값(1byte 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(1byte 정수)
     */
    public byte getValue_I1() {
        return value_I1;
    }

    /**
     * 파라미터 아이템 값(1byte 정수)을 설정한다.
     *
     * @param value_I1 파라미터 아이템 값(1byte 정수)
     */
    public void setValue_I1(byte value_I1) {
        this.value_I1 = value_I1;
    }

    /**
     * 파라미터 아이템 값(2byte 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(2byte 정수)
     */
    public short getValue_I2() {
        return value_I2;
    }

    /**
     * 파라미터 아이템 값(2byte 정수)을 설정한다.
     *
     * @param value_I2 파라미터 아이템 값(2byte 정수)
     */
    public void setValue_I2(short value_I2) {
        this.value_I2 = value_I2;
    }

    /**
     * 파라미터 아이템 값(4byte 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(4byte 정수)
     */
    public int getValue_I4() {
        return value_I4;
    }

    /**
     * 파라미터 아이템 값(4byte 정수)을 설정한다.
     *
     * @param value_I4 파라미터 아이템 값(4byte 정수)
     */
    public void setValue_I4(int value_I4) {
        this.value_I4 = value_I4;
    }

    /**
     * 파라미터 아이템 값(정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(정수)
     */
    public int getValue_I() {
        return value_I;
    }

    /**
     * 파라미터 아이템 값(정수)을 설정한다.
     *
     * @param value_I 파라미터 아이템 값(정수)
     */
    public void setValue_I(int value_I) {
        this.value_I = value_I;
    }

    /**
     * 파라미터 아이템 값(1byte 부호없는 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(1byte 부호없는 정수)
     */
    public short getValue_UI1() {
        return value_UI1;
    }

    /**
     * 파라미터 아이템 값(1byte 부호없는 정수)을 설정한다.
     *
     * @param value_UI1 파라미터 아이템 값(1byte 부호없는 정수)
     */
    public void setValue_UI1(short value_UI1) {
        this.value_UI1 = value_UI1;
    }

    /**
     * 파라미터 아이템 값(2byte 부호없는 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(2byte 부호없는 정수)
     */
    public int getValue_UI2() {
        return value_UI2;
    }

    /**
     * 파라미터 아이템 값(2byte 부호없는 정수)을 설정한다.
     *
     * @param value_UI2 파라미터 아이템 값(2byte 부호없는 정수)
     */
    public void setValue_UI2(int value_UI2) {
        this.value_UI2 = value_UI2;
    }

    /**
     * 파라미터 아이템 값(4byte 부호없는 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(4byte 부호없는 정수)
     */
    public long getValue_UI4() {
        return value_UI4;
    }

    /**
     * 파라미터 아이템 값(4byte 부호없는 정수)을 설정한다.
     *
     * @param value_UI4 파라미터 아이템 값(4byte 부호없는 정수)
     */
    public void setValue_UI4(long value_UI4) {
        this.value_UI4 = value_UI4;
    }

    /**
     * 파라미터 아이템 값(부호없는 정수)을 반환한다.
     *
     * @return 파라미터 아이템 값(부호없는 정수)
     */
    public long getValue_UI() {
        return value_UI;
    }

    /**
     * 파라미터 아이템 값(부호없는 정수)을 설정한다.
     *
     * @param value_UI 파라미터 아이템 값(부호없는 정수)
     */
    public void setValue_UI(long value_UI) {
        this.value_UI = value_UI;
    }

    /**
     * 파라미터 아이템 값(파라미터 셋)을 반환한다.
     *
     * @return 파라미터 아이템 값(파라미터 셋)
     */
    public ParameterSet getValue_ParameterSet() {
        return value_ParameterSet;
    }

    /**
     * 파라미터 아이템 값(파라미터 셋) 객체를 생성한다.
     */
    public void createValue_ParameterSet() {
        value_ParameterSet = new ParameterSet();
    }

    /**
     * 파라미터 아이템 값(파라미터 셋) 객체를 삭제한다.
     */
    public void deleteValue_ParameterSet() {
        value_ParameterSet = null;
    }

    /**
     * 파라미터 아이템 값(파라미터 배열)의 원소 개수를 반환한다.
     *
     * @return 파라미터 아이템 값(파라미터 배열)의 원소 개수
     */
    public int getValue_ParameterArrayCount() {
        if (value_ParameterArray != null) {
            return value_ParameterArray.length;
        } else {
            return 0;
        }
    }

    public ParameterItem[] getValue_ParameterArray() {
        return this.value_ParameterArray;
    }
    /**
     * 파라미터 아이템 값(파라미터 배열)의 원소를 반환한다.
     *
     * @param index 파라미터 아이템 값(파라미터 배열)의 원소 인덱스
     * @return 파라미터 아이템 값(파라미터 배열)의 원소
     */
    public ParameterItem getValue_ParameterArray(int index) {
        if (value_ParameterArray != null) {
            return value_ParameterArray[index];
        } else {
            return null;
        }
    }

    /**
     * 파라미터 아이템 값(파라미터 배열)을 생성한다.
     *
     * @param count 파라미터 아이템 값(파라미터 배열)의 원소 개수
     */
    public void createValue_ParameterArray(int count) {
        value_ParameterArray = new ParameterItem[count];
        for (int index = 0; index < count; index++) {
            value_ParameterArray[index] = new ParameterItem();
        }
    }

    /**
     * 파라미터 아이템 값(파라미터 배열)을 삭제한다.
     */
    public void deleteValue_ParameterArray() {
        value_ParameterArray = null;
    }

    /**
     * 파라미터 아이템 값(binData id)을 반환한다.
     *
     * @return 파라미터 아이템 값(binData id)
     */
    public int getValue_binData() {
        return value_binData;
    }

    /**
     * 파라미터 아이템 값(binData id)를 설정한다.
     *
     * @param value_binData 파라미터 아이템 값(binData id)
     */
    public void setValue_binData(int value_binData) {
        this.value_binData = value_binData;
    }

    public ParameterItem clone() {
        ParameterItem cloned = new ParameterItem();
        cloned.copy(this);
        return cloned;
    }

    public void copy(ParameterItem from) {
        id = from.id;
        type = from.type;
        value_BSTR = from.value_BSTR;
        value_I1 = from.value_I1;
        value_I2 = from.value_I2;
        value_I4 = from.value_I4;
        value_I = from.value_I;
        value_UI1 = from.value_UI1;
        value_UI2 = from.value_UI2;
        value_UI4 = from.value_UI4;
        value_UI = from.value_UI;

        if (from.value_ParameterSet != null) {
            createValue_ParameterSet();
            value_ParameterSet.copy(from.value_ParameterSet);
        } else {
            value_ParameterSet = null;
        }

        if (from.value_ParameterArray != null) {
            int count = from.value_ParameterArray.length;
            createValue_ParameterArray(count);
            for (int index = 0; index < count; index++) {
                value_ParameterArray[index].copy(from.value_ParameterArray[index]);
            }
        }

        value_binData = from.value_binData;
    }

    public int getSize() {
        int size = 0;
        size += 4;
        switch (this.type) {
            case NULL:
                break;
            case String:
                size += StringUtil.getUTF16LEStringSize(this.getValue_BSTR());
                break;
            case Integer1:
                size += 4;
                break;
            case Integer2:
                size += 4;
                break;
            case Integer4:
                size += 4;
                break;
            case Integer:
                size += 4;
                break;
            case UnsignedInteger1:
                size += 4;
                break;
            case UnsignedInteger2:
                size += 4;
                break;
            case UnsignedInteger4:
                size += 4;
                break;
            case UnsignedInteger:
                size += 4;
                break;
            case ParameterSet:
                size += this.value_ParameterSet.getSize();
                break;
            case Array:
                size += Arrays.stream(this.value_ParameterArray).map(item->item.getSize()).reduce(0,
                        (a,b)->a+b-2
                ) + 4;//  getSizeForParameterArray(pi);
                break;
            case BINDataID:
                size += 2;
                break;
        }

        return size;
    }

    @Override
    public void write(StreamWriter sw) throws IOException {
        sw.writeUInt2((int) this.id);
        sw.writeUInt2( this.type.getValue());
        this.writeValue(sw);
    }

    public void writeValue(StreamWriter sw) throws IOException {
        switch (this.type) {
            case NULL:
                break;
            case String:
                sw.writeUTF16LEString(this.getValue_BSTR());
                break;
            case Integer1:
                sw.writeSInt4(this.getValue_I1());
                break;
            case Integer2:
                sw.writeSInt4(this.getValue_I2());
                break;
            case Integer4:
                sw.writeSInt4(this.getValue_I4());
                break;
            case Integer:
                sw.writeSInt4(this.getValue_I());
                break;
            case UnsignedInteger1:
                sw.writeUInt4(this.getValue_UI1());
                break;
            case UnsignedInteger2:
                sw.writeUInt4(this.getValue_UI2());
                break;
            case UnsignedInteger4:
                sw.writeUInt4(this.getValue_UI4());
                break;
            case UnsignedInteger:
                sw.writeUInt4(this.getValue_UI());
                break;
            case ParameterSet:
                this.value_ParameterSet.write(sw);
//                write(this.getValue_ParameterSet(), sw);
                break;
            case Array:
//                parameterArray(pi, sw);
                short cnt = (short) this.getValue_ParameterArrayCount();
                sw.writeSInt2(cnt);
                if(cnt>0) {
                    // 동종의 값이 array 로 나열.
                    sw.writeUInt2((int) this.value_ParameterArray[0].getId());
                    for(ParameterItem pi : this.value_ParameterArray) {
                        sw.writeUInt2((pi.getType().getValue()));
                        pi.writeValue(sw);
                    }
                }
                break;
            case BINDataID:
                sw.writeUInt2(this.getValue_binData());
                break;
        }
    }
}
