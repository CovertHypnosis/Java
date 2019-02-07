package com.aws.Repository;

import com.aws.model.Equipment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquipmentRepository extends CrudRepository<Equipment, String> {
    List<Equipment> findByEquipmentNameContains(String name);
}
