package pl.tau.boardgame.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Owner")
@Table(name = "owner")
@NamedQueries({
        @NamedQuery(name = "owner.all", query = "Select o from Owner o"),
})
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    @OneToMany(cascade = CascadeType.PERSIST,
            mappedBy = "owner"
    )
    private List<Figure> figures;

    public Owner() {
    }

    public Owner(String firstName, List<Figure> figures) {
        this.firstName = firstName;
        this.figures = figures;
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

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }
}
