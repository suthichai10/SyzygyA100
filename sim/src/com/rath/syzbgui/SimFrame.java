
package com.rath.syzbgui;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.rath.syzbbck.SyzInternals;

public class SimFrame extends JFrame {
  
  private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
  
  private static final long serialVersionUID = 1L;
  
  public SimFrame(final SyzInternals si) {
    super("Syzygy A100 Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(getPreferredSize());
    setResizable(false);
    setLocationRelativeTo(null);
    
    final SimPanel panel = new SimPanel(DEFAULT_SIZE, si);
    add(panel, null);
    
    setVisible(true);
  }
  
  @Override
  public Dimension getPreferredSize() {
    return DEFAULT_SIZE;
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
