package ext.org.apache.poi.hhwpf.model.structure.section.control;

import ext.org.apache.poi.hhwpf.InstanceID;

// GsoControl 을 상속 받은 경우에는 꼭 필요하지 않음.
public interface StateUpdatable {
    void updateState(InstanceID iid);
}