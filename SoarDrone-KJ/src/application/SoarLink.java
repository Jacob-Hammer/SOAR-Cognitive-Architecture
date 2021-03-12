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
	private String move = "";
	private boolean moveup;
	private boolean moveleft;
	private boolean moveright;
	private boolean movedown;
	private boolean movenowhere;
	
//	public boolean moveUpward() {
//		return Boolean.parseBoolean(move);
//	}
	public boolean moveUp() {
//		return Boolean.parseBoolean(moveup);
		return moveup;
	}
	

	public boolean moveLeft() {
//		return Boolean.parseBoolean(moveleft);
		return moveleft;
	}

	public boolean moveRight() {
//		return Boolean.parseBoolean(moveright);
		return moveright;
	}

	public boolean moveDown() {
//		return Boolean.parseBoolean(movedown);
		return movedown;
	}

	public boolean moveNowhere() {
//		return Boolean.parseBoolean(movenowhere);
		return movenowhere;
	}

	
	public SoarLink(boolean spawnDebugger) {
		kernel = Kernel.CreateKernelInNewThread();
        agent = kernel.CreateAgent("SoarDrone");
        
        boolean result = agent.LoadProductions("SoarDrone.Soar");
        
        if(!result) {
        	System.out.println("FAILED TO LOAD SOAR FILE. It is missing or has errors");
        }
        
        
        memory =  new LinkedList<>();
        
        if(spawnDebugger) {
        	agent.SpawnDebugger();
        }
        
        //move = "";
        
        kernel.RegisterForUpdateEvent(
	        smlUpdateEventId.smlEVENT_AFTER_ALL_OUTPUT_PHASES,
	        new UpdateEventInterface() {
	            public void updateEventHandler(int eventID, Object data,
	                    Kernel kernel, int runFlags) {
	                kernel.StopAllAgents();
	                	                
	                move = agent.GetOutputLink().GetChild(0).GetValueAsString();
	                System.out.println("Agent says move " + move);

	                // I'm not sure what child we need to give it. 
	                if(move=="up"){
	                	moveup = true;
	                }

	                if(move=="left"){
	                	moveleft = true;
	                }

	                if(move=="right"){
	                	moveright = true;
	                }

	                if(move=="down"){
	                	movedown = true;
	                }

	                if(move=="nothing"){
	                	movenowhere = true;
	                }
	                
	                agent.ClearOutputLinkChanges();
	            	}
	        }, null);
	}
	
	public void sendValues(double x, double y, double speed) {
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
