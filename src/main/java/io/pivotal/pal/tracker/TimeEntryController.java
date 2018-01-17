package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepo;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService guage) {
        this.timeEntriesRepo = timeEntryRepository;
        this.counter = counter;
        this.gauge = guage;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {

        TimeEntry te = timeEntriesRepo.create(timeEntry);

        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());

        return new ResponseEntity<>(te, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = timeEntriesRepo.find(id);

        if (te != null) {
            counter.increment("TimeEntry.read");
            responseEntity = new ResponseEntity<>(te, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        ResponseEntity<List<TimeEntry>> responseEntity = null;

        counter.increment("TimeEntry.listed");

        List<TimeEntry> timeEntryList = timeEntriesRepo.list();

        responseEntity = new ResponseEntity<>(timeEntryList, HttpStatus.OK);

        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = timeEntriesRepo.update(id, timeEntry);

        if (te != null) {
            counter.increment("TimeEntry.updated");
            responseEntity = new ResponseEntity<>(te, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = timeEntriesRepo.delete(id);

        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntriesRepo.list().size());

        responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return responseEntity;

    }

}
