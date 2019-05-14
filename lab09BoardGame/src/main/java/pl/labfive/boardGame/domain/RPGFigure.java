package pl.labfive.boardGame.domain;

import javax.persistence.*;

@Entity//(name = "RPGFigure")
//@Table(name = "figure")
@NamedQueries({
        @NamedQuery(name = "figure.all", query = "SELECT p FROM \"PUBLIC\".\RPGFIGURE\ p"),
       // @NamedQuery(name = "figure.findFigureByName", query = "Select c from figure c where c.name like :name")
})
public class RPGFigure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int hp;

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

    public int getHP() {
        return hp;
    }

    public void setHP(int HP) {
        this.hp = HP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPGFigure rpgFigure = (RPGFigure) o;
        return hp == rpgFigure.hp &&
                id.equals(rpgFigure.id) &&
                name.equals(rpgFigure.name);
    }

    @Override
    public int hashCode() {
        return 1337;
    }
}
