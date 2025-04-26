package slavinn.io.jellydrop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "media.path")
public class MediaPaths {
	private String movies;
	private String shows;
	private String songs;
}
