import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Random;

public class Lixo {
    private int x, y;
    private final int tamanho = 40;
    private final int velocidade;
    private final TipoLixo tipo;
    private Image sprite;

    public Lixo(int larguraJanela, int velocidade) {
        Random random = new Random();
        this.x = random.nextInt(larguraJanela - tamanho);
        this.y = 0;
        this.velocidade = velocidade;
        this.tipo = TipoLixo.values()[random.nextInt(TipoLixo.values().length)];
        carregarSprite();
    }

    private void carregarSprite() {
        try {
            // Carrega a imagem
            File arquivo = new File(tipo.getCaminhoImagem());
            if (arquivo.exists()) {
                sprite = ImageIO.read(arquivo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar sprite do lixo: " + tipo.getNome());
        }
    }

    public void atualizar() {
        this.y += velocidade;
    }

    public void desenhar(Graphics g) {
        if (sprite != null) {
            // Desenha a imagem redimensionada
            g.drawImage(sprite, x, y, tamanho, tamanho, null);
        } else {
            // Desenha um fallback
            g.setColor(tipo.getCor());
            g.fillOval(x, y, tamanho, tamanho);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamanho() { return tamanho; }
    public TipoLixo getTipo() { return tipo; }
}
