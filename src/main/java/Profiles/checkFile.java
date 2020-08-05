package Profiles;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class checkFile {
	public checkFile(String currentUserBankAccount) throws IOException{
		FileReader reader = new FileReader(currentUserBankAccount + ".png");
		Properties properties = new Properties();
		properties.load(reader); 
		reader.close();
    }
}
