
package com.rath.syzbgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.rath.syzbbck.SyzInternals;
import com.rath.syzbbck.SyzOps;

public class MemoryPanel extends JPanel {
  
  private static final long serialVersionUID = 1L;
  
  private final SyzInternals internals;
  private final Dimension size;
  private final int entrySep;
  
  private DefaultListModel<String> memList;
  private JTextField memField;
  private int memIndex;
  
  public MemoryPanel(final Dimension size, final SyzInternals si) {
    super();
    this.size = size;
    setSize(getPreferredSize());
    this.internals = si;
    this.entrySep = (int) (this.size.getHeight() * 0.85D);
    setupMemList();
    
  }
  
  @Override
  public Dimension getPreferredSize() {
    return this.size;
  }
  
  @Override
  public Dimension getMinimumSize() {
    return getPreferredSize();
  }
  
  @Override
  public Dimension getMaximumSize() {
    return getPreferredSize();
  }
  
  private final void setupMemList() {
    
    // Instructions Label
    final JLabel memLabel = new JLabel("CPU Memory");
    add(memLabel);
    
    // Set up list and scroll box
    this.memList = new DefaultListModel<String>();
    final JList<String> list = new JList<String>(this.memList);
    
    // Default memory values
    for (short i = 0; i < 256; i++) {
      this.memList.add(i, InstrPanel.toHex(memIndex) + ":  " + SyzOps.getBinaryString((byte) 0) + "  (" + 0 + ")");
    }
    
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    final JScrollPane scrollList = new JScrollPane(list);
    final int compX = (int) (this.size.getWidth() - SimPanel.MARGIN_PANEL);
    final int scrollY = (int) (this.entrySep - SimPanel.MARGIN_PANEL);
    scrollList.setPreferredSize(new Dimension(compX, scrollY));
    add(scrollList);
    
    // Selection listener
    final ListSelectionListener lsl = new ListSelectionListener() {
      
      @Override
      public void valueChanged(ListSelectionEvent evt) {
        final JList<?> list = (JList<?>) evt.getSource();
        memIndex = list.getSelectedIndex();
      }
    };
    list.addListSelectionListener(lsl);
    list.setSelectedIndex(0);
    
    // Memory editing label
    final JLabel instrEntryLabel = new JLabel("New Memory Value:");
    add(instrEntryLabel);
    
    // Memory editing text field
    this.memField = new JTextField();
    this.memField.setPreferredSize(new Dimension(compX, 24));
    add(this.memField, BorderLayout.LINE_END);
    
    // Memory editing key listener
    final KeyListener instrListener = new KeyListener() {
      
      @Override
      public void keyPressed(KeyEvent evt) {}
      
      @Override
      public void keyReleased(KeyEvent evt) {
        if (memIndex < 0 || memIndex > 255) memIndex = 0;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
          
          // Parse byte entry
          byte b = 0;
          try {
            b = Byte.parseByte(memField.getText());
          } catch (NumberFormatException nfe) {
            b = 0;
          }
          internals.setMem((short) memIndex, b);
          memList.setElementAt(InstrPanel.toHex(memIndex) + ":  " + SyzOps.getBinaryString(b) + "  (" + b + ")",
              memIndex);
          memField.setText(null);
          list.setSelectedIndex(++memIndex);
        }
      }
      
      @Override
      public void keyTyped(KeyEvent evt) {}
      
    };
    this.memField.addKeyListener(instrListener);
  }
}
