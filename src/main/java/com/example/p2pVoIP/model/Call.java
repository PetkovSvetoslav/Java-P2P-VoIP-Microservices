package com.example.p2pVoIP.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "calls")
public class Call {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long callerId;

    @Column(nullable = false)
    private Long receiverId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CallStatus status;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    public enum CallStatus {
        ONGOING,
        ENDED
    }


}
