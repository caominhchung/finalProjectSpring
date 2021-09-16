package com.training.repository;

import com.training.entities.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    Trainee findTraineeById(Integer id);

    public Trainee findTraineeByAccount(String account);

    public Trainee findTraineeByEmail(String email);

    public Trainee findTraineeByFacebook(String facebook);

    public Trainee findTraineeByTelNumber(String phone);

    public Trainee findTraineeByName(String name);
}
