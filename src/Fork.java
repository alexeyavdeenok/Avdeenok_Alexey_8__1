/**
 * The {@code Fork} class represents a single fork used in the Dining Philosophers problem. Each
 * fork has a unique identifier and can be either taken or available. The class provides
 * synchronized methods to take and put down the fork.
 */
public class Fork {
  private final int id;
  private boolean isTaken;

  /**
   * Constructs a new {@code Fork} with the specified identifier.
   *
   * @param id the unique identifier for this fork
   */
  public Fork(int id) {
    this.id = id;
    this.isTaken = false;
  }

  /**
   * Acquires the fork if it is available. If the fork is already taken, the calling thread will be
   * blocked until the fork becomes available.
   *
   * @throws InterruptedException if the current thread is interrupted while waiting
   */
  public synchronized void take() throws InterruptedException {
    while (isTaken) {
      wait();
    }
    isTaken = true;
  }

  /** Releases the fork and notifies all waiting threads that the fork is now available. */
  public synchronized void putDown() {
    isTaken = false;
    notifyAll();
  }

  /**
   * Returns the unique identifier for this fork.
   *
   * @return the unique identifier for this fork
   */
  public int getId() {
    return id;
  }
}
