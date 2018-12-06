package model;

import java.io.IOException;

public interface CacheManager {
	public String Load(String fileContentToLoad) throws IOException;
	public void Save(String fileName, String fileContent) throws IOException;

}
