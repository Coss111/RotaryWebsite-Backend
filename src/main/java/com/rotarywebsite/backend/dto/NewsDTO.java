package com.rotarywebsite.backend.dto;

import java.time.LocalDate;

public class NewsDTO {
    private Long id;
    private String title;
    private String lead;
    private String content;
    private LocalDate publicationDate;
    private String what;
    private String when;
    private String where;
    private String why;
    private String how;
    private Long authorId;
    private String authorName;

    // Constructors, Getters and Setters
    public NewsDTO() {}
    
    // Getters and Setters for all fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getLead() { return lead; }
    public void setLead(String lead) { this.lead = lead; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    
    public String getWhat() { return what; }
    public void setWhat(String what) { this.what = what; }
    
    public String getWhen() { return when; }
    public void setWhen(String when) { this.when = when; }
    
    public String getWhere() { return where; }
    public void setWhere(String where) { this.where = where; }
    
    public String getWhy() { return why; }
    public void setWhy(String why) { this.why = why; }
    
    public String getHow() { return how; }
    public void setHow(String how) { this.how = how; }
    
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}