package com.yjtech.wisdom.tourism.portal.controller.common;

import com.google.code.kaptcha.Producer;
import com.yjtech.wisdom.tourism.common.constant.Constants;
import com.yjtech.wisdom.tourism.common.core.domain.JsonResult;
import com.yjtech.wisdom.tourism.common.utils.sign.Base64;
import com.yjtech.wisdom.tourism.common.utils.uuid.IdUtils;
import com.yjtech.wisdom.tourism.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author liuhong
 */
@RestController
public class CaptchaController {
  @Resource(name = "captchaProducer")
  private Producer captchaProducer;

  @Resource(name = "captchaProducerMath")
  private Producer captchaProducerMath;

  @Autowired private RedisCache redisCache;

  // 验证码类型
  @Value("${app.captchaType}")
  private String captchaType;

  /** 生成验证码 */
  @GetMapping("/captchaImage")
  public JsonResult getCode(HttpServletResponse response) throws IOException {
    // 保存验证码信息
    String uuid = IdUtils.simpleUUID();
    String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

    String capStr = null, code = null;
    BufferedImage image = null;

    // 生成验证码
    if ("math".equals(captchaType)) {
      String capText = captchaProducerMath.createText();
      capStr = capText.substring(0, capText.lastIndexOf("@"));
      code = capText.substring(capText.lastIndexOf("@") + 1);
      image = captchaProducerMath.createImage(capStr);
    } else if ("char".equals(captchaType)) {
      capStr = code = captchaProducer.createText();
      image = captchaProducer.createImage(capStr);
    }

    redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
    // 转换流信息写出
    FastByteArrayOutputStream os = new FastByteArrayOutputStream();
    try {
      ImageIO.write(image, "jpg", os);
    } catch (IOException e) {
      return JsonResult.error(e.getMessage());
    }

    Map<String, Object> result = new HashMap<>();
    result.put("uuid", uuid);
    result.put("img", Base64.encode(os.toByteArray()));
    return JsonResult.success(result);
  }
}
