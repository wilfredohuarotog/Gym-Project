package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.MemberShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShipEntity,Long> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

}
