import javax.swing.*;

public class TrainOverview extends Thread{
    private boolean ended;
    private final JPanel panel;

    public TrainOverview(JPanel panel){
        this.panel = panel;
        this.ended = false;
        this.setPriority(Thread.MAX_PRIORITY);
    }

    public void end(){
        this.ended = true;
    }

    public void run(){
        while (!this.ended) {
            this.panel.repaint();
            try {
                TrainOverview.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println("Error:" + ex.getMessage());
            }
        }

    }
}