package com.ledger.event.repository;

import com.ledger.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("SELECT e FROM Event e WHERE e.payload LIKE %:userId% AND e.timestamp > :startTimestamp AND e.type != 'SNAPSHOT' ORDER BY e.timestamp ASC")
    List<Event> findAfterTimestamp(@Param("userId") UUID userId, @Param("startTimestamp") long startTimestamp);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.payload LIKE %:userId% AND e.timestamp > :startTimestamp AND e.type != 'SNAPSHOT'")
    int countAfterTimestamp(@Param("userId") UUID userId, @Param("startTimestamp") long startTimestamp);

    @Query("SELECT e FROM Event e WHERE e.type = 'SNAPSHOT' AND e.payload LIKE %:userId% ORDER BY e.timestamp DESC LIMIT 1")
    Optional<Event> findFirstSnapshotByUserId(@Param("userId") UUID userId);
}
