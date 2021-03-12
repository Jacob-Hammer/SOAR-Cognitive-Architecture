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
            	
            	if(link.moveUp()) {
            		System.out.println("Agent moves up");
            		System.out.println("----");
            		speedY = 3;
                    speedX = 0;
                    GRAVITY = 0.02;
            	} 


            	if(link.moveLeft()) {
            		System.out.println("Agent moves left");
            		System.out.println("----");
            		speedX = -3;
            		speedY = 0;
            		WIND = 0.02;
            	} 

                if(link.moveRight()) {
                    System.out.println("Agent moves right");
                    System.out.println("----");
                    speedX = 3;
                    speedY = 0;
                    WIND = -0.02;
                    
                } 


                if(link.moveDown()) {
                    System.out.println("Agent moves down");
                    System.out.println("----");
                    speedY = -3;
                    speedX = 0;
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

            	speedY = speedY + GRAVITY;
            	speedX = speedX + WIND;
            	
            	y = y + speedY;
            	x = x + speedX;
            	
            	
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, W, H);
                gc.strokeLine(300, 300, 300, 540); //y top
                gc.strokeLine(540, 300, 540, 540); //y bottom
                gc.strokeLine(300, 300, 540, 300); //x top
                gc.strokeLine(300, 540, 540, 540); //x bottom
                gc.setFill(Color.ORANGE);
                gc.fillRect(
                    x,
                    y,
                    RECTW,
                    RECTH
                );
                
                link.sendValues(x, y, speedY);
                link.sendValues(x, y, speedX);
                
               if(step) {
            	   stop();
            	   step = false;
               }
               
            }
        };
        
        Button buttonStep = new Button("Step");
        Button buttonUp = new Button("Something Unhealthy 1");
        Button buttonLeft = new Button("Something Unhealthy 2");
        Button buttonRight = new Button("Something Unhealthy 3");
        Button buttonDown = new Button("Something Unhealthy 4");
        Button buttonCenter = new Button("Person adheres to all careplans");

    

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
        });

        buttonLeft.setOnAction(actionEvent ->  {
            x = 90;
        });

        buttonRight.setOnAction(actionEvent ->  {
            x = 810;
        });

        buttonDown.setOnAction(actionEvent ->  {
            y = 90;
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