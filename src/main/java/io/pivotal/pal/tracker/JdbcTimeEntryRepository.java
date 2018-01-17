package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO time_entries (project_id, user_id, date, hours) " +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            statement.setLong(1, timeEntry.getProjectId());
            statement.setLong(2, timeEntry.getUserId());
            statement.setDate(3, Date.valueOf(timeEntry.getDate()));
            statement.setInt(4, timeEntry.getHours());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {
        List<TimeEntry> results = jdbcTemplate.query("Select * from time_entries where id = ?", new Object[]{id}, new RowMapper<TimeEntry>() {
            @Override
            public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
                TimeEntry timeEntry = new TimeEntry();

                timeEntry.setProjectId(rs.getLong("project_id"));
                timeEntry.setUserId(rs.getLong("user_id"));

                java.sql.Date rsDate = rs.getDate("date");
                LocalDate localDate = rsDate.toLocalDate();
                timeEntry.setDate(localDate);

                timeEntry.setHours(rs.getInt("hours"));

                timeEntry.setId(rs.getLong("id"));

                return timeEntry;
            }
        });

        if (results.size() == 1) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("Select * from time_entries ", new RowMapper<TimeEntry>() {
            @Override
            public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
                TimeEntry timeEntry = new TimeEntry();

                timeEntry.setProjectId(rs.getLong("project_id"));
                timeEntry.setUserId(rs.getLong("user_id"));

                java.sql.Date rsDate = rs.getDate("date");
                LocalDate localDate = rsDate.toLocalDate();
                timeEntry.setDate(localDate);

                timeEntry.setHours(rs.getInt("hours"));

                timeEntry.setId(rs.getLong("id"));

                return timeEntry;
            }
        });
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbcTemplate.update("update time_entries set project_id = ?, user_id = ?, date = ?, hours =? where id = ?", timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), id);

        return find(id);
    }

    @Override
    public TimeEntry delete(long id) {
        jdbcTemplate.update("delete from time_entries where id = ?", id);

        return null;
    }
}
