package UtilsPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import EntitiesPackage.Crabby;
import MainPackage.GameElements;
import static UtilsPackage.Utils.EnemyActions.*;

public class SaveLoad {
	
	public static final String PLAYER_SPRITE = "/player_animations.png";
	public static final String MAP_SPRITE = "/Tiles.png";
	public static final String MENU_BUTTONS_URM = "/MenuButtons.png";
	public static final String CRABBY_SPRITE = "/crabby_sprite.png";
	public static final String HEALTHBAR = "/healthBar.png";
	public static final String MAP_BACKGROUND = "/forest_background.png";
	public static final String MENU_BOX = "/MenuBox.png";
	public static final String MENU_BACKGROUND = "/MenuBackground.png";
	public static final String PAUSE_BOX = "/PauseBox.png";
	public static final String PAUSE_SOUND_BUTTON = "/PauseButtons_sound.png";
	public static final String PAUSE_SLIDER_BUTTON = "/PauseButtons_slider.png";
	public static final String PAUSE_OTHER_BUTTONS = "/PauseButtons_other.png";
	public static final String COMPLETED_LEVEL_IMAGE = "/LevelPass.png";
	public static final String POTIONS_SPRITE = "/potions_sprite.png";
	
	public static BufferedImage getSpriteSet(String img_name) {
		
		BufferedImage img = null;
		InputStream is = SaveLoad.class.getResourceAsStream(img_name);
		try 
		{
			img = ImageIO.read(is);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static BufferedImage[] getAllLevels() {
		URL url = SaveLoad.class.getResource("/lvls");
		File file = null;
		try 
		{
			file = new File(url.toURI());
		} 
		catch (URISyntaxException e) 
		{
			e.printStackTrace();
		}
		
		File[] files = file.listFiles();
		File[] files_sorted = new File[files.length];
		
		for(int i = 0; i < files_sorted.length; i++)
		{
			for(int j = 0; j < files.length; j++)
			{
				if(files[j].getName().equals((i + 1) + ".png"))
				{
					files_sorted[i] = files[j];
				}
			}
		}
		
		BufferedImage[] imgs = new BufferedImage[files_sorted.length];
		
		for(int i = 0; i < imgs.length; i++)
		{
			try 
			{
				imgs[i] = ImageIO.read(files_sorted[i]);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		return imgs;
	}
}
