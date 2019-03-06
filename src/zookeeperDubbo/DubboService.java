package zookeeperDubbo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yecheng on 2016/12/8.
 */
public class DubboService implements Serializable {
    private static final long serialVersionUID = -6702413303040703645L;
    private String serviceName;
    private List<String> serviceAddressList;
    private List<String> consumersList;
    private List<String> routersList;
    private List<String> configuratorsList;

    public DubboService(String serviceName) {
        this.serviceName = serviceName;
    }

    public DubboService(String interfaceNode, List<String> serviceAddressList) {
        this.serviceName = interfaceNode;
        this.serviceAddressList = serviceAddressList;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getServiceAddressList() {
        return this.serviceAddressList;
    }

    public void setServiceAddressList(List<String> serviceAddressList) {
        this.serviceAddressList = serviceAddressList;
    }

    public List<String> getConsumersList() {
        return this.consumersList;
    }

    public void setConsumersList(List<String> consumersList) {
        this.consumersList = consumersList;
    }

    public List<String> getRoutersList() {
        return this.routersList;
    }

    public void setRoutersList(List<String> routersList) {
        this.routersList = routersList;
    }

    public List<String> getConfiguratorsList() {
        return this.configuratorsList;
    }

    public void setConfiguratorsList(List<String> configuratorsList) {
        this.configuratorsList = configuratorsList;
    }
}
