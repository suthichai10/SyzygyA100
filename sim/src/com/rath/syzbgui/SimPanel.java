
package com.rath.syzbgui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.rath.syzbbck.SyzInternals;

public class SimPanel extends JPanel {
  
  public static final int MARGIN_PANEL = 8;
  
  private static final long serialVersionUID = 1L;
  private static final int MARGIN_BOTTOM = 38;
  
  private final Dimension frameSize;
  private final InstrPanel instrPanel;
  private final ComponentPanel compPanel;
  private final MemoryPanel memPanel;
  
  public SimPanel(final Dimension dim, final SyzInternals si) {
    super();
    this.frameSize = dim;
    
    final Dimension subPanelSize = new Dimension((int) this.frameSize.getWidth() / 3 - MARGIN_PANEL,
        (int) this.frameSize.getHeight() - MARGIN_BOTTOM);
    this.instrPanel = new InstrPanel(subPanelSize, si);
    add(instrPanel, BorderLayout.WEST);
    
    this.compPanel = new ComponentPanel(subPanelSize, si);
    add(compPanel, BorderLayout.CENTER);
    
    this.memPanel = new MemoryPanel(subPanelSize, si);
    add(memPanel, BorderLayout.CENTER);
  }
  
  @Override
  public Dimension getPreferredSize() {
    return this.frameSize;
  }
  
  @Override
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }
  
  @Override
  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
}
