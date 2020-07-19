package Pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Ball {
	
	public double x, y;
	public int width, height;
	
	public double dx, dy;
	public double speed = 1.6;
	
	public static int enemyPoints = 0;
	public static int playerPoints = 0;
	
	String filepath = "hit.wav";
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		
		int angle = new Random().nextInt(120-45)+46; 
		
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}

	public void tick() {
		
		if(x+(dx * speed) + width >= Game.WIDTH) {
			dx *= -1;
		} else if(x + (dx * speed) < 0) {
			dx *= -1; 
		} 
		
		if(y >= Game.HEIGHT) {
			System.out.println("Ponto do Inimigo");
			enemyPoints++;
			new Game();
			return;
		} else if(y < 0) {
			System.out.println("Ponto do Jogador");
			playerPoints++;
			new Game();
			return;
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)),(int)(y+(dy*speed)), width, height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);
		
		if (bounds.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(120-45)+46; 
			
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			
			if (dy > 0) {
				dy *= -1;
			}
			
		} else if(bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120-45)+46; 
			
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			
			if (dy < 0) {
				dy *= -1;
			}
			
		}
		
		if(bounds.intersects(boundsPlayer)||bounds.intersects(boundsEnemy)) {
			playMusic(filepath);
		}
		
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect((int)x, (int)y, width, height);
		
	}
	
	public static void playMusic(String musicLocation) {
		try {
			File musicPath = new File(musicLocation);
			
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			} else {
				System.out.println("Can't find location");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
