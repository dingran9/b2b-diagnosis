package com.eedu.diagnosis.manager.controller.classtest;

import  com.eedu.diagnosis.manager.utils.ueditor.ActionEnter;
import com.eeduspace.b2b.report.service.TeacherReportOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ueditor富文本编辑器+七牛
-* @author zz
 */
@RestController
@RequestMapping("/ueditor")
public class QiniusController {
    Logger logger = LoggerFactory.getLogger(QiniusController.class);

    @Autowired
    private TeacherReportOpenService actionEnterService;
    @Autowired
    private ActionEnter actionEnter;

    @RequestMapping(value = "/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            String exec = actionEnter.exec(request);
            logger.info("response result：------->"+ exec);
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
