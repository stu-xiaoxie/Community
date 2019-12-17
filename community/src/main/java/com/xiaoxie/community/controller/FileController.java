package com.xiaoxie.community.controller;

import com.xiaoxie.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
    @RequestMapping("/file/upload")
    public FileDTO upload(){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("啦啦啦");
        fileDTO.setUrl("/images/wechat.png");
        return fileDTO;
    }
}
