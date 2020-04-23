package pl.exam.app.business.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String fileStorageLocation) {
        this.fileStorageLocation = createUploadDirectory(fileStorageLocation);
    }

    private Path createUploadDirectory(String fileStorageLocation) {
        try {
            Path storagePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
            return Files.createDirectories(storagePath);
        } catch (Exception e) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }

    public String save(MultipartFile file) {
        String fileName = Optional.ofNullable(file.getOriginalFilename())
                .map(StringUtils::cleanPath)
                .orElseThrow(() -> new RuntimeException("Filename must be provided"));
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        save(file, targetLocation);
        return fileName;
    }

    private void save(MultipartFile file, Path targetLocation) {
        try (InputStream dataStream = file.getInputStream()) {
            Files.copy(dataStream, targetLocation);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving file", e);
        }
    }
}
