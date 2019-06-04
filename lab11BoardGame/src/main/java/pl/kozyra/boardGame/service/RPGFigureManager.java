package pl.kozyra.boardGame.service;

import java.util.List;

import pl.kozyra.boardGame.domain.Owner;
import pl.kozyra.boardGame.domain.RPGFigure;

public interface RPGFigureManager {

    Long addRPGFigurejkk(RPGFigure RPGFigure);

    List<RPGFigure> findAllRPGFigure();

    RPGFigure findRPGFigureById(Long id);

    void deleteRPGFigure(RPGFigure RPGFigure);

    void updateRPGFigure(RPGFigure RPGFigure);

    List<RPGFigure> findRPGFigureByName(String name);

    Long addOwner(Owner owner);

    List<RPGFigure> getAllRPGFiguresForOwner(Owner owner);

    Owner findOwnerById(Long id);

    void transferRPGFigureToAnotherOwner(RPGFigure RPGFigure1, RPGFigure RPGFigure2, Owner owner1, Owner owner2);

}