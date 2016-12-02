import java.util.BitSet;

public class BloomFilter implements visitedFrontier {
	
	private static final int DEFAULT_SIZE=2<<24;
	private static final int[] seeds=new int[]{7,11,13,31,37,61};
	private BitSet bits=new BitSet(DEFAULT_SIZE);
	private Hash[] func=new Hash[seeds.length];
	
	private static int size=0;

	public BloomFilter(){
		for(int i=0;i<seeds.length;i++)
			func[i]=new Hash(DEFAULT_SIZE,seeds[i]);
	}
	
	@Override
	public void put(Url url) {
		// TODO Auto-generated method stub
		if(url!=null)
			put(url.getOriUrl());
	}

	@Override
	public void put(String value) {
		// TODO Auto-generated method stub
		size++;
		for(Hash h:func)
			bits.set(h.getHash(caculateUrl(value)), true);
	}

	@Override
	public boolean contains(Url url) {
		// TODO Auto-generated method stub
		return contains(url.getOriUrl());
	}

	@Override
	public boolean contains(String value) {
		// TODO Auto-generated method stub
		if(value==null)return false;
		
		boolean ret=true;
		for(Hash h:func)
			ret&=bits.get(h.getHash(caculateUrl(value)));
		return ret;
	}
	
	public static class Hash{
		private int cap;
		private int seed;
		public Hash(int cap,int seed){
			this.cap=cap;
			this.seed=seed;
		}
		public int getHash(String value)
		{
			int result=0;
			for(int i=0;i<value.length();i++){
				result=seed*result+value.charAt(i);
			}
			return (cap-1)&result;
		}
	}
	
	private String caculateUrl(String url)
	{
		return MD5.getMD5(url);
	}
	
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}
	
	public static void main(String[] args)
	{
		String value=new String("http://www.baidu.com");
		BloomFilter filter=new BloomFilter();
		System.out.println(filter.contains(value));
		filter.put(value);
		System.out.println(filter.contains(value));	
		
	}
}