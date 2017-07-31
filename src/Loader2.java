import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class Loader2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField ySizeField;
	private JTextField minimumField;
	private JTextField maximumField;
	private JTextField numberOfIterationsField;
	private JTextField xSizeField;
	private static JTextArea textArea;
	private static JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Loader2 frame = new Loader2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// new DataLoader();
	}

	/**
	 * Create the frame.
	 */
	public Loader2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel = new JLabel("<html>The sample X size (in cm):</html>");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(9, 10, 145, 29);
		contentPane.add(lblNewLabel);

		xSizeField = new JTextField();
		xSizeField.setBounds(9, 39, 145, 20);
		contentPane.add(xSizeField);
		xSizeField.setColumns(10);

		JLabel lbltheSampleY = new JLabel("<html>The sample Y size (in cm):</html>");
		lbltheSampleY.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbltheSampleY.setHorizontalAlignment(SwingConstants.LEFT);
		lbltheSampleY.setBounds(164, 10, 160, 29);
		contentPane.add(lbltheSampleY);

		ySizeField = new JTextField();
		ySizeField.setColumns(10);
		ySizeField.setBounds(164, 39, 160, 20);
		contentPane.add(ySizeField);

		JLabel lblminimumDistanceTravelled = new JLabel(
				"<html>Minimum distance travelled in the sample (in microns): </html>");
		lblminimumDistanceTravelled.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblminimumDistanceTravelled.setBounds(9, 72, 145, 35);
		contentPane.add(lblminimumDistanceTravelled);

		minimumField = new JTextField();
		minimumField.setColumns(10);
		minimumField.setBounds(9, 107, 145, 20);
		contentPane.add(minimumField);

		JLabel lblmaximumDistanceTravelled = new JLabel(
				"<html>Maximum distance travelled in the sample (in microns): </html>");
		lblmaximumDistanceTravelled.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblmaximumDistanceTravelled.setBounds(334, 72, 160, 35);
		contentPane.add(lblmaximumDistanceTravelled);

		maximumField = new JTextField();
		maximumField.setColumns(10);
		maximumField.setBounds(334, 107, 160, 20);
		contentPane.add(maximumField);

		JButton btnCalculate = new JButton("Calculate");
		btnCalculate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textArea.setText(null);

				new DataLoader(xSizeField.getText(), ySizeField.getText(), numberOfIterationsField.getText(),
						minimumField.getText(), maximumField.getText());
			}
		});
		btnCalculate.setBounds(196, 92, 105, 35);
		contentPane.add(btnCalculate);

		JLabel lblnumberOfParticles = new JLabel("<html>Number of particles generated (in thousands):</html>");
		lblnumberOfParticles.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblnumberOfParticles.setBounds(334, 10, 160, 29);
		contentPane.add(lblnumberOfParticles);

		numberOfIterationsField = new JTextField();
		numberOfIterationsField.setColumns(10);
		numberOfIterationsField.setBounds(334, 39, 160, 20);
		contentPane.add(numberOfIterationsField);

		scrollPane = new JScrollPane();
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(9, 138, 483, 164);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setAutoscrolls(false);
		textArea.setDoubleBuffered(true);

	}

	static void addTextToBigField(String string) {
		textArea.append(string + "\n");
		textArea.update(textArea.getGraphics());
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		// scrollPane.setViewportView(textArea);
		// scrollPane.update(scrollPane.getGraphics());
	}
}
