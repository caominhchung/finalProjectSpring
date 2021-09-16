package com.training.repository;

import com.training.dto.DashboardClass;
import com.training.entities.Class;
import com.training.entities.Trainer;
import com.training.entities.enumeration.ClassTypeName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    List<Class> findClassByName(String name);

    Page<Class> findClassByTrainerId(Pageable pageable, Integer id);

    @Query("select new  com.training.dto.DashboardClass(count(t.statusOfClass), t.statusOfClass) " +
            "from Class t GROUP BY t.statusOfClass")
    List<DashboardClass> countClassByStatus();

    @Query("select new  com.training.dto.DashboardClass(count(t.statusOfClass), t.statusOfClass) " +
            "from Class t WHERE t.type = ?1 GROUP BY t.statusOfClass")
    List<DashboardClass> countClassByStatusAndTypeClass(ClassTypeName classTypeName);

    @Query("select new  com.training.dto.DashboardClass(count(t.statusOfClass), t.statusOfClass) " +
            "from Class t WHERE t.type = ?1 AND (t.startDate >= ?2 and t.endDate <= ?3) " +
            "GROUP BY t.statusOfClass")
    List<DashboardClass> countClassByStatusAndTypeClassAndDate(ClassTypeName classTypeName, Date startDate, Date endDate);

    Class findClassByTrainer(Trainer trainer);
}
