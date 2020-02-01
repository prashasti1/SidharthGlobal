package com.siddarthglobalschool.Util;

public class HomeWorkList {
    private String  home_work_date;
  private String  submission_date;
  private String  file_name;
  private String  description;

    public String getHome_work_date() {
        return home_work_date;
    }

    public void setHome_work_date(String home_work_date) {
        this.home_work_date = home_work_date;
    }

    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    private String  subject_name;
}
