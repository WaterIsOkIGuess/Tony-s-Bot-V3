package DisB.TDisB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.utils.PermissionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.MessageBuilder;

import javax.security.auth.login.LoginException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.lang.Math;
import java.net.URLEncoder;


public class App extends ListenerAdapter {

	public static void main (String[] args) throws LoginException, IllegalArgumentException, IOException, InterruptedException, RateLimitedException{
		try {
			JDA api = new JDABuilder(AccountType.BOT).setToken("TOKEN HERE").setAudioEnabled(true).setAutoReconnect(true).build();
			api.getPresence().setGame(Game.watching("&!help"));
			api.addEventListener(new App());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
		public void onMessageReceived(MessageReceivedEvent event) {
			

			
			
			String[] messageSent = event.getMessage().getContentRaw().split(" ");
			String unsplit = event.getMessage().getContentRaw();
			
			if (messageSent[0].equalsIgnoreCase("&!help")) {
			event.getChannel().sendMessage("&!bw - Makes image black and white.\n" +
										   "&!pinkify - Makes image pink.\n" +
										   "&!LefttoRight - Mirrors left side of image to the right.\n" +
										   "&!RighttoLeft - Mirrors right side of image to the left.\n" +
										   "&!ToptoBottom - Mirrors top side of image to the bottom.\n" +
										   "&!BottomtoTop - Mirrors bottom side of image to the top.\n" +
										   "&!T - Tweets to https://twitter.com/ShaggGuy").queue();
			}
			//event.getChannel().sendMessage(unsplit).queue();
			
			
			if (messageSent[0].equalsIgnoreCase("&!bw")){
				BlackWhite(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!pinkify")){
				pinkify(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!LefttoRight")){
				LefttoRight(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!ToptoBottom")){
				ToptoBottom(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!RighttoLeft")){
				RighttoLeft(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!BottomtoTop")){
				BottomtoTop(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!T")){
				try {
					sendTwitterMessage(event.getMessage().getContentRaw().substring(4, event.getMessage().getContentRaw().length()));
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
			if (messageSent[0].equalsIgnoreCase("&!w")){
				try {
					weather(event.getMessage().getContentRaw().substring(4, event.getMessage().getContentRaw().length()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
 }
		
		public static void weather(String event) throws UnsupportedEncodingException {
		     
			try {
				
			    // Host url
		          String host = "https://weather2020-weather-v1.p.rapidapi.com/zip/e8ecee8ff60c478f8a36280fea0524fe/"+event;
		          String charset = "UTF-8";
		          // Headers for a request
		          String x_rapidapi_host = "weather2020-weather-v1.p.rapidapi.com";
		          String x_rapidapi_key = "484e2a0258msh1a8008e8bae719cp1505efjsnf2a812bfd30a";//Type here your key
		          // Params
		          String s = event;
		      // Format query for preventing encoding problems
		          
					String query = String.format("s=%s",
					   URLEncoder.encode(s, charset));

					HttpResponse <JsonNode> response = Unirest.get(host + "?" + query)
							.header("x-rapidapi-host",  x_rapidapi_host)
							.header("x-rapidapi-key", x_rapidapi_key)
							.asJson();
					
					
				      Gson gson = new GsonBuilder().setPrettyPrinting().create();
				      JsonParser jp = new JsonParser();
				      JsonElement je = jp.parse(response.getBody().toString());
				      String prettyJsonString = gson.toJson(je);
				      System.out.println(prettyJsonString);
				
			} 
			catch (UnirestException e) {
				e.printStackTrace();
			}

			
			
		  }
		
		public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
			TextChannel textChannel = event.getGuild().getTextChannelsByName("botspam",true).get(0);
			textChannel.sendMessage(event.getMember().getEffectiveName() + " has left the server.").queue();
		}
		public void onGuildMemberJoin(GuildMemberJoinEvent event) {
			TextChannel textChannel = event.getGuild().getTextChannelsByName("botspam",true).get(0);
			textChannel.sendMessage(event.getMember().getAsMention() + " has joined the server.").queue();
		}
		
		public void sendTwitterMessage(String thing) throws TwitterException
	    {
	    	ConfigurationBuilder cb = new ConfigurationBuilder();
	    	cb.setDebugEnabled(true)
	    	  .setOAuthConsumerKey("gM76arM1V9WmzE7MI7d36oX3O")
	    	  .setOAuthConsumerSecret("ehM6QUeJjzlMkxamZqJ8rZmkBYglOcVWqEdRkUXNe8wMrb588c")
	    	  .setOAuthAccessToken("2439222218-a8RvIYdlDRE95akWGcGvAjPfcaVnauyHH6EOAYQ")
	    	  .setOAuthAccessTokenSecret("3A46GJrVLkOUSJs0dNgYX3rKNdix10weH8hz0KZba3KKI");
	    	TwitterFactory tf = new TwitterFactory(cb.build());
	    	Twitter twitter = tf.getInstance();
	    	try {
				twitter.updateStatus(thing);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			
	    }
		
		
		
	public void BlackWhite(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File(attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\"+attachment.getFileName());
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println(e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int p = img.getRGB(x,y);
				  
				  int a = (p>>24)&0xff;
				  int r = (p>>16)&0xff;
				  int g = (p>>8)&0xff;
				  int b = p&0xff;
				  
				  int tr = (int)(0.393*r + 0.769*g + 0.189*b);
				  int tg = (int)(0.349*r + 0.686*g + 0.168*b);
				  int tb = (int)(0.272*r + 0.534*g + 0.131*b);
				  
				  if(tr > 255){
					  r = 255;
					}else{
					  r = tr;
					}
				  if(tg > 255){
					  g = 255;
					}else{
					  g = tg;
					}
				  if(tb > 255){
					  b = 255;
					}else{
					  b = tb;
					}
				  p = (a<<24) | (r<<16) | (g<<8) | b;
				  img.setRGB(x, y, p);
			  }
			}

		try{
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
	
	public void pinkify(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File(attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\"+attachment.getFileName());
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println(e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int p = img.getRGB(x,y);
				  int a = (p>>24)&0xff;
				  int r = (p>>16)&0xff;
				  int g = (p>>8)&0xff;
				  int b = p&0xff;
				  
				  int tr = (int)(0.393*r + 9.769*g + 0.189*b);
				  int tg = (int)(0.349*r + 0.686*g + .168*b);
				  int tb = (int)(5.272*r + 0.534*g + 0.131*b);
				  
				  if(tr > 255){
					  r = 255;
					}else{
					  r = tr;
					}
				  if(tg > 255){
					  g = 255;
					}else{
					  g = tg;
					}
				  if(tb > 255){
					  b = 255;
					}else{
					  b = tb;
					}
				  p = (a<<24) | (r<<16) | (g<<8) | b;
				  img.setRGB(x, y, p);
			  }
			}

		try{
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
		
	public void LefttoRight(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File(attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\"+attachment.getFileName());
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println(e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int p = img.getRGB(x,y);
				  int a = (p>>24)&0xff;
				  int r = (p>>16)&0xff;
				  int g = (p>>8)&0xff;
				  int b = p&0xff;
				  p = (a<<24) | (r<<16) | (g<<8) | b;
				  img.setRGB(Math.abs(x-(width-1)), y, p);
			  }
			}

		try{
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
	public void ToptoBottom(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File(attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\"+attachment.getFileName());
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println(e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		for(int y = 0; y < height; y++){
			  for(int x = 0; x < width; x++){
				  int p = img.getRGB(x,y);
				  int a = (p>>24)&0xff;
				  int r = (p>>16)&0xff;
				  int g = (p>>8)&0xff;
				  int b = p&0xff;
				  p = (a<<24) | (r<<16) | (g<<8) | b;
				  img.setRGB(x, Math.abs(y-(height-1)), p);
			  }
			}

		try{
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
	
	public void RighttoLeft(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File(attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\"+attachment.getFileName());
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println(e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		for(int y = 0; y < height; y++){
			  for(int x = width-1; x > 0; x--){
				  int p = img.getRGB(x,y);
				  int a = (p>>24)&0xff;
				  int r = (p>>16)&0xff;
				  int g = (p>>8)&0xff;
				  int b = p&0xff;
				  p = (a<<24) | (r<<16) | (g<<8) | b;
				  img.setRGB(Math.abs(x-width), y, p);
			  }
			}

		try{
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
	

	
	public void BottomtoTop(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File(attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\"+attachment.getFileName());
			img = ImageIO.read(f);
		}
		catch (IOException e){
			System.out.println(e);
		}
		int width = img.getWidth();
		int height = img.getHeight();
		for(int y = height-1; y > 0; y--){
			  for(int x = 0; x < width; x++){
				  int p = img.getRGB(x,y);
				  int a = (p>>24)&0xff;
				  int r = (p>>16)&0xff;
				  int g = (p>>8)&0xff;
				  int b = p&0xff;
				  p = (a<<24) | (r<<16) | (g<<8) | b;
				  img.setRGB(x, Math.abs(y-(height)), p);
			  }
			}

		try{
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
	
	
	
	
	
	}
			
	




