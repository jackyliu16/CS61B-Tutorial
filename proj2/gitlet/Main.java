package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.*;

import static gitlet.Helper.*;

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
        log.setLogLevel(LogLevel.OFF);
        log.info("========== NEXT OPERATION %s ==========", args[0]);

        // if args is empty
        if (args.length == 0) {
            // NOTE ERR 01: If a user doesn’t input any arguments, print the message and exit
            System.out.println("Please enter a command.");
            System.exit(0);
        }

        String firstArg = args[0];
        StatusController sc;

        switch (firstArg) {
            case "init" -> {    // finish
                log.debug("init command");
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
                sc = Helper.getStatus();
                if (args.length < 1) {
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }

                // calling add operation
                for (int i = 1; i < args.length; i++ ) {
                    log.debug("args[%d] = %s", i, args[i]);
                    sc.addFile(args[i]);
                }

                // save StatusController
                Helper.saveStatus(sc);
            }

            case "commit" -> {
                // TODO: handle commit [message] command
                log.debug("commit command");
                exitIfNotGitLetDirectory();
                if (args.length > 2) {
                    // NOTE: in the accusation right now the user will only input one file at the time.
                    System.out.println("Incorrect operands.");
                    System.exit(0);
                }
                sc = Helper.getStatus();
                sc.commit(args[1]);
                Helper.saveStatus(sc);
            }

            case "checkout" -> {
                // args[0] = flag
                log.debug("checkout command");
                exitIfNotGitLetDirectory();
                if (args.length == 3 && Objects.equals(args[1], "--")) {
                    // checkout -- [filename]
                    // cp the file in the latest commit into work directory
                    String hash = Helper.getCurrent().getLatestCommit().getFileHashIfExist(args[2]);
                    if (hash == null) exitProgramWithMessage("File does not exist in that commit. ");
                    log.debug(hash);
                    // just copy the file in the blob folder into the place ?
                    File original = Utils.join(REPO, BLOB_FOLDER, hash);
                    File destination = Utils.join(CWD, args[2]);
                    try {
                        Helper.copyFile(original, destination);
                    } catch (IOException e) {
                        log.error("IOException");
                    }
                } else if (args.length == 4 && Pattern.matches("[a-f0-9]{40}", args[1])) {
                    // checkout [commit-id] -- [file name]
                    // TODO if we only using the unique prefix to find the commit
                    // if the input is match to a commit
                    // 1. check if the commit exist
                    // 2. if the file exist in the commit, just copy it and overwrite the file in the work directory
                    if (!Objects.equals(args[2], "--")) exitProgramWithMessage("Incorrect operands.");
                    File commitFile = Utils.join(REPO, COMMIT_FOLDER, args[1]);
                    if (!commitFile.exists()) exitProgramWithMessage("No commit with that id exists.");
                    Commit commit = Utils.readObject(commitFile, Commit.class);
                    String hash = commit.getFileHashIfExist(args[2]);
                    if (hash == null) exitProgramWithMessage("File does not exist in that commit.");
                    // just copy the file in the blob folder into the place ?
                    File original = Utils.join(REPO, BLOB_FOLDER, hash);
                    File destination = Utils.join(CWD, hash);
                    try {
                        Helper.copyFile(original, destination);
                    } catch (IOException e) {
                        log.error("IOException");
                    }
                } else {
                    // checkout [branch name]
                    if (args.length != 2) exitProgramWithMessage("Incorrect operands.");
                    // first check if the name is the branch
//                  BC we need to compare if the branch is current branch thus we not using Branch branch = Helper.getBranchIfExist(args[1]);
                    if (Objects.equals(getCurrent().getName(), args[2])) exitProgramWithMessage("No need to checkout the current branch.");
                    List<String> branchNames = Utils.plainFilenamesIn(Utils.join(REPO, BRANCH_FOLDER));
                    if (branchNames == null || branchNames.isEmpty()) exitProgramWithMessage("No such branch exists.");
                    // BC the name of the branchFile is the branch name
                    Branch branch;
                    if (branchNames.contains(args[1])) {
                        File branchFile = Utils.join(REPO, BRANCH_FOLDER, args[1]);
                        branch = Utils.readObject(branchFile, Branch.class);
                    } else {
                        exitProgramWithMessage("No such branch exists.");
                    }
                    // TODO finish the copy all file from branch
                }
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
                sc = Helper.getStatus();
                System.out.println(sc);
            }

            case "rm" -> {
                log.debug("rm command");
                throw new UnsupportedOperationException("rm");
            }

            case "log" -> {
                log.debug("log command");
                Helper.printLog();
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

            case "test" -> {
                log.debug("just for test");
                sc = Helper.getStatus();
                
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

    private static void exitProgramWithMessage(String message) {
        System.out.println(message);
        System.exit(0);
    }
}
