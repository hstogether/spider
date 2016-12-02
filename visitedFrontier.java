
public interface visitedFrontier {
	public void put(Url url);
	public void put(String value);
	
	public boolean contains(Url url);
	public boolean contains(String value);
}
