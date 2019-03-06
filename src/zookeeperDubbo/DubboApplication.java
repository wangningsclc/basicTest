package zookeeperDubbo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yecheng on 2016/12/8.
 */
public class DubboApplication implements Serializable {
    private static final long serialVersionUID = -8508041176948370663L;
    private String applicationName;
    private List<DubboService> dubboServices;

    public DubboApplication(String appName) {
        this.applicationName = appName;
    }

    public String getApplicationName() {
        return this.applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<DubboService> getDubboServices() {
        return this.dubboServices;
    }

    public void setDubboServices(List<DubboService> dubboServices) {
        this.dubboServices = dubboServices;
    }
}
