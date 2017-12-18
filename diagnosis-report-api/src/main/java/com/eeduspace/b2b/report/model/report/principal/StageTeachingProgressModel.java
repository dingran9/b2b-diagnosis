package com.eeduspace.b2b.report.model.report.principal;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuhongfei on 2017/10/11.
 */
public class StageTeachingProgressModel implements Serializable{

    private String releaseCode;

    private String releaseName;

    private Date startTime;

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    private Integer sn;

    public String getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(String releaseCode) {
        this.releaseCode = releaseCode;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
