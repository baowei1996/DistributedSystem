package DCS;

import java.io.File;

/**
 * 配置文件读取类
 */
public class ConfigContext {
    static File configFile ;

    public static void setConfigFile(String filePath){
        configFile = new File(filePath);
    }

    public static String getParkAddress(){
        //TODO 替换为对于配置文件的查询
        return "127.0.0.1:8010";
    }
    public static String getParkHost(){
        return getHost(getParkAddress());
    }
    public static String getParkPort(){
        return getPort(getParkAddress());
    }

    //提供分离IP和Port的静态方法
    public static String getHost(String ip_port){
        return ip_port.substring(0,ip_port.indexOf(':'));
    }
    public static String getPort(String ip_port){
        return ip_port.substring(ip_port.indexOf(':')+1);
    }

    public static int getInitServices() {
        //TODO 替换为从配置文件中查询
        return 5;
    }

    public static int getMaxServices() {
        //TODO 替换为从配置文件中查询
        return 10;
    }

    public static long getHeartBeatInterval(){
        return 10*1000;
    }
}
