package slavinn.io.jellydrop;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

	@GetMapping("/")
	public String home() {
		return "upload";
	}

	@PostMapping("/upload")
	public ResponseEntity<?> handleFileUplaod(@RequestParam("file") MultipartFile file) {
		Path tempFile = Paths.get("/tmp/" + file.getOriginalFilename());
		try {
			file.transferTo(tempFile);
			return ResponseEntity.ok("File uploaded successfully");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
		}
	}

	@PostMapping("/submit-upload")
	public ResponseEntity<?> saveFile(@RequestParam("filename") String filename,
			@RequestParam("contentType") String contentType) {
		// Determine the destination folder based on the content type
		String destinationFolder = getDestinationFolder(contentType);
		// Logic to move file from temp storage to NFS server
		try {
			Files.move(Paths.get("/tmp/" + filename), Paths.get(destinationFolder));
			return ResponseEntity.ok("File moved to appropriate folder");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
		}
	}

	private String getDestinationFolder(String contentType) {
		switch (contentType) {
			case "movie":
				return "/exports/jellyfin/Movies/";
			case "song":
				return "/exports/jellyfind/Music/";
			case "show":
				return "/exports/jellyfin/Shows/";
			default:
				return "/exports/others/";
		}
	}

}
