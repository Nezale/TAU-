package pl.tau.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.tau.boardgame.domain.Figure;
import pl.tau.boardgame.domain.Owner;
import pl.tau.boardgame.service.FigureManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class OwnerController {
    @Autowired
    FigureManager figureManager;

    @RequestMapping("/figures")
    public List<Figure> getFigures(){
        List<Figure> figures = new LinkedList<>();
        for (Figure p : figureManager.findAllFigure()) {
            figures.add(p.clone());
        }
        return figures;
    }

    @RequestMapping(value = "/figures",method = RequestMethod.POST)
    public Figure addFigure(@RequestBody Figure figure) {
        figure.setId(figureManager.addFigure(figure.clone()));
        return figure;
    }

    @RequestMapping("/")
    public String index() {
        return "BoardGame app";
    }

    @RequestMapping(value = "/figure/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Figure getFigure(@PathVariable("id") Long id) throws SQLException {
        return figureManager.findFigureById(id);
    }

    @RequestMapping(value = "/figure", produces = MediaType.APPLICATION_JSON_VALUE, method = PUT)
    @ResponseBody
    public Figure updateFigure(@RequestBody Figure figure) throws SQLException {
        figureManager.updateFigure(figure);
        return figure;
    }

    @RequestMapping(value = "/figures", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Figure> getFigures(@RequestParam(value = "filter", required = false) String s) throws SQLException {
        List<Figure> figures = new LinkedList<>();
        for (Figure f : figureManager.findAllFigure()) {
            if (f == null) {
                figures.add(f.clone());
            } else if (f.getName().contains(s)) {
                figures.add(f);
            }
        }
        return figures;
    }

    @RequestMapping(value = "/figure/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteFigure(@PathVariable("id") Long id) throws SQLException {
        figureManager.deleteFigure(figureManager.findFigureById(id));
        return "OK";
    }
}
