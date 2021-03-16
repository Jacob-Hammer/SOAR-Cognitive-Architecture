// SendValues()
// if(move.equals()) in GetChild() area
// changed moveUpward() and moveLeftward()

package application;

import sml.Agent;
import sml.Identifier;
import sml.Kernel;
import sml.smlUpdateEventId;
import sml.Kernel.UpdateEventInterface;

import java.util.*;

public class SoarLink {

	private List<Identifier> memory;

	private Kernel kernel;
	private Agent agent;
	private String moveX = "";
	private String moveY = "";
	private String move = "";
	private String test = "";
	private boolean moveup;
	private boolean moveleft;
	private boolean moveright;
	private boolean movedown;
	private boolean movenowhere;

//	public boolean moveUpward() {
//		return moveup;
//	}
//	public boolean moveLeftward() {
//		return moveleft;
//	}
	public boolean moveUpward() {
		boolean y = false;
    	System.out.println("moveUpward() move is " + move);
		if (move.equals("down")) {
			y = true;
		}
		return y;
	}
	public boolean moveLeftward() {
		boolean x = false;
		System.out.println("moveLeftward() move is " + move);
		if (move.equals("left")) {
			x = true;
		}
		return x;
	}

	public SoarLink(boolean spawnDebugger) {
		kernel = Kernel.CreateKernelInNewThread();
        agent = kernel.CreateAgent("SoarDrone");

        boolean result = agent.LoadProductions("SoarDrone.Soar");

        if(!result) {
        	System.out.println("FAILED TO LOAD SOAR FILE. It is missing or has errors");
        }


        memory =  new LinkedList<>();

//        if(spawnDebugger) {
//        	agent.SpawnDebugger();
//        }

        kernel.RegisterForUpdateEvent(
	        smlUpdateEventId.smlEVENT_AFTER_ALL_OUTPUT_PHASES,
	        new UpdateEventInterface() {
	            public void updateEventHandler(int eventID, Object data,
	                    Kernel kernel, int runFlags) {
	                kernel.StopAllAgents();

//	                moveX = agent.GetOutputLink().GetChild(0).GetValueAsString();
//	                moveY = agent.GetOutputLink().GetChild(0).GetValueAsString();
//	            	System.out.println("SoarLink() moveX is " + moveX);
//	            	System.out.println("SoarLink() moveY is " + moveY);
	                move = agent.GetOutputLink().GetChild(0).GetValueAsString();
	                test = agent.GetOutputLink().FindByAttribute("direction", 0).GetValueAsString();
//	                test = agent.GetOutputLink().FindByAttribute("direction", 1).GetValueAsString();
	                System.out.println("*********");
	                System.out.println("FindAttributeBy() returns: " + test);
	                System.out.println("*********");
	                System.out.println("SoarLink() says move " + move);
	                if(move.equals("up")){
	                	moveup = true;
	                }

	                if(move.equals("left")){
	                	moveleft = true;
	                }

	                if(move.equals("right")){
	                	moveright = true;
	                }

	                if(move.equals("down")){
	                	movedown = true;
	                }

	                agent.ClearOutputLinkChanges();
	            	}
	        }, null);
	}

//	public void sendValues(double y, double speed) {
	public void sendValues(double y, double x, double speed) {
		Iterator<Identifier> wmeIter = memory.iterator();
		while (wmeIter.hasNext()) {
			Identifier wme = wmeIter.next();

			wme.DestroyWME();
			wmeIter.remove();
		}

		Identifier inputLink = agent.GetInputLink();

		Identifier positionBlock = agent.CreateIdWME(inputLink, "position");
		memory.add(positionBlock);

		agent.CreateFloatWME(positionBlock, "yValue", y);
		agent.CreateFloatWME(positionBlock, "xValue", x);
		agent.CreateFloatWME(positionBlock, "speed", speed);

		System.out.println("RUN");

		agent.RunSelf(1);

		/*Identifier result = agent.GetOutputLink();
		System.out.println(result.GetAttribute());*/

		return;
	}
}
