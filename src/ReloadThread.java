package src;

import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;

public class ReloadThread implements Runnable {
    JLabel rechargeLabel;
	GamePanel gp;
	public ReloadThread(JLabel r, GamePanel gp)
	{
		rechargeLabel = r;
		this.gp = gp;
	}

    public void run()
		{
			int i = 150;
			String recharge = "Reloading: ";
			String block = Character.toString ((char) i);
			recharge = recharge.concat(block);
			rechargeLabel.setText(recharge);
			try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			recharge = recharge.concat(block);
			rechargeLabel.setText(recharge);
			try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//BROKEN RECHARGE
			recharge = recharge.concat(block);
			rechargeLabel.setText(recharge);
			((Tank)(gp.actors.get(0))).setReload(true);
		}
	}
