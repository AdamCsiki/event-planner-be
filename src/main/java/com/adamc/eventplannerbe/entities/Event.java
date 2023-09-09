package com.adamc.eventplannerbe.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String creator;
    private Integer maxParticipants;
    private Date startDate;
    private Date endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<User> invitees = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<User> participants = new ArrayList<>();

    @Getter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<User> blackList = new ArrayList<>();

    public void setInvitees(List<User> invitees) {
        this.invitees = invitees;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public void setBlackList(List<User> blackList) {
        this.blackList = blackList;
    }

}
