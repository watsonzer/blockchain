package fr.isep.algo;

import java.util.Map;

public class Transaction {
    public String emetteurToken;
    public String receveurToken;
    public double montant;

    public Transaction(String emetteurToken, String receveurToken, double montant) {
        this.emetteurToken = emetteurToken;
        this.receveurToken = receveurToken;
        this.montant = montant;
    }

    public boolean pay(Map<String, Portefeuille> wallets) {
        if (emetteurToken.equals(receveurToken)) return false;

        Portefeuille emetteur = wallets.get(emetteurToken);
        Portefeuille receveur = wallets.get(receveurToken);

        if (emetteur == null || receveur == null) return false;

        if (emetteur.debiter(montant)) {
            receveur.crediter(montant);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Transaction de " + montant + " ISEPcoins de " + emetteurToken.substring(0, 5)
                + " vers " + receveurToken.substring(0, 5);
    }
}
