package Game.java;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Ball.java.Ball;
import Paddle.java.Paddle;

public class Game extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	private Ball ball;
	private Paddle paddle;
	private int score;
	private boolean gameOver;
	private Random rand = new Random();
	public Game() {
	ball = new Ball(250, 100, 10, rand.nextInt(5) + 1, rand.nextInt(5) + 1);
	paddle = new Paddle(225, 450, 50, 10, 5);
	score = 0;
	gameOver = false;
	JFrame frame = new JFrame("Bouncing Ball");
	frame.addKeyListener(this);
	frame.add(this);
	frame.setSize(500, 500);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	Thread gameLoop = new Thread(new Runnable() {
	public void run() {
	while (!gameOver) {
	update();
	repaint();
	try {
	Thread.sleep(10);
	} catch (InterruptedException e) {
	e.printStackTrace();
	}
	}
	}
	});
	gameLoop.start();
	}
	public void update() {
	ball.move();
	// Check for collision with walls
	if (ball.getX() - ball.getRadius() < 0 || ball.getX() + ball.getRadius() > getWidth()) {
	ball.setDx(-ball.getDx());
	}
	if (ball.getY() - ball.getRadius() < 0) {
	ball.setDy(-ball.getDy());
	}
	if (ball.getY() + ball.getRadius() > getHeight()) {
	gameOver = true;
	}
	// Check for collision with paddle
	if (ball.getY() + ball.getRadius() >= paddle.getY() && ball.getY() + ball.getRadius() <= paddle.getY() + paddle.getHeight() && ball.getX() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth()) {
			ball.setDy(-ball.getDy());
			score++;
	}
	// Move paddle with arrow keys
	if (leftPressed()) {
	paddle.moveLeft();
	}
	if (rightPressed()) {
	paddle.moveRight();
	}
	}
	public void paintComponent(Graphics g) {
	super.paintComponent(g);
	ball.draw(g);
	paddle.draw(g);
	g.setColor(Color.BLACK);
	g.setFont(new Font("Arial", Font.BOLD, 24));
	g.drawString("Score: " + score, 10, 30);
	if (gameOver) {
	g.setColor(Color.RED);
	g.setFont(new Font("Arial", Font.BOLD, 36));
	g.drawString("Game Over", getWidth() / 2 - 100, getHeight() / 2);
	}
	}
	public boolean leftPressed() {
	return keyState[KeyEvent.VK_LEFT];
	}
	public boolean rightPressed() {
	return keyState[KeyEvent.VK_RIGHT];
	}
	private boolean[] keyState = new boolean[256];
	public void keyPressed(KeyEvent e) {
	keyState[e.getKeyCode()] = true;
	}
	public void keyReleased(KeyEvent e) {

		keyState[e.getKeyCode()] = false;
	}
	public void keyTyped(KeyEvent e) {
	}
	public static void main(String[] args) {
	new Game();
	}
	}
