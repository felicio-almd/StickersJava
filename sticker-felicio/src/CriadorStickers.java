import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.TextLayout;

import javax.imageio.ImageIO;

public class CriadorStickers {


    void cria(InputStream inputStream, String nomeArquivo) throws Exception{

            // lendo a imagem
            
            BufferedImage imagemOriginal = ImageIO.read(inputStream);
            
            // cria nova imagem em memoria com transparencia e tamanho novo            
            int largura = imagemOriginal.getWidth();
            int altura = imagemOriginal.getHeight();
            int novaAltura = altura + 150;
            BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);
                        
            // copiar a imagem original pra novo imagem (em memoria)            
            Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
            graphics.drawImage(imagemOriginal, 0, 0, null);

            // configurar a fonte          
            var fonte = new Font("Impact", Font.BOLD, 72);
            graphics.setColor(Color.RED);
            graphics.setFont(fonte);

            // escrever uma frase na nova imagem            
            String texto = "DAORA";
            FontMetrics fontMetrics = graphics.getFontMetrics();
            Rectangle2D Retangulo = fontMetrics.getStringBounds(texto, graphics);
            int larguraTexto = (int) Retangulo.getWidth();
            int X = (largura - larguraTexto) / 2 ;
            int Y = novaAltura - 50;
            graphics.drawString(texto, X, Y);

            FontRenderContext fontRenderContext = graphics.getFontRenderContext();
            var textLayout = new TextLayout(texto, fonte, fontRenderContext);

            // fazendo outline
            Shape outline = textLayout.getOutline(null);
            AffineTransform transform = graphics.getTransform();
            transform.translate(X, Y);
            graphics.setTransform(transform);

            var outlineStroke = new BasicStroke(largura * 0.004f);
            graphics.setStroke(outlineStroke);

            graphics.setColor(Color.BLACK);
            graphics.draw(outline);
            graphics.setClip(outline);


            // escrever a nova imagem em arquivo            
            ImageIO.write(novaImagem, "png", new File(nomeArquivo));


    }

}
