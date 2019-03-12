package pl.kozyra.tau.Domain;

import java.util.Objects;

public class RPGfigure {

    private Long id;
    private String name;
    private int HP;

    public long getId() {
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

    public RPGfigure(Long id, String name, int HP) {
        this.name = name;
        this.id = id;
        this.HP = HP;
    }

    public RPGfigure(){

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPGfigure rpGfigure = (RPGfigure) o;
        return id == rpGfigure.id &&
                HP == rpGfigure.HP &&
                Objects.equals(name, rpGfigure.name);
    }

    @Override
    public String toString() {
        return "FigureDao{" +
                "id=" + id +
                ", name=" + name +
                ", HP=" + HP +
                '}';
    }
}
