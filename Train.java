import java.util.Random;

public class Train extends Thread{

    private final int num;

    private final int h = 32;

    private Double x0;
    private Double y0;
    private int direction;
    private double speed;

    private final MainFile parent;

    private boolean ended;

    public Train(int num, MainFile parent){
        this.num = num + 1;
        this.ended = false;
        this.parent = parent;
    }

    public void setParam(int x0, int y0, int speed){
        this.x0 = (double) x0;
        this.y0 = (double) y0;

        Random r = new Random();
        direction = r.nextInt(2);
        this.speed = speed;
    }

    public int getXLeft(){
        int w = 32;
        return (int)Math.round(this.x0 - w / 2.0);
    }

    public int getYLeft(){
        return (int)Math.round(this.y0 - this.h / 2.0);
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
                this.x0 -= speed;
            } else {
                this.x0 += speed;
            }

            int hp = 800;
            if (x0 >= hp - h / 2)
                direction = 1;
            if (x0 <= h / 2)
                direction = 2;

            try {
                TrainOverview.sleep(30);
            } catch (InterruptedException ex) {
                System.out.println("Error:" + ex.getMessage());
            }

        }
        this.parent.ThreadTrainEnd();
        this.parent.ThreadEnd();
    }
}