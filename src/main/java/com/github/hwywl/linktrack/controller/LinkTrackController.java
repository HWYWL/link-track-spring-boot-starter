package com.github.hwywl.linktrack.controller;

import com.github.hwywl.linktrack.config.LinkTrackConfig;
import com.github.hwywl.linktrack.model.LinkTrackNode;
import com.github.hwywl.linktrack.model.SystemStatistic;
import com.github.hwywl.linktrack.service.RunTimeNodeService;
import com.github.hwywl.linktrack.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 前端页面展示
 *
 * @author huangwenyi
 */
@Controller
@RequestMapping("/linkTrack")
public class LinkTrackController {
    @Autowired
    LinkTrackConfig linkTrackConfig;

    /**
     * 主页
     *
     * @return
     */
    @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        List<LinkTrackNode> list = RunTimeNodeService.getControllers();
        mv.addObject("list", list);
        SystemStatistic system = RunTimeNodeService.getRunStatistic();
        mv.addObject("system", system);
        mv.addObject("config", CacheUtil.getCache());
        String template = "index-freemarker";
        if ("thymeleaf".equals(linkTrackConfig.getShowTemplate())) {
            template = "index-thymeleaf";
        }

        mv.setViewName(template);
        return mv;
    }

    /**
     * 获取树形结构
     *
     * @param methodName 方法名称
     * @return
     */
    @GetMapping("/getTree")
    @ResponseBody
    public LinkTrackNode getTree(String methodName) {
        return RunTimeNodeService.getGraph(methodName);
    }

}
