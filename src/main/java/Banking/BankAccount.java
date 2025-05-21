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
        deposit(amount,true);
    }

    public void deposit(int amount,boolean hasMessage) {
        lock.lock();
        balance += amount;
        lock.unlock();
        if(hasMessage){
            System.out.println("+" + amount + " to the " + getId() + " account");
            System.out.println("new balance is " + balance);
        }
    }
    public void withdraw(int amount) {
        withdraw(amount,true);
    }
    public void withdraw(int amount,boolean hasMessage) {
        lock.lock();
        balance -= amount;
        lock.unlock();
        if(hasMessage) {
            System.out.println("-" + amount + " from the " + getId() + " account");
            System.out.println("new balance is " + balance);
        }
    }

    public void transfer(BankAccount target, int amount) {
        withdraw(amount,false);
        target.deposit(amount,false);
        System.out.println(amount + " transferred from the account " + getId() + " to account " + target.getId());
        System.out.println("new balance is " + balance);
    }
}
