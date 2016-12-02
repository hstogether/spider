import java.io.FileNotFoundException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.sleepycat.je.DatabaseException;

public class Spider6
{
	private UrlQueue urlQueue;
	private DownloadFile downloader;
	private ExecutorService exec;
	
	private Url visitUrl;
	
	private boolean url=false;
	private boolean download=false;
	private boolean extract=false;
	
	private synchronized void getUrl() throws Exception{
		System.out.println("in getUrl");
		do{
			visitUrl=urlQueue.getUnvisitedUrl();
		}while(visitUrl==null||!visitUrl.getOriUrl().startsWith("http"));
		
		urlQueue.addVistedUrl(visitUrl);
		System.out.println(visitUrl.getOriUrl());
		
		url=true;
		download=false;
		extract=false;
		notifyAll();
		//while(download==false&&extract==false)wait();//???
	}
	
	private synchronized void downloadPage() throws InterruptedException {
		System.out.println("in downloadPage");
		while(url==false||download==true)wait();
		downloader.downloadFile(visitUrl.getOriUrl());
		
		download=true;
		notifyAll();
	}
	
	private synchronized void extractUrl() throws Exception
	{
		System.out.println("in extractUrl");
		while(url==false||extract==true)wait();
		Set<String> links;
		links = HtmlParserTool.extractLinks(visitUrl.getOriUrl(),filter);
		addUrlBatch(links.toArray(new String[]{}));		
		
		extract=true;
		notifyAll();
	}
	
	private synchronized void waitForDownloadingAndExtracking() throws InterruptedException{
		while(download==false&&extract==false)wait();
	}

	private class DownloadPage implements Runnable{
		private Spider6 spider6;
		public DownloadPage(Spider6 spider6){this.spider6=spider6;}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!Thread.interrupted()){
				try {
					//spider6.waitForGetUrl();
					spider6.downloadPage();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class ExtractUrl implements Runnable{
		private Spider6 spider6;
		public ExtractUrl(Spider6 spider6){this.spider6=spider6;}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!Thread.interrupted()){
				try {
					//spider6.waitForGetUrl();
					spider6.extractUrl();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private class GetUrl implements Runnable{
		private Spider6 spider6;
		public GetUrl(Spider6 spider6){this.spider6=spider6;}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(!Thread.interrupted()){
				try {
					//spider6.waitForDownloading();
					spider6.getUrl();
					waitForDownloadingAndExtracking();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void crawing(String[] seeds,Spider6 spider6) throws Exception{
		addUrlBatch(seeds);
		exec=Executors.newFixedThreadPool(3);
		while(!urlQueue.unVisitedUrlIsEmpty() && urlQueue.getVisitedNumber()<=1000)
		{	
			exec.execute(new DownloadPage(spider6));
			exec.execute(new ExtractUrl(spider6));
			exec.execute(new GetUrl(spider6));
		}
		exec.shutdown();
	}
	
	public Spider6(String homeDirectory) throws DatabaseException, FileNotFoundException 
	{
		urlQueue=new UrlQueue(homeDirectory);
		downloader=new DownloadFile();
		exec=Executors.newCachedThreadPool();
	}

	private void addUrlBatch(String[] urls) throws Exception {
		for(String url:urls)
			urlQueue.addUnvisitedUrl(new Url(url));
	}
	
	LinkFilter filter=new LinkFilter(){
		public boolean accept(String url)
		{
			//if(url.startsWith("http://www.lietu.com"))
				return true;
			//else return false;
		}
	};
	
	public static void main(String[] args)
	{
		try{
			Spider6 spider6=new Spider6("data\\url");
			spider6.crawing(new String[]{"http://www.twt.edu.cn"},spider6);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}