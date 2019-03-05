package pl.kozyra.tau.Domain;

public class RPGfigure {

    private long id;
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

    @Override
    public String toString() {
        return "FigureDao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", HP=" + HP +
                '}';
    }
}
