package com.nextvoyager.conferences.service.changereport;

import com.nextvoyager.conferences.model.entity.Report;
import com.nextvoyager.conferences.model.entity.User;

public interface ChangeReportAction {
    void change(Report report, User speaker);
}
