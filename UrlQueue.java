import java.io.FileNotFoundException;
import com.sleepycat.je.DatabaseException;

public class UrlQueue
{
	private Frontier unvisitedUrl;
	private BloomFilter visitedUrl;
	
	public UrlQueue(String homeDirectory) throws DatabaseException, FileNotFoundException{
		unvisitedUrl=new Frontier(homeDirectory);
		visitedUrl=new BloomFilter();
	}
	
	protected void finalize(){
		unvisitedUrl.close();
	}
	public  void addVistedUrl(Url url) throws Exception
	{
		visitedUrl.put(url);
	}	
	
	public void addUnvisitedUrl(Url url) throws Exception
	{
		if(url!=null && !url.getOriUrl().trim().equals("")
				&& !unvisitedUrl.contains(url) && !visitedUrl.contains(url))
			unvisitedUrl.putUrl(url);
	}
	
	public  Url getUnvisitedUrl() throws Exception
	{
		return unvisitedUrl.getNext();
	}
	
	
	public int getVisitedNumber()
	{
		return visitedUrl.size();
	}
	
	public boolean unVisitedUrlIsEmpty()
	{
		return unvisitedUrl.isEmpty();
	}
}