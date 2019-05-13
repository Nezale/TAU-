package pl.tau.boardgame.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity(name = "Figure")
@Table(name = "figure")
@NamedQueries({
        @NamedQuery(name = "figure.all", query = "Select p from Figure p"),
        @NamedQuery(name = "figure.findFigure", query = "Select c from Figure c where c.name like :figureNameFragment"),
        @NamedQuery(name = "figure.findFigureByOwner", query = "Select c from Figure c where c.owner like :ownerNameFragment")
})
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer HP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Figure() {
    }

    public Figure(String name, Integer HP) {
        this.name = name;
        this.HP= HP;
    }

    public Figure(String name, Integer HP, Owner owner) {
        this.name = name;
        this.HP = HP;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHP() {
        return HP;
    }

    public void setHP(Integer HP) {
        this.HP = HP;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return Objects.equals(id, figure.id) &&
                Objects.equals(name, figure.name) &&
                Objects.equals(HP, figure.HP) &&
                Objects.equals(owner, figure.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, HP, owner);
    }

    @Override
    public String toString() {
        return "Figure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", HP=" + HP +
                ", owner=" + owner +
                '}';
    }
}
