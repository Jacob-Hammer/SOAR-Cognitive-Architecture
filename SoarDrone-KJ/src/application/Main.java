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

    public static final double RECTW = 40;
    public static final double RECTH = 40;
    public static double GRAVITY = 0.03;
    public static double WIND = 0.03;
    
    private double x = 330;
    private double y = 500;
    private double speedX = 1;
    private double speedY = 1;
    
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
            	if(y > 840) {
            		speedY = 0;
            		y = 840;
            	}
            	if(y < 0) {
            		speedY = 0;
            		y = 0;
            	}
            	if(x > 840) {
            		speedX = 0;
            		x = 840;
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
                gc.strokeLine(300, 300, 300, 540); // center box y top
                gc.strokeLine(540, 300, 540, 540); // center box y bottom
                gc.strokeLine(300, 300, 540, 300); // center box x top
                gc.strokeLine(300, 540, 540, 540); // center box x bottom
                gc.setFill(Color.ORANGE);
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
        Button buttonUp = new Button("Unhealthy 1");
        Button buttonLeft = new Button("Unhealthy 2");
        Button buttonRight = new Button("Unhealthy 3");
        Button buttonDown = new Button("Unhealthy 4");
        Button buttonCenter = new Button("Healthy!");

        // Set button locations
        buttonStep.setTranslateX(0);
        buttonStep.setTranslateY(0);
        buttonUp.setTranslateX(135);
        buttonUp.setTranslateY(0);
        buttonLeft.setTranslateX(270);
        buttonLeft.setTranslateY(0);
        buttonRight.setTranslateX(305);
        buttonRight.setTranslateY(0);
        buttonDown.setTranslateX(540);
        buttonDown.setTranslateY(0);
        buttonCenter.setTranslateX(675);
        buttonCenter.setTranslateY(0);

        buttonStep.setOnAction(actionEvent ->  {
        	step = false;
            timer.start();
        });

        buttonUp.setOnAction(actionEvent ->  {
            y = 810;
//        	speedY = -30;
//        	speedY += 3;
        });

        buttonLeft.setOnAction(actionEvent ->  {
            x = 90;
//            speedX = -30;
//            speedX += -3;
        });

        buttonRight.setOnAction(actionEvent ->  {
            x = 810;
//            speedX = 30;
//            speedX += 3;
        });

        buttonDown.setOnAction(actionEvent ->  {
            y = 90;
//            speedY = 30;
//            speedY += -3;
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