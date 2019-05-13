package pl.tau.boardgame.service;

import java.util.List;

import pl.tau.boardgame.domain.Figure;
import pl.tau.boardgame.domain.Owner;

public interface FigureManager {

    Long addFigure(Figure figure);

    List<Figure> findAllFigure();

    Figure findFigureById(Long id);

    void deleteFigure(Figure figure);

    void updateFigure(Figure figure);

    List<Figure> findFigureByName(String phoneName);

    Long addOnwer(Owner owner);

    List<Figure> getAllFiguresForOwner(Owner owner);

    Owner findOwnerById(Long id);

    void transferFigureToAnotherOwner(Figure figure1, Figure figure2, Owner owner1, Owner owner2);

}
