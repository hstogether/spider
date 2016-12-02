import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
//import org.apache.commons.httpclient.UsernamePasswordCredentials;
//import org.apache.commons.httpclient.auth.AuthScope;
//import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class DownloadFile
{
	private static HttpClient httpClient=new HttpClient();

	static
	{
		/*
		httpClient.getHostConfiguration().setProxy("103.231.67.176", 9999);
		httpClient.getParams().setAuthenticationPreemptive(true);
		httpClient.getParams().setParameter(CredentialsProvider.PROVIDER, new MyProxyCredentialsProvider());//FIXME
		httpClient.getState().setProxyCredentials(new AuthScope("103.231.67.176",AuthScope.ANY_PORT, AuthScope.ANY_REALM),
				new UsernamePasswordCredentials("userName","password"));
		*/
	}
	
	public DownloadFile(){
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
	}
	
	public String getFileNameByUrl(String url,String contentType)
	{
		url=url.substring("http://".length());
		if(contentType.indexOf("html")!=-1)
		{
			return url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
		}
		else
		{
			return url.replaceAll("[\\?/:|<>\"]", "_")+"."+contentType.substring(contentType.indexOf("/")+1);
		}
	}
	
	private void saveToLocal(String fileName,String url,InputStream input)
	{
		Warehouse.insert(fileName,url, input);
	}
	
	public String downloadFile(String url)
	{
		String fileName=null;
		
		PostMethod postMethod=new PostMethod(url);
		//postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);
		postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		try
		{
			InputStream respondBody=null;
			int statusCode=httpClient.executeMethod(postMethod);
			
			if(statusCode==HttpStatus.SC_OK)//200
			{
				respondBody=postMethod.getResponseBodyAsStream();
				fileName=getFileNameByUrl(url,postMethod.getResponseHeader("Content-Type").getValue());
				saveToLocal(fileName,url,respondBody);
			}
			else if(statusCode==HttpStatus.SC_MOVED_TEMPORARILY || statusCode==HttpStatus.SC_MOVED_PERMANENTLY
					|| statusCode==HttpStatus.SC_SEE_OTHER || statusCode==HttpStatus.SC_TEMPORARY_REDIRECT)//302 301 303 307
			{
				Header header=postMethod.getResponseHeader("location");
				if(header!=null)
				{
					String newUrl=header.getValue();
					if(newUrl==null||newUrl.trim().equals(""))
					{
						newUrl="/";
					//}
					PostMethod redirect=new PostMethod(newUrl);
					
					if(httpClient.executeMethod(redirect)==HttpStatus.SC_OK)
					{
						respondBody=postMethod.getResponseBodyAsStream();
						fileName=getFileNameByUrl(url,postMethod.getResponseHeader("Content-Type").getValue());
						saveToLocal(fileName,url,respondBody);
					}
					else System.err.println("Method failed: "+ postMethod.getStatusLine());
					}
				}
			}
			else
			{
				System.err.println("Method failed: "+ postMethod.getStatusLine());
			}			
		}
		catch(HttpException e)
		{
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			postMethod.releaseConnection();
		}
		
		return fileName;
	}
	
}