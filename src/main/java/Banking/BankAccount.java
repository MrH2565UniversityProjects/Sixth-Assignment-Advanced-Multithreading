package Banking;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private final int id;
    private int balance;
    private final Lock lock = new ReentrantLock();

    public BankAccount(int id, int initialBalance) {
        this.id = id;
        this.balance = initialBalance;
    }

    public int getId(){
        return  id;
    }
    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    public void deposit(int amount) {
        lock.lock();
        balance += amount;
        lock.unlock();
        System.out.println("+" + amount + " to the " + getId() + "account");
        System.out.println("new balance is " + balance);
    }

    public void withdraw(int amount) {
        lock.lock();
        balance -= amount;
        lock.unlock();
        System.out.println("-" + amount + " from the " + getId() + "account");
        System.out.println("new balance is " + balance);
    }

    public void transfer(BankAccount target, int amount) {
        lock.lock();
        target.getLock().lock();
        balance -= amount;
        target.balance += amount;
        System.out.println(amount + "transferred from the account " + getId() + " to account " + target.getId());
        System.out.println("new balance is " + balance);
    }
}
