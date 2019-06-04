package pl.kozyra.boardGame.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Owner")
@NamedQuery(name = "owner.all", query = "Select o from Owner o")

public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "owner"
    )
    private List<RPGFigure> RPGFigures;

    public Owner() {
    }

    public Owner(String firstName, List<RPGFigure> RPGFigures) {
        this.firstName = firstName;
        this.RPGFigures = RPGFigures;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<RPGFigure> getRPGFigures() {
        return RPGFigures;
    }

    public void setRPGFigures(List<RPGFigure> RPGFigures) {
        this.RPGFigures = RPGFigures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(id, owner.id) &&
                Objects.equals(firstName, owner.firstName) &&
                Objects.equals(RPGFigures, owner.RPGFigures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, RPGFigures);
    }
}