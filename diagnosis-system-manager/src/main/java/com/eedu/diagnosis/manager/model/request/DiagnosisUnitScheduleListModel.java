package com.eedu.diagnosis.manager.model.request;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by dqy on 2017/10/13.
 */
public class DiagnosisUnitScheduleListModel {

    @Valid
    @NotEmpty(message = "diagnosisUnitSchedules is empty.")
    private List<DiagnosisUnitScheduleModel> diagnosisUnitSchedules;

    public List<DiagnosisUnitScheduleModel> getDiagnosisUnitSchedules() {
        return diagnosisUnitSchedules;
    }

    public void setDiagnosisUnitSchedules(List<DiagnosisUnitScheduleModel> diagnosisUnitSchedules) {
        this.diagnosisUnitSchedules = diagnosisUnitSchedules;
    }
}
