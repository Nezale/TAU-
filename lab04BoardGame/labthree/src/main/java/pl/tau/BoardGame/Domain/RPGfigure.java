package pl.tau.BoardGame.Domain;

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

    public RPGfigure(Long id, String name, int HP) {
        this.name = name;
        this.id = null;
        this.HP = HP;
    }

    public RPGfigure(){

    }
    @Override
    public boolean equals(Object o) {
        System.out.print(o.toString() + " rowna sie " + this);
        if (this == o) {
            System.out.println(true);
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            System.out.println(false);
            return false;
        }
        RPGfigure rpGfigure = (RPGfigure) o;

        boolean r = id.longValue() == rpGfigure.id.longValue() &&
                HP == rpGfigure.HP &&
                name.equals(rpGfigure.name);
        System.out.print("ostatni warunek" + r);
        return r;
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
