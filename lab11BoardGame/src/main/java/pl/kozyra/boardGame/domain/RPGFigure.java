package pl.kozyra.boardGame.domain;

import javax.persistence.*;
import java.util.Objects;
@Entity(name = "RPGFigure")
@Table(name = "RPGFigure")
@NamedQueries({
        @NamedQuery(name = "RPGFigure.all", query = "Select b from RPGFigure b"),
        @NamedQuery(name = "RPGFigure.findRPGFigureByName", query = "Select b from RPGFigure b where b.name like :modelNameFragment"),
        @NamedQuery(name = "RPGFigure.findRPGFiguresByOwner", query = "Select c from RPGFigure c where c.owner= :owner")
})
public class RPGFigure {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer HP;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Owner owner;

    public RPGFigure() {
    }

    public RPGFigure(String name, Integer HP) {
        this.name = name;
        this.HP = HP;
    }

    public RPGFigure(Long id, String name, Integer HP) {
        this.id = id;
        this.name = name;
        this.HP = HP;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPGFigure RPGFigure = (RPGFigure) o;
        return Objects.equals(id, RPGFigure.id) &&
                Objects.equals(name, RPGFigure.name) &&
                Objects.equals(HP, RPGFigure.HP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, HP);
    }

    @Override
    public String toString() {
        return "RPGFigure{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", HP=" + HP +
                '}';
    }

    public RPGFigure clone() {
        RPGFigure p = new RPGFigure();
        p.owner = null;
        p.id = id;
        p.name = name;
        p.HP = HP;
        return p;
    }

}