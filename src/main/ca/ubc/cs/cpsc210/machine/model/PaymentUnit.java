package ca.ubc.cs.cpsc210.machine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the payment unit in a vending machine.
 */
public class PaymentUnit {
    private int numLoonies;   // number of loonies banked in machine for making change
    private int numQuarters;  // number of quarters banked in machine for making change
    private int numDimes;     // number of dimes banked in machine for making change
    private int numNickels;   // number of nickels banked in machine for making change
    private List<Coin> currentTransaction;   // list of coins inserted into machine during current transaction

    // EFFECTS: constructs a payment unit with no banked coins and no coins inserted into the machine
    // as part of a payment
    public PaymentUnit() {
        this.numLoonies = 0;
        this.numQuarters = 0;
        this.numDimes = 0;
        this.numNickels = 0;
        currentTransaction = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: clears all the coins banked in the unit
    public void clearCoinsBanked() {
        this.numLoonies = 0;
        this.numQuarters = 0;
        this.numDimes = 0;
        this.numNickels = 0;

    }

    // REQUIRES: number > 0
    // MODIFIES: this
    // EFFECTS: adds number coins of type c to the banked coins in the unit
    public void addCoinsToBanked(Coin c, int number) {
        switch (c) {
            case DIME:
                this.numDimes = this.numDimes + number;
                break;
            case LOONIE:
                this.numLoonies = this.numLoonies + number;
                break;
            case NICKEL:
                this.numNickels = this.numNickels + number;
                break;
            case QUARTER:
                this.numQuarters = this.numQuarters + number;
                break;
            default:
                break;
        }
    }

    // EFFECTS: returns number of coins banked of the given type
    public int getNumberOfCoinsBankedOfType(Coin c) {
        int numCoins = 0;
        switch (c) {
            case DIME:
                numCoins = this.numDimes;
                break;
            case LOONIE:
                numCoins = this.numLoonies;
                break;
            case QUARTER:
                numCoins = this.numQuarters;
                break;
            case NICKEL:
                numCoins = this.numNickels;
                break;
            default:
                break;
        }
        return numCoins;
    }

    // EFFECTS: returns the total value of all coins banked in the unit
    public int getValueOfCoinsBanked() {
        return (this.numLoonies * Coin.LOONIE.getValue() + this.numQuarters * Coin.QUARTER.getValue() +
                this.numDimes * Coin.DIME.getValue() + this.numNickels * Coin.NICKEL.getValue());
    }

    // MODIFIES: this
    // EFFECTS: adds coin c to the unit as a part of a transaction
    public void insertCoin(Coin c) {
        currentTransaction.add(c);
    }

    // EFFECTS: returns value of coins inserted for current transaction
    public int getValueOfCoinsInserted() {
        int val = 0;
        for (Coin next : currentTransaction) {
            if (next == Coin.DIME)
                val = val + Coin.DIME.getValue();
            else if (next == Coin.LOONIE)
                val = val + Coin.LOONIE.getValue();
            else if (next == Coin.QUARTER)
                val = val + Coin.QUARTER.getValue();
            else
                val = val + Coin.NICKEL.getValue();
        }
        return val;
    }

    // MODIFIES: this
    // EFFECTS: coins inserted for current transaction are cleared; list of coins
    // inserted for current transaction is returned in the order in which they were inserted.
    public List<Coin> cancelTransaction() {
        List<Coin> canceled = new ArrayList<>();
        canceled.addAll(currentTransaction);
        currentTransaction.clear();
        return canceled;
    }

    // REQUIRES: cost <= total value of coins inserted as part of current transaction
    // MODIFIES: this
    // EFFECTS: adds coins inserted to coins banked in unit and returns list of coins that will be provided as change.
    // Coins of largest possible value are used when determining the change.  Change in full is not guaranteed -
    // will provide only as many coins as are banked in the machine, without going over the amount due.
    public List<Coin> makePurchase(int cost) {
        for (Coin next : currentTransaction) {
            if (next == Coin.LOONIE)
                this.numLoonies++;
            else if (next == Coin.QUARTER)
                this.numQuarters++;
            else if (next == Coin.DIME)
                this.numDimes++;
            else
                this.numNickels++;
        }
        return change(cost);
    }

    //EFFECTS: returns the list of coins required as change for a transaction, helper for makePurchase()

    private List<Coin> change(int cost) {
        int l;
        int q;
        int d;
        int n;
        List<Coin> changeList = new ArrayList<>();
        int change = getValueOfCoinsInserted() - cost;
        if (change == 0) {
            currentTransaction.clear();
            return changeList;
        } else {
            if (this.numLoonies != 0) {
                l = change / Coin.LOONIE.getValue();
                change = change - l * Coin.LOONIE.getValue();
                this.numLoonies = this.numLoonies - l;
                addCoin(changeList, Coin.LOONIE, l);
            }
            if (this.numQuarters != 0) {
                q = change / Coin.QUARTER.getValue();
                change = change - q * Coin.QUARTER.getValue();
                this.numQuarters = this.numQuarters - q;
                addCoin(changeList, Coin.QUARTER, q);
            }
            if (this.numDimes != 0) {
                d = change / Coin.DIME.getValue();
                change = change - d * Coin.DIME.getValue();
                this.numDimes = this.numDimes - d;
                addCoin(changeList, Coin.DIME, d);
            }
            if (this.numNickels != 0) {
                n = change / Coin.NICKEL.getValue();
                this.numNickels = this.numNickels - n;
                addCoin(changeList, Coin.NICKEL, n);
            }
            currentTransaction.clear();
        }

        return changeList;

    }

    //EFFECTS: adds a Coin to the List a certain number of times

    private void addCoin(List<Coin> l, Coin c, int number) {
        for (int i = 0; i < number; i++) {
            l.add(c);
        }
    }

}
