package ru.vaganov.ResourceServer.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.ResourceServer.models.Parameter;
import ru.vaganov.ResourceServer.services.CatalogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/catalog")
public class CatalogController {
    Logger logger = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/all")
    public ResponseEntity<List<Parameter>> getFullCatalog(
            @RequestParam(name = "type", required = false) Parameter.ResearchType type){
        if(type == null) return new ResponseEntity<>(catalogService.findAll(), HttpStatus.OK);

        return new ResponseEntity<>(catalogService.findByResearchType(type), HttpStatus.OK);
    }

    @GetMapping("/all/grouped")
    public ResponseEntity<HashMap<Parameter.ResearchType, List<Parameter>>> getFullCatalogGroupedByResearch(){
        logger.debug("Request to /catalog/all/grouped");
        List<Parameter> params = catalogService.findAll();
        HashMap<Parameter.ResearchType, List<Parameter>> map = new HashMap<>();
        for(Parameter p: params){
            if(!map.containsKey(p.getResearchType())) map.put(p.getResearchType(), new ArrayList<>());
            map.get(p.getResearchType()).add(p);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //@Operation(hidden = true)
    @GetMapping("/{id}")
    public ResponseEntity<Parameter> getParamByid(@PathVariable Long id){
        Parameter parameter = catalogService.findById(id);
        if(parameter == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(parameter, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parameter> editParameter(@RequestBody Parameter parameter){
        Parameter paramToChange = catalogService.findById(parameter.getId());
        if(paramToChange == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        paramToChange.updateFieldsBy(parameter);
        paramToChange = catalogService.save(paramToChange);
        return new ResponseEntity<>(paramToChange, HttpStatus.OK);
    }
}
