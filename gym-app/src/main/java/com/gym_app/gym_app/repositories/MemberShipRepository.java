package com.gym_app.gym_app.repositories;

import com.gym_app.gym_app.entities.MemberShipEntity;
import com.gym_app.gym_app.entities.emuns.MemberShipName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShipEntity,Long> {

    boolean existsByName(MemberShipName name);
    boolean existsByNameAndIdNot(MemberShipName name, Long id);

}
