package com.CrimsonBackendDatabase.crimsondb.Applications.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationModel {
    private String status;
    private String coverLetter;
    private Date timeStamp;
}
