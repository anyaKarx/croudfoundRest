package ru.cft.croudfounding.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.ZZZ")
    @NotNull
    private LocalDateTime startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.ZZZ")
    @NotNull
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_project_parent_user"))
    @JsonBackReference
    @NotNull
    private User parent;

    @Column(name = "cash_amount")
    @NotNull
    private Long cashAmount;

    @Column(name = "cash_donated")
    @NotNull
    private Long cashDonated;

    @Column(name = "description")
    @NotNull
    private String description;
}
