package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.Result.Result;
import com.atguigu.ggkt.vod.service.VodServise;
import com.atguigu.ggkt.vod.utils.ConstantPropertiesUtil;
import com.atguigu.ggkt.vod.utils.Signature;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author shkstart
 * @create 2022-10-21 16:29
 */

@Api("腾讯云点播")
@RestController
@RequestMapping("/admin/vod")
public class VodController {

    @Autowired
    private VodServise vodServise;

//    上传视频接口
    @PostMapping("upload")
    public Result upload(){
        String fileId = vodServise.updateVideo();
        return Result.ok(fileId);
    }

    //    删除视频
    @DeleteMapping("remove/{fileId}")
    public Result remove(@PathVariable String fileId){
        vodServise.removeVideo(fileId);
        return Result.ok(null);
    }

//    上传
    @GetMapping("sign")
    public Result sign() {
        Signature sign = new Signature();
        // 设置 App 的云 API 密钥
        sign.setSecretId(ConstantPropertiesUtil.ACCESS_KEY_ID);
        sign.setSecretKey(ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2); // 签名有效期：2天
        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return Result.ok(signature);
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
            return Result.fail(null);
        }
    }

}
