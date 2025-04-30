package slavinn.io.jellydrop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "media.path")
public class MediaPaths {
	private String movies;
	private String shows;
	private String songs;

	public String getMovies() {
		return this.movies;
	}

	public void setMovies(String moviePath) {
		this.movies = moviePath;
	}

	public String getShows() {
		return this.shows;
	}

	public void setShows(String showsPath) {
		this.shows = showsPath;
	}

	public String getSongs() {
		return this.songs;
	}

	public void setSongs(String songsPath) {
		this.songs = songsPath;
	}
}
