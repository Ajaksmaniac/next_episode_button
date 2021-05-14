package ajaksmaniac.webscraping.htmlunit.HTMLUnit_test;

public class Episode {

	int id;
	String url;
	boolean lastWatched = false;
	public Episode(int id, String url, boolean lastWatched) {
		super();
		this.id = id;
		this.url = url;
		this.lastWatched = lastWatched;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isLastWatched() {
		return lastWatched;
	}
	public void setLastWatched(boolean lastWatched) {
		this.lastWatched = lastWatched;
	}
	@Override
	public String toString() {
		return "Episode [id=" + id + ", url=" + url + ", lastWatched=" + lastWatched + "]";
	}
	
	
	
}
