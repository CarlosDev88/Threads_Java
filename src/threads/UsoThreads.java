package threads;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UsoThreads {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame marco = new MarcoRebote();

	}

}

class PelotaHilos implements Runnable {

	private Pelota pelota;
	private Component componenet;

	public PelotaHilos(Pelota pelota, Component unComponente) {
		this.pelota = pelota;
		this.componenet = unComponente;
	}

	@Override
	public void run() {
		// for (int i = 1; i <= 3000; i++) {

		while (!Thread.currentThread().isInterrupted()) {
			pelota.mueve_pelota(componenet.getBounds());
			componenet.paint(componenet.getGraphics());

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Thread.currentThread().interrupt();

			}

		}

	}

}

//Movimiento de la pelota-----------------------------------------------------------------------------------------

class Pelota {

	// Mueve la pelota invirtiendo posición si choca con límites

	public void mueve_pelota(Rectangle2D limites) {

		x += dx;

		y += dy;

		if (x < limites.getMinX()) {

			x = limites.getMinX();

			dx = -dx;
		}

		if (x + TAMX >= limites.getMaxX()) {

			x = limites.getMaxX() - TAMX;
			dx = -dx;
		}

		if (y < limites.getMinY()) {

			y = limites.getMinY();
			dy = -dy;
		}

		if (y + TAMY >= limites.getMaxY()) {

			y = limites.getMaxY() - TAMY;

			dy = -dy;

		}

	}

	// Forma de la pelota en su posición inicial

	public Ellipse2D getShape() {

		return new Ellipse2D.Double(x, y, TAMX, TAMY);

	}

	private static final int TAMX = 15;
	private static final int TAMY = 15;
	private double x = 0;
	private double y = 0;
	private double dx = 1;
	private double dy = 1;

}

// Lámina que dibuja las pelotas----------------------------------------------------------------------

class LaminaPelota extends JPanel {

	public void add(Pelota b) {
		pelotas.add(b);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Pelota b : pelotas) {
			g2.fill(b.getShape());
		}

	}

	private ArrayList<Pelota> pelotas = new ArrayList<Pelota>();
}

//Marco con lámina y botones------------------------------------------------------------------------------

class MarcoRebote extends JFrame {

	public MarcoRebote() {

		setBounds(200, 100, 600, 350);
		setTitle("Rebotes");

		lamina = new LaminaPelota();

		arranca1 = new Button("Hilo1");
		arranca1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comienza_el_juego(e);
			}
		});
		arranca2 = new Button("Hilo2");
		arranca2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comienza_el_juego(e);
			}
		});
		arranca3 = new Button("Hilo3");
		arranca3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				comienza_el_juego(e);
			}
		});

		detener1 = new Button("Detener1");
		detener1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detener(e);
			}
		});
		detener2 = new Button("Detener2");
		detener2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detener(e);
			}
		});
		detener3 = new Button("Detener3");
		detener3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				detener(e);
			}
		});

		add(lamina, BorderLayout.CENTER);
		JPanel laminaBotones = new JPanel();

		laminaBotones.add(arranca1);
		laminaBotones.add(arranca2);
		laminaBotones.add(arranca3);
		laminaBotones.add(detener1);
		laminaBotones.add(detener2);
		laminaBotones.add(detener3);

		add(laminaBotones, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	// Añade pelota y la bota 1000 veces

	public void comienza_el_juego(ActionEvent e) {

		Pelota pelota = new Pelota();
		lamina.add(pelota);
		Runnable r = new PelotaHilos(pelota, lamina);

		if (e.getSource().equals(arranca1)) {
			hilo = new Thread(r);
			hilo.start();

		} else if (e.getSource().equals(arranca2)) {
			hilo2 = new Thread(r);
			hilo2.start();

		} else if (e.getSource().equals(arranca3)) {
			hilo3 = new Thread(r);
			hilo3.start();
		}

	}

	public void detener(ActionEvent e) {

		if (e.getSource().equals(detener1)) {
			hilo.interrupt();

		} else if (e.getSource().equals(detener2)) {
			hilo2.interrupt();

		} else if (e.getSource().equals(detener3)) {
			hilo3.interrupt();
		}

	}

	private LaminaPelota lamina;
	private Thread hilo;
	private Thread hilo2;
	private Thread hilo3;

	Button arranca1;
	Button arranca2;
	Button arranca3;

	Button detener1;
	Button detener2;
	Button detener3;

}
