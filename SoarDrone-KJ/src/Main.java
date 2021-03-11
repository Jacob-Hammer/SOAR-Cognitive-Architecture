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
    public static double AIRRESISTANCE = 0.009;
    public static double LEFTWIND = 0.008;
    public static double RIGHTWIND = 0.008;
    
    private double x = 0;
    private double y = 0;
    private double speed;
    
    private boolean step = false;
    
    private static SoarLink link;
    
    private int interval = 0;

    // maybe lets add some latitude to x. 
    

    @Override 
    public void start(Stage stage) {
        final Canvas canvas = new Canvas(W, H);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {


                // if we are super high and within the x-range. let gravity pull us down. 
            	if(y > 280 && x < 190 && x > 110) {
            		speed = 0;
            		y = 280;
            	}



            	
            	/*if(link.moveUp()) {
            		System.out.println("Agent says move up");
            		GRAVITY = -0.02;
            	} else {
            		GRAVITY = 0.03;
            	}
            	
            	speed = speed + GRAVITY;
            	
            	y = y + speed;*/
            	
            	if(link.moveUp() && interval == 5) {
            		System.out.println("Agent says move up");
        			speed = -1;
        			interval = 0;
        		} else { 
        			interval++;
        		}


                if(link.moveLeft() && interval == 5) {
                    System.out.println("Agent says move left");
                    speed = -1;
                    interval = 0;
                } else { 
                    interval++;
                }


                if(link.moveRight() && interval == 5) {
                    System.out.println("Agent says move right");
                    speed = -1;
                    interval = 0;
                } else { 
                    interval++;
                }


                if(link.moveDown() && interval == 5) {
                    System.out.println("Agent says move down");
                    speed = -1;
                    interval = 0;
                } else { 
                    interval++;
                }

                speed = speed + GRAVITY;
                
                y = y + speed;

                x = x + speed;
        	
        		
            	
            	
            	
        		interval++; 
            	
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(Color.WHITE);
                gc.fillRect(0, 0, W, H);
                // need to modify this to be a box
                // top line 180, bottom line 100, on Y-axis, want it to be 
                gc.strokeLine(110, 100, 190, 100);
                gc.strokeLine(110, 180, 190, 180);
                gc.setFill(Color.ORANGE);
                gc.fillRect(
                    x,
                    y,
                    RECTW,
                    RECTH
                );
                
                link.sendValues(y, speed);
                
               if(step) {
            	   stop();
            	   step = false;
               }
               
            }
        };
        
        Button button = new Button("Step");
        Button buttonUp = new Button("Something Unhealthy 1");
        Button buttonLeft = new Button("Something Unhealthy 2");
        Button buttonRight = new Button("Something Unhealthy 3");
        Button buttonDown = new Button("Something Unhealthy 4");
        
        button.setOnAction(actionEvent ->  {
        	step = false;
            timer.start();
        });
        
        buttonUp.setOnAction(actionEvent ->  {
        	y = 270;
        });

        buttonLeft.setOnAction(actionEvent ->  {
            x = 30;
        });

        buttonRight.setOnAction(actionEvent ->  {
            x = 270;
        });

        buttonDown.setOnAction(actionEvent ->  {
            y = 30;
        });
        
        stage.setScene(
            new Scene(
                new Pane(
                    canvas,
                    button,
                    buttonUp,
                    buttonLeft,
                    buttonRight,
                    buttonDown
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
