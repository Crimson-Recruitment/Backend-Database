package com.CrimsonBackendDatabase.crimsondb.Applications;

import com.CrimsonBackendDatabase.crimsondb.Jobs.Jobs;
import com.CrimsonBackendDatabase.crimsondb.Meetings.Meetings;
import com.CrimsonBackendDatabase.crimsondb.Users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
public class Applications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @Column(length = 2048)
    private String coverLetter;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date timeStamp;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "job_id", referencedColumnName = "id")
    private Jobs job;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;

    @OneToOne(mappedBy = "application", cascade = CascadeType.ALL)
    private Meetings meetings;


}
