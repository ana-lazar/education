package rest.contest.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.contest.domain.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.contest.repositories.RaceRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/races")
public class RaceController {
    @Autowired
    private RaceRepository raceRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Race> getAll(){
        return StreamSupport.stream(raceRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Race> race = raceRepository.findOne(id);
        if (race.isEmpty())
            return new ResponseEntity<String>("Race not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Race>(race.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Race create(@RequestBody Race race){
        race.setId(null);
        raceRepository.save(race);
        return race;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Race race) {
        if (raceRepository.findOne(id).isEmpty()) {
            return new ResponseEntity<String>("Race doesn't exist", HttpStatus.BAD_REQUEST);
        }
        race.setId(id);
        raceRepository.save(race);
        return new ResponseEntity<Race>(race, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Race race = raceRepository.remove(id);
        return new ResponseEntity<Race>(race, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String raceError(RuntimeException exception) {
        return exception.getMessage();
    }
}
