import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Practica06MendozaReyesAngelEmanuel extends JFrame {

    private JPanel panelEntrada;
    private PanelCuadricula panelCuadricula;
    private BufferedImage buffer;
    private Graphics graPixel;

    public Practica06MendozaReyesAngelEmanuel() {
        setTitle("Practica06MendozaReyesAngelEmanuel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        panelCuadricula = new PanelCuadricula();
        add(panelCuadricula, BorderLayout.CENTER);

        JMenuBar barraMenu = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
        String[] opciones = {"Dibujar Línea", "Dibujar Cuadrado", "Dibujar Rectángulo", "Dibujar Círculo", "Dibujar Óvalo"};
        for (String opcion : opciones) {
            JMenuItem elementoMenu = new JMenuItem(opcion);
            elementoMenu.addActionListener(e -> mostrarCamposEntrada(opcion));
            menu.add(elementoMenu);
        }
        barraMenu.add(menu);
        setJMenuBar(barraMenu);

        panelEntrada = new JPanel(new GridBagLayout());
        add(panelEntrada, BorderLayout.NORTH);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        graPixel = buffer.getGraphics();
        graPixel.setColor(Color.WHITE);
        graPixel.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        dibujarCuadricula(graPixel, buffer.getWidth(), buffer.getHeight());
        panelCuadricula.actualizarBuffer(buffer);
    }

    private void mostrarCamposEntrada(String figura) {
        panelEntrada.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JPanel panelCampos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        switch (figura) {
            case "Dibujar Línea":
                agregarCampoEntrada(panelCampos, "x1");
                agregarCampoEntrada(panelCampos, "y1");
                agregarCampoEntrada(panelCampos, "x2");
                agregarCampoEntrada(panelCampos, "y2");
                break;
            case "Dibujar Cuadrado":
                agregarCampoEntrada(panelCampos, "x");
                agregarCampoEntrada(panelCampos, "y");
                agregarCampoEntrada(panelCampos, "Lado");
                break;
            case "Dibujar Rectángulo":
                agregarCampoEntrada(panelCampos, "x");
                agregarCampoEntrada(panelCampos, "y");
                agregarCampoEntrada(panelCampos, "Ancho");
                agregarCampoEntrada(panelCampos, "Alto");
                break;
            case "Dibujar Círculo":
                agregarCampoEntrada(panelCampos, "x");
                agregarCampoEntrada(panelCampos, "y");
                agregarCampoEntrada(panelCampos, "Radio");
                break;
            case "Dibujar Óvalo":
                agregarCampoEntrada(panelCampos, "x");
                agregarCampoEntrada(panelCampos, "y");
                agregarCampoEntrada(panelCampos, "Radio Mayor");
                agregarCampoEntrada(panelCampos, "Radio Menor");
                break;
        }
        agregarCampoEntrada(panelCampos, "Color");

        JButton botonDibujar = new JButton("Dibujar");
        botonDibujar.addActionListener(e -> {
            dibujarFigura(figura);
            panelEntrada.setVisible(false);
        });
        gbc.gridy++;
        panelEntrada.add(panelCampos, gbc);
        gbc.gridy++;
        panelEntrada.add(botonDibujar, gbc);

        panelEntrada.revalidate();
        panelEntrada.repaint();
        panelEntrada.setVisible(true);
    }

    private void agregarCampoEntrada(JPanel panel, String etiqueta) {
        JLabel etiquetaCampo = new JLabel(etiqueta);
        etiquetaCampo.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField campoTexto = new JTextField(5);
        campoTexto.setName(etiqueta);
        if (!etiqueta.equals("Color")) {
            campoTexto.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                        e.consume(); 
                    }
                }
            });
        }
        panel.add(etiquetaCampo);
        panel.add(campoTexto);
    }

    private void dibujarFigura(String figura) {
        Component[] componentes = panelEntrada.getComponents();
        int x1 = 0, y1 = 0, x2 = 0, y2 = 0, lado = 0, ancho = 0, alto = 0, radio = 0, radioMayor = 0, radioMenor = 0;
        Color color = Color.BLACK;

        for (Component componente : componentes) {
            if (componente instanceof JPanel) {
                for (Component campo : ((JPanel) componente).getComponents()) {
                    if (campo instanceof JTextField) {
                        JTextField campoTexto = (JTextField) campo;
                        String nombre = campoTexto.getName();
                        String texto = campoTexto.getText();
                        if (nombre != null && !texto.isEmpty()) {
                            switch (nombre) {
                                case "x1":
                                    x1 = Integer.parseInt(texto);
                                    break;
                                case "y1":
                                    y1 = Integer.parseInt(texto);
                                    break;
                                case "x2":
                                    x2 = Integer.parseInt(texto);
                                    break;
                                case "y2":
                                    y2 = Integer.parseInt(texto);
                                    break;
                                case "x":
                                    x1 = Integer.parseInt(texto);
                                    break;
                                case "y":
                                    y1 = Integer.parseInt(texto);
                                    break;
                                case "Lado":
                                    lado = Integer.parseInt(texto);
                                    break;
                                case "Ancho":
                                    ancho = Integer.parseInt(texto);
                                    break;
                                case "Alto":
                                    alto = Integer.parseInt(texto);
                                    break;
                                case "Radio":
                                    radio = Integer.parseInt(texto);
                                    break;
                                case "Radio Mayor":
                                    radioMayor = Integer.parseInt(texto);
                                    break;
                                case "Radio Menor":
                                    radioMenor = Integer.parseInt(texto);
                                    break;
                                case "Color":
                                    color = obtenerColorDesdeString(texto);
                                    break;
                            }
                        }
                    }
                }
            }
        }

        switch (figura) {
            case "Dibujar Línea":
                dibujarLinea(x1, y1, x2, y2, color);
                break;
            case "Dibujar Cuadrado":
                dibujarRectangulo(x1, y1, x1 + lado, y1 + lado, color);
                break;
            case "Dibujar Rectángulo":
                dibujarRectangulo(x1, y1, x1 + ancho, y1 + alto, color);
                break;
            case "Dibujar Círculo":
                dibujarCirculo(x1, y1, radio, color);
                break;
            case "Dibujar Óvalo":
                dibujarOvalo(x1, y1, radioMayor, radioMenor, color);
                break;
        }
        panelCuadricula.actualizarBuffer(buffer);
    }

    private Color obtenerColorDesdeString(String colorStr) {
        switch (colorStr.toLowerCase()) {
            case "rojo":
                return Color.RED;
            case "verde":
                return Color.GREEN;
            case "amarillo":
                return Color.YELLOW;
            case "azul":
                return Color.BLUE;
            case "naranja":
                return Color.ORANGE;
            default:
                JOptionPane.showMessageDialog(this, "Color inválido. Usando color negro por defecto.");
                return Color.BLACK;
        }
    }

    private void putPixel(int x, int y, Color c) {
        if (x >= 0 && x < buffer.getWidth() && y >= 0 && y < buffer.getHeight()) {
            buffer.setRGB(x, y, c.getRGB());
        }
    }

    private void dibujarLinea(int x1, int y1, int x2, int y2, Color color) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            putPixel(x1, y1, color);
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    private void dibujarRectangulo(int x1, int y1, int x2, int y2, Color color) {
        for (int x = x1; x <= x2; x++) {
            putPixel(x, y1, color);
            putPixel(x, y2, color);
        }
        for (int y = y1; y <= y2; y++) {
            putPixel(x1, y, color);
            putPixel(x2, y, color);
        }
    }

    private void dibujarCirculo(int x0, int y0, int radio, Color color) {
        int x = radio;
        int y = 0;
        int err = 0;

        while (x >= y) {
            putPixel(x0 + x, y0 + y, color);
            putPixel(x0 + y, y0 + x, color);
            putPixel(x0 - y, y0 + x, color);
            putPixel(x0 - x, y0 + y, color);
            putPixel(x0 - x, y0 - y, color);
            putPixel(x0 - y, y0 - x, color);
            putPixel(x0 + y, y0 - x, color);
            putPixel(x0 + x, y0 - y, color);

            if (err <= 0) {
                y += 1;
                err += 2 * y + 1;
            }
            if (err > 0) {
                x -= 1;
                err -= 2 * x + 1;
            }
        }
    }

    private void dibujarOvalo(int x0, int y0, int radioX, int radioY, Color color) {
        int x = radioX;
        int y = 0;
        int err = 0;
        int dosCuadradoA = 2 * radioX * radioX;
        int dosCuadradoB = 2 * radioY * radioY;
        int cambioX = radioY * radioY * (1 - 2 * radioX);
        int cambioY = radioX * radioX;
        int errorElipse = 0;
        int detencionX = dosCuadradoB * radioX;
        int detencionY = 0;

        while (detencionX >= detencionY) {
            putPixel(x0 + x, y0 + y, color);
            putPixel(x0 - x, y0 + y, color);
            putPixel(x0 - x, y0 - y, color);
            putPixel(x0 + x, y0 - y, color);
            y++;
            detencionY += dosCuadradoA;
            errorElipse += cambioY;
            cambioY += dosCuadradoA;
            if ((2 * errorElipse + cambioX) > 0) {
                x--;
                detencionX -= dosCuadradoB;
                errorElipse += cambioX;
                cambioX += dosCuadradoB;
            }
        }

        x = 0;
        y = radioY;
        cambioX = radioY * radioY;
        cambioY = radioX * radioX * (1 - 2 * radioY);
        errorElipse = 0;
        detencionX = 0;
        detencionY = dosCuadradoA * radioY;

        while (detencionX <= detencionY) {
            putPixel(x0 + x, y0 + y, color);
            putPixel(x0 - x, y0 + y, color);
            putPixel(x0 - x, y0 - y, color);
            putPixel(x0 + x, y0 - y, color);
            x++;
            detencionX += dosCuadradoB;
            errorElipse += cambioX;
            cambioX += dosCuadradoB;
            if ((2 * errorElipse + cambioY) > 0) {
                y--;
                detencionY -= dosCuadradoA;
                errorElipse += cambioY;
                cambioY += dosCuadradoA;
            }
        }
    }

    private void dibujarCuadricula(Graphics g, int ancho, int alto) {
        g.setColor(Color.LIGHT_GRAY);
        int tamanoCuadricula = 20;
        for (int i = 0; i < ancho; i += tamanoCuadricula) {
            g.drawLine(i, 0, i, alto);
        }
        for (int i = 0; i < alto; i += tamanoCuadricula) {
            g.drawLine(0, i, ancho, i);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Practica06MendozaReyesAngelEmanuel ventana = new Practica06MendozaReyesAngelEmanuel();
            ventana.setVisible(true);
        });
    }
}

class PanelCuadricula extends JPanel {
    private BufferedImage buffer;

    public PanelCuadricula() {
        buffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffer.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
        dibujarCuadricula(g, buffer.getWidth(), buffer.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }

    public void actualizarBuffer(BufferedImage nuevoBuffer) {
        this.buffer = nuevoBuffer;
        repaint();
    }

    private void dibujarCuadricula(Graphics g, int ancho, int alto) {
        g.setColor(Color.LIGHT_GRAY);
        int tamanoCuadricula = 20; 
        for (int i = 0; i < ancho; i += tamanoCuadricula) {
            g.drawLine(i, 0, i, alto);
        }
        for (int i = 0; i < alto; i += tamanoCuadricula) {
            g.drawLine(0, i, ancho, i);
        }
    }
}