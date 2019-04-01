package pl.kozyra.tau.Domain;

import java.util.Objects;

public class RPGfigure {

    private Long id;
    private String name;
    private int HP;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    @Override
    public String toString() {
        return "FigureDao{" +
                "id=" + id +
                ", name=" + name +
                ", HP=" + HP +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPGfigure rpGfigure = (RPGfigure) o;
        return HP == rpGfigure.HP &&
                Objects.equals(id, rpGfigure.id) &&
                Objects.equals(name, rpGfigure.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, HP);
    }
}
