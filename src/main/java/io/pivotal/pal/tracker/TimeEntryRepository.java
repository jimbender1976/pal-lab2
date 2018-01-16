package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(final TimeEntry timeEntry);

    TimeEntry find(final long id);

    List<TimeEntry> list();

    TimeEntry update(final long id, final TimeEntry timeEntry);

    TimeEntry delete(final long id);
}
