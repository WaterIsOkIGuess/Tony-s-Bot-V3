package Twitter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.*; 

import javax.swing.JFileChooser;

import com.google.common.io.Files;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;
import twitter4j.conf.ConfigurationBuilder;

public class sendTwitterMessage {
	public sendTwitterMessage(MessageReceivedEvent event) throws TwitterException, FileNotFoundException
    {
    	ConfigurationBuilder cb = new ConfigurationBuilder();
    	cb.setDebugEnabled(true)
    	  .setOAuthConsumerKey("")
    	  .setOAuthConsumerSecret("")
    	  .setOAuthAccessToken("")
    	  .setOAuthAccessTokenSecret("");
    	TwitterFactory tf = new TwitterFactory(cb.build());
    	Twitter twitter = tf.getInstance();
    	try {
    		
    		
    		String stringMessage = event.getMessage().getContentRaw().substring(3, event.getMessage().getContentRaw().length());
    		Message.Attachment attachment;
    		

    		if (event.getMessage().getAttachments().isEmpty()) {
    			StatusUpdate status = new StatusUpdate(stringMessage);
    			twitter.updateStatus(status);
    		}
    		
    		
    		
    		else {
    			
    		attachment = event.getMessage().getAttachments().get(0);
    		File file;
    		String ext = Files.getFileExtension("twitterpics\\" + attachment.getFileName());
    		file = new File("twitterpics\\in." + ext);
    		UploadedMedia media = null;
    		attachment.download(file);

    		
    		if (ext.equalsIgnoreCase("mp4")) {
    			
    			StatusUpdate status = new StatusUpdate(stringMessage);
    			media = twitter.uploadMediaChunked(file.getName(), new BufferedInputStream(new FileInputStream(file)));
    			status.setMediaIds(media.getMediaId());
	    	    twitter.updateStatus(status);
    		}
    		else if (ext.equalsIgnoreCase("mov")) {
    			
    			StatusUpdate status = new StatusUpdate(stringMessage);
    			media = twitter.uploadMediaChunked(file.getName(), new BufferedInputStream(new FileInputStream(file)));
    			status.setMediaIds(media.getMediaId());
	    	    twitter.updateStatus(status);
    		}
    		else{
	    		StatusUpdate status = new StatusUpdate(stringMessage);
	    	    status.setMedia(file);
	    	    twitter.updateStatus(status);
    		}
    		
    		//File myObj = new File("twitterpics\\" + attachment.getFileName()); 
    		//file.deleteOnExit();
    	    if (file.delete()) { 
    	      //
    	    } else {
    	      //
    	    } 

    	    
    	    
    		}
			
    		
    		
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
    }
}
