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
	private String move;
	
	public boolean moveUp() {
		return Boolean.parseBoolean(moveUp);
	}

	public boolean moveLeft() {
		return Boolean.parseBoolean(moveLeft);
	}

	public boolean moveRight() {
		return Boolean.parseBoolean(moveRight);
	}

	public boolean moveDown() {
		return Boolean.parseBoolean(moveDown);
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
        
        move = "";
        
        
        kernel.RegisterForUpdateEvent(
	        smlUpdateEventId.smlEVENT_AFTER_ALL_OUTPUT_PHASES,
	        new UpdateEventInterface() {
	            public void updateEventHandler(int eventID, Object data,
	                    Kernel kernel, int runFlags) {
	                kernel.StopAllAgents();
	                	                
	                moveUp = agent.GetOutputLink().GetChild(0).GetValueAsString();
	                System.out.println("Agent says move " + moveUp);

	                // I'm not sure what child we need to give it. 

	                moveLeft = agent.GetOutputLink().GetChild(1).GetValueAsString();
	                System.out.println("Agent says move " + moveLeft);

	                moveRight = agent.GetOutputLink().GetChild(2).GetValueAsString();
	                System.out.println("Agent says move " + moveRight);

	                moveDown = agent.GetOutputLink().GetChild(3).GetValueAsString();
	                System.out.println("Agent says move " + moveDown);
	                
		
	                agent.ClearOutputLinkChanges();
	
	            	}
	        }, null);
	}
	
	public void sendValues(double y, double speed) {
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
