package com.ws.masterserver.repository;

import com.ws.masterserver.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author myname
 */
@Repository
public interface BrandRepository extends JpaRepository<BrandEntity, String> {
    BrandEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    @Query("select m from BrandEntity m where m.active = ?1")
    List<BrandEntity> findByActive(boolean b);
}
