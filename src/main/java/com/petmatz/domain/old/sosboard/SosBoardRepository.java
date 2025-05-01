//package com.petmatz.domain.old.sosboard;
//
//import com.petmatz.domain.old.sosboard.entity.SosBoard;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface SosBoardRepository extends JpaRepository<SosBoard, Long> {
//
//    // 지역 필터링 (User의 region 기준)
//    @Query("SELECT s FROM SosBoard s JOIN s.user u WHERE u.region = :region")
//    Page<SosBoard> findByUserRegion(@Param("region") String region, Pageable pageable);
//
//    Page<SosBoard> findByUserNickname(String nickname, Pageable pageable);
//
//    void deleteByUserId(Long id);
//}
//
