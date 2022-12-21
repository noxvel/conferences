package com.nextvoyager.conferences.service.changereport.speaker;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;
import com.nextvoyager.conferences.service.changereport.ChangeReportAction;

public class AcceptProposeBySpeaker implements ChangeReportAction {
    @Override
    public void change(Report report, User speaker) {
        report.setStatus(Report.Status.CONFIRMED);
    }
}
