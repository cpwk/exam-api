package com.yang.exam.commons.file.controller;

import com.yang.exam.commons.authority.Action;
import com.yang.exam.commons.authority.SessionType;
import com.yang.exam.commons.controller.BaseController;
import com.yang.exam.commons.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/adm/file")
public class AdmFileController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/upload_token")
    @Action(session = SessionType.NONE)
    public ModelAndView upload_token(String namespace, String fileName, int fileSize) throws Exception {
        return feedback(fileService.uploadToken("img", fileName, fileSize, true));
    }


}
