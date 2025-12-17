package ext.org.apache.poi.hhwpf.util;

final public class NumberParser {
    private NumberParser() {}

    static public Double parseDouble(String str) {
        return parseDouble(str, 0D);
    }
    static public Double parseDouble(String str, Double def) {
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {
            return def;
        }
    }
    static public Float parseFloat(String str) {
        return parseFloat(str, 0F);
    }
    static public Float parseFloat(String str, Float def) {
        try {
            return Float.parseFloat(str);
        } catch (Exception ex) {
            return def;
        }
    }
    static public Long parseLong(String str) {
        return parseLong(str, 0L);
    }
    static public Long parseLong(String str, Long def) {
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {
            return def;
        }
    }
    static public Integer parseInt(String str) {
        return parseInt(str, 0);
    }
    static public Integer parseInt(String str, Integer def) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {
            return def;
        }
    }
}
