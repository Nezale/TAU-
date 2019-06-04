package pl.kozyra.boardGame.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.kozyra.boardGame.domain.RPGFigure;
import pl.kozyra.boardGame.service.RPGFigureManager;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RPGFigureController {

    @Autowired
    RPGFigureManager libraryManager;

    @RequestMapping(value ="/RPGFigures", method = RequestMethod.GET)
    public java.util.List<RPGFigure> getRPGFigures() {
        List<RPGFigure> RPGFigures = new LinkedList<>();
        for (RPGFigure p : libraryManager.findAllRPGFigure()) {
            RPGFigures.add(p.clone());
        }
        return RPGFigures;
    }

    @RequestMapping(value = "/RPGFigures",method = RequestMethod.POST)
    public RPGFigure addRPGFigure(@RequestBody RPGFigure nRPGFigure) {
        nRPGFigure.setId(libraryManager.addRPGFigurejkk(nRPGFigure));
        return nRPGFigure;
    }

    @RequestMapping(value = "/RPGFigure",method = RequestMethod.PUT)
    public String updateRPGFigure(@RequestBody RPGFigure nRPGFigure) {
        libraryManager.updateRPGFigure(nRPGFigure);
        return "ok";
    }




    @RequestMapping("/")
    public String index() {
        return "Hello";
    }

    @RequestMapping(value = "/RPGFigure/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RPGFigure getRPGFigure(@PathVariable("id") Long id) throws SQLException {
        return libraryManager.findRPGFigureById(id).clone();
    }

    @RequestMapping(value = "/RPGFigures", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RPGFigure> getRPGFigures(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<RPGFigure> RPGFigures = new LinkedList<RPGFigure>();
        for (RPGFigure p : libraryManager.findAllRPGFigure()) {
            if (f == null) {
                RPGFigures.add(p.clone());
            } else if (p.getName().contains(f)) {
                RPGFigures.add(p);
            }
        }
        return RPGFigures;
    }

    @RequestMapping(value = "/RPGFigure/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRPGFigure(@PathVariable("id") Long id) throws SQLException {
        libraryManager.deleteRPGFigure(libraryManager.findRPGFigureById(id));
        return "OK";
    }

}
