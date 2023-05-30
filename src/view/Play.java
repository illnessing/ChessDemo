package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Play {
	String audio; // 音乐文件
	//播放音乐方法
	public Play(String audio) {
		this.audio = audio;
		try {
			Player player = new Player(new FileInputStream(this.audio));  // 创建播放器
			System.out.println("播放音乐："+this.audio);
			player.play();                                            // 开始播放
		} catch (JavaLayerException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}