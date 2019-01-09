package cn.com.niub.jpa;

import cn.com.niub.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, String>, JpaSpecificationExecutor<File> {

    @Modifying
    @Query(value = "update File set delFlag=0 where mid=?1")
    void deleteByMid(String mid);
}
