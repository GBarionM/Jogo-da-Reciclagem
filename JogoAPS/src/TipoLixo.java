import java.awt.Color;

public enum TipoLixo {
    PLASTICO(Color.RED, "Plástico", "imagens/garrafa.png"),
    PAPEL(Color.BLUE, "Papel", "imagens/jornal.png"),
    VIDRO(Color.GREEN, "Vidro", "imagens/vidro.png"),
    METAL(Color.YELLOW, "Metal", "imagens/lata.png");

    private final Color cor;
    private final String nome;
    private final String caminhoImagem;

    TipoLixo(Color cor, String nome, String caminhoImagem) {
        this.cor = cor;
        this.nome = nome;
        this.caminhoImagem = caminhoImagem;
    }

    public Color getCor() { return cor; }
    public String getNome() { return nome; }
    public String getCaminhoImagem() { return caminhoImagem; }
}


