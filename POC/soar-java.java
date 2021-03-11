import java.io.IOException;

import sml.Agent;
import sml.Identifier;
import sml.Kernel;
import sml.smlUpdateEventId;
import sml.Kernel.UpdateEventInterface;

import java.util.Scanner;
/**
 * Very simple environment that adds a WME to the agent's input link with
 * attribute "hello" and value "world" and then stops when any command from the
 * agent is received.

 * Very simple environment that adds a WME to the agent's input link with
 * attribute "value-change" that is the integer they want and then stops when any command from the
 * agent is received.

 *
 * <p>
 * There is little to no error handling performed in this example, do not write
 * programs without error handling (more complete examples are below).
 *
 * <p>
 * Also see the Soar code in src/main/resources/state_maintenance.soar
 *
 * <p>
 * http://code.google.com/p/soar/wiki/HelloWorld
 *
 * @author voigtjr
 */
public class state_maintenance
{

    /*
     * The Kernel and Agent objects (along with input-link identifiers and some
     * output-link stuff) are usually cached on the object.
     */

    private Kernel kernel;

    private Agent agent;

    private add = 1;

    public state_maintenance()
    {
        /*
         * The first thing to do is to create the Soar kernel. The Soar kernel
         * is essentially a container for Soar agents and a way to interface
         * with the system in general as opposed to only a specific agent. Most
         * systems only use one agent so this might seem confusing or
         * unnecessary at first.
         */

        System.out.println("Creating Kernel...");
        kernel = Kernel.CreateKernelInNewThread();

        /*
         * The name of the function CreateKernelInNewThread is a bit
         * misleading--Soar itself is single-threaded. See the ThreadsInSML
         * document for details.
         *
         * The kernel object is used to create agents:
         */

        System.out.println("Creating Agent...");
        agent = kernel.CreateAgent("soar");

        /*
         * Once the agent is created it must be given Soar productions to run.
         */
        if (!agent.LoadProductions("state_maintenance.soar")) {
            System.err.println("Can't load state_maintenance.soar");
            System.exit(1);
        }

        /*
         * Next, the environment sets up communication with the agent. In this
         * simple example, it is just going to put a WME on the input-link (I2
         * ^hello world):
         */

        System.out.println("Creating input link...");
        // agent.GetInputLink().CreateStringWME("value-change", );
        if (add) {
          agent.GetInputLink().CreateIntWME("value-add", getNum());
        }
        else {
          agent.GetInputLink().CreateIntWME("value-sub", getNum());
        }
        /*
         * The environment now needs to register an event handler that fires
         * after all agents (only one agent in this case) proceed past their
         * output phases. The environment can check the output-link for commands
         * and act on them during this event.
         */

        System.out.println("Registering for update event...");
        kernel.RegisterForUpdateEvent(
                smlUpdateEventId.smlEVENT_AFTER_ALL_OUTPUT_PHASES,
                new UpdateEventInterface()
                {
                    public void updateEventHandler(int eventID, Object data,
                            Kernel kernel, int runFlags)
                    {
                        System.out.println("Update event...");

                        /*
                         * An output-link command is simply an identifier child
                         * on the output-link. Here, we iterate through all
                         * commands.
                         */

                        for (int index = 0; index < agent.GetNumberCommands(); ++index)
                        {
                            Identifier command = agent.GetCommand(index);

                            String name = command.GetCommandName();
                            System.out.println("Received command: " + name);
                            kernel.StopAllAgents();
                            command.AddStatusComplete();
                        }

                        /*
                         * After iterating, the changes must be cleared so that
                         * the commands we already saw do not show up on future
                         * decision cycles.
                         */

                        agent.ClearOutputLinkChanges();

                        System.out.println("Update event complete.");
                    }
                }, null);

        /*
         * This simple environment tells Soar to stop when any command is
         * received. In a more complex environment, different commands would be
         * tested for and command parameters read off of the output link.
         *
         * Note that the output-link also serves as a mini-input link, usually
         * in the form of "status" WMEs. There are two helper functions for
         * simple versions of this, Identifier.AddStatusComplete() and
         * Identifier.AddStatusError() which add ^status complete or ^status
         * error on to the command identifier. This kind of feedback makes it
         * easy for the agent to tell if the environment has received the
         * command.
         */

        System.out.println("Running agents...");
        kernel.RunAllAgents(5);

        /*
         * With the productions, input and output set up, Soar executes for five
         * decision cycles in this example. Normally, you would tell Soar to run
         * forever, but it is capped at five in this example so that it will
         * quickly fail if the agent doesn't perform as expected.
         *
         * Note that all execution commands (such as RunAllAgents and
         * RunAllAgentsForever) block until the requested number of decision
         * cycles are completed or Soar is interrupted. This means that if your
         * environment needs to be running at the same time as Soar, that you
         * need to spawn a thread for either your environment or for Soar. Note
         * that Soar is single-threaded internally, meaning that--as long as you
         * created the kernel locally--the events are fired in the same thread
         * that calls run.
         *
         * Once the run has completed, it is time to shut down
         */

        System.out.println("Shutting down...");
        kernel.DestroyAgent(agent);
        agent = null;
        kernel.Shutdown();
        kernel = null;

        System.out.println("Done.");
    }

    public static void main(String[] args)
    {
        new state_maintenance();
    }

    public int getNum()
    {
      Scanner reader = new Scanner(System.in);
      System.out.println("Enter a number: ");
      int n = reader.nextInt();
      reader.close();
      return n;
    }

}
