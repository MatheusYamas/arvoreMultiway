package arvore23;


// Classe principal
public class Arvore23 {
    private Node raiz;
    public Arvore23() {
        this.raiz = null;
    }

    //Classe public inserir
    public void inserir(Integer informacao) {
        if (vazia(this.raiz)) {
            this.raiz = new Node(informacao);
            return;
        }
        Node resultado = recursaoInserir(this.raiz, informacao);
        if (resultado != null) {
            this.raiz = resultado;
        }
    }

    //Recursão para inserir valor
    private Node recursaoInserir(Node no, Integer informacao) {
        if (no.eFolha()) {
            return no.cheio() ? split(no, informacao, null) : adicionarInfo(no, informacao);
        }
        Node filhoPromovido;
        if (informacao < no.getInformacao1()) {
            filhoPromovido = recursaoInserir(no.getEsquerda(), informacao);
        }
        else if (!no.cheio() || informacao < no.getInformacao2()) {
            filhoPromovido = recursaoInserir(no.getMeio(), informacao);
        }
        else {
            filhoPromovido = recursaoInserir(no.getDireita(), informacao);
        }
        return (filhoPromovido == null) ? null : absorverPromocao(no, filhoPromovido);
    }


    private Node adicionarInfo(Node no, Integer info) {
        no.adicionarInformacao(info);
        return null;
    }

    private Node absorverPromocao(Node pai, Node filhoPromovido) {
        Integer valorPromovido = filhoPromovido.getInformacao1();
        if (!pai.cheio()) {
            absorverSplit(pai, filhoPromovido, valorPromovido);
            return null;
        }
        else {
            return split(pai, valorPromovido, filhoPromovido);
        }
    }

    private void absorverSplit(Node pai, Node filhoPromovido, Integer valorPromovido) {
        Integer informacaoOriginal = pai.getInformacao1();
        pai.adicionarInformacao(valorPromovido);
        if (valorPromovido < informacaoOriginal) {
            pai.setDireita(pai.getMeio());
            pai.setEsquerda(filhoPromovido.getEsquerda());
            pai.setMeio(filhoPromovido.getMeio());
        }
        else {
            pai.setDireita(filhoPromovido.getMeio());
            pai.setMeio(filhoPromovido.getEsquerda());
        }
    }

    private Node split(Node no, Integer informacao, Node nosFilhos) {
        Integer menor, meio, maior;
        if (informacao < no.getInformacao1()) {
            menor = informacao;
            meio = no.getInformacao1();
            maior = no.getInformacao2();
        }
        else if (informacao < no.getInformacao2()) {
            menor = no.getInformacao1();
            meio = informacao;
            maior = no.getInformacao2();
        }
        else {
            menor = no.getInformacao1();
            meio = no.getInformacao2();
            maior = informacao;
        }

        no.setInformacao1(menor); no.setInformacao2(null);
        Node noIrmao = new Node(maior);
        Node noPai = new Node(meio);
        noPai.setEsquerda(no); noPai.setMeio(noIrmao);

        if (nosFilhos != null) {
            if (informacao < meio) {
                noIrmao.setEsquerda(no.getMeio()); noIrmao.setMeio(no.getDireita());
                no.setEsquerda(nosFilhos.getEsquerda()); no.setMeio(nosFilhos.getMeio());
            }
            else if (informacao > meio) {
                noIrmao.setEsquerda(nosFilhos.getEsquerda()); noIrmao.setMeio(nosFilhos.getMeio());
            }
            else {
                no.setMeio(nosFilhos.getEsquerda()); noIrmao.setEsquerda(nosFilhos.getMeio());
                noIrmao.setMeio(no.getDireita());
            }
            no.setDireita(null);
        }
        return noPai;
    }

    //Classe public buscar
    public boolean buscar(Integer valor) {
        return recursaoBuscar(this.raiz, valor);
    }

    private boolean recursaoBuscar(Node no, Integer valor) {
        if (no == null) return false;
        if (valor.equals(no.getInformacao1()) || (no.cheio() && valor.equals(no.getInformacao2()))) {
            return true;
        }
        if (valor < no.getInformacao1()) {
            return recursaoBuscar(no.getEsquerda(), valor);
        }
        else if (no.cheio() && valor > no.getInformacao2()) {
            return recursaoBuscar(no.getDireita(), valor);
        }
        else {
            return recursaoBuscar(no.getMeio(), valor);
        }
    }

    //Classe public verificar tabela in-ordem
    public void inOrdem() {
        System.out.println("Percorrendo Em-Ordem:");
        recursaoIn(this.raiz);
    }

    private void recursaoIn(Node no) {
        if (no == null) return;
        recursaoIn(no.getEsquerda());
        if(no.getInformacao1() != null) {
            System.out.print(no.getInformacao1() + " ");
        }
        recursaoIn(no.getMeio());
        if (no.cheio()) {
            System.out.print(no.getInformacao2() + " ");
            recursaoIn(no.getDireita());
        }
    }

    //Classe public remover
    public void remover(Integer valor) {
        if (vazia(raiz)) {
            return;
        }
        recursaoRemover(this.raiz, valor);
        if (raiz != null && raiz.getInformacao1() == null) {
            this.raiz = raiz.getEsquerda();
        }
    }

    private void recursaoRemover(Node no, Integer valor) {
        if (no == null) return;

        int caminho = (valor < no.getInformacao1()) ? 0 : (!no.cheio() || valor <= no.getInformacao2()) ? 1 : 2;
        if (caminho == 1 && (valor.equals(no.getInformacao1()) || (no.cheio() && valor.equals(no.getInformacao2())))) {
            if (no.eFolha()) {
                no.removerValor(valor);
            }
            else {
                Node sucessor = encontrarSucessor(no, valor);
                Integer valorSucessor = sucessor.getInformacao1();
                recursaoRemover(this.raiz, valorSucessor);
                if (valor.equals(no.getInformacao1())) {
                    no.setInformacao1(valorSucessor);
                }
                else {
                    no.setInformacao2(valorSucessor);
                }
            }
        }
        else {
            recursaoRemover(no.getFilho(caminho), valor);
            Underflow(no, caminho);
        }
    }

    private Node encontrarSucessor(Node no, Integer valor) {
        Node sucessorNode = valor.equals(no.getInformacao1()) ? no.getMeio() : no.getDireita();
        while (sucessorNode != null && !sucessorNode.eFolha()) {
            sucessorNode = sucessorNode.getEsquerda();
        }
        return sucessorNode;
    }

    private void Underflow(Node pai, int indiceFilhoVazio) {
        Node filhoVazio = pai.getFilho(indiceFilhoVazio);
        if (filhoVazio != null && !vazia(filhoVazio)) {
            return;
        }

        Node irmaoEsq = (indiceFilhoVazio > 0) ? pai.getFilho(indiceFilhoVazio - 1) : null;
        Node irmaoDir = (indiceFilhoVazio < 2) ? pai.getFilho(indiceFilhoVazio + 1) : null;

        if (irmaoEsq != null && irmaoEsq.cheio()) {
            redistribuirEsquerda(pai, indiceFilhoVazio, irmaoEsq, filhoVazio);
        }
        else if (irmaoDir != null && irmaoDir.cheio()) {
            redistribuirDireita(pai, indiceFilhoVazio, irmaoDir, filhoVazio);
        }
        else if (irmaoEsq != null) {
            fundirEsquerda(pai, indiceFilhoVazio, irmaoEsq);
        }
        else if (irmaoDir != null) {
            fundirDireita(pai, indiceFilhoVazio, irmaoDir);
        }
    }

    private void redistribuirEsquerda(Node p, int i, Node irmao, Node filho) {
        filho.adicionarInformacao(p.getInformacao(i - 1));
        p.setInformacao(i - 1, irmao.getInformacao2());
        irmao.removerValor(irmao.getInformacao2());
    }

    private void redistribuirDireita(Node p, int i, Node irmao, Node filho) {
        filho.adicionarInformacao(p.getInformacao(i));
        p.setInformacao(i, irmao.getInformacao1());
        irmao.removerValor(irmao.getInformacao1());
    }

    private void fundirEsquerda(Node pai, int indiceFilho, Node irmao) {
        irmao.adicionarInformacao(pai.getInformacao(indiceFilho - 1));
        pai.removerValor(pai.getInformacao(indiceFilho - 1));
        if (indiceFilho == 1) {
            pai.setMeio(irmao);
        }
        else {
            pai.setDireita(irmao);
        }
    }

    private void fundirDireita(Node pai, int indiceFilho, Node irmao) {
        Node filho = pai.getFilho(indiceFilho);
        filho.adicionarInformacao(pai.getInformacao(indiceFilho));
        filho.adicionarInformacao(irmao.getInformacao1());
        if(irmao.cheio()) {
            filho.adicionarInformacao(irmao.getInformacao2());
        }
        pai.removerValor(pai.getInformacao(indiceFilho));
        if (indiceFilho == 0) {
            pai.setMeio(filho);
        }
        pai.setDireita(null);
    }

    private boolean vazia(Node no) {
        return no == null;
    }
}