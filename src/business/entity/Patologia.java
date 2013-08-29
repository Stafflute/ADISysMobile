package business.entity;

public class Patologia implements Entity {
    private String codice;
    private String nome;
    private int gravita;

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        codice = codice.trim();
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        nome = nome.trim();
        this.nome = nome;
    }

    public int getGravita() {
        return gravita;
    }

    public void setGravita(int gravita) {
        this.gravita = gravita;
    }

    @Override
    public boolean equals(Object o) {
        Patologia patologia = (Patologia) o;
        return (o != null) &&
                codice.equals(patologia.codice);
    }

    @Override
    public int hashCode() {
        return codice.hashCode();
    }

    private static final String SEPARATOR = " ";
    private static final String COMMA = ", ";

    public String toString() {
        return codice + SEPARATOR + nome + COMMA + gravita;
    }
}
