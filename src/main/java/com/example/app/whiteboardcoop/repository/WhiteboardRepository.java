package com.example.app.whiteboardcoop.repository;

import com.example.app.whiteboardcoop.model.User;
import com.example.app.whiteboardcoop.model.Whiteboard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhiteboardRepository extends JpaRepository<Whiteboard, Long> {

    List<Whiteboard> findByActiveTrue(Pageable pageable);
    List<Whiteboard> findByActiveFalse(Pageable pageable);
    List<Whiteboard> findByOwnerAndActiveTrue(User owner, Pageable pageable);
    List<Whiteboard> findByOwnerAndActiveFalse(User owner, Pageable pageable);
    int countAllByActiveTrue();
    int countAllByActiveFalse();
    int countAllByActiveTrueAndOwner(User owner);
    int countAllByActiveFalseAndOwner(User owner);
}
