package ru.cft.croudfounding.repository.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "donation")
public class Donation {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_project", foreignKey = @ForeignKey(name = "fk_project_donation"))
    @JsonBackReference
    private Project idProject;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "id_user", foreignKey = @ForeignKey(name = "fk_user_donation"))
    @JsonBackReference
    private User idUser;

    @Column(name = "amount", nullable = false)
    private Long amount;

}
