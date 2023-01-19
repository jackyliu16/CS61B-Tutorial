package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author jackyliu16@github.com
 */
public class Main {
    static final String REPO_NAME = ".gitlet";
    static final Logger log = Logger.INSTANCE;
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        log.setLogLevel(LogLevel.Trace);
        log.debug("start Main function");

        // if args is empty
        if (args.length == 0) {
            // NOTE ERR 01: If a user doesn’t input any arguments, print the message and exit
            System.out.println("Please enter a command.");
            System.exit(0);
        }

        String firstArg = args[0];

        switch (firstArg) {
            case "init" -> {
                log.debug("init command");
                // TODO: handle the `init` command
                if (ifGitLetDirectoryExists()) {
                    System.out.println("A Gitlet version-control system already exists in the current directory.");
                    System.exit(0);
                }
                Helper.initRepository();
            }

            case "add" -> {
                // TODO: handle the `add [filename]` command
                log.debug("add command");
                exitIfNotGitLetDirectory();
            }

            case "commit" -> {
                // TODO: handle commit [message] command
                log.debug("commit command");
                exitIfNotGitLetDirectory();
                if (args.length > 1) {
                    // NOTE: in the accusation right now the user will only input one file at the time.
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
            }

            case "checkout" -> {
                log.debug("checkout command");
                exitIfNotGitLetDirectory();
                // TODO: if there is more than one string has been input?
            }

            // TODO: handler checkout command;
            case "merge" -> {
                log.debug("merge command");
                exitIfNotGitLetDirectory();
            }

            // TODO: handler status command;
            case "status" -> {
                log.debug("status command");
                exitIfNotGitLetDirectory();
            }

            case "rm" -> {
                log.debug("rm command");
                throw new UnsupportedOperationException("rm");
            }

            case "log" -> {
                log.debug("log command");
                throw new UnsupportedOperationException("log");
            }

            case "global-log" -> {
                log.debug("global-log command");
                throw new UnsupportedOperationException("global-log");
            }

            case "find" -> {
                log.debug("find command");
                throw new UnsupportedOperationException("find");
            }

            case "branch" -> {
                log.debug("branch command");
                throw new UnsupportedOperationException("branch");
            }

            case "rm-branch" -> {
                log.debug("rm-branch command");
                throw new UnsupportedOperationException("rm-branch");
            }

            case "reset" -> {
                log.debug("reset command");
                throw new UnsupportedOperationException("reset");
            }

            // TODO: handler merge command
            default -> {
                log.debug("unidentified command");
                System.out.println("No command with that name exists.");
                System.exit(0);
            }
        }
    }

    public static void exitIfNotGitLetDirectory() {
        File gitLetDirectly = Utils.join(CWD, REPO_NAME);
        log.trace(gitLetDirectly.toString());
        if (!gitLetDirectly.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
        log.trace("git-let repo check successes");
    }

    public static boolean ifGitLetDirectoryExists() {
        File gitLetDirectly = Utils.join(CWD, REPO_NAME);
        log.trace(gitLetDirectly.toString());
        return gitLetDirectly.exists();
    }
}
