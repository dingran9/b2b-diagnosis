package com.eedu.diagnosis.protal.model.response;

import java.util.List;

/**
 * 知识点获取的视频
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-18 14:23
 **/
public class VideoByKnowledgeModel {
    List<VideoInfoModel> resourceVideoList;

    public List<VideoInfoModel> getResourceVideoList() {
        return resourceVideoList;
    }

    public void setResourceVideoList(List<VideoInfoModel> resourceVideoList) {
        this.resourceVideoList = resourceVideoList;
    }
}
