package com.ws.masterserver.repository;

import com.ws.masterserver.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query("select s\n" +
            "from BrandEntity s\n" +
            "where (:textSearch is null or upper(unaccent(s.name)) like concat('%', unaccent(:textSearch), '%'))\n" +
            "and (:active is null or s.active = :active)")
    Page<BrandEntity> search(@Param("textSearch") String textSearch,
                            @Param("active") Boolean active,
                            Pageable pageable);
}
