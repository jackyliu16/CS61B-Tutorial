package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author jackyliu16@github.com
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(CWD, ".capers");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        CAPERS_FOLDER.mkdir();
        Dog.DOG_FOLDER.mkdir();
        try {
            Utils.join(CAPERS_FOLDER, "story").createNewFile();
        } catch (IOException e) {
            System.out.println("there is a IOException: create story file error !");
            e.printStackTrace();
        }
        assert CAPERS_FOLDER.exists();
        assert Dog.DOG_FOLDER.exists();
        assert Utils.join(CAPERS_FOLDER, "story").exists();
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File f = Utils.join(CAPERS_FOLDER, "story");
        // make sure in the previous section we have create the file
        assert f.exists();

        // read from the file
        String file_context = Utils.readContentsAsString(f);
        // write the info into the file
        file_context += text;
        file_context += "\n";
        System.out.println(file_context);
        Utils.writeContents(f, file_context);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog dog = new Dog(name, breed, age);
        dog.saveDog();
//        System.out.println(Dog.fromFile(name));
        System.out.println(dog);
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog birthDog = Dog.fromFile(name);
        birthDog.haveBirthday();
        birthDog.saveDog();     // save the age change information
    }
}
