package threads;

public class SincronizandoHilos {
	public static void main(String[] args) {
		HilosVarios h = new HilosVarios();
		HilosVarios2 h2 = new HilosVarios2(h);
	
		h2.start();
		h.start();

	}
}

class HilosVarios extends Thread {

	public void run() {

		for (int i = 0; i < 15; i++) {
			System.out.println("ejecutando hilo: " + this.getName());

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

}

class HilosVarios2 extends Thread {

	private Thread hilo;

	public HilosVarios2(Thread hilo) {
		this.hilo = hilo;
	}

	public void run() {
		
		try {
			hilo.join();
		} catch (InterruptedException e1) {
			
			e1.printStackTrace();
		}

		for (int i = 0; i < 15; i++) {
			System.out.println("ejecutando hilo: " + this.getName());

			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

}
