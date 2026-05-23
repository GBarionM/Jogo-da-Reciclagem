import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Lixeira {
    private int x;
    private final int y = 680;
    private final int largura = 90;
    private final int altura = 80;
    private final int velocidade = 25;
    private final int larguraJanela;
    private int indiceTipo = 0;
    private Image spriteLixeira;

    public Lixeira(int larguraJanela) {
        this.larguraJanela = larguraJanela;
        this.x = (larguraJanela - largura) / 2;
        carregarSprite();
    }

    private void carregarSprite() {
        try {
            File arquivo = new File("imagens/lixeira.png");
            if (arquivo.exists()) {
                spriteLixeira = ImageIO.read(arquivo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar sprite da lixeira.");
        }
    }

    public void mover(int direcao) {
        x += direcao * velocidade;
        if (x < 0) x = 0;
        if (x > larguraJanela - largura) x = larguraJanela - largura;
    }

    public void mudarTipo() {
        indiceTipo = (indiceTipo + 1) % TipoLixo.values().length;
    }

    public void desenhar(Graphics g) {
        TipoLixo tipoAtual = getTipoAtual();

        // Desenha uma aura luminosa colorida atrás da lixeira para indicar o tipo ativo
        g.setColor(tipoAtual.getCor());
        g.fillRoundRect(x - 5, y - 5, largura + 10, altura + 10, 15, 15);

        if (spriteLixeira != null) {
            g.drawImage(spriteLixeira, x, y, largura, altura, null);
        } else {
            // Caixa sólida como alternativa de erro
            g.fillRect(x, y, largura, altura);
        }

        // Exibe o texto indicador acima do objeto
        g.setColor(Color.WHITE);
        g.drawString(tipoAtual.getNome().toUpperCase(), x + 15, y - 12);
    }

    public TipoLixo getTipoAtual() {
        return TipoLixo.values()[indiceTipo];
    }

    public boolean colidiuCom(Lixo lixo) {
        return lixo.getY() + lixo.getTamanho() >= this.y &&
                lixo.getX() + lixo.getTamanho() >= this.x &&
                lixo.getX() <= this.x + this.largura;
    }
}
