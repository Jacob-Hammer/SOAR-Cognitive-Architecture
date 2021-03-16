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
    public static double GRAVITY = 0.003;
    public static double WIND = 0.003;

    private boolean gravTrigger = false;
    private boolean revgravTrigger = false;
    private boolean windTrigger = false;
    private boolean revwindTrigger = false;

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

//            	if(link.moveUpward()) {
//            		System.out.println("Agent moves up");
//            		System.out.println("----");
//            		GRAVITY = -0.02;
//            	} else {
//            		GRAVITY = 0.01;
//            		System.out.println("Agent moves down");
//            		System.out.println("----");
//            	}
//            	if(link.moveLeftward()) {
//            		System.out.println("Agent moves left");
//            		System.out.println("----");
//            		WIND = -0.02;
//            	} else {
//            		WIND = 0.01;
//            		System.out.println("Agent moves right");
//            		System.out.println("----");
//            	}

            	if(link.moveUpward()) {
            		System.out.println("Agent moves up");
//            		speedX = 0;
//            		speedY = -3;
            		GRAVITY = -0.01;
            		if (gravTrigger) {GRAVITY -= .03; gravTrigger = false;}
            	} else {
            		System.out.println("Agent moves down");
//            		speedX = 3;
//            		speedY = 0;
            		GRAVITY = 0.005;
            		if (revgravTrigger) {GRAVITY += .03; revgravTrigger = false;}
            	}
            	if(link.moveLeftward()) {
            		System.out.println("Agent moves left");
//            		speedX = -3;
//                    speedY = 0;
//            		WIND = -0.1;
            	} else {
            		System.out.println("Agent moves right");
//            		speedX = 0;
//                    speedY = 3;
//            		WIND = 0.05;
            	}
            	if (x > 420) {
            		WIND = -0.003;
            		if (revwindTrigger) {WIND -= .03; revwindTrigger = false;}
            	}
            	if (x < 460) {
            		WIND = 0.003;
            		if (windTrigger) {WIND += .03; windTrigger = false;}
            	}

            	System.out.println("speedY is " + speedY);
            	System.out.println("speedX is " + speedX);
            	System.out.println("GRAVITY is " + GRAVITY);
            	System.out.println("WIND is " + WIND);
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
//        		interval++;

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

                link.sendValues(y, x, speedY);
                System.out.println("----------VALUES SENT----------");
//                link.sendValues(y, speedY);
//                link.sendValues(x, speedX);

               if(step) {
            	   stop();
            	   step = false;
               }

            }
        };

//        Button buttonStep = new Button("Step");
//        Button buttonRun = new Button("Run");
//        buttonStep.setTranslateX(0);
//        buttonStep.setTranslateY(0);
//        buttonRun.setTranslateX(45);
//        buttonRun.setTranslateY(0);
//
//        buttonStep.setOnAction(actionEvent ->  {
//        	step = false;
//            timer.start();
//        });
//
//        buttonRun.setOnAction(actionEvent ->  {
//        	y = 270;
//        });
//
//        stage.setScene(
//            new Scene(
//                new Pane(
//                    canvas,
//                    buttonStep,
//                    buttonRun
//                )
//            )
//        );

        // Create buttons
        Button buttonStep = new Button("Start");
        Button buttonUp = new Button("Place Q1");
        Button buttonLeft = new Button("Place Q2");
        Button buttonRight = new Button("Place Q3");
        Button buttonDown = new Button("Place Q4");
//        Button buttonMoveUp = new Button("Move Up");
//        Button buttonMoveLeft = new Button("Move Left");
//        Button buttonMoveRight = new Button("Move Right");
//        Button buttonMoveDown = new Button("Move Down");
        Button buttonMoveUp = new Button("Tobacco Consumption");
        Button buttonMoveLeft = new Button("Excess Alcohol");
        Button buttonMoveRight = new Button("Inadequate Sleep");
        Button buttonMoveDown = new Button("Poor Diet");
        Button buttonCenter = new Button("Healthy!");
        Button buttonReset = new Button("Reset");
        // Tobacco consumption, excess alcohol, inadequate sleep, poor diet

        // Set button colors
        buttonCenter.setStyle("-fx-background-color: #7CFC00; ");
        buttonMoveUp.setStyle("-fx-background-color: #FFA07A; ");
        buttonMoveLeft.setStyle("-fx-background-color: #FFA07A; ");
        buttonMoveRight.setStyle("-fx-background-color: #FFA07A; ");
        buttonMoveDown.setStyle("-fx-background-color: #FFA07A; ");
//        buttonUp.setStyle("-fx-background-color: #DCDCDC; ");
//        buttonLeft.setStyle("-fx-background-color: #DCDCDC; ");
//        buttonRight.setStyle("-fx-background-color: #DCDCDC; ");
//        buttonDown.setStyle("-fx-background-color: #DCDCDC; ");

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
        buttonMoveUp.setMaxSize(150,30);
        buttonMoveUp.setMinSize(150,30);
        buttonMoveLeft.setMaxSize(150,30);
        buttonMoveLeft.setMinSize(150,30);
        buttonMoveRight.setMaxSize(150,30);
        buttonMoveRight.setMinSize(150,30);
        buttonMoveDown.setMaxSize(150,30);
        buttonMoveDown.setMinSize(150,30);
        buttonCenter.setMaxSize(100,30);
        buttonCenter.setMinSize(100,30);
        buttonReset.setMaxSize(100,30);
        buttonReset.setMinSize(100,30);
        buttonStep.setTranslateX(0);
        buttonStep.setTranslateY(0);
        buttonUp.setTranslateX(200);
        buttonUp.setTranslateY(0);
        buttonLeft.setTranslateX(300);
        buttonLeft.setTranslateY(0);
        buttonRight.setTranslateX(400);
        buttonRight.setTranslateY(0);
        buttonDown.setTranslateX(500);
        buttonDown.setTranslateY(0);
//        buttonMoveUp.setTranslateX(500);
//        buttonMoveUp.setTranslateY(0);
//        buttonMoveLeft.setTranslateX(600);
//        buttonMoveLeft.setTranslateY(0);
//        buttonMoveRight.setTranslateX(700);
//        buttonMoveRight.setTranslateY(0);
//        buttonMoveDown.setTranslateX(800);
//        buttonMoveDown.setTranslateY(0);
        buttonMoveUp.setTranslateX(0);
        buttonMoveUp.setTranslateY(870);
        buttonMoveLeft.setTranslateX(150);
        buttonMoveLeft.setTranslateY(870);
        buttonMoveRight.setTranslateX(300);
        buttonMoveRight.setTranslateY(870);
        buttonMoveDown.setTranslateX(450);
        buttonMoveDown.setTranslateY(870);
        buttonCenter.setTranslateX(600);
        buttonCenter.setTranslateY(870);
        buttonReset.setTranslateX(100);
        buttonReset.setTranslateY(0);

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
        	gravTrigger = true;
        });

        buttonMoveLeft.setOnAction(actionEvent ->  {
        	windTrigger = true;
        });

        buttonMoveRight.setOnAction(actionEvent ->  {
        	revwindTrigger = true;
        });

        buttonMoveDown.setOnAction(actionEvent ->  {
        	revgravTrigger = true;
        });

        buttonCenter.setOnAction(actionEvent ->  {
          speedX = 0;
          speedY = 0;
          GRAVITY = 0;
          WIND = 0;
      });
        buttonReset.setOnAction(actionEvent ->  {
        	x = 330;
            y = 240;
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
                    buttonCenter,
                    buttonReset
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
