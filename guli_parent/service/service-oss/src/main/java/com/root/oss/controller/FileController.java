package com.root.oss.controller;

import com.root.oss.service.FileService;
import com.root.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "阿里云文件上传")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class FileController {

    @Autowired
    private FileService fileService;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upLoadFile(@ApiParam(name = "file",value = "文件",required = true) @RequestParam("file") MultipartFile file){
        String filePath=fileService.upLoad(file);
        return R.ok().message("文件上传成功").data("url",filePath);
    }
}
