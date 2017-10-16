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
    //still producing more than it is having.. going -ve. Fix that and you're done.
    private List<Coin> change(int cost) {

        List<Coin> changeList = new ArrayList<>();
        int change = getValueOfCoinsInserted() - cost;
        if (change == 0) {
            currentTransaction.clear();
            return changeList;
        } else {
            while (this.numLoonies > 0 && change >= Coin.LOONIE.getValue()) {
                change = change - Coin.LOONIE.getValue();
                this.numLoonies--;
                changeList.add(Coin.LOONIE);
            }

            while (this.numQuarters > 0 && change >= Coin.QUARTER.getValue()) {
                change = change - Coin.QUARTER.getValue();
                this.numQuarters--;
                changeList.add(Coin.QUARTER);
            }

            while (this.numDimes > 0 && change >= Coin.DIME.getValue()) {
                change = change - Coin.DIME.getValue();
                this.numDimes--;
                changeList.add(Coin.DIME);
            }
            while (this.numNickels > 0 && change >= Coin.NICKEL.getValue()) {
                change = change - Coin.NICKEL.getValue();
                this.numNickels--;
                changeList.add(Coin.NICKEL);
            }
            currentTransaction.clear();
        }
        return changeList;

    }

}
