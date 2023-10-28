package com.adamc.eventplannerbe.repos;

import com.adamc.eventplannerbe.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    ArrayList<Project> findAllByName(String name);
    @Query(value = "SELECT id,name FROM event_planner.project", nativeQuery = true)
    ArrayList<Project> findAllPreview();
    Project findOneById(Long id);
    void deleteById(Long id);
}
