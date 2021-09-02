
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class FrmGraphic extends javax.swing.JFrame implements ActionListener {

    // FrmGraphic içerisinde yeni Frame nesnesi tanımladım.
    JFrame frame = new JFrame("Batuhan Paint");

    // <editor-fold defaultstate="collapsed" desc=" Bütün Menü Nesnelerim burada ">
    // Kullanacağım menünün elemanlarını nesne biçiminde tanımladım, new yapıp uğraşmamak için, hem de anlaşılır olması için.
    JMenuBar menubar = new JMenuBar();

    JMenu dosya = new JMenu("Dosya");
    JMenu sekiller = new JMenu("Şekiller");
    JMenu renkler = new JMenu("Renk Seçimi");
    JMenu pencere = new JMenu("Pencere");

    JMenuItem dosyayeni = new JMenuItem("Yeni Dosya");
    JMenuItem dosyacikis = new JMenuItem("Dosya Çıkış");
    JMenuItem sekillercizgi = new JMenuItem("Çizgi");
    JMenuItem sekillerdikdort = new JMenuItem("Dikdörtgen");
    JMenuItem sekillerdikdortround = new JMenuItem("Round Dikdörtgen");
    JMenuItem sekillerdaire = new JMenuItem("Daire");
    JMenuItem sekillerucgen = new JMenuItem("Üçgen");

    JMenuItem renkcizgi = new JMenuItem("Çizgi Rengi");
    JMenuItem renkdolgu = new JMenuItem("Dolgu Rengi");
    JMenuItem renkarka = new JMenuItem("Arkaplan Rengi");

    JMenuItem pencerebuy = new JMenuItem("Pencere Büyült");
    JMenuItem pencerekuc = new JMenuItem("Pencere Küçült");
    // </editor-fold>

    // Aşağıda instance olarak kullanacağım nesneleri tanımladım.
    GraphicPanel gp;
    Dimension ekranboyut;

    // Renk seçiminin sabitlerini tanımladım.
    enum colorType {
        cizgi,
        dolgu,
        arkaplan
    }

    // clrType nesnesinde, standartı olarak Çizgi tanımladım.
    colorType clrType = colorType.cizgi;

    public FrmGraphic() {
        // FrmGraphic, yani herşeyi barındıran ana frame'in boyutunu değiştiriyorum. 
        // Super ile ana classtaki (Compopent) verilere, metodlara ulaşıp değerlerini değiştirebiliyorum. 
        ekranboyut = new Dimension(1020, 750);
        super.setPreferredSize(ekranboyut);

        initComponents();

        // GraphicPanel nesnesinin kendisini ve birkaç özellikle tanımlayıp, Çizim Panelime ekliyorum.
        // Daha sonra Bilgisayar ekranımızın 4 yandan tam ortasına konumlandırıyorum.
        gp = new GraphicPanel();
        gp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gp.setBounds(5, 5, 760, 680);

        pnl_PaintCizimEkranı.add(gp);
        setLocationRelativeTo(null);

        //<editor-fold defaultstate="collapsed" desc=" Menümü tasarladığım kod kısmı ">
        //--- Menümü manuel olarak oluşturuyorum.
        menubar.add(dosya);
        menubar.add(sekiller);
        menubar.add(renkler);
        menubar.add(pencere);

        dosya.add(dosyayeni);
        dosya.add(dosyacikis);
        sekiller.add(sekillercizgi);
        sekiller.add(sekillerdikdort);
        sekiller.add(sekillerdikdortround);
        sekiller.add(sekillerdaire);
        sekiller.add(sekillerucgen);
        renkler.add(renkcizgi);
        renkler.add(renkdolgu);
        renkler.add(renkarka);
        pencere.add(pencerebuy);
        pencere.add(pencerekuc);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc=" Dosya MenuItemlarının Listenerları ">
        
        // İşlevlerini kazanmaları bütün Menü Item'lara ActionListener ekliyorum.
        
        dosyacikis.addActionListener(new ActionListener() {
            @Override
            // Event sayesinde çıkış ekledim.
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        dosyayeni.addActionListener(new ActionListener() {
            @Override
            // Event sayesinde yeni şeyler ekledim. İçeride açıkladım.
            public void actionPerformed(ActionEvent e) {
                // Önceden oluşturduğumuz Graphic panel nesnesini, çizdiğimiz paneli, kaldırıp aynı standartlarda yenisini ekledim.
                pnl_PaintCizimEkranı.remove(gp);

                gp = new GraphicPanel();

                btn_LineColor.setBackground(Color.BLACK);
                btn_FillColor.setBackground(Color.YELLOW);
                btn_BackgroundColor.setBackground(Color.WHITE);

                // Yeni açacak paint'te seçili şekli (aynı standartlarda) Çizgi yapıp, etrafında border koyarak gösterdim.
                setButtonBorder(pnl_Sekiller, btn_Line);
                String cmd = e.getActionCommand();
                switch (cmd) {
                    case "cizgi":
                        gp.drawmode = GraphicPanel.Sekil.cizgi;
                        break;
                    default:
                        gp.drawmode = GraphicPanel.Sekil.cizgi;
                        break;
                }

                // Yeni açacak paint'te kalınlığı (aynı standartlarda) en düşük seviyeye getirdim.
                rdbtn_kalinlik_1.setSelected(true);

                // Yeni Graphic panelin özelliklerini tanımladım.
                gp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                gp.setBounds(5, 5, 760, 680);

                // Çizim panelimize oluşturduğum Graphic paneli içine ekledim.
                pnl_PaintCizimEkranı.add(gp);

                repaint();
            }
        });

        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc=" Şekiller MenuItemlarının Listenerları ">
        
        // Çizgi ve diğer şekiller seçildiğinde DrawMode yani çizgi şeklinin değiştirdim. Daha sonra paneldeki butonların kenarlarını ayarladım.
        
        sekillercizgi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.drawmode = GraphicPanel.Sekil.cizgi;
                setButtonBorder(pnl_tool, btn_Line);
                setBorderButtons(e);
                
            }
        });

        sekillerdikdort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.drawmode = GraphicPanel.Sekil.dikdortgen;
                setButtonBorder(pnl_tool, btn_Rectangle);
                setBorderButtons(e);
            }
        });

        sekillerdikdortround.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.drawmode = GraphicPanel.Sekil.rounddikdortgen;
                setButtonBorder(pnl_tool, btn_RoundDikdortgen);
                setBorderButtons(e);
            }
        });

        sekillerdaire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.drawmode = GraphicPanel.Sekil.daire;
                setButtonBorder(pnl_tool, btn_Daire);
                setBorderButtons(e);
            }
        });

        sekillerucgen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gp.drawmode = GraphicPanel.Sekil.ucgen;
                setButtonBorder(pnl_tool, btn_Ucgen);
                setBorderButtons(e);
            }
        });
        //</editor-fold>    
        
        //<editor-fold defaultstate="collapsed" desc=" Renkler MenuItemlarının Listenerları ">
        renkcizgi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Çizgi için Renk Seçin", Color.GRAY);
                btn_LineColor.setBackground(c);
                gp.setLineColor(c);
            }
        });
        
        renkdolgu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Dolgu için Renk Seçin", Color.GRAY);
                btn_FillColor.setBackground(c);
                gp.setFillColor(c);    
            }
        });
        
        renkarka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Arkaplan için Renk Seçin", Color.GRAY);
                btn_BackgroundColor.setBackground(c);
                gp.setBackground(c);
                gp.setBackColor(c);
            }
        });
        
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc=" Pencere MenuItemlarının Listenerları ">
        pencerebuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(MAXIMIZED_BOTH);
            }
        });
        
        pencerekuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setState(ICONIFIED);
            }
        });
        //</editor-fold>
        
        // Panelde default menü bar'ımı kendi oluşturduğum menubar olarak atıyorum.
        this.setJMenuBar(menubar);

    }

    // ActionListener'ı Frame'e (FrmGraphics) implement ettiğimizde zorunlu olan actionPerformed metodu(event).
    @Override
    public void actionPerformed(ActionEvent e) {    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butonKalinlik_Grup = new javax.swing.ButtonGroup();
        pnl_tool = new javax.swing.JPanel();
        pnl_color = new javax.swing.JPanel();
        btn_White = new javax.swing.JButton();
        btn_Black = new javax.swing.JButton();
        btn_Red = new javax.swing.JButton();
        btn_Yellow = new javax.swing.JButton();
        btn_Green = new javax.swing.JButton();
        btn_Blue = new javax.swing.JButton();
        btn_Cyan = new javax.swing.JButton();
        btn_Pink = new javax.swing.JButton();
        pnl_ColorSet = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_LineColor = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btn_FillColor = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_BackgroundColor = new javax.swing.JButton();
        pnl_Sekiller = new javax.swing.JPanel();
        btn_Line = new javax.swing.JButton();
        btn_Rectangle = new javax.swing.JButton();
        btn_RoundDikdortgen = new javax.swing.JButton();
        btn_Daire = new javax.swing.JButton();
        btn_Ucgen = new javax.swing.JButton();
        pnl_CizimTipi = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        slider_CizimTipi = new javax.swing.JSlider();
        jLabel7 = new javax.swing.JLabel();
        pnl_Kalinlik = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rdbtn_kalinlik_1 = new javax.swing.JRadioButton();
        rdbtn_kalinlik_2 = new javax.swing.JRadioButton();
        rdbtn_kalinlik_3 = new javax.swing.JRadioButton();
        rdbtn_kalinlik_4 = new javax.swing.JRadioButton();
        rdbtn_kalinlik_5 = new javax.swing.JRadioButton();
        pnl_PaintCizimEkranı = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Batuhan Kara Paint");
        setBackground(new java.awt.Color(255, 255, 255));

        pnl_tool.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_tool.setMinimumSize(new java.awt.Dimension(350, 50));
        pnl_tool.setPreferredSize(new java.awt.Dimension(230, 150));

        pnl_color.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnl_color.setPreferredSize(new java.awt.Dimension(150, 110));
        pnl_color.setLayout(new java.awt.GridLayout(4, 4));

        btn_White.setBackground(new java.awt.Color(255, 255, 255));
        btn_White.setText("Beyaz");
        btn_White.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_White);

        btn_Black.setBackground(java.awt.Color.black);
        btn_Black.setForeground(new java.awt.Color(255, 255, 255));
        btn_Black.setText("Siyah");
        btn_Black.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Black);

        btn_Red.setBackground(java.awt.Color.red);
        btn_Red.setText("Kırmızı");
        btn_Red.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Red);

        btn_Yellow.setBackground(java.awt.Color.yellow);
        btn_Yellow.setText("Sarı");
        btn_Yellow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Yellow);

        btn_Green.setBackground(java.awt.Color.green);
        btn_Green.setText("Yeşil");
        btn_Green.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Green);

        btn_Blue.setBackground(java.awt.Color.blue);
        btn_Blue.setText("Mavi");
        btn_Blue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Blue);

        btn_Cyan.setBackground(new java.awt.Color(0, 255, 255));
        btn_Cyan.setText("Cyan");
        btn_Cyan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Cyan);

        btn_Pink.setBackground(new java.awt.Color(255, 105, 180));
        btn_Pink.setText("Pembe");
        btn_Pink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_WhiteActionPerformed(evt);
            }
        });
        pnl_color.add(btn_Pink);

        pnl_tool.add(pnl_color);

        pnl_ColorSet.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Renk Seçim", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(102, 102, 102))); // NOI18N
        pnl_ColorSet.setPreferredSize(new java.awt.Dimension(225, 100));
        pnl_ColorSet.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setText(" Çizgi Rengi");
        pnl_ColorSet.add(jLabel1);

        btn_LineColor.setBackground(java.awt.Color.black);
        btn_LineColor.setActionCommand("cizgirengi");
        btn_LineColor.setPreferredSize(new java.awt.Dimension(200, 100));
        btn_LineColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineColorActionPerformed(evt);
            }
        });
        pnl_ColorSet.add(btn_LineColor);

        jLabel2.setText(" Dolgu Rengi");
        pnl_ColorSet.add(jLabel2);

        btn_FillColor.setBackground(java.awt.Color.yellow);
        btn_FillColor.setActionCommand("dolgurengi");
        btn_FillColor.setPreferredSize(new java.awt.Dimension(200, 100));
        btn_FillColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineColorActionPerformed(evt);
            }
        });
        pnl_ColorSet.add(btn_FillColor);

        jLabel3.setText(" Arkaplan Rengi");
        pnl_ColorSet.add(jLabel3);

        btn_BackgroundColor.setBackground(java.awt.Color.white);
        btn_BackgroundColor.setActionCommand("arkaplanrengi");
        btn_BackgroundColor.setPreferredSize(new java.awt.Dimension(200, 100));
        btn_BackgroundColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineColorActionPerformed(evt);
            }
        });
        pnl_ColorSet.add(btn_BackgroundColor);

        pnl_tool.add(pnl_ColorSet);

        pnl_Sekiller.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnl_Sekiller.setPreferredSize(new java.awt.Dimension(150, 200));
        pnl_Sekiller.setLayout(new java.awt.GridLayout(5, 1));

        btn_Line.setText("Çizgi");
        btn_Line.setActionCommand("cizgi");
        btn_Line.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineActionPerformed(evt);
            }
        });
        pnl_Sekiller.add(btn_Line);

        btn_Rectangle.setText("Dikdörtgen");
        btn_Rectangle.setActionCommand("dikdortgen");
        btn_Rectangle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineActionPerformed(evt);
            }
        });
        pnl_Sekiller.add(btn_Rectangle);

        btn_RoundDikdortgen.setText("Round Dikdörtgen");
        btn_RoundDikdortgen.setActionCommand("rounddikdortgen");
        btn_RoundDikdortgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineActionPerformed(evt);
            }
        });
        pnl_Sekiller.add(btn_RoundDikdortgen);

        btn_Daire.setText("Daire");
        btn_Daire.setActionCommand("daire");
        btn_Daire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineActionPerformed(evt);
            }
        });
        pnl_Sekiller.add(btn_Daire);

        btn_Ucgen.setText("Üçgen");
        btn_Ucgen.setActionCommand("ucgen");
        btn_Ucgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LineActionPerformed(evt);
            }
        });
        pnl_Sekiller.add(btn_Ucgen);

        pnl_tool.add(pnl_Sekiller);

        pnl_CizimTipi.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 153, 153), null, null, new java.awt.Color(153, 153, 153)));
        pnl_CizimTipi.setPreferredSize(new java.awt.Dimension(200, 100));

        jLabel4.setText("- Çizim Tipi -");
        pnl_CizimTipi.add(jLabel4);

        slider_CizimTipi.setMaximum(3);
        slider_CizimTipi.setMinimum(1);
        slider_CizimTipi.setValue(2);
        slider_CizimTipi.setPreferredSize(new java.awt.Dimension(165, 26));
        slider_CizimTipi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                slider_CizimTipiStateChanged(evt);
            }
        });
        pnl_CizimTipi.add(slider_CizimTipi);

        jLabel7.setText("Doldur         Doldur+Çizgi         Çizgi");
        pnl_CizimTipi.add(jLabel7);

        pnl_tool.add(pnl_CizimTipi);

        pnl_Kalinlik.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        pnl_Kalinlik.setPreferredSize(new java.awt.Dimension(200, 75));

        jLabel5.setText("- Çizgi Kalınlığı -");
        pnl_Kalinlik.add(jLabel5);

        butonKalinlik_Grup.add(rdbtn_kalinlik_1);
        rdbtn_kalinlik_1.setSelected(true);
        rdbtn_kalinlik_1.setText("1");
        rdbtn_kalinlik_1.setActionCommand("kalinlik1");
        rdbtn_kalinlik_1.setBorderPainted(true);
        rdbtn_kalinlik_1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        rdbtn_kalinlik_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtn_kalinlik_1ActionPerformed(evt);
            }
        });
        pnl_Kalinlik.add(rdbtn_kalinlik_1);

        butonKalinlik_Grup.add(rdbtn_kalinlik_2);
        rdbtn_kalinlik_2.setText("2");
        rdbtn_kalinlik_2.setActionCommand("kalinlik2");
        rdbtn_kalinlik_2.setBorderPainted(true);
        rdbtn_kalinlik_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtn_kalinlik_1ActionPerformed(evt);
            }
        });
        pnl_Kalinlik.add(rdbtn_kalinlik_2);

        butonKalinlik_Grup.add(rdbtn_kalinlik_3);
        rdbtn_kalinlik_3.setText("3");
        rdbtn_kalinlik_3.setActionCommand("kalinlik3");
        rdbtn_kalinlik_3.setBorderPainted(true);
        rdbtn_kalinlik_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtn_kalinlik_1ActionPerformed(evt);
            }
        });
        pnl_Kalinlik.add(rdbtn_kalinlik_3);

        butonKalinlik_Grup.add(rdbtn_kalinlik_4);
        rdbtn_kalinlik_4.setText("4");
        rdbtn_kalinlik_4.setActionCommand("kalinlik4");
        rdbtn_kalinlik_4.setBorderPainted(true);
        rdbtn_kalinlik_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtn_kalinlik_1ActionPerformed(evt);
            }
        });
        pnl_Kalinlik.add(rdbtn_kalinlik_4);

        butonKalinlik_Grup.add(rdbtn_kalinlik_5);
        rdbtn_kalinlik_5.setText("5");
        rdbtn_kalinlik_5.setActionCommand("kalinlik5");
        rdbtn_kalinlik_5.setBorderPainted(true);
        rdbtn_kalinlik_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbtn_kalinlik_1ActionPerformed(evt);
            }
        });
        pnl_Kalinlik.add(rdbtn_kalinlik_5);

        pnl_tool.add(pnl_Kalinlik);

        getContentPane().add(pnl_tool, java.awt.BorderLayout.LINE_START);

        pnl_PaintCizimEkranı.setLayout(null);
        getContentPane().add(pnl_PaintCizimEkranı, java.awt.BorderLayout.CENTER);
        pnl_PaintCizimEkranı.getAccessibleContext().setAccessibleName("");

        getAccessibleContext().setAccessibleName("BatuhanKaraPaint");
        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //<editor-fold defaultstate="collapsed" desc=" Frame'imdeki nesnelere ait event metotları ve kendi oluşturduğum metotlar ">
    
    // Butonların seçili olmasını sürekli göstereceğim için tekrarlamamak, kısa ve anlaşılır olması adına bir buton kenarlığını yapacak bir metod yazdım.
    private void setBorderButtons(ActionEvent e) {
        
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "cizgi":
                gp.drawmode = GraphicPanel.Sekil.cizgi;
                break;
            case "silgi":
                gp.drawmode = GraphicPanel.Sekil.silgi;
                break;
            case "dikdortgen":
                gp.drawmode = GraphicPanel.Sekil.dikdortgen;
                break;
            case "ucgen":
                gp.drawmode = GraphicPanel.Sekil.ucgen;
                break;
            case "daire":
                gp.drawmode = GraphicPanel.Sekil.daire;
                break;
            case "kalem":
                gp.drawmode = GraphicPanel.Sekil.kalem;
                break;
            case "rounddikdortgen":
                gp.drawmode = GraphicPanel.Sekil.rounddikdortgen;
                break;
        }
    }
    
    // EVENT: Renk seçiminde gözüken çizgi/dolgu/arkaplan renklerinin seçildiğinde event tetiklenmesini sağlar.
    private void btn_LineColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LineColorActionPerformed
        Color tmp = ((JButton) evt.getSource()).getBackground();
        String cmd = evt.getActionCommand();
        switch (cmd) {
            case "cizgirengi":
                clrType = colorType.cizgi;
                gp.setLineColor(tmp);
                break;
            case "dolgurengi":
                clrType = colorType.dolgu;
                gp.setFillColor(tmp);
                break;
            case "arkaplanrengi":
                clrType = colorType.arkaplan;
                gp.setBackground(tmp);
                gp.setBackColor(tmp);
                repaint();
                break;
        }
        gp.repaint();
    }//GEN-LAST:event_btn_LineColorActionPerformed

    // EVENT: Renkler panelinindeki renkler için event tetiklenmesini sağlar.
    private void btn_WhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_WhiteActionPerformed
        Color tmp = ((JButton) evt.getSource()).getBackground();
        switch (clrType) {
            case cizgi:
                btn_LineColor.setBackground(tmp);
                gp.setLineColor(tmp);
                break;
            case dolgu:
                btn_FillColor.setBackground(tmp);
                gp.setFillColor(tmp);
                break;
            case arkaplan:
                btn_BackgroundColor.setBackground(tmp);
                gp.setBackColor(tmp);
                break;
        }
    }//GEN-LAST:event_btn_WhiteActionPerformed

    // EVENT: Şekiller seçildiğinde event'in tetiklenmesini sağlar.
    private void btn_LineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LineActionPerformed
        JButton btn = (JButton) evt.getSource();
        setButtonBorder(pnl_Sekiller, btn);
        String cmd = evt.getActionCommand();
        setBorderButtons(evt);


    }//GEN-LAST:event_btn_LineActionPerformed

    // EVENT: Çizim tipinin durumu değiştiğinde ilişik elementlerin değiştirilmesini sağlar.
    private void slider_CizimTipiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_slider_CizimTipiStateChanged

    }//GEN-LAST:event_slider_CizimTipiStateChanged

    // EVENT: Çizgi kalınlığı radio buttonlar ile değiştirildiğinde kalınlığın değişmesini sağlar, kalınlıktaki bütün Radio
    private void rdbtn_kalinlik_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbtn_kalinlik_1ActionPerformed
        //gp.cizgiKalinlik = 2;
        switch (evt.getActionCommand()) {
            case "kalinlik1":
                gp.cizgiKalinlik = 2f;
                break;
            case "kalinlik2":
                gp.cizgiKalinlik = 4f;
                break;
            case "kalinlik3":
                gp.cizgiKalinlik = 6f;
                break;
            case "kalinlik4":
                gp.cizgiKalinlik = 8f;
                break;
            case "kalinlik5":
                gp.cizgiKalinlik = 10f;
                break;
        }
    }//GEN-LAST:event_rdbtn_kalinlik_1ActionPerformed

    // Metot: Paneldeki butonların seçili olduğunu göstermek için genel bir kenarlık metodu oluşturuyorum.
    void setButtonBorder(JPanel xpanel, JButton xbuton) {
        xbuton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        Component[] components = xpanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton btn = (JButton) component;
                if (btn != xbuton) {
                    btn.setBorder(BorderFactory.createEmptyBorder());
                }
            }
        }
    }

    //</editor-fold>
    
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Tasarımın UI'ı ile alakalı kod kısmı, Nimbus diye bir şey">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGraphic.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrmGraphic f = new FrmGraphic();
                f.setVisible(true);
            }
        });
        */
        
        // Son olarak frame'i oluşturup, gösterdiğim yer.
        FrmGraphic frm = new FrmGraphic();
        frm.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_BackgroundColor;
    private javax.swing.JButton btn_Black;
    private javax.swing.JButton btn_Blue;
    private javax.swing.JButton btn_Cyan;
    private javax.swing.JButton btn_Daire;
    private javax.swing.JButton btn_FillColor;
    private javax.swing.JButton btn_Green;
    private javax.swing.JButton btn_Line;
    private javax.swing.JButton btn_LineColor;
    private javax.swing.JButton btn_Pink;
    private javax.swing.JButton btn_Rectangle;
    private javax.swing.JButton btn_Red;
    private javax.swing.JButton btn_RoundDikdortgen;
    private javax.swing.JButton btn_Ucgen;
    private javax.swing.JButton btn_White;
    private javax.swing.JButton btn_Yellow;
    public javax.swing.ButtonGroup butonKalinlik_Grup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel pnl_CizimTipi;
    private javax.swing.JPanel pnl_ColorSet;
    private javax.swing.JPanel pnl_Kalinlik;
    private javax.swing.JPanel pnl_PaintCizimEkranı;
    private javax.swing.JPanel pnl_Sekiller;
    private javax.swing.JPanel pnl_color;
    private javax.swing.JPanel pnl_tool;
    public javax.swing.JRadioButton rdbtn_kalinlik_1;
    public javax.swing.JRadioButton rdbtn_kalinlik_2;
    public javax.swing.JRadioButton rdbtn_kalinlik_3;
    public javax.swing.JRadioButton rdbtn_kalinlik_4;
    public javax.swing.JRadioButton rdbtn_kalinlik_5;
    private javax.swing.JSlider slider_CizimTipi;
    // End of variables declaration//GEN-END:variables
}
