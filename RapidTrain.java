import java.util.Random;

public class RapidTrain extends Thread{

    private final int num;

    private final int w = 32;

    private Double x0;
    private Double y0;
    private int direction;
    private double speed;

    private final MainFile parent;

    private boolean ended;

    public RapidTrain(int num, MainFile parent){
        this.num = num + 1;
        this.ended = false;
        this.parent = parent;
    }

    public void setParam(int x0, int y0, int speed){
        this.x0 = (double) x0;
        this.y0 = (double) y0;

        Random r = new Random();
        direction = r.nextInt(2);
        this.speed = speed * 2.5;
    }

    public int getXLeft(){
        return (int)Math.round(this.x0 - this.w / 2.0);
    }

    public int getYLeft(){
        int h = 32;
        return (int)Math.round(this.y0 - h / 2.0);
    }

    public int getNum(){
        return this.num;
    }

    public void end(){
        this.ended = true;
    }

    public void run(){
        while (!this.ended) {

            if (direction == 1) {
                this.y0 -= speed;
            } else {
                this.y0 += speed;
            }

            int wp = 800;
            if (y0 >= wp - w / 2)
                direction = 1;
            if (y0 <= w / 2)
                direction = 2;

            try {
                RapidTrainOverview.sleep(30);
            } catch (InterruptedException ex) {
                System.out.println("Error:" + ex.getMessage());
            }

        }
        this.parent.ThreadRapidTrainEnd();
        this.parent.ThreadEnd();
    }
}