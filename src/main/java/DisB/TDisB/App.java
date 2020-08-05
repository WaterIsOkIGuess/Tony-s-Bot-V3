package DisB.TDisB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.events.ReadyEvent;
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
import java.util.ArrayList;
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

import Database.Connect;
import Database.createTable;
import ImgAlt.BlackWhite;
import ImgAlt.BottomtoTop;
import ImgAlt.LefttoRight;
import ImgAlt.RighttoLeft;
import ImgAlt.ToptoBottom;
import ImgAlt.pinkify;
import Profiles.checkFile;
import Profiles.makeProfile;
import Twitter.sendTwitterMessage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;

import java.lang.Math;
import java.net.URLEncoder;


public class App extends ListenerAdapter {

	public static void main (String[] args) throws LoginException, IllegalArgumentException, IOException, InterruptedException, RateLimitedException{
		try {
			JDA api = new JDABuilder(AccountType.BOT).setToken("NjUwNzk2MzM5NzY4MDY2MDUw.XwUI-Q.Y46PZQz_93aImBLqRZEjM_AGPzY").setAudioEnabled(true).setAutoReconnect(true).build();
			api.getPresence().setGame(Game.watching("&!help"));
			api.addEventListener(new App());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
		public void onMessageReceived(MessageReceivedEvent event) {
			

			
			
			String[] messageSent = event.getMessage().getContentRaw().split(" ");
			String unsplit = event.getMessage().getContentRaw();
			
			String currentUser = event.getAuthor().getId();
			String currentUserString = event.getAuthor().getName();
			
			System.out.println(currentUserString + ": " + unsplit);
			
			//createTable test = new createTable(currentUser);
			Connect con = new Connect(currentUserString, currentUser);

			try {
				checkFile checkFile = new checkFile("Profiles\\" + currentUser);
				//System.out.println("Profile Found");
			} catch (IOException e1) {
				try {
					//System.out.println("Profile Doesn't Exist");
					makeProfile makeProfile = new makeProfile("Profiles\\" + currentUser, currentUserString, currentUser);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
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
				BlackWhite BlackWhite = new BlackWhite(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!pinkify")){
				pinkify pinkify = new pinkify(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!LefttoRight")){	
				LefttoRight LefttoRight = new LefttoRight(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!ToptoBottom")){
				ToptoBottom ToptoBottom = new ToptoBottom(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!RighttoLeft")){
				RighttoLeft RighttoLeft = new RighttoLeft(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!BottomtoTop")){
				BottomtoTop BottomtoTop = new BottomtoTop(event);
			}
			if (messageSent[0].equalsIgnoreCase("&!T")){
				try {
					sendTwitterMessage sendTwitterMessage = new sendTwitterMessage(event);
				} catch (TwitterException | FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (messageSent[0].equalsIgnoreCase("&!CheckServers")){
				List<String> list = new ArrayList<>();
			    for (Guild guild : event.getJDA().getGuilds()) {
			    	list.add(guild.getName());
			    }
			    int listSize = list.size();

			    for (int i = 0; i<listSize; i++){
			    	event.getChannel().sendMessageFormat(list.get(i)).queue();
			    }
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
		
		
		
		
		
		
		
		
		
		
		
	
	
	

		
	
	
	
	
	

	
	
	/*/////////////////DISCONTINUED//////////////////////
	 * 
	 if (messageSent[0].equalsIgnoreCase("&!w")){
				try {
					weather(event.getMessage().getContentRaw().substring(4, event.getMessage().getContentRaw().length()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	public static void weather(String event) throws UnsupportedEncodingException {
	     
		try {
			
		    // Host url
	          String host = "";
	          String charset = "UTF-8";
	          // Headers for a request
	          String x_rapidapi_host = "";
	          String x_rapidapi_key = "";//Type here your key
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
	  */
	
	
	
	
	}
			
	




