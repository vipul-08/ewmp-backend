package com.hashedin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {
    private String to;
    private  String from ;
    private String subject;
    private String content;
    private HashMap<String, Object> model;
}
