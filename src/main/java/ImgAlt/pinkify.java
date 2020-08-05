package ImgAlt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class pinkify {
	public pinkify(MessageReceivedEvent event) {
		Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		File file = new File("Images\\" + attachment.getFileName());
	    attachment.download(file);
	    
	    
		BufferedImage img = null;
		File f = null;
		
		try {
			f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Images\\"+attachment.getFileName());
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
			  f = new File("C:\\Users\\Tony\\Desktop\\NewDisBot\\TDisB\\Images\\Output.jpg");
			  ImageIO.write(img, "jpg", f);
			  event.getChannel().sendFile(new File("Images\\Output.jpg")).queue();
			}catch(IOException e){
			  System.out.println(e);
			}
	}
}
