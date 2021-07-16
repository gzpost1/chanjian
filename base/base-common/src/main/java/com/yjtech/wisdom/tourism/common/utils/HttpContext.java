/**
 * Copyright 2018-2020 Mujun~ & Mujun~ (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yjtech.wisdom.tourism.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 快捷获取HttpServletRequest,HttpServletResponse
 *
 * @author Mujun~
 * @Date 2020/09/29 11:24
 */
@Slf4j
public class HttpContext {

	/**
	 * 获取请求的ip地址
	 *
	 */
	public static String getIp() {
		HttpServletRequest request = HttpContext.getRequest();
		if (request == null) {
			return "127.0.0.1";
		} else {
			return request.getRemoteHost();
		}
	}

	/**
	 * 获取当前请求的Request对象
	 *
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		} else {
			return requestAttributes.getRequest();
		}
	}

	/**
	 * 获取当前请求的Response对象
	 *
	 */
	public static HttpServletResponse getResponse() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			return null;
		} else {
			return requestAttributes.getResponse();
		}
	}

	/**
	 * 获取所有请求的值
	 *
	 */
	public static Map<String, String> getRequestParameters() {
		HashMap<String, String> values = new HashMap<>();
		HttpServletRequest request = HttpContext.getRequest();
		if (request == null) {
			return values;
		}
		Enumeration enums = request.getParameterNames();
		while (enums.hasMoreElements()) {
			String paramName = (String) enums.nextElement();
			String paramValue = request.getParameter(paramName);
			values.put(paramName, paramValue);
		}
		return values;
	}
	
	public static String getBodyString(HttpServletRequest request)  {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
