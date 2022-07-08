package ru.cft.croudfounding.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.ZZZ")
    private LocalDateTime date;

    @Column(name = "end_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.ZZZ")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_project_parent_user"))
    @JsonBackReference
    private User parent;

    @Column(name = "cash_amount", nullable = false)
    private Long cashAmount;

    @Column(name = "cash_donated", nullable = false)
    private Long cashDonated ;

    @Column(name = "description", nullable = false)
    private String description;

}
