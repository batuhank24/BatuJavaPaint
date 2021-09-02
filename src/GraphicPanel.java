
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GraphicPanel extends JPanel {
    
    // Şekillerin oluşturulması için yazacağım metodlarda Graphics2D sınıfından elemanlar, işlemler kullanacağım. 
    // Pointler ise şekilleri oluştururken, tanımlarken kullanacağım noktalar.
    Graphics2D g2;
    public Point startdrag, enddrag, midPoint;
    // Yapacağım renk özelliklerinin standartlarını Color sınıfından türetilmiş farklı nesnelere ayarladım.
    // Standart çizgi kalınlığını ise float olarak tanımladım.
    Color backColor = Color.WHITE;
    Color lineColor = Color.BLACK;
    Color fillColor = Color.YELLOW;
    float cizgiKalinlik = 2.5f;
    
    // Shape class'ından bir nesne oluşturdum, daha sonra yaptığım şekilleri ve detayları üzerine kaydetmek için ArrayList'ler tanımladım.
    Shape shape;
    ArrayList<Shape> sekiller = new ArrayList<Shape>();
    ArrayList<myObject> sekildetay = new ArrayList<myObject>();
    
    // Şekillerimi kontrollerde (switch-case) kullanmak için her birini sabit olarak tanımladım.
    public enum Sekil {
        kalem,
        silgi,
        cizgi,
        dikdortgen,
        rounddikdortgen,
        ucgen,
        daire,
    }
    
    // Çizim tiplerinin sabitleri burada tanımlı, fakat işlevsel hale getiremedim. Yani kodlarını ayarlayamadım.
    public enum SekilType {
        line,
        fill,
        linefill
    }

    // Standart olarak seçili Şekil ve Çizim tipini burada tanımladım, daha sonra zaten başka metotlar ile kontrol edilerek değiştirilebilecek.
    Sekil drawmode = Sekil.cizgi;
    SekilType drawtype = SekilType.linefill;

    // myObject class'ı çizimin özelliklerini barındırıyor.
    class myObject {

        Color linecolor;
        Color fillcolor;
        SekilType sekiltip;
        float cizgikalinlik;
        Sekil sekil;
    }

    public GraphicPanel() {
        // Çizim için kullandığım panel'imin Background color'u 'standart olarak' burada tanımlı, başka metotlar ile değiştirilebilecek.
        setBackground(backColor);

        // Mouse hareketlerimin kontrol etmesi için Listenerlar kullandım sonra içeride başlangıç, güncel , bitiş işlemlerimi tanımladım,
        // mouse bıraktığımda myObject class'ında tanımladığım özellikleri yeni bir p nesnesi oluşturarak
        // default olarak tanımladığım özelliklerle eşitledim daha sonra nesneye ait özelliklerin kontrollerini ve sıfırlamalarını yaptım.
        
        // mouseDragged event'i için ayrı Adapter kullandım. MouseMotionAdapter kullanmamın sebebi;
        // Mouse hareket esnasında olduğu zaman, bu Listener birden fazla kez kullanılacak bu sebeple her harekette güncelleme yapılacak.
        // Çizim yaptığımız ekran üzerinde görüntünün güncel olarak gözükmesi sağlanıyor.
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            // Mouse basılı tutulup çekilirken yapılacak olan tanımlamaları buraya yaptım.
            public void mouseDragged(MouseEvent e) {
                enddrag = e.getPoint();
                repaint();
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            // Mouse'a tıklanıldığında yapılması gerekenleri burada ayarladım;
            // (Mouse'un başlangıç noktasının koordinatlarını çekip önceden tanımladığım Point nesnelere aktardım)
            public void mousePressed(MouseEvent e) {
                startdrag = e.getPoint();
                enddrag = startdrag;
                repaint();
            }

            @Override
            // Mouse bırakıldığında yapılması gerekenleri burada ayarladım;
            // (Mouse ile çizilen şeklin özelliklerini ve metotlarını Shape class'ından oluşturduğum shape nesnesine eşitledim, 
            // daha sonra özellikleri ve şekli başta oluşturduğum ArrayList'lerin içine atadım. Son olarak koordinatları ve şekli sıfırladım.)
            public void mouseReleased(MouseEvent e) {
                myObject p = new myObject();

                p.cizgikalinlik = cizgiKalinlik;
                p.linecolor = lineColor;
                p.fillcolor = fillColor;
                p.sekil = drawmode;
                p.sekiltip = drawtype; // Çizgi, Dolgu, ÇizgiDolgu
                switch (drawmode) {
                    case cizgi:
                        shape = makeLine(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                        break;
                    case dikdortgen:
                        shape = makeRectangle(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                        break;
                    case rounddikdortgen:
                        shape = makeRoundRectangle(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                        break;
                    case ucgen:
                        shape = makeTriangle();
                        break;
                    case daire:
                        shape = makeCircle(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                        break;
                }

                if (shape != null) {
                    sekiller.add(shape);
                    sekildetay.add(p);
                }

                startdrag = null;
                enddrag = null;
                shape = null;

                repaint();
            }
        });

    }
    
    
    @Override
    // paintComponent metodunun içerisinde paint üzerinde yapacağım etkinlikleri kontrol ettim ve yapmak istediğim ekstra şeyleri tanımladım.
    public void paintComponent(Graphics g) {
        // paintComponent(g) ile Graphics g nesnesinin üstünde yaptığım değişiklikleri metodun içindeki kod satırlarında atadım.
        super.paintComponent(g);

        // g2 nesnesini Graphics tipine çevirdim, çünkü kullanacağımız temelde Graphics kütüphanesi.
        g2 = (Graphics2D) g;

        // Ekstralar olması için internetten araştırıp gridler oluşturdum.
        int kenarUzunlugu = 20;

        int satirSayisi = getHeight() / kenarUzunlugu;
        int stdX = kenarUzunlugu;
        for (int i = 0; i < satirSayisi; i++) {
            g.drawLine(0, stdX, getWidth(), stdX);
            stdX = stdX + kenarUzunlugu;
        }

        int sutunSayisi = getWidth() / kenarUzunlugu;
        int stdY = kenarUzunlugu;
        for (int i = 0; i < sutunSayisi; i++) {
            g.drawLine(stdY, 0, stdY, getHeight());
            stdY = stdY + kenarUzunlugu;
        }

        //g2.setStroke(new BasicStroke(2.5f));
        //g2.setColor(lineColor);
        int index = 0;

        // Her bir şekil için ait olduğu SekilDetay list'indeki özelliklerini kontrol eder, sonra elle yazdığım özelliklerini uyguluyor.
        for (Shape s : sekiller) {

            myObject p = sekildetay.get(index);

            g2.setStroke(new BasicStroke(p.cizgikalinlik));
            g2.setColor(p.linecolor);

            if (p.sekiltip != SekilType.fill || p.sekil == Sekil.cizgi) {
                g2.draw(s);
            }

            if (p.sekiltip != SekilType.line) {
                g2.setColor(p.fillcolor);
                g2.fill(s);
            }
            index++;
        }

        // Mouse ile basılı tutmaya başlama esnasından bırakma esnasına kadar geçen sürede, başlangıç ve bitiş noktalarını kontrol eder.
        // Daha sonra Şekile göre gerekli metotları getirir ardından Shape class'ından bir nesneye oluşturduğumuz şekilleri veya çizgiyi atar.
        if (startdrag != null && enddrag != null) {
            Shape r = null;
            switch (drawmode) {
                case cizgi:
                    r = makeLine(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                    break;
                case dikdortgen:
                    r = makeRectangle(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                    break;
                case rounddikdortgen:
                    r = makeRoundRectangle(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                    break;
                case ucgen:
                    r = makeTriangle();
                    break;
                case daire:
                    r = makeCircle(startdrag.x, startdrag.y, enddrag.x, enddrag.y);
                    break;
            }
            if (r != null) {
                g2.draw(r);
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc=" Şekilleri Pointleriyle oluşturduğum metotlar ">
    // Burada şekilleri çizmek için oluşturduğum metotlar var, pointleri parametre olarak aldım ve yukarıdaki kısımda kullanılmasını sağladım.
    // makeTriangle yani üçgen yapmak için kullandığım metodta ekstradan 3. bir nokta kullanmam lazımdı.
    // Global ve instance olarak Point tipinde bir 'midPoint' nesnesi tanımladım. Metodun içerisinde atamalarını yapıp, üçgenin koordinatlarının, noktalarının (dizi) 3. elemanını tanımlamak için kullandım.
    private Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private RoundRectangle2D.Float makeRoundRectangle(int x1, int y1, int x2, int y2) {
        return new RoundRectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2), 50, 50);
    }

    private Ellipse2D.Float makeCircle(int x1, int y1, int x2, int y2) {
        return new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private Polygon makeTriangle() {
        if (startdrag.x > enddrag.x) {
            midPoint = new Point((enddrag.x + (Math.abs(startdrag.x - enddrag.x) / 2)), (int) enddrag.getY());
        } else {
            midPoint = new Point((enddrag.x - (Math.abs(startdrag.x - enddrag.x) / 2)), (int) enddrag.getY());
        }

        int[] xpo = {startdrag.x, enddrag.x, midPoint.x};
        int[] ypo = {startdrag.y, startdrag.y, midPoint.y};

        return new Polygon(xpo, ypo, 3);
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Class içinde kullanılan getter ve setterlar burada tanımlı ">
    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    //</editor-fold>
}
