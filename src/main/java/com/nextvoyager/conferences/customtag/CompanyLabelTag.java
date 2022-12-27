package com.nextvoyager.conferences.customtag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.Year;
import java.util.StringJoiner;

public class CompanyLabelTag extends SimpleTagSupport {

    public static final String COMPANY_NAME = "Company, Inc";

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter writer = getJspContext().getOut();
        StringJoiner sj = new StringJoiner(" ");
        sj.add("©")
                .add(String.valueOf(Year.now().getValue()))
                .add(COMPANY_NAME);
        writer.print(sj.toString());
    }
}
