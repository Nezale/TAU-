package pl.tau.boardgame.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.tau.boardgame.domain.Figure;
import pl.tau.boardgame.domain.Owner;

import java.util.List;

public class FigureManagerImpl implements FigureManager {
    @Override
    public Long addFigure(Figure figure) {
        return null;
    }

    @Override
    public List<Figure> findAllFigure() {
        return null;
    }

    @Override
    public Figure findFigureById(Long id) {
        return null;
    }

    @Override
    public void deleteFigure(Figure figure) {

    }

    @Override
    public void updateFigure(Figure figure) {

    }

    @Override
    public List<Figure> findFigureByName(String phoneName) {
        return null;
    }

    @Override
    public Long addOnwer(Owner owner) {
        return null;
    }

    @Override
    public List<Figure> getAllFiguresForOwner(Owner owner) {
        return null;
    }

    @Override
    public Owner findOwnerById(Long id) {
        return null;
    }

    @Override
    public void transferFigureToAnotherOwner(Figure figure1, Figure figure2, Owner owner1, Owner owner2) {

    }
}
