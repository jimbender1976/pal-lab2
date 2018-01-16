package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private HashMap<Long, TimeEntry> repo = null;

    public InMemoryTimeEntryRepository() {
        repo = new HashMap<Long, TimeEntry>();
    }

    public TimeEntry create(final TimeEntry timeEntry) {
        Long id = null;

        id = new Long(repo.size() + 1);

        timeEntry.setId(id.longValue());

        repo.put(id, timeEntry);

        return timeEntry;
    }

    public TimeEntry find(final long id) {
        TimeEntry timeEntry = null;
        Long idToFind = null;

        idToFind = new Long(id);

        timeEntry = repo.get(idToFind);

        return timeEntry;
    }

    public List<TimeEntry> list() {
        List<TimeEntry> timeEntryList = null;

        timeEntryList = new ArrayList<>();

        for (Long id : repo.keySet()) {
            timeEntryList.add(repo.get(id));
        }
        return timeEntryList;
    }

    public TimeEntry update(final long id, final TimeEntry timeEntry) {
        TimeEntry te = null;

        te = repo.get(id);

        if (te != null) {
            repo.remove(id);
            timeEntry.setId(id);
            repo.put(id, timeEntry);
            return repo.get(id);
        }
        return null;
    }

    public TimeEntry delete(final long id) {
        ResponseEntity<TimeEntry> responseEntity = null;

        TimeEntry te = null;

        te = repo.get(new Long(id));

        if (te != null) {
            repo.remove(new Long(te.getId()));
        }
        return te;
    }
}
