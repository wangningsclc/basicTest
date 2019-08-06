package zookeeperDubbo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * @Auth wn
 * @Date 2019/6/4
 */
public class ZookeeperTest {

    public static void main(String[] args) {
        try {
            ZooKeeper zk  = new ZooKeeper("127.0.0.1:2181",3000 , new TestWatcher() );
            String node = "/node1";
            Stat stat = zk.exists(node, false);
            String createResult = zk.create(node, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(createResult);
            byte[] b = zk.getData(node, false, stat);
            System.out.println(new String(b));
            zk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TestWatcher implements Watcher {


    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("----------11-----" + watchedEvent.getPath());
        System.out.println("----------11-----" + watchedEvent.getState());
        System.out.println("----------11-----" + watchedEvent.getType());
    }
}