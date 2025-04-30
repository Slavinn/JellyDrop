package slavinn.io.jellydrop;

import java.io.IOException;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {

	@Autowired
	private MediaPaths mediaPaths;

	@GetMapping("/")
	public String home() {
		return "upload";
	}

	@PostMapping("/upload")
	public ResponseEntity<?> handleFileUplaod(@RequestParam("file") MultipartFile file,
			@RequestParam("contentType") String contentType) {
		Map<String, String> response = new HashMap<>();

		Path tempFile = Paths.get("/tmp/" + file.getOriginalFilename());
		try {
			file.transferTo(tempFile);

			Path destinationFolder = Paths
					.get(getDestinationFolder(contentType));

			Path targetFile = destinationFolder.resolve(file.getOriginalFilename());

			Files.move(tempFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
			response.put("message", "File uploaded and saved successfully.");

			return ResponseEntity.ok(response);
		} catch (IOException e) {
			if (!Files.exists(tempFile)) {
				response.put("error", "Error uploading file");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(response);
			} else {
				response.put("error", "Error saving file:\n" + e.toString());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(response);
			}
		}
	}

	private String getDestinationFolder(String contentType) {
		switch (contentType) {
			case "movie":
				return mediaPaths.getMovies();
			case "song":
				return mediaPaths.getSongs();
			case "show":
				return mediaPaths.getShows();
			default:
				throw new IllegalArgumentException("Invalid content type");
		}
	}
}
