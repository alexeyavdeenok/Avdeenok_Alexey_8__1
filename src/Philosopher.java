public class Philosopher extends Thread {
  private final int id;
  private final Fork leftFork;
  private final Fork rightFork;
  private final int eatingTime;

  public Philosopher(int id, Fork leftFork, Fork rightFork, int eatingTime) {
    this.id = id;
    this.leftFork = leftFork;
    this.rightFork = rightFork;
    this.eatingTime = eatingTime;
  }

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

  private void think() throws InterruptedException {
    System.out.println("Philosopher " + id + " is thinking.");
    Thread.sleep((long) (Math.random() * 1000));
  }

  private void eat() throws InterruptedException {
    synchronized (leftFork) {
      System.out.println("Philosopher " + id + " picked up left fork " + leftFork.getId());
      synchronized (rightFork) {
        System.out.println("Philosopher " + id + " picked up right fork " + rightFork.getId());
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep(eatingTime);
        System.out.println("Philosopher " + id + " is done eating.");
        rightFork.putDown();
      }
      leftFork.putDown();
    }
  }
}
