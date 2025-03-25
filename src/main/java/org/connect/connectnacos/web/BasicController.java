/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
