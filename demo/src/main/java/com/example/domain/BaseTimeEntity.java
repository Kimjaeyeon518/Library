package com.example.domain;

import lombok.Getter;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // BaseTimeEntity 클래스를 상속하면 아래 필드들을 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing (자동으로 값 매핑) 기능 추가
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime creadtedDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}
