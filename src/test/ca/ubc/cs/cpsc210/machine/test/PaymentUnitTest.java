package ca.ubc.cs.cpsc210.machine.test;


import ca.ubc.cs.cpsc210.machine.model.Coin;
import ca.ubc.cs.cpsc210.machine.model.PaymentUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentUnitTest {

    private PaymentUnit machine;

    @BeforeEach
    public void runBefore() {
        machine = new PaymentUnit();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, machine.getValueOfCoinsBanked());
        assertEquals(0, machine.getValueOfCoinsInserted());

    }

    @Test
    public void testAddCoinsToBanked() {
        machine.addCoinsToBanked(Coin.LOONIE, 2);
        assertEquals(200, machine.getValueOfCoinsBanked());
        machine.addCoinsToBanked(Coin.QUARTER, 2);
        assertEquals(250, machine.getValueOfCoinsBanked());
        machine.addCoinsToBanked(Coin.DIME, 2);
        assertEquals(270, machine.getValueOfCoinsBanked());
        machine.addCoinsToBanked(Coin.NICKEL, 1);
        assertEquals(275, machine.getValueOfCoinsBanked());
    }

    @Test
    public void testClearCoinsBanked() {
        assertEquals(0, machine.getValueOfCoinsBanked());
        machine.clearCoinsBanked();
        assertEquals(0, machine.getValueOfCoinsBanked());
        machine.addCoinsToBanked(Coin.LOONIE, 1);
        machine.addCoinsToBanked(Coin.NICKEL, 1);
        assertEquals(105, machine.getValueOfCoinsBanked());
        machine.clearCoinsBanked();
        assertEquals(0, machine.getValueOfCoinsBanked());
    }

    @Test
    public void testGetNumberOfCoinsBankedOfType() {
        assertEquals(0, machine.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(0, machine.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(0, machine.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(0, machine.getNumberOfCoinsBankedOfType(Coin.DIME));

        machine.addCoinsToBanked(Coin.LOONIE, 1);
        machine.addCoinsToBanked(Coin.NICKEL, 1);
        machine.addCoinsToBanked(Coin.QUARTER, 2);
        machine.addCoinsToBanked(Coin.DIME, 2);

        assertEquals(1, machine.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(1, machine.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(2, machine.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(2, machine.getNumberOfCoinsBankedOfType(Coin.DIME));

    }

    @Test

    public void testInsertCoin() {
        machine.insertCoin(Coin.LOONIE);
        assertEquals(100, machine.getValueOfCoinsInserted());
        machine.insertCoin(Coin.DIME);
        machine.insertCoin(Coin.QUARTER);
        machine.insertCoin(Coin.QUARTER);
        machine.insertCoin(Coin.NICKEL);
        assertEquals(165, machine.getValueOfCoinsInserted());

    }

    @Test

    public void testCancelTransaction() {
        List<Coin> cancelled;
        cancelled = new ArrayList<>();
        assertEquals(cancelled, machine.cancelTransaction());
        machine.insertCoin(Coin.LOONIE);
        machine.insertCoin(Coin.LOONIE);
        machine.insertCoin(Coin.QUARTER);
        cancelled.add(Coin.LOONIE);
        cancelled.add(Coin.LOONIE);
        cancelled.add(Coin.QUARTER);
        assertEquals(cancelled, machine.cancelTransaction());

    }

    @Test

    public void testMakePurchaseWhenNoChange() {
        machine.insertCoin(Coin.LOONIE);
        assertEquals(new ArrayList<Coin>(), machine.makePurchase(100));
        assertEquals(0,machine.getValueOfCoinsInserted());
        assertEquals(100,machine.getValueOfCoinsBanked());

    }

    @Test

    public void testMakePurchaseWhenFullChangeCannotBeReturned() {
        machine.addCoinsToBanked(Coin.QUARTER,3);
        machine.addCoinsToBanked(Coin.DIME, 3);
        machine.addCoinsToBanked(Coin.NICKEL, 2);
        List<Coin> change;
        machine.insertCoin(Coin.LOONIE);
        change = new ArrayList<Coin>();
        change.add(Coin.QUARTER);
        change.add(Coin.QUARTER);
        change.add(Coin.DIME);
        assertEquals(change, machine.makePurchase(36));
        assertEquals(0,machine.getValueOfCoinsInserted());
        assertEquals(155,machine.getValueOfCoinsBanked());

    }

    @Test

    public void testMakePurchaseWhenFullChangeCanBeReturned() {
        machine.addCoinsToBanked(Coin.QUARTER,3);
        machine.addCoinsToBanked(Coin.DIME, 3);
        machine.addCoinsToBanked(Coin.NICKEL, 4);
        List<Coin> change;
        machine.insertCoin(Coin.LOONIE);
        machine.insertCoin(Coin.QUARTER);
        machine.insertCoin(Coin.QUARTER);
        machine.insertCoin(Coin.DIME);
        machine.insertCoin(Coin.NICKEL);
        change = new ArrayList<Coin>();
        change.add(Coin.DIME);
        assertEquals(change, machine.makePurchase(155));
        assertEquals(0,machine.getValueOfCoinsInserted());
        assertEquals(280,machine.getValueOfCoinsBanked());

    }

    @Test

    public void testMakePurchaseCorrectCoinsAreReturned(){
        machine.addCoinsToBanked(Coin.QUARTER,1);
        machine.addCoinsToBanked(Coin.DIME,4);

        List<Coin> change;
        machine.insertCoin(Coin.LOONIE);
        change=new ArrayList<>();
        change.add(Coin.QUARTER);
        change.add(Coin.DIME);
        assertEquals(change,machine.makePurchase(60));
        assertEquals(0,machine.getValueOfCoinsInserted());
        assertEquals(130,machine.getValueOfCoinsBanked());
    }

    @Test

    public void testMakePurchaseCoverageCases(){
        machine.addCoinsToBanked(Coin.DIME,1);
        machine.addCoinsToBanked(Coin.NICKEL,3);

        List<Coin> change=new ArrayList<>();
        machine.insertCoin(Coin.LOONIE);
        change.add(Coin.DIME);
        change.add(Coin.NICKEL);
        change.add(Coin.NICKEL);
        change.add(Coin.NICKEL);
        assertEquals(change,machine.makePurchase(75));
        assertEquals(0,machine.getValueOfCoinsInserted());
        assertEquals(100,machine.getValueOfCoinsBanked());
    }

    @Test

    public void testMakePurchaseCoverageCases2(){
        List<Coin> change=new ArrayList<>();
        machine.insertCoin(Coin.LOONIE);
        change.add(Coin.LOONIE);
        assertEquals(change,machine.makePurchase(0));
        assertEquals(0,machine.getValueOfCoinsInserted());
        assertEquals(0,machine.getValueOfCoinsBanked());
    }


}
