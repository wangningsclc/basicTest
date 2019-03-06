package zookeeperDubbo;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yecheng on 2016/12/8.
 */
public class DubboTransfer {
    public static final int SESSION_TIMEOUT = 10000;
    public static final String DUBBO_NODE = "/dubbo";
    public static final String DUBBO_BACKUP_NODE = "/dubbo_backup";
    public static final String PROVIDERS_NODE = "/providers";
    public static final String CONSUMERS_NODE = "/consumers";
    public static final String ROUTES_NODE = "/routers";
    public static final String CONFIGURATORS_NODE = "/configurators";
    public static final Character SEPARATOR = Character.valueOf('/');
    private static final String UTF_8 = "UTF-8";
    private List<String> targetApplicationList;
    private ZooKeeper zooKeeper;
    private String transIp;
    private String transPort;

    public DubboTransfer(String zookeeperAddress, List<String> targetApplicationList) throws IOException {
        this.targetApplicationList = targetApplicationList;
        this.zooKeeper = new ZooKeeper(zookeeperAddress, 10000, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("event = " + event);
            }
        });
    }

    public DubboTransfer(String zookeeperAddress, List<String> targetApplicationList, String transIp, String transPort) throws IOException {
        this.targetApplicationList = targetApplicationList;
        this.transIp = transIp;
        this.transPort = transPort;
        this.zooKeeper = new ZooKeeper(zookeeperAddress, 10000, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("event = " + event);
            }
        });
    }

    public List<DubboApplication> getApplicationList() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        return this.getApplicationList("/dubbo");
    }

    public void transfer(DubboTransfer sourceTransfer) throws KeeperException, UnsupportedEncodingException, InterruptedException {
        try {
        	List<DubboApplication>  dubboApplications=sourceTransfer.getApplicationList();
            this.backup();
            this.clear("/dubbo", dubboApplications);
            this.push("/dubbo", dubboApplications);
        } catch (InterruptedException var3) {
            var3.printStackTrace();
            throw var3;
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            throw var4;
        } catch (KeeperException var5) {
            var5.printStackTrace();
            throw var5;
        }
    }

    public void recovery() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        try {
            System.out.println("Recovery start ####");
            List e = this.getApplicationList("/dubbo_backup");
            if(this.zooKeeper.exists("/dubbo", false) == null) {
                this.create("/dubbo");
            }

            this.clear("/dubbo", e);
            this.push("/dubbo", e);
            this.clear("/dubbo_backup", e);
            System.out.println("Recovery end ####");
        } catch (InterruptedException var2) {
            var2.printStackTrace();
            throw var2;
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            throw var3;
        } catch (KeeperException var4) {
            var4.printStackTrace();
            throw var4;
        }
    }

    public void clear() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        try {
            this.clear(this.getApplicationList());
        } catch (InterruptedException var2) {
            var2.printStackTrace();
            throw var2;
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            throw var3;
        } catch (KeeperException var4) {
            var4.printStackTrace();
            throw var4;
        }
    }

    public void clearAll() throws InterruptedException, KeeperException {
        try {
            this.deleteAll("/dubbo");
        } catch (InterruptedException var2) {
            var2.printStackTrace();
            throw var2;
        } catch (KeeperException var3) {
            var3.printStackTrace();
            throw var3;
        }
    }

    public void clearBackup() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        try {
            this.clear("/dubbo_backup", this.getApplicationList("/dubbo_backup"));
        } catch (InterruptedException var2) {
            var2.printStackTrace();
            throw var2;
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            throw var3;
        } catch (KeeperException var4) {
            var4.printStackTrace();
            throw var4;
        }
    }

    public void clearBackupAll() throws InterruptedException, KeeperException {
        try {
            this.deleteAll("/dubbo_backup");
        } catch (InterruptedException var2) {
            var2.printStackTrace();
            throw var2;
        } catch (KeeperException var3) {
            var3.printStackTrace();
            throw var3;
        }
    }

    public void backup() throws InterruptedException, UnsupportedEncodingException, KeeperException {
        try {
            System.out.println("Backup start ####");
            List e = this.getApplicationList();
            if(this.zooKeeper.exists("/dubbo_backup", false) == null) {
                this.create("/dubbo_backup");
            }

            this.push("/dubbo_backup", e);
            this.clear("/dubbo", e);
            System.out.println("Backup end ####");
        } catch (InterruptedException var2) {
            var2.printStackTrace();
            throw var2;
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            throw var3;
        } catch (KeeperException var4) {
            var4.printStackTrace();
            throw var4;
        }
    }

    private List<DubboService> getDubboServiceList2(String rootNode, DubboApplication dubboApplication) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        ArrayList dubboServiceList = new ArrayList();
        if(this.zooKeeper.exists(rootNode, false) != null) {
            List rootNodeList = this.zooKeeper.getChildren(rootNode, true);
            Iterator i$ = rootNodeList.iterator();

            while(true) {
                String interfaceNode;
                String path;
                List nodeList;
                ArrayList serviceAddressList;
                boolean flag;
                String configuratorsList;
                do {
                    do {
                        if(!i$.hasNext()) {
                            return dubboServiceList;
                        }

                        interfaceNode = (String)i$.next();
                        path = rootNode + SEPARATOR + interfaceNode + "/providers";
                    } while(this.zooKeeper.exists(path, false) == null);

                    nodeList = this.zooKeeper.getChildren(path, true);
                    serviceAddressList = new ArrayList();
                    flag = false;
                    Iterator consumersList = nodeList.iterator();

                    while(consumersList.hasNext()) {
                        String routersList = (String)consumersList.next();
                        configuratorsList = URLDecoder.decode(routersList, "UTF-8");
                        if(configuratorsList.contains("application=" + dubboApplication.getApplicationName())) {
                            flag = true;
                            serviceAddressList.add(routersList);
                        }
                    }
                } while(!flag);

                ArrayList consumersList1 = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/consumers";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    Iterator routersList1 = nodeList.iterator();

                    while(routersList1.hasNext()) {
                        configuratorsList = (String)routersList1.next();
                        consumersList1.add(configuratorsList);
                    }
                }

                ArrayList routersList2 = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/routers";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    Iterator configuratorsList1 = nodeList.iterator();

                    while(configuratorsList1.hasNext()) {
                        String dubboService = (String)configuratorsList1.next();
                        routersList2.add(dubboService);
                    }
                }

                ArrayList configuratorsList2 = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/configurators";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    Iterator dubboService1 = nodeList.iterator();

                    while(dubboService1.hasNext()) {
                        String node = (String)dubboService1.next();
                        configuratorsList2.add(node);
                    }
                }

                DubboService dubboService2 = new DubboService(interfaceNode, serviceAddressList);
                dubboService2.setConfiguratorsList(configuratorsList2);
                dubboService2.setRoutersList(routersList2);
                dubboService2.setConsumersList(consumersList1);
                dubboServiceList.add(dubboService2);
            }
        } else {
            return dubboServiceList;
        }
    }

    private List<String> getServiceDetail(String rootNode, DubboService dubboService, boolean needProviders, boolean needConsumers, boolean needRoutes, boolean needConfigurators) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        HashSet applicationSet = new HashSet();
        if(this.zooKeeper.exists(rootNode, false) != null && dubboService != null) {
            String interfaceNode = dubboService.getServiceName();
            ArrayList configuratorsList;
            String path;
            List nodeList;
            Iterator i$;
            String node;
            if(needProviders) {
                configuratorsList = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/providers";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    i$ = nodeList.iterator();

                    while(true) {
                        if(!i$.hasNext()) {
                            dubboService.setServiceAddressList(configuratorsList);
                            break;
                        }

                        node = (String)i$.next();
                        System.out.println("transIp:[" + this.transIp + "]--- transPort:[" + this.transPort + "]---->node:[" + node + "]");
                        String decodeNode;
                        String appName;
                        if(null != this.transIp && !"".equals(this.transIp)) {
                            decodeNode = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
                            node = node.replaceAll(decodeNode, this.transIp);
                            if(null != this.transPort && !"".equals(this.transPort)) {
                                node = URLDecoder.decode(node, "UTF-8");
                                appName = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}:(\\d+))";
                                Pattern p = Pattern.compile(appName);
                                Matcher m = p.matcher(node);

                                String port;
                                for(port = ""; m.find(); port = m.group(2)) {
                                    ;
                                }

                                node = URLEncoder.encode(node.replace(port, this.transPort), "UTF-8");
                            }

                            configuratorsList.add(node);
                        } else {
                            configuratorsList.add(node);
                        }

                        decodeNode = URLDecoder.decode(node, "UTF-8");
                        appName = this.getApplicationName(decodeNode);
                        if(!applicationSet.contains(appName)) {
                            applicationSet.add(appName);
                        }
                    }
                }
            }

            if(needConsumers) {
                configuratorsList = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/consumers";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    i$ = nodeList.iterator();

                    while(i$.hasNext()) {
                        node = (String)i$.next();
                        configuratorsList.add(node);
                    }

                    dubboService.setConsumersList(configuratorsList);
                }
            }

            if(needRoutes) {
                configuratorsList = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/routers";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    i$ = nodeList.iterator();

                    while(i$.hasNext()) {
                        node = (String)i$.next();
                        configuratorsList.add(node);
                    }

                    dubboService.setRoutersList(configuratorsList);
                }
            }

            if(needConfigurators) {
                configuratorsList = new ArrayList();
                path = rootNode + SEPARATOR + interfaceNode + "/configurators";
                if(this.zooKeeper.exists(path, false) != null) {
                    nodeList = this.zooKeeper.getChildren(path, true);
                    i$ = nodeList.iterator();

                    while(i$.hasNext()) {
                        node = (String)i$.next();
                        configuratorsList.add(node);
                    }

                    dubboService.setConfiguratorsList(configuratorsList);
                }
            }
        }

        return new ArrayList(applicationSet);
    }

    private List<String> getApplication2(String rootNode) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        HashSet applicationSet = new HashSet();
        if(this.zooKeeper.exists(rootNode, false) != null) {
            List rootNodeList = this.zooKeeper.getChildren(rootNode, false);
            Iterator i$ = rootNodeList.iterator();

            while(true) {
                String allNode;
                do {
                    if(!i$.hasNext()) {
                        return new ArrayList(applicationSet);
                    }

                    String node = (String)i$.next();
                    allNode = rootNode + SEPARATOR + node + "/providers";
                } while(this.zooKeeper.exists(allNode, false) == null);

                List nodeList = this.zooKeeper.getChildren(allNode, false);
                Iterator i$1 = nodeList.iterator();

                while(i$1.hasNext()) {
                    String n = (String)i$1.next();
                    String decodeNode = URLDecoder.decode(n, "UTF-8");
                    String appName = this.getApplicationName(decodeNode);
                    if(this.targetApplicationList.contains(appName)) {
                        applicationSet.add(appName);
                    }
                }
            }
        } else {
            return new ArrayList(applicationSet);
        }
    }

    private void deleteDubboApplication(String rootNode, DubboApplication dubboApplication) throws KeeperException, InterruptedException {
        Iterator i$ = dubboApplication.getDubboServices().iterator();

        while(i$.hasNext()) {
            DubboService dubboService = (DubboService)i$.next();
            this.deleteAll(rootNode + SEPARATOR + dubboService.getServiceName());
        }

    }

    public void deleteAll(String path) throws KeeperException, InterruptedException {
        try {
            if(this.zooKeeper.exists(path, false) != null) {
                this.zooKeeper.delete(path, -1);
                System.out.println("Delete node #### " + path);
            }
        } catch (KeeperException.NotEmptyException var5) {
            Iterator i$ = this.zooKeeper.getChildren(path, false).iterator();

            while(i$.hasNext()) {
                String childrenPath = (String)i$.next();
                this.deleteAll(path + "/" + childrenPath);
                this.deleteAll(path);
            }
        }

    }

    private void clear(List<DubboApplication> dubboApplicationList) throws KeeperException, InterruptedException {
        this.clear("/dubbo", dubboApplicationList);
    }

    private void clear(String rootNode, List<DubboApplication> dubboApplicationList) throws KeeperException, InterruptedException {
        Iterator i$ = dubboApplicationList.iterator();

        while(i$.hasNext()) {
            DubboApplication dubboApplication = (DubboApplication)i$.next();
            System.out.println("Clear dubbo application #### " + dubboApplication.getApplicationName());
            this.deleteDubboApplication(rootNode, dubboApplication);
        }

    }

    private void push(String rootNode, List<DubboApplication> dubboApplicationList) throws KeeperException, InterruptedException {
        this.create(rootNode);
        Iterator i$ = dubboApplicationList.iterator();

        while(i$.hasNext()) {
            DubboApplication dubboApplication = (DubboApplication)i$.next();
            System.out.println("Push dubbo application #### " + dubboApplication.getApplicationName());
            Iterator i$1 = dubboApplication.getDubboServices().iterator();

            while(i$1.hasNext()) {
                DubboService service = (DubboService)i$1.next();
                this.create(rootNode + SEPARATOR + service.getServiceName());
                this.create(rootNode + SEPARATOR + service.getServiceName() + "/providers");
                Iterator i$2 = service.getServiceAddressList().iterator();

                while(i$2.hasNext()) {
                    String address = (String)i$2.next();
                    this.create(rootNode + SEPARATOR + service.getServiceName() + "/providers" + SEPARATOR + address);
                }
            }
        }

    }

    public void create(String path) throws KeeperException, InterruptedException {
        if(this.zooKeeper.exists(path, false) == null) {
            this.zooKeeper.create(path, (byte[])null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Create node #### " + path);
        }

    }

    private List<DubboApplication> getApplicationList(String rootNode) throws InterruptedException, UnsupportedEncodingException, KeeperException {
        ArrayList dubboApplicationList = new ArrayList();
        HashMap applicationMap = new HashMap();
        if(this.zooKeeper.exists(rootNode, false) != null) {
            List rootNodeList = this.zooKeeper.getChildren(rootNode, true);
            Iterator i$ = rootNodeList.iterator();

            while(i$.hasNext()) {
                String interfaceNode = (String)i$.next();
                DubboService dubboService = new DubboService(interfaceNode);
                List appNames = this.getServiceDetail(rootNode, dubboService, true, true, true, true);
                Iterator i$1 = appNames.iterator();

                while(i$1.hasNext()) {
                    String appName = (String)i$1.next();
                    if(this.targetApplicationList.contains(appName)) {
                        DubboApplication dubboApplication = (DubboApplication)applicationMap.get(appName);
                        if(dubboApplication == null) {
                            dubboApplication = new DubboApplication(appName);
                            ArrayList dubboServices = new ArrayList();
                            dubboApplication.setDubboServices(dubboServices);
                            applicationMap.put(appName, dubboApplication);
                            dubboApplicationList.add(dubboApplication);
                        }

                        dubboApplication.getDubboServices().add(dubboService);
                    }
                }
            }
        }

        return dubboApplicationList;
    }

    String getApplicationName(String providersNode) {
        Pattern pattern = Pattern.compile("&application=(.+?)&");
        Matcher matcher = pattern.matcher(providersNode);
        if(matcher.find()) {
            String application = matcher.group();
            application = application.replaceAll("&", "");
            application = application.replaceAll("application=", "");
            return application;
        } else {
            return null;
        }
    }

    private DubboTransfer() {
    }

    public void close() throws InterruptedException {
        this.zooKeeper.close();
    }

    public String getTransIp() {
        return this.transIp;
    }

    public void setTransIp(String transIp) {
        this.transIp = transIp;
    }

    public String getTransPort() {
        return this.transPort;
    }

    public void setTransPort(String transPort) {
        this.transPort = transPort;
    }

    public int getCharacterPosition(String source, String patter, int time) {
        Matcher slashMatcher = Pattern.compile(patter).matcher(source);
        int mIdx = 0;

        while(slashMatcher.find()) {
            ++mIdx;
            if(mIdx == time) {
                break;
            }
        }

        return slashMatcher.start();
    }

    public static void main(String[] args) {
        System.out.println(111);
    }
}
