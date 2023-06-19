package com.magadiflo.book.security.app.service;

import com.magadiflo.book.security.app.entities.Workout;
import com.magadiflo.book.security.app.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;

    @PreAuthorize("#workout.user == authentication.name")
    public void saveWorkout(Workout workout) {
        this.workoutRepository.save(workout);
    }

    public List<Workout> findWorkouts() {
        return this.workoutRepository.findAllByUser();
    }

    public void deleteWorkout(Integer id) {
        this.workoutRepository.deleteById(id);
    }
}
