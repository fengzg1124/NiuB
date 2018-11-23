package cn.com.niub.service;

import cn.com.niub.domain.AuditContent;
import cn.com.niub.dto.AuditContentDto;
import cn.com.niub.jpa.AuditContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuditContentService {
    @Autowired
    AuditContentRepository auditContentRepository;

    public List<AuditContentDto> findByOrderId(String orderId){
                List<AuditContent> auditContents = auditContentRepository.findByOrderId(orderId);
                List<AuditContentDto> auditContentDtos = new ArrayList<>();
                for(AuditContent auditContent:auditContents){
                    AuditContentDto auditContentDto = new AuditContentDto(auditContent);
                    auditContentDtos.add(auditContentDto);
        }
        return auditContentDtos;
    }

    public AuditContentDto findByOrderIdAndStatus(String orderId,String status){
        return new AuditContentDto(auditContentRepository.findByOrderIdAndStatus(orderId,status));
    }

    public AuditContentDto saveAuditContent(AuditContentDto dto){
        AuditContent auditContent = auditContentRepository.findByOrderIdAndStatus(dto.getOrderId(),dto.getStatus());
        if (null!=auditContent) {
            dto.setId(auditContent.getId());
        }
        dto = new AuditContentDto(auditContentRepository.saveAndFlush(new AuditContent(dto)));
        return dto;
    }
}
