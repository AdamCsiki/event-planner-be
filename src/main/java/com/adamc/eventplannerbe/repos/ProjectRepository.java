package com.adamc.eventplannerbe.repos;

import com.adamc.eventplannerbe.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    ArrayList<Project> findAllByName(String name);
    Project findOneById(Long id);
}
