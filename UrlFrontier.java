public interface UrlFrontier {
	public Url getNext() throws Exception;
	public boolean putUrl(Url url) throws Exception;
}