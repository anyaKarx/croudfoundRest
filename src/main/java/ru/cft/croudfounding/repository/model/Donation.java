package ru.cft.croudfounding.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "donation")
@Setter
@Getter
public class Donation {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", foreignKey = @ForeignKey(name = "fk_project_donation"))
    @JsonBackReference
    private Project idProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user_donation"))
    @JsonBackReference
    private User idUser;

    @Column(name = "amount", nullable = false)
    private Long amount;

}
