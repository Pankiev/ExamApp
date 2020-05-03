package pl.exam.app.business.user.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.exam.app.business.upload.FileStorageService;
import pl.exam.app.business.user.control.UserService;

import java.util.Collection;

@RestController
public class UsersController {

    private final FileStorageService fileStorageService;
    private final UserService userService;

    public UsersController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @GetMapping("/classesWithUsers")
    public Collection<SchoolClass> getClassesWithUsers() {
        return userService.getClassesWithUsers();
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.save(file);
        String downloadUri = buildDownloadUri(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.TEXT_PLAIN)
                .body(downloadUri);
    }

    private String buildDownloadUri(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();
    }
}
