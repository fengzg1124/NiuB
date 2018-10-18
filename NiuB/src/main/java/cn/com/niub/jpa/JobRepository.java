package cn.com.niub.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.niub.domain.Job;

public interface JobRepository extends JpaRepository<Job, String>, JpaSpecificationExecutor<Job> {

}
