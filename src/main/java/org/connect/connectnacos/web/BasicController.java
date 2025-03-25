package org.connect.connectnacos.web;

import com.alibaba.nacos.api.naming.pojo.Instance;
import org.connect.connectnacos.service.NacosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BasicController {
    @Autowired
    private NacosService nacosService;

    @RequestMapping("/registerService")
    @ResponseBody
    public String registerService() {
        nacosService.registerService();
        return "registerService";
    }

    @RequestMapping("/deregisterService")
    @ResponseBody
    public String deregisterService() {
        nacosService.deregisterService();
        return "deregisterService";
    }

    @RequestMapping("/getAllServices")
    @ResponseBody
    public List<Instance> getServicesOfServer() {
        return nacosService.getAllServices();
    }

    @RequestMapping("/getOneOnlineService")
    @ResponseBody
    public Instance getOneOnlineServices() {
        return nacosService.getOneOnlineService();
    }

    @RequestMapping("/giveBackService")
    @ResponseBody
    public String giveBackService() {
        nacosService.giveBackService();
        return "giveBackService";
    }

}
