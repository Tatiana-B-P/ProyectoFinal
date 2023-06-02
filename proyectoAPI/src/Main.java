import presentacion.BuscadorPaises;
import presentacion.logSistema;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        JFrame frameLog = new JFrame();
        logSistema logSistema = new logSistema();
        frameLog.setContentPane(logSistema.getComponentPane());
        frameLog.setBounds(100,100, 350, 300);
        frameLog.setTitle("Log del sistema");
        frameLog.setResizable(false);
        frameLog.setVisible(true);
        Thread.sleep(1000);
        JFrame frameArchivo = new JFrame();
        frameArchivo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BuscadorPaises buscadorPaises = new BuscadorPaises();
        frameArchivo.setContentPane(buscadorPaises.getComponentPane());
        frameArchivo.setBounds(100,100, 350, 400);
        frameArchivo.setTitle("Generador de archivos");
        frameArchivo.setResizable(false);
        frameArchivo.setVisible(true);
    }
}