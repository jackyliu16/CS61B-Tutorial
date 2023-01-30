package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
                if (args.length != 2) {
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
                    Commit commit = Helper.getCommitIfExist(args[1]);
                    log.debug(commit);
                    String hash = commit.getFileHashIfExist(args[3]);
                    log.debug(hash);
                    if (hash == null) exitProgramWithMessage("File does not exist in that commit.");
                    // just copy the file in the blob folder into the place ?
                    File original = Utils.join(REPO, BLOB_FOLDER, hash);
                    File destination = Utils.join(CWD, args[3]);    // the copy dest is the fileName
                    try {
                        Helper.copyFile(original, destination);
                    } catch (IOException e) {
                        log.error("IOException");
                    }
                } else {
                    log.info("checkout [branch name]");
                    log.info("checkout %s %s", args[0], args[1]);
                    if (args.length != 2) exitProgramWithMessage("Incorrect operands.");
                    // first check if the name is the branch
//                  BC we need to compare if the branch is current branch thus we not using Branch branch = Helper.getBranchIfExist(args[1]);
                    if (Objects.equals(getCurrent().getName(), args[1])) exitProgramWithMessage("No need to checkout the current branch.");
                    List<String> branchNames = Utils.plainFilenamesIn(Utils.join(REPO, BRANCH_FOLDER));
                    if (branchNames == null || branchNames.isEmpty()) exitProgramWithMessage("No such branch exists.");
                    log.debug("branch list: %s", branchNames);
                    // BC the name of the branchFile is the branch name
                    assert branchNames != null;
                    Branch branch = null;
                    if (branchNames.contains(args[1])) {
                        File branchFile = Utils.join(REPO, BRANCH_FOLDER, args[1]);
                        if (!branchFile.exists()) log.error("branch file not exist(shouldn't happen)");
                        branch = Utils.readObject(branchFile, Branch.class);
                        if (branch == null) log.error("branch is null");
                        assert branch != null;
                        // If a working file is untracked in the current branch and
                        // would be overwritten by the checkout, print There is an
                        // untracked file in the way; delete it, or add and commit it first.
                        sc = getStatus();
                        sc.checkOutAllFileInBranch(branch);
                        branch.getLatestCommit().resetFileOnTheCommitToWorkDirectory();
                    } else {
                        exitProgramWithMessage("No such branch exists.");
                    }
                    // TODO change the current branch to another branch.
                    if (branch == null) exitProgramWithMessage("No such branch exists.");
                    assert branch != null;
                    Helper.saveCurrentAndSetAsSpecBranch(branch);
                }
            }

            // TODO: handler checkout command;
            case "merge" -> {
                log.debug("merge command");
                exitIfNotGitLetDirectory();
                throw new UnsupportedOperationException();
            }

            // TODO: handler status command;
            case "status" -> {
                log.debug("status command");
                exitIfNotGitLetDirectory();
                sc = Helper.getStatus();
                System.out.println(sc);
            }

            case "rm" -> {
                // java gitlet.Main rm [file name]
                log.debug("rm command");
                // if stage for addition then remove
                // if in the track of current commit then stage it for remove and remove from work directory
                // if the file haven't been addition and haven't track in the haed commit. -> No reason to remove the file.
                sc = getStatus();
                sc.deleteFile(args[1]);
                Helper.saveStatus(sc);
            }

            case "log" -> {
                log.debug("log command");
                File logFile = Utils.join(REPO, CACHE_FOLDER, LOG_FILE);
                String str = Utils.readContentsAsString(logFile);
                System.out.println(str);
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
                if (args.length != 2) exitProgramWithMessage("Incorrect operands.");
                if (Helper.getBranchIfExist(args[1]) != null) exitProgramWithMessage("A branch with that name already exists.");
                getCurrent().baseOnItCreateANewBranch(args[1]);
            }

            case "rm-branch" -> {
                log.debug("rm-branch command");
                if (Objects.equals(getCurrent().getName(), args[1])) exitProgramWithMessage("Cannot remove the current branch.");
                Branch branch = Helper.getBranchIfExist(args[1]);
                if (branch == null) exitProgramWithMessage("A branch with that name does not exist.");
                assert branch != null;
                // BC the Utils.restrictedDelete .gitlet info error -> we only using our operation
                File branchFile = Utils.join(REPO, BRANCH_FOLDER, branch.getName());
                if (!branchFile.exists()) exitProgramWithMessage("A branch with that name does not exist.");
                if (branchFile.isDirectory()) log.error("branch file is directory");
                branchFile.delete();
            }

            case "reset" -> {
                log.debug("reset command");
                throw new UnsupportedOperationException("reset");
            }

            // TODO remove this
            case "test" -> {
                // print the file info of the latest commit in the branch
                log.setLogLevel(LogLevel.Trace);
                Branch branch = Helper.getCurrent();
                List<String> branchFiles = Utils.plainFilenamesIn(Utils.join(REPO, BRANCH_FOLDER));
                System.out.println(branch);
                if (branchFiles != null) {
                    for (String branchFile: branchFiles) {
                        File file = Utils.join(REPO, BRANCH_FOLDER, branchFile);
                        Branch theBranch = Utils.readObject(file, Branch.class);
                        System.out.println(theBranch);
                    }
                }
                Commit p = branch.getLatestCommit();
                while (p != null) {
                    System.out.printf("%s: %s\n", Utils.sha1(Utils.serialize(p)).substring(0, 7), p.getMapping());
                    p = p.prev;
                }

//                for (Commit p = branch.getLatestCommit(); p == null; p = p.prev) {
//                    System.out.printf("%s: %s", Utils.sha1(Utils.serialize(p)).substring(0, 7), p.getMapping());
//                }
//                Commit commit = branch.getLatestCommit();
//                log.info(commit);
//                // get the mapping info of it
//                log.info(commit.getMapping());
            }

            // TODO will only been using in debug operation
            case "hash" -> {
                File file = Utils.join(CWD, args[1]);
                try {
                    System.out.println(Utils.sha1((Object) Files.readAllBytes(file.toPath())));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
