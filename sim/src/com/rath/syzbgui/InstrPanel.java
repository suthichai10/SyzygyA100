
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

import com.rath.syzbbck.SyzInstr;
import com.rath.syzbbck.SyzInternals;
import com.rath.syzbbck.SyzOps;

public class InstrPanel extends JPanel {
  
  private static final long serialVersionUID = 1L;
  
  private final SyzInternals internals;
  private final Dimension size;
  private final int entrySep;
  
  private DefaultListModel<String> instrList;
  private JTextField instrField;
  private int instrIndex;
  
  public InstrPanel(final Dimension size, final SyzInternals si) {
    super();
    this.size = size;
    setSize(getPreferredSize());
    this.internals = si;
    this.entrySep = (int) (this.size.getHeight() * 0.85D);
    setupInstrList();
    
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
  
  private final void setupInstrList() {
    
    // Instructions Label
    final JLabel instrLabel = new JLabel("Instruction Register");
    add(instrLabel);
    
    // Set up list and scroll box
    this.instrList = new DefaultListModel<String>();
    final JList<String> list = new JList<String>(this.instrList);
    
    // Default instructions
    for (short i = 0; i < 256; i++) {
      final byte b = this.internals.getInstr(i);
      this.instrList.add(i,
          toHex(i).toUpperCase() + ":  " + SyzInstr.decode(b) + " (" + SyzOps.getBinaryString(b) + ")");
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
        instrIndex = list.getSelectedIndex();
      }
    };
    list.addListSelectionListener(lsl);
    list.setSelectedIndex(0);
    
    // Instruction entry label
    final JLabel instrEntryLabel = new JLabel("Instruction Entry:");
    add(instrEntryLabel);
    
    // Instruction entry text field
    this.instrField = new JTextField();
    this.instrField.setPreferredSize(new Dimension(compX, 24));
    add(this.instrField, BorderLayout.LINE_END);
    
    // Instruction entry key listener
    final KeyListener instrListener = new KeyListener() {
      
      @Override
      public void keyPressed(KeyEvent evt) {}
      
      @Override
      public void keyReleased(KeyEvent evt) {
        if (instrIndex < 0 || instrIndex > 255) instrIndex = 0;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
          
          final String entry = instrField.getText();
          byte entryByte = 0;
          try {
            entryByte = SyzInstr.encode(entry);
          } catch (NumberFormatException nfe) {
            entryByte = 0;
          }
          
          System.out.println("Encoded \"" + entry + "\" as \"" + SyzOps.getBinaryString(entryByte) + "\"");
          
          internals.setInstr((short) instrIndex, entryByte);
          instrList.setElementAt(
              toHex(instrIndex) + ":  " + SyzInstr.decode(entryByte) + " (" + SyzOps.getBinaryString(entryByte) + ")",
              instrIndex);
          instrField.setText(null);
          list.setSelectedIndex(++instrIndex);
        }
      }
      
      @Override
      public void keyTyped(KeyEvent evt) {}
      
    };
    this.instrField.addKeyListener(instrListener);
  }
  
  /**
   * Converts a number to its hexadecimal representation.
   * 
   * @param n the number to convert.
   * @return a zero-padded hexadecimal number as a String.
   */
  public static final String toHex(int n) {
    final String conv = Integer.toHexString(n).toUpperCase();
    if (conv.length() <= 1) {
      return "0" + conv;
    }
    return conv;
  }
}
