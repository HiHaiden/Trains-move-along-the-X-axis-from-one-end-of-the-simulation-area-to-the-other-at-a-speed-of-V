import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class CanvasGUI extends JPanel {

    private Image TrainImage, RapidTrainImage;
    private Train[] Trains;
    private RapidTrain[] RapidTrains;

    public CanvasGUI(){
        try{
            this.TrainImage = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("Train32.png")));
            this.RapidTrainImage = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("RapidTrain32.png")));
        }
        catch (IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void setParam(Train[] Trains, RapidTrain[] RapidTrains){
        this.Trains = Trains;
        this.RapidTrains = RapidTrains;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(this.getBackground());
        g.fillRect(0,0,this.getWidth(),this.getHeight());

        g.setFont(g.getFont().deriveFont(Font.BOLD,9.0f));
        if(this.Trains != null){
            for (Train train : this.Trains){
                g.drawImage(this.TrainImage, train.getXLeft(),train.getYLeft(),null);
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(train.getNum()),train.getXLeft() + 13, train.getYLeft() + 22);
            }
        }
        if(this.RapidTrains != null){
            for (RapidTrain rapidtrain : this.RapidTrains){
                g.drawImage(this.RapidTrainImage, rapidtrain.getXLeft(), rapidtrain.getYLeft(),null);
                g.setColor(Color.BLUE);
                g.drawString(Integer.toString(rapidtrain.getNum()),rapidtrain.getXLeft() + 12, rapidtrain.getYLeft() + 21);
            }
        }
    }
}