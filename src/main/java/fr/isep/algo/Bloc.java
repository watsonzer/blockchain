package fr.isep.algo;

import java.util.*;

public class Bloc {
    private static int compteur = 0;
    private int id;
    private Date date;
    public List<Transaction> transactions = new ArrayList<>();

    public Bloc() {
        this.id = ++compteur;
        this.date = new Date();
    }

    public Bloc add(Transaction t, Map<String, Portefeuille> wallets, List<Bloc> blockchain) {
        transactions.add(t);

        if (transactions.size() == 10) {
            System.out.println("🔗 Bloc " + id + " plein. Validation des transactions...");
            for (Transaction tx : transactions) {
                boolean success = tx.pay(wallets);
                System.out.println("   - " + tx + " → " + (success ? "✅" : "❌"));
            }
            Bloc nouveauBloc = new Bloc();
            blockchain.add(nouveauBloc);
            return nouveauBloc;
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bloc ").append(id).append(" (").append(transactions.size()).append(" tx)\n");
        for (Transaction t : transactions) {
            sb.append("  - ").append(t).append("\n");
        }
        return sb.toString();
    }
}
