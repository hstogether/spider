import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Url implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1262124779511671048L;
	
	public Url(){}
	
	public Url(String oriUrl) {
		// TODO Auto-generated constructor stub
		this.oriUrl=oriUrl;
	}

	public String getOriUrl() {
		return oriUrl;
	}
	public void setOriUrl(String oriUrl) {
		this.oriUrl = oriUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getUrlNo() {
		return urlNo;
	}

	public void setUrlNo(int urlNo) {
		this.urlNo = urlNo;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getHitNum() {
		return hitNum;
	}

	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}

	public String[] getUrlRefrences() {
		return urlRefrences;
	}

	public void setUrlRefrences(String[] urlRefrences) {
		this.urlRefrences = urlRefrences;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public Date getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Date timeToLive) {
		this.timeToLive = timeToLive;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	private String oriUrl;
	
	private String url;
	private int urlNo;
	private int statusCode;
	private int hitNum;
	private String[] urlRefrences;
	private int layer;	
	
	private String charSet;
	private String type;
	private String title;
	private String author;
	private String abstractText;
	private String description;
	
	private int weight;
	private int fileSize;
	private Date timeToLive;
	private Timestamp lastUpdateTime;
	

}