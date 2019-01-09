package cn.com.niub.service;

import cn.com.niub.domain.File;
import cn.com.niub.dto.FileDto;
import cn.com.niub.jpa.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    public FileDto findFileById(String id){
        return new FileDto(fileRepository.findOne(id));
    }

    public FileDto saveFile(FileDto fileDto){
        fileDto = new FileDto(fileRepository.saveAndFlush(new File(fileDto)));
        return fileDto;
    }

    @Transactional
    public void deleteByMid(String mid){
        fileRepository.deleteByMid(mid);
    }

}
