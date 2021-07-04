package threads;

import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReentrantLock;

public class BnacoSinSincronizar {

	public static void main(String[] args) {
		Banco b = new Banco();

		for (int i = 0; i < 100; i++) {
			EjecucionTransfencias r = new EjecucionTransfencias(b, i, 2000);
			Thread t = new Thread(r);
			t.start();
		}
	}

}

class Banco {

	private final double[] cuentas;
	//private Lock cierreBanco = new ReentrantLock();
	//private Condition saldoSuficiente;

	public Banco() {
		cuentas = new double[100];

		for (int i = 0; i < cuentas.length; i++) {
			cuentas[i] = 2000;
		}

		//saldoSuficiente = cierreBanco.newCondition();
	}

	public synchronized void transferencis(int cuentaOrigen, int cuentaDestino, double monto) throws InterruptedException {
		//cierreBanco.lock();

		//try {

			while (cuentas[cuentaOrigen] < monto) {			
				//saldoSuficiente.await();
				wait();
			} 

			// System.out.println(Thread.currentThread());
			cuentas[cuentaOrigen] -= monto;

			System.out.printf("%10.2f de %d para %d", monto, cuentaOrigen, cuentaDestino);
			cuentas[cuentaDestino] += monto;

			System.out.printf("--saldo total: %10.2f%n--", getSaldoTotal());
			//saldoSuficiente.signal();
			notifyAll();

		//} //finally {
			//cierreBanco.unlock();
		//}

	}

	public double getSaldoTotal() {
		double sumaCuenta = 0;

		for (double d : cuentas) {
			sumaCuenta += d;
		}

		return sumaCuenta;
	}

}

class EjecucionTransfencias implements Runnable {
	private Banco banco;
	private int deLacuneta;
	private double cantidadMax;

	public EjecucionTransfencias(Banco baco, int de, double max) {
		this.banco = baco;
		this.deLacuneta = de;
		this.cantidadMax = max;
	}

	@Override
	public void run() {

		try {
			while (true) {
				int paraLacuenta = (int) (100 * Math.random());
				double cantidad = cantidadMax * Math.random();

				banco.transferencis(deLacuneta, paraLacuenta, cantidad);
				Thread.sleep(200);
			}

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}
