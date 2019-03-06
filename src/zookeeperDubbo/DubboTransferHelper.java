package zookeeperDubbo;

import java.util.Arrays;

/**
 * Created by yecheng on 2016/12/8.
 *
 * 这个是主方法，可以直接运行
 *
 *
 */
public class DubboTransferHelper {
    /**
     * 环境接入本地
     * 首先会清除本地的dubbo注册信息，然后接入源环境的注册信息
     * @param sourceAddr
     * @param targetAddr
     * @param appNames
     */
    public static void switchZK(String sourceAddr,String targetAddr,String[] appNames){
        try{
            DubboTransfer sourceTransfer = new DubboTransfer(sourceAddr, Arrays.asList(appNames),null,null);
            DubboTransfer targetTransfer = new DubboTransfer(targetAddr, Arrays.asList(appNames));
            targetTransfer.transfer(sourceTransfer);
            System.out.println("=========从"+sourceAddr+"接入【"+Arrays.asList(appNames)+"】到"+targetAddr+"成功！============");
        }catch (Exception e){
            System.out.println("switchZK运行出错："+e.toString());
        }
    }

    /**
     * 清理本地zk
     * @param targetAddr
     * @param appNames
     */
    public static void cleanLocalZK(String targetAddr,String[] appNames){
        try{
            DubboTransfer targetTransfer = new DubboTransfer(targetAddr, Arrays.asList(appNames));
            targetTransfer.clear();
            System.out.println("=========清理本地【"+Arrays.asList(appNames)+"】成功！============");
        }catch (Exception e){
            System.out.println("cleanLocalZK运行出错："+e.toString());
        }
    }

    /**
     * 清除本地所有zk接入
     * @param targetAddr
     */
    public static void cleanLocalZKAll(String targetAddr){
        try{
            DubboTransfer targetTransfer = new DubboTransfer(targetAddr, null);
            targetTransfer.clearAll();
            System.out.println("=========清理本地所有dubbo服务成功！============");
        }catch (Exception e){
            System.out.println("cleanLocalZKAll运行出错："+e.toString());
        }
    }

    /**
     * 恢复本地，拷贝本地备份
     * @param targetAddr
     * @param appNames
     */
    public static void recoveryLocalZK(String targetAddr,String[] appNames){
        try{
            DubboTransfer targetTransfer = new DubboTransfer(targetAddr, Arrays.asList(appNames));
            targetTransfer.recovery();
            System.out.println("=========恢复本地【"+Arrays.asList(appNames)+"】成功！============");
        }catch (Exception e){
            System.out.println("recoveryLocalZK运行出错："+e.toString());
        }
    }

    /**
     * 清除本地zk备份，注意这里清除的是备份内容
     * @param targetAddr
     * @param appNames
     */
    public static void cleanLocalZKBak(String targetAddr,String[] appNames){
        try{
            DubboTransfer targetTransfer = new DubboTransfer(targetAddr, Arrays.asList(appNames));
            targetTransfer.clearBackup();
            System.out.println("=========清理本地【"+Arrays.asList(appNames)+"】的备份成功！============");
        }catch (Exception e){
            System.out.println("cleanLocalZKBak运行出错："+e.toString());
        }
    }

    /**
     * 清除本地的zk所有备份内容
     * @param targetAddr
     */
    public static void cleanLocalZKBakAll(String targetAddr){
        try{
            DubboTransfer targetTransfer = new DubboTransfer(targetAddr,null);
            targetTransfer.clearBackupAll();
            System.out.println("=========清理本地所有的备份成功！============");
        }catch (Exception e){
            System.out.println("cleanLocalZKBakAll运行出错："+e.toString());
        }
    }

    public static void main(String[] args) {
        System.out.printf("2222");
    }
}
