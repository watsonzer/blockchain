package fr.isep.algo;

public class Portefeuille {
    private String proprietaire;
    private String token;
    private double solde;

    public Portefeuille(String proprietaire, String token, double solde) {
        this.proprietaire = proprietaire;
        this.token = token;
        this.solde = solde;
    }

    public String getToken() {
        return token;
    }

    public double getSolde() {
        return solde;
    }

    public void crediter(double montant) {
        this.solde += montant;
    }

    public boolean debiter(double montant) {
        if (this.solde >= montant) {
            this.solde -= montant;
            return true;
        }
        return false;
    }

    public String getProprietaire() {
        return proprietaire;
    }
}
