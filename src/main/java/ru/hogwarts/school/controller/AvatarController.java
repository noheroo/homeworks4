package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.record.AvatarRecord;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
//        if (avatar.getSize() >= 1024 * 300) {
//            return ResponseEntity.badRequest().body("File is too big");
//        }
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/fromDb")
    public ResponseEntity<byte[]> downloadAvatarFromDb(@PathVariable Long studentId) {
        AvatarRecord avatarRecord = avatarService.findStudentAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatarRecord.getMediaType()));
        headers.setContentLength(avatarRecord.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatarRecord.getData());
    }

    @GetMapping(value = "/{studentId}/fromFolder")
    public void downloadAvatarFromFolder(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        AvatarRecord avatarRecord = avatarService.findStudentAvatar(studentId);
        Path path = Path.of(avatarRecord.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatarRecord.getMediaType());
            response.setContentLength((int) avatarRecord.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/allByPages")
    public ResponseEntity<List<AvatarRecord>> getAllAvatars(@RequestParam int pageNumber,
                                                            @RequestParam int pageSize) {
        return ResponseEntity.ok(avatarService.getAllAvatars(pageNumber, pageSize));
    }

}
