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
    public static final double W = 300;
    public static final double H = 300;

    public static final double RECTW = 20;
    public static final double RECTH = 20;
    public static double GRAVITY = 0.03;
    public static double WIND = 0.03;
    
    private double x = 110;
    private double y = 80;
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
            	if(y > 280) {
            		speedY = 0;
            		y = 280;
            	}
            	if(y < 0) {
            		speedY = 0;
            		y = 0;
            	}
            	if(x > 280) {
            		speedX = 0;
            		x = 280;
            	}
            	if(x < 0) {
            		speedX = 0;
            		x = 0;
            	}
            	
            	if(link.moveUpward()) {
            		System.out.println("Agent moves up");
            		System.out.println("----");
            		GRAVITY = -0.02;
            	} else {
            		GRAVITY = 0.01;
            		System.out.println("Agent moves down");
            		System.out.println("----");
            	}
            	if(link.moveLeftward()) {
            		System.out.println("Agent moves left");
            		System.out.println("----");
            		WIND = -0.02;
            	} else {
            		WIND = 0.01;
            		System.out.println("Agent moves right");
            		System.out.println("----");
            	}
            	
            	
            	speedY = speedY + GRAVITY;
            	speedX = speedX + WIND;
            	
            	y = y + speedY;
            	x = x + speedX;
            	
//            	if(link.moveUp() && interval == 5) {
//            		System.out.println("Agent says move up");
//        			speed = -1;
//        			interval = 0;
//        		} else { 
//        			interval++;
//        		}
//            	
//            	speed = speed + GRAVITY;
//            	
//        		y = y + speed;
        	
        		
            	
            	
            	
        		interval++; 
            	
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, W, H);
                gc.strokeLine(100, 100, 100, 180); //y top
                gc.strokeLine(180, 100, 180, 180); //y bottom
                gc.strokeLine(100, 100, 180, 100); //x top
                gc.strokeLine(100, 180, 180, 180); //x bottom
                gc.setFill(Color.ORANGE);
                gc.fillRect(
                    x,
                    y,
                    RECTW,
                    RECTH
                );
                
                link.sendValues(y, speedY);
                link.sendValues(x, speedX);
                
               if(step) {
            	   stop();
            	   step = false;
               }
               
            }
        };
        
        Button buttonStep = new Button("Step");
        Button buttonRun = new Button("Run");
        buttonStep.setTranslateX(0);
        buttonStep.setTranslateY(0);
        buttonRun.setTranslateX(45);
        buttonRun.setTranslateY(0);
        
        buttonStep.setOnAction(actionEvent ->  {
        	step = false;
            timer.start();
        });
        
        buttonRun.setOnAction(actionEvent ->  {
        	y = 270;
        });
        
        stage.setScene(
            new Scene(
                new Pane(
                    canvas,
                    buttonStep,
                    buttonRun
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