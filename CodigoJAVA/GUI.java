/*
DATOS
*Mora Guzman Jose Antonio
*Proyecto compiladores
*Opcion LOGO
*Modificación GUI: Botones Redondeados y Estilo Moderno
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class GUI extends JFrame {

    Parser parser;

    // Elementos Interfaz
    JTextArea codeArea;
    JScrollPane codeScroll;
    JPanelDibujo drawArea;
    
    // Usamos nuestra clase personalizada en lugar de JButton normal
    BotonRedondo trazar, limpiar, limpiarDibujo;
    JComboBox<String> combo;
    
    // Colores del Tema
    Color colorFondo = new Color(45, 45, 48);      
    Color colorCodigo = new Color(30, 30, 30);     
    Color colorTexto = new Color(220, 220, 220);   
    Color colorBoton = new Color(0, 122, 204);     
    Color colorBotonHover = new Color(28, 151, 234); 
    Color colorPanelDibujo = Color.WHITE;

    public GUI() {
        super("LOGO - Compilador Gráfico");
        
        this.setLayout(new BorderLayout(10, 10)); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        this.getContentPane().setBackground(colorFondo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        parser = new Parser();
        parser.symbolInst();

        // --- ZONA OESTE: EDITOR ---
        codeArea = new JTextArea(20, 20);
        codeArea.setBackground(colorCodigo);
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        codeArea.setForeground(colorTexto);
        codeArea.setCaretColor(Color.WHITE);
        codeArea.setLineWrap(true);
        codeArea.setWrapStyleWord(true);
        codeArea.setTabSize(4);
        codeArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        codeScroll = new JScrollPane(codeArea);
        codeScroll.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60)));
        codeScroll.setPreferredSize(new Dimension(500, 0)); 
        this.add(codeScroll, BorderLayout.WEST);

        // --- ZONA CENTRO: LIENZO ---
        drawArea = new JPanelDibujo();
        drawArea.setBackground(colorPanelDibujo);
        drawArea.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
        this.add(drawArea, BorderLayout.CENTER);

        // --- ZONA SUR: CONTROLES ---
        JPanel panelControles = new JPanel();
        panelControles.setBackground(colorFondo);
        panelControles.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
        panelControles.setPreferredSize(new Dimension(0, 80)); 

        // COMBO BOX
        combo = new JComboBox<>();
        combo.addItem("SELECCIONAR FIGURA PREDEFINIDA");
        combo.addItem("ESPIRÓGRAFO 1");
        combo.addItem("ESPIRÓGRAFO 2");
        combo.addItem("POLÍGONOS");
        combo.addItem("ESTRELLA DE DAVID");
        combo.addItem("PENTAGRAMA");
        combo.addItem("ESPIRAL CUADRADA");
        combo.addItem("HILBERT");
        combo.addItem("KOCH");
        combo.addItem("ÁRBOL");
        combo.addItem("REGLA");
        
        combo.setPreferredSize(new Dimension(300, 40)); 
        combo.setBackground(Color.WHITE);
        combo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        combo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                int sel = combo.getSelectedIndex();
                switch(sel){
                    case 0: resetear(); break;
                    case 1: cargarCodigo("procedure espirografo(){\n	for(i=0;i<150;i=i+1){\n         COLOR[450, i*320, i*510];\n         AVANZAR[i*4];\n         GIRAR[145];\n     }\n}\nBAJAR[100];\nespirografo();"); break;
                    case 2: cargarCodigo("procedure cuadrado(){\n	for(i=0;i<4;i=i+1){\n         AVANZAR[150];\n         GIRAR[90];\n     }\n}\n\nprocedure espirografo2(){\n	for(j=0;j<24;j=j+1){\n         COLOR[j*120, j*480, j*60];\n         cuadrado();\n         GIRAR[15];\n     }\n}\nBAJAR[50];\nespirografo2();"); break;
                    case 3: cargarCodigo("procedure poligonos()\n{\nAVANZAR[-100];\nGIRAR[90];\nAVANZAR[-200];\nGIRAR[-90];\n\nfor(i=0;i<3;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[120];\n}\n\nfor(i=0;i<4;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[90];\n}\n\nfor(i=0;i<5;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[72];\n}\nfor(i=0;i<6;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[60];\n}\nfor(i=0;i<7;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[51];\n}\nfor(i=0;i<8;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[45];\n}\nfor(i=0;i<9;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[40];\n}\nfor(i=0;i<10;i=i+1){\n   	COLOR[i*13, i*26, i*51];   	\n	AVANZAR[100];\n\nGIRAR[36];\n}\n}\nAVANZAR[55]; \npoligonos();"); break;
                    case 4: cargarCodigo("procedure estrella()\n{\n\nfor(i=0;i<6;i=i+1){\n   	COLOR[255,0,0];\n	AVANZAR[150];\n\nGIRAR[60];\n}\nfor(i=0;i<6;i=i+1){\n	AVANZAR[-150];\n	GIRAR[60];\n	AVANZAR[150];\n	GIRAR[-120];\n}\n}\nAVANZAR[-70];\nBAJAR[270];\nestrella();"); break;
                    case 5: cargarCodigo("procedure pentagrama()\n{\n\nGIRAR[108];\nfor(i=0;i<5;i=i+1){\n   	COLOR[255,0,255]; \n	AVANZAR[100];\nGIRAR[72];\n}\nGIRAR[-72];\nfor(i=0;i<5;i=i+1){\n	AVANZAR[160];\n	GIRAR[144];\n	AVANZAR[160];\n	GIRAR[-72];\n}\n}\nAVANZAR[100]; \nBAJAR[100]; \npentagrama();"); break;
                    case 6: cargarCodigo("for(i=0;i<80;i=i+1){\n  COLOR[i*18, i*16, i*31];\n  AVANZAR[i*5];\n  GIRAR[90];\n}"); break;
                    case 7: cargarCodigo("procedure hilbert(){\n	for(i=0; i<$1; i=i+1){\n	COLOR[50,205,50];\n	GIRAR[$2];\n	hilbert($1-1,-$2);\n	AVANZAR[15];\n	GIRAR[-$2];\n	hilbert($1-1,$2);\n	AVANZAR[15];\n	hilbert($1-1,$2);\n	GIRAR[-$2];\n	AVANZAR[15];\n	hilbert($1-1,-$2);\n	GIRAR[$2];\n	}\n}\nAVANZAR[-220];\nBAJAR[300];\nhilbert(5,90);"); break;
                    case 8: cargarCodigo("procedure segmento()\n{\nif($1==0){\n	COLOR[30, 144, 255];\n	AVANZAR[$2];\n	\n}\nif($1>=1){\n	segmento($1-1, $2*0.3333);\n	GIRAR[60];\n	segmento($1-1, $2*0.3333);\n	GIRAR[-120];\n	segmento($1-1, $2*0.3333);\n	GIRAR[60];\n	segmento($1-1, $2*0.3333);\n}\n}\n\nprocedure koch(){\n     AVANZAR[-250];\n     GIRAR[90];\n     AVANZAR[-150];\n     GIRAR[-90];\n     GIRAR[60];\n     segmento($1, 500);\n     GIRAR[-120];\n     segmento($1, 500);\n     GIRAR[-120];\n     segmento($1, 500);\n}\nBAJAR[80];\nkoch(4);"); break;
                    case 9: cargarCodigo("procedure arbol(){\n if($1>4){\n     AVANZAR[$1];\n     GIRAR[20];\n     COLOR[67, 101, 4];\n     arbol($1-4);\n     GIRAR[320];\n     COLOR[154, 205, 50];\n     arbol($1-4);\n     GIRAR[20];\n     COLOR[154, 205, 50];\n     AVANZAR[(-1)*($1)];\n}\n}\n\nGIRAR[90];\nAVANZAR[-200];\narbol(54);"); break;
                    case 10: cargarCodigo("procedure regla(){\n\nif($2>0){\n	regla($1*0.5, $2-1);\n	AVANZAR[$1*0.5];\n	GIRAR[90];\n	AVANZAR[$2*40];\n	AVANZAR[-$2*40];\n	GIRAR[-90];\n	regla($1*0.5, $2-1);\n	AVANZAR[-$1*0.5];\n}\n}\nAVANZAR[-250];\nBAJAR[100];\nCOLOR[0, 128, 128];\nregla(520,6);"); break;
                }
            }
        });
        panelControles.add(combo);
  
        // 1. Boton EJECUTAR (Redondeado)
        trazar = new BotonRedondo(" EJECUTAR CÓDIGO ");
        colorearBoton(trazar, colorBoton);
        trazar.setPreferredSize(new Dimension(180, 40));
        trazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                parser.limpiar();
                if (parser.compilar(codeArea.getText())) {
                    CurrentState estadoEnVivo = parser.getCurrentState();
                    drawArea.setCurrentState(estadoEnVivo);
                    
                    Timer timerRepintado = new Timer(20, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            drawArea.repaint(); 
                        }
                    });
                    timerRepintado.start();

                    new Thread(() -> {
                        parser.ejecutar(); 
                        timerRepintado.stop(); 
                        drawArea.repaint(); 
                    }).start();
                } else {
                    resetear();
                }
            }
        });
        panelControles.add(trazar);

        // 2. Boton LIMPIAR CÓDIGO (Redondeado)
        limpiar = new BotonRedondo("BORRAR EDITOR");
        colorearBoton(limpiar, new Color(200, 60, 60)); 
        limpiar.setPreferredSize(new Dimension(150, 40));
        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                codeArea.setText(null);
            }
        });
        panelControles.add(limpiar);
        
        // 3. Boton LIMPIAR LIENZO (Redondeado)
        limpiarDibujo = new BotonRedondo("LIMPIAR LIENZO");
        colorearBoton(limpiarDibujo, new Color(255, 140, 0));
        limpiarDibujo.setPreferredSize(new Dimension(150, 40));
        limpiarDibujo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
               resetear();
            }
        });
        panelControles.add(limpiarDibujo);
        
        this.add(panelControles, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    // --- MÉTODOS AUXILIARES ---

    private void resetear(){
        parser = new Parser();
        parser.symbolInst();
        drawArea.setCurrentState(parser.getCurrentState());
        drawArea.repaint();
    }

    private void cargarCodigo(String codigo){
        resetear();
        codeArea.setText(codigo);
    }

    // Configura los colores y eventos para el botón redondeado
    private void colorearBoton(JButton btn, Color colorBase) {
        btn.setBackground(colorBase);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                if(btn.getText().equals(" EJECUTAR CÓDIGO ")){
                     btn.setBackground(colorBotonHover);
                } else {
                    btn.setBackground(colorBase.darker());
                }
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(colorBase);
            }
        });
    }

    // --- CLASE INTERNA PARA BOTONES REDONDOS ---
    class BotonRedondo extends JButton {
        private int radio = 30; // Controla qué tan redondos son

        public BotonRedondo(String texto) {
            super(texto);
            setContentAreaFilled(false); // Importante: quita el fondo cuadrado por defecto
            setFocusPainted(false);
            setBorderPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setForeground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            // Esto hace que los bordes se vean suaves y no pixelados
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Dibujamos el fondo redondeado
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
            
            g2.dispose();
            // Pintamos el texto encima
            super.paintComponent(g);
        }
    }
}