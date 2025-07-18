package ru.vaganov.lehaim.catalog;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vaganov.lehaim.catalog.entity.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/catalog")
@Tag(name = "Каталог Параметров")
@Slf4j
@RequiredArgsConstructor
public class ParameterCatalogController {

    private final ParameterCatalogService parameterCatalogService;

    @GetMapping("/all")
    public ResponseEntity<List<Parameter>> getFullCatalog(
            @RequestParam(name = "type", required = false) Parameter.ResearchType type){
        if(type == null) return new ResponseEntity<>(parameterCatalogService.findAll(), HttpStatus.OK);

        return new ResponseEntity<>(parameterCatalogService.findByResearchType(type), HttpStatus.OK);
    }

    @GetMapping("/all/grouped")
    public ResponseEntity<HashMap<Parameter.ResearchType, List<Parameter>>> getFullCatalogGroupedByResearch(){
        log.debug("Request to /catalog/all/grouped");
        List<Parameter> params = parameterCatalogService.findAll();
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
        Parameter parameter = parameterCatalogService.findById(id);
        if(parameter == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(parameter, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parameter> editParameter(@RequestBody Parameter parameter){
        Parameter paramToChange = parameterCatalogService.findById(parameter.getId());
        if(paramToChange == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        paramToChange.updateFieldsBy(parameter);
        paramToChange = parameterCatalogService.save(paramToChange);
        return new ResponseEntity<>(paramToChange, HttpStatus.OK);
    }
}
