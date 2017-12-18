package com.eedu.diagnosis.manager.utils.ueditor.upload;

import  com.eedu.diagnosis.manager.utils.ueditor.define.State;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;

	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = new Base64Uploader().save(this.request,
					this.conf);
		} else {
			state = new BinaryUploader().save(this.request, this.conf);
		}

		return state;
	}
}
