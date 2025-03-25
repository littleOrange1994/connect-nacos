package org.connect.connectnacos.service;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fancheng.zeng
 * @title
 * @email Fancheng.Zeng@geely.com
 * @create 2025/3/24
 * @desc
 */
@Service
public class NacosService {
    @Autowired
    private NamingService namingService;
    @Value("${server.port}")
    private int port;

    /**
     * 注册一个Sunshine节点
     */
    public void registerService() {
        try {
            // 注册服务到 Nacos
            Instance instance = new Instance();
            instance.setIp(LocalIpAddress.getLocalIpUsingNetworkInterface());  // 服务实例的 IP
            instance.setPort(port);           // 服务实例的端口

            // 设置元数据
            Map<String, String> metadata = new HashMap<>();
            metadata.put("name", "Sunshine");
            metadata.put("status", "online");
            metadata.put("ip", instance.getIp());
            metadata.put("pin", "123456");

            // 将元数据添加到 Instance 中
            instance.setMetadata(metadata);

            namingService.registerInstance("SunshineServer", instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销一个Sunshine节点
     */
    public void deregisterService() {
        try {
            // 注销服务
            namingService.deregisterInstance("SunshineServer", LocalIpAddress.getLocalIpUsingNetworkInterface(), port);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有空闲的Sunshine节点
     */
    public List<Instance> getAllOnlineServices() {
        List<Instance> list = new ArrayList<>();
        List<Instance> allServices = getAllServices();
        for (Instance instance : allServices) {
            //过滤元数据为 online 的服务
            Map<String, String> metadata = instance.getMetadata();
            if ("online".equals(metadata.get("status"))) {
                list.add(instance);
            }
        }
        return list;
    }

    /**
     * 获取一个空闲的Sunshine节点，并设置节点状态为占用中
     * todo 后期需要增加分布式锁，控制并发
     */
    public synchronized Instance getOneOnlineService() {
        //获取所有空闲的节点
        List<Instance> allOnlineServices = getAllOnlineServices();
        if (allOnlineServices == null || allOnlineServices.size() == 0) {
            return null;
        }
        //获取一个空闲节点
        Instance instance = allOnlineServices.get(0);
        //修改节点状态
        updateInstance(instance,"occupy");
        return instance;
    }

    /**
     * 归还节点，并设置节点状态为空闲
     */
    public void giveBackService() {
        //获取所有的节点
        List<Instance> allOnlineServices = getAllServices();
        if (allOnlineServices == null || allOnlineServices.size() == 0) {
            return;
        }

        //匹配当前节点
        Instance nowInstance=null;
        for (Instance instance : allOnlineServices) {
            if(instance.getIp().equals(LocalIpAddress.getLocalIpUsingNetworkInterface())){
                nowInstance=instance;
                break;
            }
        }
        //修改节点状态
        updateInstance(nowInstance,"online");
    }

    /**
     * 获取所有的Sunshine节点，不区分状态
     */
    public List<Instance> getAllServices() {
        List<Instance> list=new ArrayList<>();
        try {
            ListView<String> servicesOfServer = namingService.getServicesOfServer(1, 1000);
            List<String> data = servicesOfServer.getData();
            for (String name : data) {
                list.addAll(namingService.getAllInstances(name));
            }
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 修改节点状态
     */
    public void updateInstance(Instance instance,String status) {
        try {
            Map<String, String> metadata=instance.getMetadata();
            // 设置元数据
            metadata.put("status", status);

            // 将元数据添加到 Instance 中
            instance.setMetadata(metadata);

//            namingService.deregisterInstance("SunshineServer", LocalIpAddress.getLocalIpUsingNetworkInterface(), port);
            //覆写元数据
            namingService.registerInstance("SunshineServer", instance);
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }


}
