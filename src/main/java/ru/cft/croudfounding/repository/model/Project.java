package ru.cft.croudfounding.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.ZZZ")
    private LocalDateTime date;

    @Column(name = "end_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss.ZZZ")
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_project_parent_user"))
    @JsonBackReference
    private User parent;

    @Column(name = "cash_amount", nullable = false)
    private Long cashAmount;

    @Column(name = "cash_donated", nullable = false)
    private Long cashDonated;

    @Column(name = "description", nullable = false)
    private String description;

}
