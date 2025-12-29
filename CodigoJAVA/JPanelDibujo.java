/*
DATOS
*Manrique Godinez Daniel Alejandro
*Proyecto compiladores
*Opcion LOGO
*/
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JPanelDibujo extends JPanel{
    
    CurrentState currentState;
    
    // TAMAÑO DEL CURSOR (Ajusta este número si quieres que sea más grande o pequeño)
    private final int TAMAÑO_CURSOR = 20; 

    public JPanelDibujo(){
        currentState = new CurrentState();  
    }
    
    public void setCurrentState(CurrentState currentState){
        this.currentState = currentState;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics2D g2d = (Graphics2D) g;
        // Antialiasing para líneas suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Interpolación para que la imagen redimensionada se vea bien (no borrosa)
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        ArrayList<Linea> lineas = currentState.getLineas();
        
        // --- CÁLCULO DEL CENTRO DINÁMICO ---
        int centroX = this.getWidth() / 2;
        int centroY = this.getHeight() / 2;

        // DIBUJAR LÍNEAS
        for(int i = 0; i < lineas.size(); i++){
            int x0 = centroX + lineas.get(i).getX0();
            int y0 = centroY - lineas.get(i).getY0();
            int x1 = centroX + lineas.get(i).getX1();
            int y1 = centroY - lineas.get(i).getY1();
            
            g2d.setColor(lineas.get(i).getColor());
            g2d.drawLine(x0, y0, x1, y1);
        }
        
        // DIBUJO DEL PINCEL (CURSOR)
        BufferedImage rawImg = null;
        try {
            rawImg = ImageIO.read(getClass().getResourceAsStream("Media/pincel.png"));
        } catch (Exception e){
            // Silencioso si falla
        }
        
        if (rawImg != null) {
            // 1. REESCALAR LA IMAGEN (FIX DEL TAMAÑO GIGANTE)
            // Creamos una versión pequeña de la imagen original antes de rotarla
            Image tmp = rawImg.getScaledInstance(TAMAÑO_CURSOR, TAMAÑO_CURSOR, Image.SCALE_SMOOTH);
            BufferedImage img = new BufferedImage(TAMAÑO_CURSOR, TAMAÑO_CURSOR, BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D gImg = img.createGraphics();
            gImg.drawImage(tmp, 0, 0, null);
            gImg.dispose();

            // 2. CÁLCULO DE POSICIÓN Y ROTACIÓN
            // Ahora usamos 'img' que ya es pequeña (60x60)
            int drawLocationX = centroX + (int)currentState.getX() - (img.getWidth()/2); 
            int drawLocationY = centroY - (int)currentState.getY() - (img.getHeight()/2);

            double rotationRequired = Math.toRadians((-1)*currentState.getAngulo());
            double locationX = img.getWidth() / 2;
            double locationY = img.getHeight() / 2;
            
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

            // 3. DIBUJAR FINAL
            g2d.drawImage(op.filter(img, null), drawLocationX, drawLocationY, null);
        }
    }
}