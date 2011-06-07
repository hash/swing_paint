/*
 * PaintView.java
 */
package paint;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import paint.operations.*;
import paint.stuff.*;

/**
 * The application's main frame.
 */
public class PaintView extends FrameView {

    final JFileChooser fc = new JFileChooser();
    private ChooseColor cc = null;
    private File current = null;
    private Flip oFliping = new Flip();
    private Pen tPen = new Pen();
    private Line tLine = new Line();
    private Rectangle tRectangle = new Rectangle();
    private Circle tCircle = new Circle();
    private Rubber tRubber = new Rubber();
    private Fill tFill = new Fill();
    final PaintMain p = PaintMain.getPaint();

    public static void log(String s) {
        System.out.println(s);
    }

    public PaintView(SingleFrameApplication app) {
        super(app);

        initComponents();
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        tRubber.setDelete(p.getBackground());
        p.draw();
        getFrame().addWindowListener(new WindowAdapter() {

            @Override
            public void windowActivated(WindowEvent e) {
                p.updateCanvas();
            }
        });
        ActionListener buttonsListener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText(((JButton) e.getSource()).getToolTipText().toString());
            }
        };
        pencil.addActionListener(buttonsListener);
        line.addActionListener(buttonsListener);
        rectangle.addActionListener(buttonsListener);
        circle.addActionListener(buttonsListener);
        fill.addActionListener(buttonsListener);
        color.addActionListener(buttonsListener);
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = PaintApp.getApplication().getMainFrame();
            aboutBox = new PaintAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        PaintApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        /*
        drawing = new javax.swing.JPanel();
        */drawing = PaintMain.getPaint();
        topToolB = new javax.swing.JToolBar();
        back = new javax.swing.JButton();
        flipV = new javax.swing.JButton();
        flipH = new javax.swing.JButton();
        turn90l = new javax.swing.JButton();
        turn90r = new javax.swing.JButton();
        turn180 = new javax.swing.JButton();
        leftToolB = new javax.swing.JToolBar();
        pencil = new javax.swing.JButton();
        line = new javax.swing.JButton();
        rectangle = new javax.swing.JButton();
        circle = new javax.swing.JButton();
        rubber = new javax.swing.JButton();
        color = new javax.swing.JButton();
        fill = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        drawing.setName("drawing"); // NOI18N

        javax.swing.GroupLayout drawingLayout = new javax.swing.GroupLayout(drawing);
        drawing.setLayout(drawingLayout);
        drawingLayout.setHorizontalGroup(
            drawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        drawingLayout.setVerticalGroup(
            drawingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(drawing);

        topToolB.setFloatable(false);
        topToolB.setRollover(true);
        topToolB.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        topToolB.setMargin(new java.awt.Insets(1, 1, 1, 1));
        topToolB.setName("topToolB"); // NOI18N
        topToolB.setPreferredSize(new java.awt.Dimension(100, 38));

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(paint.PaintApp.class).getContext().getActionMap(PaintView.class, this);
        back.setAction(actionMap.get("back")); // NOI18N
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/back.png"))); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(paint.PaintApp.class).getContext().getResourceMap(PaintView.class);
        back.setToolTipText(resourceMap.getString("back.toolTipText")); // NOI18N
        back.setMaximumSize(new java.awt.Dimension(36, 36));
        back.setMinimumSize(new java.awt.Dimension(36, 36));
        back.setName("back"); // NOI18N
        back.setPreferredSize(new java.awt.Dimension(36, 36));
        topToolB.add(back);

        flipV.setAction(actionMap.get("flipVerically")); // NOI18N
        flipV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/flip_v.png"))); // NOI18N
        flipV.setToolTipText(resourceMap.getString("flipV.toolTipText")); // NOI18N
        flipV.setMaximumSize(new java.awt.Dimension(36, 36));
        flipV.setMinimumSize(new java.awt.Dimension(36, 36));
        flipV.setName("flipV"); // NOI18N
        flipV.setPreferredSize(new java.awt.Dimension(36, 36));
        topToolB.add(flipV);

        flipH.setAction(actionMap.get("flipHorizontally")); // NOI18N
        flipH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/flip_h.png"))); // NOI18N
        flipH.setToolTipText(resourceMap.getString("flipH.toolTipText")); // NOI18N
        flipH.setMaximumSize(new java.awt.Dimension(36, 36));
        flipH.setMinimumSize(new java.awt.Dimension(36, 36));
        flipH.setName("flipH"); // NOI18N
        flipH.setPreferredSize(new java.awt.Dimension(36, 36));
        topToolB.add(flipH);

        turn90l.setAction(actionMap.get("turnLeft90")); // NOI18N
        turn90l.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/90L.png"))); // NOI18N
        turn90l.setToolTipText(resourceMap.getString("turn90l.toolTipText")); // NOI18N
        turn90l.setMaximumSize(new java.awt.Dimension(36, 36));
        turn90l.setMinimumSize(new java.awt.Dimension(36, 36));
        turn90l.setName("turn90l"); // NOI18N
        turn90l.setPreferredSize(new java.awt.Dimension(36, 36));
        topToolB.add(turn90l);

        turn90r.setAction(actionMap.get("turnRight90")); // NOI18N
        turn90r.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/90R.png"))); // NOI18N
        turn90r.setToolTipText(resourceMap.getString("turn90r.toolTipText")); // NOI18N
        turn90r.setMaximumSize(new java.awt.Dimension(36, 36));
        turn90r.setMinimumSize(new java.awt.Dimension(36, 36));
        turn90r.setName("turn90r"); // NOI18N
        turn90r.setPreferredSize(new java.awt.Dimension(36, 36));
        topToolB.add(turn90r);

        turn180.setAction(actionMap.get("turn180deg")); // NOI18N
        turn180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/180.png"))); // NOI18N
        turn180.setToolTipText(resourceMap.getString("turn180.toolTipText")); // NOI18N
        turn180.setMaximumSize(new java.awt.Dimension(36, 36));
        turn180.setMinimumSize(new java.awt.Dimension(36, 36));
        turn180.setName("turn180"); // NOI18N
        turn180.setPreferredSize(new java.awt.Dimension(36, 36));
        topToolB.add(turn180);

        leftToolB.setFloatable(false);
        leftToolB.setOrientation(1);
        leftToolB.setRollover(true);
        leftToolB.setMargin(new java.awt.Insets(1, 1, 1, 1));
        leftToolB.setName("leftToolB"); // NOI18N
        leftToolB.setPreferredSize(new java.awt.Dimension(100, 36));

        pencil.setAction(actionMap.get("setPencilActive")); // NOI18N
        pencil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/pencil.png"))); // NOI18N
        pencil.setToolTipText(resourceMap.getString("pencil.toolTipText")); // NOI18N
        pencil.setMaximumSize(new java.awt.Dimension(36, 36));
        pencil.setMinimumSize(new java.awt.Dimension(36, 36));
        pencil.setName("pencil"); // NOI18N
        pencil.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(pencil);

        line.setAction(actionMap.get("setLineActive")); // NOI18N
        line.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/line.png"))); // NOI18N
        line.setToolTipText(resourceMap.getString("line.toolTipText")); // NOI18N
        line.setMaximumSize(new java.awt.Dimension(36, 36));
        line.setMinimumSize(new java.awt.Dimension(36, 36));
        line.setName("line"); // NOI18N
        line.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(line);

        rectangle.setAction(actionMap.get("setRectangleActive")); // NOI18N
        rectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/rectangle.png"))); // NOI18N
        rectangle.setToolTipText(resourceMap.getString("rectangle.toolTipText")); // NOI18N
        rectangle.setMaximumSize(new java.awt.Dimension(36, 36));
        rectangle.setMinimumSize(new java.awt.Dimension(36, 36));
        rectangle.setName("rectangle"); // NOI18N
        rectangle.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(rectangle);

        circle.setAction(actionMap.get("setCircleActive")); // NOI18N
        circle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/circle.png"))); // NOI18N
        circle.setToolTipText(resourceMap.getString("circle.toolTipText")); // NOI18N
        circle.setMaximumSize(new java.awt.Dimension(36, 36));
        circle.setMinimumSize(new java.awt.Dimension(36, 36));
        circle.setName("circle"); // NOI18N
        circle.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(circle);

        rubber.setAction(actionMap.get("setRubberActive")); // NOI18N
        rubber.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/rubber.png"))); // NOI18N
        rubber.setToolTipText(resourceMap.getString("rubber.toolTipText")); // NOI18N
        rubber.setMaximumSize(new java.awt.Dimension(36, 36));
        rubber.setMinimumSize(new java.awt.Dimension(36, 36));
        rubber.setName("rubber"); // NOI18N
        rubber.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(rubber);

        color.setAction(actionMap.get("chooseColor")); // NOI18N
        color.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/select_color.png"))); // NOI18N
        color.setToolTipText(resourceMap.getString("color.toolTipText")); // NOI18N
        color.setMaximumSize(new java.awt.Dimension(36, 36));
        color.setMinimumSize(new java.awt.Dimension(36, 36));
        color.setName("color"); // NOI18N
        color.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(color);

        fill.setAction(actionMap.get("setFillActive")); // NOI18N
        fill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/paint/resources/icons/fill.png"))); // NOI18N
        fill.setToolTipText(resourceMap.getString("fill.toolTipText")); // NOI18N
        fill.setMaximumSize(new java.awt.Dimension(36, 36));
        fill.setMinimumSize(new java.awt.Dimension(36, 36));
        fill.setName("fill"); // NOI18N
        fill.setPreferredSize(new java.awt.Dimension(36, 36));
        leftToolB.add(fill);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(leftToolB, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE))
            .addComponent(topToolB, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(topToolB, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(leftToolB, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItem1.setAction(actionMap.get("open")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        fileMenu.add(jMenuItem1);

        jMenuItem2.setAction(actionMap.get("save")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        fileMenu.add(jMenuItem2);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 576, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void open() {
        if (fc.showDialog(getFrame(), "Open") == JFileChooser.APPROVE_OPTION) {
            try {
                current = fc.getSelectedFile();
                p.setImage(ImageIO.read(current));
            } catch (IOException ex) {
                Logger.getLogger(PaintView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fc.setSelectedFile(null);
        p.updateCanvas();
    }

    @Action
    public void save() {
        if (current != null) {
            try {
                ImageIO.write(p.getImage(), "bmp", current);
            } catch (IOException ex) {
                Logger.getLogger(PaintView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Action
    public void chooseColor() {
        if (cc == null) {
            JFrame mainFrame = PaintApp.getApplication().getMainFrame();
            cc = new ChooseColor(mainFrame);
            cc.setLocationRelativeTo(mainFrame);
        }
        cc.setActive(p.getColor());
        PaintApp.getApplication().show(cc);
        p.setColor(cc.getSelected());
    }

    @Action
    public void back() {
        p.back();
    }

    @Action
    public void flipVerically() {
        oFliping.setOperation(Flip.FLIP_V);
        p.operation(oFliping);
    }

    @Action
    public void flipHorizontally() {
        oFliping.setOperation(Flip.FLIP_H);
        p.operation(oFliping);
    }

    @Action
    public void turnLeft90() {
        oFliping.setOperation(Flip.FLIP_90CCW);
        p.operation(oFliping);
    }

    @Action
    public void turnRight90() {
        oFliping.setOperation(Flip.FLIP_90CW);
        p.operation(oFliping);
    }

    @Action
    public void turn180deg() {
        oFliping.setOperation(Flip.FLIP_180);
        p.operation(oFliping);
    }

    @Action
    public void setPencilActive() {
        p.setThingToPaint(tPen);
    }

    @Action
    public void setLineActive() {
        p.setThingToPaint(tLine);
    }

    @Action
    public void setRectangleActive() {
        p.setThingToPaint(tRectangle);
    }

    @Action
    public void setCircleActive() {
        p.setThingToPaint(tCircle);
    }

    @Action
    public void setRubberActive() {
        p.setThingToPaint(tRubber);
    }

    @Action
    public void setFillActive() {
        p.setThingForClick(tFill);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton circle;
    private javax.swing.JButton color;
    /*
    private javax.swing.JPanel drawing;
    */private JComponent drawing;
    private javax.swing.JButton fill;
    private javax.swing.JButton flipH;
    private javax.swing.JButton flipV;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar leftToolB;
    private javax.swing.JButton line;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton pencil;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton rectangle;
    private javax.swing.JButton rubber;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JToolBar topToolB;
    private javax.swing.JButton turn180;
    private javax.swing.JButton turn90l;
    private javax.swing.JButton turn90r;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
