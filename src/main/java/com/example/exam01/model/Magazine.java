package com.example.exam01.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Magazine extends Document {
    private String publisher;
    private String issueNumber;

    @Temporal(TemporalType.DATE)
    private Date dateIssue;

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getIssueNumber() { return issueNumber; }
    public void setIssueNumber(String issueNumber) { this.issueNumber = issueNumber; }

    public Date getDateIssue() { return dateIssue; }
    public void setDateIssue(Date dateIssue) { this.dateIssue = dateIssue; }
}