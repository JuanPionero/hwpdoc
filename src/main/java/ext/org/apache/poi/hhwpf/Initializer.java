package ext.org.apache.poi.hhwpf;

public interface Initializer<T> {
    void init(T target) throws Exception;
}
