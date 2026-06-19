package com.v1.donor.repository;

import com.v1.donor.entity.Donar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonarRepository extends JpaRepository<Donar,Integer> {

}
