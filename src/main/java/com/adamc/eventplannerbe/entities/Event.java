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

    @Column(length = 50)
    private String title;

    @Column(length = 25)
    private String creator;

    @Column
    private Integer maxParticipants;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<User> invitees = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    private List<User> participants = new ArrayList<>();

    @Getter
    @OneToMany(fetch = FetchType.EAGER)
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
