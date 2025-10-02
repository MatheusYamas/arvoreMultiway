package arvore23;

public class Node {
    private Integer informacao1;
    private Integer informacao2;
    private Node esquerda;
    private Node direita;
    private Node meio;

    public Node(Integer informacao) {
        this.informacao1 = informacao;
        this.informacao2 = null;
        this.esquerda = null;
        this.direita = null;
        this.meio = null;
    }

    public boolean cheio() {
        return this.informacao2 != null;
    }

    public boolean eFolha() {
        return this.esquerda == null;
    }

    public void adicionarInformacao(Integer novaInformacao) {
        if (this.informacao1 == null) {
            this.informacao1 = novaInformacao;
            return;
        }
        if (novaInformacao > this.informacao1) {
            this.informacao2 = novaInformacao;
        } else {
            this.informacao2 = this.informacao1;
            this.informacao1 = novaInformacao;
        }
    }

    public void removerValor(Integer valor) {
        if (valor.equals(this.informacao1)) {
            this.informacao1 = this.informacao2;
            this.informacao2 = null;
        } else if (valor.equals(this.informacao2)) {
            this.informacao2 = null;
        }
    }

    public Integer getInformacao(int indice) {
        return (indice == 0) ? this.informacao1 : this.informacao2;
    }

    public void setInformacao(int indice, Integer valor) {
        if (indice == 0) {
            this.informacao1 = valor;
        } else {
            this.informacao2 = valor;
        }
    }

    public Node getFilho(int indice) {
        if (indice == 0) return this.esquerda;
        if (indice == 1) return this.meio;
        return this.direita; // indice == 2
    }

    public Integer getInformacao1() { return informacao1; }
    public void setInformacao1(Integer informacao1) { this.informacao1 = informacao1; }
    public Integer getInformacao2() { return informacao2; }
    public void setInformacao2(Integer informacao2) { this.informacao2 = informacao2; }
    public Node getDireita() { return direita; }
    public Node getEsquerda() { return esquerda; }
    public Node getMeio() { return meio; }
    public void setEsquerda(Node esquerda) { this.esquerda = esquerda; }
    public void setDireita(Node direita) { this.direita = direita; }
    public void setMeio(Node meio) { this.meio = meio; }
}