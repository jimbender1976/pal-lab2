package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {

        TimeEntry te = timeEntryRepository.create(timeEntry);

        return new ResponseEntity<>(te, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = timeEntryRepository.find(id);

        if (te != null) {
            responseEntity = new ResponseEntity<>(te, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        ResponseEntity<List<TimeEntry>> responseEntity = null;

        List<TimeEntry> timeEntryList = timeEntryRepository.list();

        responseEntity = new ResponseEntity<>(timeEntryList, HttpStatus.OK);

        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = timeEntryRepository.update(id, timeEntry);

        if (te != null) {
            responseEntity = new ResponseEntity<>(te, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = timeEntryRepository.delete(id);

        responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return responseEntity;

    }

}
