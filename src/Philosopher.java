/**
 * The {@code Philosopher} class represents a philosopher in the Dining Philosophers problem. Each
 * philosopher alternates between thinking and eating, and requires two forks to eat. This class
 * extends {@code Thread} and overrides the {@code run} method to define the philosopher's behavior.
 */
public class Philosopher extends Thread {
  private final int id;
  private final Fork leftFork;
  private final Fork rightFork;
  private final int eatingTime;

  /**
   * Constructs a new {@code Philosopher} with the specified identifier, left and right forks, and
   * eating time.
   *
   * @param id the unique identifier for this philosopher
   * @param leftFork the left fork assigned to this philosopher
   * @param rightFork the right fork assigned to this philosopher
   * @param eatingTime the time this philosopher spends eating
   */
  public Philosopher(int id, Fork leftFork, Fork rightFork, int eatingTime) {
    this.id = id;
    this.leftFork = leftFork;
    this.rightFork = rightFork;
    this.eatingTime = eatingTime;
  }

  /**
   * Defines the behavior of the philosopher, which alternates between thinking and eating. This
   * method is called when the thread is started.
   */
  @Override
  public void run() {
    try {
      while (true) {
        think();
        eat();
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Simulates the philosopher thinking. The philosopher will think for a random amount of time up
   * to 1000 milliseconds.
   *
   * @throws InterruptedException if the current thread is interrupted while sleeping
   */
  private void think() throws InterruptedException {
    System.out.println("Philosopher " + id + " is thinking.");
    Thread.sleep((long) (Math.random() * 1000));
  }

  /**
   * Simulates the philosopher eating. The philosopher will pick up the left fork first, then the
   * right fork, eat for the specified eating time, and then put down both forks.
   *
   * @throws InterruptedException if the current thread is interrupted while sleeping
   */
  private void eat() throws InterruptedException {
    synchronized (leftFork) {
      synchronized (rightFork) {
        System.out.println("Philosopher " + id + " picked up forks");
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep(eatingTime);
        System.out.println("Philosopher " + id + " is done eating.");
        rightFork.putDown();
      }
      leftFork.putDown();
    }
  }
}
