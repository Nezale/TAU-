package pl.labfive.boardGame.service;

import java.util.List;

import pl.labfive.boardGame.domain.RPGFigure;

public interface FigureManager {

	Long addFigure(RPGFigure rpgFigure);
	void updateFigure(RPGFigure rpgFigure);
	RPGFigure findFigureById(Long id);
	void deleteFigure(RPGFigure rpgFigure);
	List<RPGFigure>  findAllFigures();
	List<RPGFigure> findFiguresByName(String name);
}
