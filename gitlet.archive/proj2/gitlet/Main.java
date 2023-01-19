package gitlet;

import gitlet.Logger;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {
    static Logger = Logger.INSTANCE; 
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                break;
            // TODO: FILL THE REST IN
            case "commit":
                // TODO: handle commit [message] command (which i guest there is no need to finish optional part)
                break;
            case "checkout":
                // TODO: handler checkout command;
                break;
            case "merge":
                // TODO: handler merge command
                break;
            case DEFAULT:
                // TODO: handler default command (which connect to the help function)
                break;
        }
    }
}
