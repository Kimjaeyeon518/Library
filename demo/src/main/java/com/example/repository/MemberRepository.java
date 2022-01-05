package com.example.repository;

import com.example.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(@Param("email") String email);

    Page<Member> findAll(Pageable pageable);

    @Query("select m from Member m where m.role = :role")
    Page<Member> findAll(@Param("role") String role, Pageable pageable);
}