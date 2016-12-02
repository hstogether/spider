import java.io.File;
import java.io.FileNotFoundException;

import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public abstract class BDBFrontier{
	public BDBFrontier(String homeDirectory) throws DatabaseException,FileNotFoundException{
		//Open Environment
		System.out.println("Opening environment in: "+homeDirectory);
		EnvironmentConfig environmentConfig=new EnvironmentConfig();
		environmentConfig.setTransactional(true);
		environmentConfig.setAllowCreate(true);
		environment=new Environment(new File(homeDirectory),environmentConfig);
		
		//Open Catalog
		DatabaseConfig catalogDBConfig=new DatabaseConfig();
		catalogDBConfig.setTransactional(true);
		catalogDBConfig.setAllowCreate(true);
		catalogDatabase=environment.openDatabase(null, CLASS_CATALOG, catalogDBConfig);
		catalog=new StoredClassCatalog(catalogDatabase);
		
		//open Database
		DatabaseConfig dbConfig=new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);
		database=environment.openDatabase(null, "URL", dbConfig);
	}
	
	public void close() throws DatabaseException{
		database.close();
		catalog.close();//?? close the catalogDatabase the same time?
		environment.close();
	}
	
	protected abstract Object put(Object key,Object value);
	protected abstract Object get(Object key);
	protected abstract Object delete(Object key);
	
	private Environment environment;
	protected StoredClassCatalog catalog;
	protected Database database;
	
	private static final String CLASS_CATALOG="java_class_catalog";//name
	protected Database catalogDatabase;
	
}