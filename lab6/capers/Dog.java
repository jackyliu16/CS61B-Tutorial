package capers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import static capers.Utils.*;
import static capers.CapersRepository.CAPERS_FOLDER;

/** Represents a dog that can be serialized.
 * @author jackyliu16@github.com
*/
public class Dog implements Serializable{

    /** Folder that dogs live in. */
    static final File DOG_FOLDER = Utils.join(CAPERS_FOLDER, "dogs");

    /** Age of dog. */
    private int age;
    /** Breed of dog. */
    private String breed;
    /** Name of dog. */
    private String name;

    /**
     * Creates a dog object with the specified parameters.
     * @param name Name of dog
     * @param breed Breed of dog
     * @param age Age of dog
     */
    public Dog(String name, String breed, int age) {
        this.age = age;
        this.breed = breed;
        this.name = name;
    }

    /**
     * Reads in and deserializes a dog from a file with name NAME in DOG_FOLDER.
     *
     * @param name Name of dog to load
     * @return Dog read from file
     */
    public static Dog fromFile(String name) {
        File dogFile = Utils.join(DOG_FOLDER, name);
        if (!dogFile.exists())
            return null;

        assert dogFile.exists();
        return Utils.readObject(dogFile, Dog.class);
    }

    /**
     * Increases a dog's age and celebrates!
     */
    public void haveBirthday() {
        age += 1;
        System.out.println(toString());
        System.out.println("Happy birthday! Woof! Woof!");
    }

    /**
     * Saves a dog to a file for future use.
     */
    public void saveDog() {
        // if   dog name exist
        //          just change it's value
        // else     create a new file and add the Serializable information into it.
        assert this.name != null;
        File dogFile = Utils.join(DOG_FOLDER, this.name);
        if (dogFile.exists()) {
            // rewrite the value of the saving dog
            Utils.writeObject(dogFile, this);
        } else {
            // create a file and write into it
            try {
                dogFile.createNewFile();
            } catch (IOException e) {
                System.out.println("IOException when create a dog");
            }
            assert dogFile.exists();

            Utils.writeObject(dogFile, this);
        }
    }

    @Override
    public String toString() {
        return String.format(
            "Woof! My name is %s and I am a %s! I am %d years old! Woof!",
            name, breed, age);
    }

}
