package pro.sbs.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pro.sbs.domain.Images;
import pro.sbs.domain.Team;
import pro.sbs.repository.TeamImageRepository;

@RequiredArgsConstructor
@Service
public class ImageService {
    @Value("${file.dir}")
    private String fileDir;
    
    private final TeamImageRepository teamImageRepository;
    
    public Integer saveFile(MultipartFile file, Team team) throws IllegalStateException, IOException {
        if (file.isEmpty()) {
            return null;
        }
        
        /*
         * (1) 파일 원래 이름 (origName)
         * (2) DB에 저장할 변경된 이름 (uuid)
         * (3) 확장자명 (extension)
         * ----------------
         * (4) 파일 경로 (savedPath)
         */
        
        // 원래 파일 이름 추출
        String origName = file.getOriginalFilename();
        
        // 파일 이름으로 쓸 uuid 생성
        Integer uuid = team.getTeamId();
        
        // 확장자명 추출
        String extension = origName.substring(origName.lastIndexOf("."));
        
        // uuid + extensiton 결합
        String savedName = uuid + extension;
        
        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;
        
        
        String path = ("D:\\test\\");
        File folderDir = new File(path);
        
        // 파일 엔터티 생성
        Images image = Images.builder().OriginalName(origName).fileId(uuid).fileUrl(savedPath).extension(extension).build();
        
        // 실제로 컴퓨터에 파일 저장하기.
        
        if (!folderDir.exists()) {
            folderDir.mkdir();
        }
        
        File makeFolder = new File("D:\\test\\" + uuid);
        
        
        
        makeFolder.mkdir();
        
        // 실제로 로컬에 uuid를 파일명으로 저장
        file.transferTo(new File(savedPath));
        
        // 데이터베이스에 파일 정보 저장 (레퍼지토리 호출)
        Images savedFile = teamImageRepository.save(image);
        
        return savedFile.getFid(); // DB에 저장될 사진 번호를 리턴합니다.
    }

    public Images readFile(Integer teamId) {
        Images entity = teamImageRepository.findByFileId(teamId).get();
                
        return entity;
        
    }

    public List<Images> findAll() {
        List<Images> files = teamImageRepository.findAll();
        return files;
    }
}
