package model;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileCacheManager implements CacheManager{
	private File filesDirectory;
	
	public FileCacheManager(String directoryName)
	{
		this.filesDirectory = new File(directoryName);
		this.createCacheDir();
	}
	
	private void createCacheDir()
	{
		// if the directory does not exist, create it
				if (!this.filesDirectory.exists()) {
				    System.out.println("creating directory: " + this.filesDirectory.getName());
				    boolean result = false;

				    try{
				        this.filesDirectory.mkdir();
				        result = true;
				    } 
				    catch(SecurityException se){
				        //handle it
				    }        
				    if(result) {    
				        System.out.println("DIR created");  
				    }
				}

	}
	public String Load(String fileContentToLoad) throws IOException
	{
		//Access to FileSystem to check whether the file exists
		int targetFileName = fileContentToLoad.hashCode();
		Path targetFilePath = Paths.get(this.filesDirectory.getName(), String.format("%d.txt", targetFileName));
		File targetFile = new File(targetFilePath.toString());
		if(!targetFile.exists())
		{
			return "";
		}
		else
		{
			BufferedReader br = new BufferedReader(new FileReader(targetFilePath.toString()));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line;

			    while ((line = br.readLine()) != null) {
			        sb.append(line);
			        sb.append("X");
			    }
			    return sb.toString();
			} finally {
			    br.close();
			}

		}
	}
	
	public void Save(String fileName, String fileContent) throws IOException
	{
		//Access to FileSystem and save the given PipeGameState
		int targetFileName = fileName.hashCode();
		Path targetPath = Paths.get(this.filesDirectory.getName(), String.format("%d.txt", targetFileName));
		File targetFile = new File(targetPath.toString());
		targetFile.createNewFile();
		PrintWriter writer = new PrintWriter(targetFile.toString(), "UTF-8");
		//writer.println(fileContent);
		writer.println(fileContent);
		writer.close();
	}
}
