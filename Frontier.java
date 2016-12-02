import java.io.FileNotFoundException;
import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.je.DatabaseException;

public class Frontier extends BDBFrontier implements UrlFrontier{
	
	private StoredMap<Integer, Url> urlMap=null;//a database view
	private Integer head;
	private Integer tail;

	public Frontier(String homeDirectory) throws DatabaseException, FileNotFoundException {
		super(homeDirectory);
		// TODO Auto-generated constructor stub
		EntryBinding<Integer> keyBinding=new SerialBinding<Integer>(catalog,Integer.class);
		SerialBinding<Url> valueBinding=new SerialBinding<Url>(catalog,Url.class);
		urlMap=new StoredMap<Integer,Url>(database,keyBinding,valueBinding,true);
		head=0;
		tail=0;
	}

	@Override
	public Url getNext() throws Exception {
		// TODO Auto-generated method stub
		Url result=null;
		if(!urlMap.isEmpty()){
			result=urlMap.get(head);
			delete(head++);
			//System.out.println("get"+head+" "+result.getOriUrl());
		}
		
		//System.out.println(result.getOriUrl());
		return result;
	}

	@Override
	public boolean putUrl(Url url) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("put" + tail+" "+url.getOriUrl());
		if( put(tail++,url) != null) return true;
		else return false;
	}

	@Override
	protected Object put(Object key, Object value) {
		// TODO Auto-generated method stub
		//System.out.println((String)key+" "+((Url)value).getOriUrl());
		return urlMap.put((Integer)key, (Url)value);

	}

	@Override
	protected Object get(Object key) {
		// TODO Auto-generated method stub
		return urlMap.get(key);
	}

	@Override
	protected Object delete(Object key) {
		// TODO Auto-generated method stub
		return urlMap.remove(key);
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return urlMap.isEmpty();
	}

	public boolean contains(Url url) {
		// TODO Auto-generated method stub
		return urlMap.containsValue(url);
	}
	
	public static void main(String[] args){
		try{
			Frontier frontier=new Frontier("data\\url");
			
			/*
			Frontier frontier=new Frontier("D:\\workspace\\db");
			Url url=new Url();
			url.setOriUrl("http://www.163.com");
			frontier.putUrl(url);
			url.setOriUrl("http://www.164.com");
			frontier.putUrl(url);
			url.setOriUrl("http://www.165.com");
			frontier.putUrl(url);
			System.out.println(frontier.getNext().getOriUrl());
			System.out.println(frontier.getNext().getOriUrl());
			System.out.println(frontier.getNext().getOriUrl());
			*/
			System.out.println(((Url)frontier.get(60)).getOriUrl());
			frontier.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
	}
}