package synthesizer;// TODO: Make sure to make this class a part of the synthesizer package
//package <package name>;

import java.util.ArrayList;

//Make sure this class is public
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this divsion operation into an int. For better
        //       accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        this.buffer = new ArrayRingBuffer<Double>((int) Math.round(SR / frequency));
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in the buffer, and replace it with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each other.

        // create the double and make sure there haven't same inside.
        ArrayList<Double> save = new ArrayList<>();
        for (int i = 0; i < buffer.capacity(); i++) {
            double r;
            do {
                r = Math.random() - 0.5;
            } while (save.contains(r));
            save.add(r);
        }

        // first deque until throw RunTimeException
        try {
            // remove all items
            for (int i = buffer.fillCount(); i >= 0; i--) {
                buffer.dequeue();
            }
        } catch (RuntimeException e) {
            System.out.println("RunTimeException: buffer deque forgot");
            // do nothing
        }

        // now all item will be remove;
        assert buffer.isEmpty();

        for (double item : save) {
            buffer.enqueue(item);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double item = buffer.dequeue();
        double peek = buffer.peek();
        buffer.enqueue((DECAY * (item + peek)) / 2);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
