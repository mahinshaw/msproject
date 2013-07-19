package GAIL.src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class PrototypePedigreeFamilyView extends JPanel {
	public PrototypePedigreeFamilyView() {
		/*DefaultListModel alleles = new DefaultListModel();
		alleles.addElement("11");
		alleles.addElement("12");
		alleles.addElement("22");

		setLayout(null);

		JList motherList = new JList(alleles);
		JList fatherList = new JList(alleles);
		JList jbList = new JList(alleles);
		JList sisterList = new JList(alleles);

		Dimension mSize = motherList.getPreferredScrollableViewportSize();
		Dimension fSize = fatherList.getPreferredScrollableViewportSize();
		Dimension jSize = jbList.getPreferredScrollableViewportSize();
		Dimension sSize = sisterList.getPreferredScrollableViewportSize();

		motherList.setBounds(75 - mSize.width - 10, 50, mSize.width + 5, mSize.height/3 + 5);
		fatherList.setBounds(240, 50, fSize.width + 5, fSize.height/3 + 5);
		jbList.setBounds(75 - jSize.width - 10, 125, jSize.width + 5, jSize.height/3 + 5);
		sisterList.setBounds(240, 125, sSize.width + 5, sSize.height/3 + 5);

		motherList.setSelectedIndex(1);
		fatherList.setSelectedIndex(1);
		sisterList.setSelectedIndex(2);

		motherList.setEnabled(false);
		fatherList.setEnabled(false);
		sisterList.setEnabled(false);

		add(motherList);
		add(fatherList);
		add(jbList);
		add(sisterList);*/

		setBackground(Color.white);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		// Set AA to on, saving the old state to be restored later
		Object rhOldAA = g2d.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(Color.BLACK);

		char[] qm = "?".toCharArray();

		// Mother
		//g2d.fillArc(75, 50, 35, 35, 270, 180);
		g2d.drawArc(75, 50, 35, 35, 0, 360);
		g2d.drawChars(qm, 0, qm.length, 75+14, 50+21);
		char[] mother = "Mother".toCharArray();
		g2d.drawChars(mother, 0, mother.length, 75, 100);

		// Father
		//g2d.fillRect(217, 50, 18, 35);
		g2d.drawRect(200, 50, 35, 35);
		g2d.drawChars(qm, 0, qm.length, 200+14, 50+21);
		char[] father = "Father".toCharArray();
		g2d.drawChars(father, 0, father.length, 200, 100);

		// Line from mother to father
		g2d.drawLine(110, 67, 200, 67);

		// Brother
		g2d.drawRect(75, 125, 35, 35);
		g2d.drawChars(qm, 0, qm.length, 75+14, 125+21);
		char[] brother = "Brother".toCharArray();
		g2d.drawChars(brother, 0, brother.length, 75, 175);

		// JB
		g2d.fillArc(200, 125, 35, 35, 0, 360);
		g2d.drawArc(200, 125, 35, 35, 0, 360);

		// Line(s) from JB to sister and that line to the mother-father line
		g2d.drawLine(92, 115, 217, 115);  // brother-sister line
		g2d.drawLine(92, 125, 92, 115);   // brother to brother-sister line
		g2d.drawLine(217, 125, 217, 115); // sister to brother-sister line
		g2d.drawLine(155, 115, 155, 67); // brother-sister line to mother-father line

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, rhOldAA); // Restore AA state
	}
}
