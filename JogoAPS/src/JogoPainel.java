import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JogoPainel extends JPanel implements ActionListener {
    private final int LARGURA = 600;
    private final int ALTURA = 800;

    private final Lixeira lixeira;
    private Lixo lixo;
    private final Timer timer;
    private Image background;

    private EstadoJogo estadoAtual = EstadoJogo.MENU;
    private int pontuacao = 0;
    private int vidas = 3;
    private int velocidadelixo = 4;
    private int opcaoMenu = 0;

    public JogoPainel() {
        setFocusable(true);
        lixeira = new Lixeira(LARGURA);
        lixo = new Lixo(LARGURA, velocidadelixo);

        carregarBackground();

        timer = new Timer(16, this);
        timer.start();

        addKeyListener(new ConstrutorTeclado());
    }


    private void carregarBackground() {
        try {
            File arquivo = new File("imagens/fundo.png");
            if (arquivo.exists()) {
                background = ImageIO.read(arquivo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem de fundo. Usando fundo preto padrão.");
        }
    }

    private void iniciarJogo() {
        pontuacao = 0;
        vidas = 3;

        if (opcaoMenu == 0) velocidadelixo = 4;
        else if (opcaoMenu == 1) velocidadelixo = 6;
        else velocidadelixo = 8;

        lixo = new Lixo(LARGURA, velocidadelixo);
        estadoAtual = EstadoJogo.JOGANDO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Se a imagem carregou com sucesso, desenha ela. Senão, usa o fundo preto.
        if (background != null) {
            g.drawImage(background, 0, 0, LARGURA, ALTURA, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, LARGURA, ALTURA);
        }

        if (estadoAtual == EstadoJogo.MENU) {
            desenharMenu(g);
        } else if (estadoAtual == EstadoJogo.JOGANDO) {
            desenharJogo(g);
        } else if (estadoAtual == EstadoJogo.GAME_OVER) {
            desenharGameOver(g);
        }
    }

    private void desenharMenu(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, LARGURA, ALTURA);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("JOGO DA RECICLAGEM", 110, 150);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Selecione a Dificuldade (Setas + Enter):", 120, 250);

        String[] dificuldades = {"[ Fácil ]", "[ Médio ]", "[ Difícil ]"};
        for (int i = 0; i < dificuldades.length; i++) {
            if (i == opcaoMenu) {
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Arial", Font.BOLD, 24));
            } else {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.PLAIN, 20));
            }
            g.drawString(dificuldades[i], 240, 320 + (i * 40));
        }
    }

    private void desenharJogo(Graphics g) {
        lixeira.desenhar(g);
        lixo.desenhar(g);


        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, LARGURA, 50);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Pontuação: " + pontuacao, 20, 32);
        g.drawString("Vidas: " + vidas, 500, 32);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Setas: Mover | Espaço: Mudar Lixeira", 200, 32);
    }

    private void desenharGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, LARGURA, ALTURA);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 45));
        g.drawString("GAME OVER", 170, 220);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Pontuação Final: " + pontuacao, 200, 300);

        g.setColor(Color.CYAN);
        g.setFont(new Font("Arial", Font.ITALIC, 18));
        g.drawString("Pressione ENTER para voltar ao Menu", 140, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (estadoAtual == EstadoJogo.JOGANDO) {
            lixo.atualizar();

            if (lixeira.colidiuCom(lixo)) {
                if (lixeira.getTipoAtual() == lixo.getTipo()) {
                    pontuacao += 10;
                } else {
                    pontuacao -= 5;
                    vidas--;
                }
                lixo = new Lixo(LARGURA, velocidadelixo);
            }

            if (lixo.getY() > ALTURA) {
                vidas--;
                lixo = new Lixo(LARGURA, velocidadelixo);
            }

            if (vidas <= 0) {
                estadoAtual = EstadoJogo.GAME_OVER;
            }
        }
        repaint();
    }

    private class ConstrutorTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int codigo = e.getKeyCode();

            if (estadoAtual == EstadoJogo.MENU) {
                if (codigo == KeyEvent.VK_UP) {
                    opcaoMenu = (opcaoMenu - 1 + 3) % 3;
                }
                if (codigo == KeyEvent.VK_DOWN) {
                    opcaoMenu = (opcaoMenu + 1) % 3;
                }
                if (codigo == KeyEvent.VK_ENTER) {
                    iniciarJogo();
                }
            }
            else if (estadoAtual == EstadoJogo.JOGANDO) {
                if (codigo == KeyEvent.VK_LEFT) lixeira.mover(-1);
                if (codigo == KeyEvent.VK_RIGHT) lixeira.mover(1);
                if (codigo == KeyEvent.VK_SPACE) lixeira.mudarTipo();
            }
            else if (estadoAtual == EstadoJogo.GAME_OVER) {
                if (codigo == KeyEvent.VK_ENTER) {
                    estadoAtual = EstadoJogo.MENU;
                }
            }
        }
    }
}



