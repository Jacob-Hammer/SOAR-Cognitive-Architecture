package application;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Main extends Application {
    public static final double W = 900;
    public static final double H = 900;

    public static final double RECTW = 30;
    public static final double RECTH = 30;
    public static double GRAVITY = 0.03;
    public static double WIND = 0.03;
    
    private double x = 330;
    private double y = 240;
    private double speedX;
    private double speedY;
    
    private boolean step = false;
    
    private static SoarLink link;
    
    private int interval = 0;
    

    @Override 
    public void start(Stage stage) {
        final Canvas canvas = new Canvas(W, H);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	// Set boundaries of map
            	if(y > 870) {
            		speedY = 0;
            		y = 870;
            	}
            	if(y < 0) {
            		speedY = 0;
            		y = 0;
            	}
            	if(x > 870) {
            		speedX = 0;
            		x = 870;
            	}
            	if(x < 0) {
            		speedX = 0;
            		x = 0;
            	}
            	
            	// Adjust speed and influence based on Soar link
            	if(link.moveUp()) {
            		System.out.println("Agent moves up");
            		System.out.println("----");
                    speedX = 0;
            		speedY = -3;
                    GRAVITY = -0.02;
            	} 
            	if(link.moveLeft()) {
            		System.out.println("Agent moves left");
            		System.out.println("----");
            		speedX = 3;
            		speedY = 0;
            		WIND = 0.02;
            	} 
                if(link.moveRight()) {
                    System.out.println("Agent moves right");
                    System.out.println("----");
                    speedX = -3;
                    speedY = 0;
                    WIND = -0.02;
                    
                } 
                if(link.moveDown()) {
                    System.out.println("Agent moves down");
                    System.out.println("----");
                    speedX = 0;
                    speedY = 3;
                    GRAVITY = 0.02;
                } 
                if(link.moveNowhere()) {
                    System.out.println("Agent moves nowhere");
                    System.out.println("----");
                    speedY = 0;
                    speedX = 0;
                    GRAVITY = 0.02;
                    WIND = -0.02;
                } 

                // Adjust speed
//            	speedY = speedY + GRAVITY;
//            	speedX = speedX + WIND;
            	
            	// Move box based on speed
            	y = y + speedY;
            	x = x + speedX;
            	
            	// Set graphics
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, W, H);
                gc.strokeLine(330, 330, 330, 570); // center box y top
                gc.strokeLine(570, 330, 570, 570); // center box y bottom
                gc.strokeLine(330, 330, 570, 330); // center box x top
                gc.strokeLine(330, 570, 570, 570); // center box x bottom
                gc.setFill(Color.ORANGE);
                if (x < 560 && y < 560 && x > 320 && y > 320) {
                	gc.setFill(Color.GREEN);
                }
                gc.fillRect(
                    x,
                    y,
                    RECTW,
                    RECTH
                );
                
                // Send values from Soar link
//                link.sendValues(x, y, speedX, speedY);
                link.sendValues(x, y);
                
               if(step) {
            	   stop();
            	   step = false;
               }
               
            }
        };
        
        // Create buttons
        Button buttonStep = new Button("Step");
        Button buttonUp = new Button("Place Q1");
        Button buttonLeft = new Button("Place Q2");
        Button buttonRight = new Button("Place Q3");
        Button buttonDown = new Button("Place Q4");
        Button buttonMoveUp = new Button("Move Up");
        Button buttonMoveLeft = new Button("Move Left");
        Button buttonMoveRight = new Button("Move Right");
        Button buttonMoveDown = new Button("Move Down");
        Button buttonCenter = new Button("Healthy!");

        // Set button sizes and locations
        buttonStep.setMaxSize(100,30);
        buttonStep.setMinSize(100,30);
        buttonUp.setMaxSize(100,30);
        buttonUp.setMinSize(100,30);
        buttonLeft.setMaxSize(100,30);
        buttonLeft.setMinSize(100,30);
        buttonRight.setMaxSize(100,30);
        buttonRight.setMinSize(100,30);
        buttonDown.setMaxSize(100,30);
        buttonDown.setMinSize(100,30);
        buttonMoveUp.setMaxSize(100,30);
        buttonMoveUp.setMinSize(100,30);
        buttonMoveLeft.setMaxSize(100,30);
        buttonMoveLeft.setMinSize(100,30);
        buttonMoveRight.setMaxSize(100,30);
        buttonMoveRight.setMinSize(100,30);
        buttonMoveDown.setMaxSize(100,30);
        buttonMoveDown.setMinSize(100,30);
        buttonCenter.setMaxSize(100,30);
        buttonCenter.setMinSize(100,30);
        buttonStep.setTranslateX(0);
        buttonStep.setTranslateY(0);
        buttonUp.setTranslateX(100);
        buttonUp.setTranslateY(0);
        buttonLeft.setTranslateX(200);
        buttonLeft.setTranslateY(0);
        buttonRight.setTranslateX(300);
        buttonRight.setTranslateY(0);
        buttonDown.setTranslateX(400);
        buttonDown.setTranslateY(0);
        buttonMoveUp.setTranslateX(500);
        buttonMoveUp.setTranslateY(0);
        buttonMoveLeft.setTranslateX(600);
        buttonMoveLeft.setTranslateY(0);
        buttonMoveRight.setTranslateX(700);
        buttonMoveRight.setTranslateY(0);
        buttonMoveDown.setTranslateX(800);
        buttonMoveDown.setTranslateY(0);
        buttonCenter.setTranslateX(0);
        buttonCenter.setTranslateY(30);

        buttonStep.setOnAction(actionEvent ->  {
        	step = false;
            timer.start();
        });

        buttonUp.setOnAction(actionEvent ->  {
        	x = 600;
            y = 300;
        });

        buttonLeft.setOnAction(actionEvent ->  {
        	x = 300;
            y = 300;
        });

        buttonRight.setOnAction(actionEvent ->  {
        	x = 300;
            y = 600;
        });

        buttonDown.setOnAction(actionEvent ->  {
        	x = 600;
            y = 600;
        });
        
        buttonMoveUp.setOnAction(actionEvent ->  {
        	GRAVITY = 1;
        });

        buttonMoveLeft.setOnAction(actionEvent ->  {
        	WIND = 1;
        });

        buttonMoveRight.setOnAction(actionEvent ->  {
        	WIND = 1;
        });

        buttonMoveDown.setOnAction(actionEvent ->  {
        	GRAVITY = 1;
        });
        
        buttonCenter.setOnAction(actionEvent ->  {
          speedX = 0;
          speedY = 0;
          GRAVITY = 0;
          WIND = 0;
      });
        
        
        stage.setScene(
            new Scene(
                new Pane(
                        canvas,
                        buttonStep,
                        buttonUp,
                        buttonLeft,
                        buttonRight,
                        buttonDown,
                        buttonMoveUp,
                        buttonMoveLeft,
                        buttonMoveRight,
                        buttonMoveDown,
                        buttonCenter
                )
            )
        );
        
        
        stage.show();
    }
    
    public static void main(String[] args) {
    	link = new SoarLink(true);
    	launch(args); 
    }
} 