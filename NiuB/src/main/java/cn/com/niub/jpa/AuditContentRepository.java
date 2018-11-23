package cn.com.niub.jpa;

import cn.com.niub.domain.AuditContent;
import cn.com.niub.dto.AuditContentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.validation.constraints.Null;
import java.util.List;

public interface AuditContentRepository extends JpaRepository<AuditContent, String>, JpaSpecificationExecutor<AuditContent> {

    List<AuditContent> findByOrderId(String orderId);

    AuditContent findByOrderIdAndStatus(String orderId,String status);
}
