package fr.isep.algo;

import java.util.*;

public class Main {
    public static Map<String, Portefeuille> wallets = new HashMap<>();
    public static List<Bloc> blockchain = new ArrayList<>();

    public static void main(String[] args) {
        String[] noms = {"Alice", "Bob", "Chloé", "David", "Emma"};
        creerPortefeuilles(noms);

        blockchain.add(new Bloc());
        simulation();

        afficherSoldes();
        afficherDerniersBlocs(3);
        analyserTransactions();
        grapheDesEchanges();
    }

    public static void creerPortefeuilles(String[] noms) {
        for (String nom : noms) {
            String token = UUID.randomUUID().toString();
            wallets.put(token, new Portefeuille(nom, token, 10.0));
        }
        System.out.println("✅ Portefeuilles créés :");
        for (Portefeuille p : wallets.values()) {
            System.out.println(" - " + p.getProprietaire() + " (" + p.getToken().substring(0, 5) + "...): 10 ISEPcoins");
        }
    }

    public static void simulation() {
        Random rand = new Random();
        List<String> tokens = new ArrayList<>(wallets.keySet());
        Bloc blocActuel = blockchain.get(0);

        for (int i = 0; i < 55; i++) {
            String emetteur = tokens.get(rand.nextInt(tokens.size()));
            String receveur = tokens.get(rand.nextInt(tokens.size()));
            double montant = 1 + rand.nextInt(5);

            Transaction t = new Transaction(emetteur, receveur, montant);
            blocActuel = blocActuel.add(t, wallets, blockchain);
        }

        System.out.println("\n✅ Simulation terminée : " + blockchain.size() + " blocs créés.");
    }

    public static void afficherSoldes() {
        System.out.println("\n📊 Soldes finaux :");
        for (Portefeuille p : wallets.values()) {
            System.out.printf(" - %s : %.2f ISEPcoins\n", p.getProprietaire(), p.getSolde());
        }
    }

    public static void afficherDerniersBlocs(int n) {
        System.out.println("\n📦 Derniers blocs de la blockchain (antéchronologique) :");
        for (int i = blockchain.size() - 1; i >= Math.max(0, blockchain.size() - n); i--) {
            System.out.println(blockchain.get(i));
        }
    }

    public static void analyserTransactions() {
        Map<String, Integer> compteurTransactions = new HashMap<>();
        Map<String, Double> montantTransactions = new HashMap<>();

        for (Bloc bloc : blockchain) {
            for (Transaction t : bloc.transactions) {
                compteurTransactions.put(t.emetteurToken,
                        compteurTransactions.getOrDefault(t.emetteurToken, 0) + 1);
                montantTransactions.put(t.emetteurToken,
                        montantTransactions.getOrDefault(t.emetteurToken, 0.0) + t.montant);
            }
        }

        String hackerToken = (String) wallets.keySet().toArray()[0];
        Portefeuille hacker = wallets.get(hackerToken);

        System.out.println("\n👾 Étudiant malveillant : " + hacker.getProprietaire());
        System.out.println(" - Transactions effectuées : " + compteurTransactions.getOrDefault(hackerToken, 0));
        System.out.printf(" - Volume total : %.2f ISEPcoins\n", montantTransactions.getOrDefault(hackerToken, 0.0));
    }

    public static void grapheDesEchanges() {
        List<String> tokens = new ArrayList<>(wallets.keySet());
        int n = tokens.size();
        double[][] matrice = new double[n][n];

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) indexMap.put(tokens.get(i), i);

        for (Bloc bloc : blockchain) {
            for (Transaction t : bloc.transactions) {
                int i = indexMap.get(t.emetteurToken);
                int j = indexMap.get(t.receveurToken);
                matrice[i][j] += t.montant;
            }
        }

        System.out.println("\n📈 Matrice des échanges (poids = ISEPcoins transférés) :");
        System.out.print("       ");
        for (int i = 0; i < n; i++) {
            System.out.print(tokens.get(i).substring(0, 5) + " ");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.print(tokens.get(i).substring(0, 5) + " ");
            for (int j = 0; j < n; j++) {
                System.out.printf("%6.1f ", matrice[i][j]);
            }
            System.out.println();
        }
    }
}
