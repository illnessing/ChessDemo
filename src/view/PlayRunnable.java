package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Runnable;

package com.Play;

public class PlayRunnable implements Runnable{
	String audio;

	public PlayRunnable(String audio) {
		this.audio = audio;
	}

	@Override
	public void run() {
		Play play = new Play(this.audio);

	}
}
