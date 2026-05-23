import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Jogo da Reciclagem");
        JogoPainel painel = new JogoPainel();

        janela.add(painel);
        janela.setSize(600, 800);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }
}
