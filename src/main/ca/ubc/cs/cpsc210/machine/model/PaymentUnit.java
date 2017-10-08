package ca.ubc.cs.cpsc210.machine.model;

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
        //TODO: complete implementation
    }

    // MODIFIES: this
    // EFFECTS: clears all the coins banked in the unit
    public void clearCoinsBanked() {
        //TODO: complete implementation
    }

    // REQUIRES: number > 0
    // MODIFIES: this
    // EFFECTS: adds number coins of type c to the banked coins in the unit
    public void addCoinsToBanked(Coin c, int number) {
        //TODO: complete implementation
    }

    // EFFECTS: returns number of coins banked of the given type
    public int getNumberOfCoinsBankedOfType(Coin c) {
        //TODO: complete implementation
        return 0;
    }

    // EFFECTS: returns the total value of all coins banked in the unit
    public int getValueOfCoinsBanked() {
        //TODO: complete implementation
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: adds coin c to the unit as a part of a transaction
    public void insertCoin(Coin c) {
        //TODO: complete implementation
    }

    // EFFECTS: returns value of coins inserted for current transaction
    public int getValueOfCoinsInserted() {
        //TODO: complete implementation
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: coins inserted for current transaction are cleared; list of coins
    // inserted for current transaction is returned in the order in which they were inserted.
    public List<Coin> cancelTransaction() {
        //TODO: complete implementation
        return null;
    }

    // REQUIRES: cost <= total value of coins inserted as part of current transaction
    // MODIFIES: this
    // EFFECTS: adds coins inserted to coins banked in unit and returns list of coins that will be provided as change.
    // Coins of largest possible value are used when determining the change.  Change in full is not guaranteed -
    // will provide only as many coins as are banked in the machine, without going over the amount due.
    public List<Coin> makePurchase(int cost) {
        //TODO: complete implementation
        return null;
    }
}
