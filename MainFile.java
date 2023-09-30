import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainFile implements ActionListener, ChangeListener{

    private final JFrame jFrame;
    private final JButton start,stop;
    private final CanvasGUI panel1;
    private final JPanel panel2,panel3;
    private final JSlider Trains, RapidTrains, speed;
    private final JLabel label1, label2, label3;

    private Train[] TrainsArray;
    private RapidTrain[] RapidTrainsArray;
    private TrainOverview TrainOverview;
    private RapidTrainOverview RapidTrainOverview;
    private static int TrainRunnableCount;
    private static int RapidTrainRunnableCount;

    public MainFile(){
        this.jFrame = new JFrame("Поезда");
        this.panel1 = new CanvasGUI();
        this.start = new JButton("Начать");
        this.stop = new JButton("Остановить");
        this.panel2 = new JPanel();
        this.panel3 = new JPanel();
        this.Trains = new JSlider();
        this.RapidTrains = new JSlider();
        this.speed = new JSlider();
        this.label1 = new JLabel("Количество Поездов");
        this.label2 = new JLabel("Количество Скоростных Поездов");
        this.label3 = new JLabel("Скорость");
    }

    public void launchFrame(){
        this.label1.setPreferredSize(new Dimension(180,24));
        this.label1.setFont(new Font("Arial",Font.PLAIN,12));
        this.label1.setHorizontalAlignment(SwingConstants.CENTER);

        this.label2.setPreferredSize(new Dimension(180,24));
        this.label2.setFont(new Font("Arial",Font.PLAIN,12));
        this.label2.setHorizontalAlignment(SwingConstants.CENTER);

        this.label3.setPreferredSize(new Dimension(180,24));
        this.label3.setFont(new Font("Arial",Font.PLAIN,12));
        this.label3.setHorizontalAlignment(SwingConstants.CENTER);

        this.Trains.setPreferredSize(new Dimension(180,45));
        this.Trains.setMinimum(0);
        this.Trains.setMaximum(200);
        this.Trains.setValue(40);
        this.Trains.setMajorTickSpacing(40);
        this.Trains.setMinorTickSpacing(20);
        this.Trains.setPaintTicks(true);
        this.Trains.setPaintLabels(true);
        this.Trains.setFont(new Font("Arial",Font.PLAIN,12));
        this.Trains.addChangeListener(this);

        this.RapidTrains.setPreferredSize(new Dimension(180,45));
        this.RapidTrains.setMinimum(0);
        this.RapidTrains.setMaximum(200);
        this.RapidTrains.setValue(40);
        this.RapidTrains.setMajorTickSpacing(40);
        this.RapidTrains.setMinorTickSpacing(20);
        this.RapidTrains.setPaintTicks(true);
        this.RapidTrains.setPaintLabels(true);
        this.RapidTrains.setFont(new Font("Arial",Font.PLAIN,12));
        this.RapidTrains.addChangeListener(this);

        this.speed.setPreferredSize(new Dimension(180,45));
        this.speed.setMinimum(0);
        this.speed.setMaximum(10);
        this.speed.setValue(3);
        this.speed.setMajorTickSpacing(2);
        this.speed.setMinorTickSpacing(1);
        this.speed.setPaintTicks(true);
        this.speed.setPaintLabels(true);
        this.speed.setFont(new Font("Arial",Font.PLAIN,12));
        this.speed.addChangeListener(this);

        this.start.setPreferredSize(new Dimension(80,30));
        this.start.setFont(new Font("Arial",Font.PLAIN,12));
        this.start.addActionListener(this);

        this.stop.setPreferredSize(new Dimension(80,30));
        this.stop.setFont(new Font("Arial",Font.PLAIN,12));
        this.stop.setEnabled(false);
        this.stop.addActionListener(this);

        this.panel1.setBorder(BorderFactory.createMatteBorder(1,1,0,0,Color.GRAY));
        this.panel1.setBackground(Color.WHITE);
        this.panel3.setPreferredSize(new Dimension(180,30));
        this.panel2.setBorder(BorderFactory.createMatteBorder(1,1,0,0,Color.GRAY));
        this.panel2.setPreferredSize(new Dimension(200,-1));
        this.panel2.add(this.label1,BorderLayout.NORTH);
        this.panel2.add(this.Trains,BorderLayout.NORTH);
        this.panel2.add(this.label2,BorderLayout.NORTH);
        this.panel2.add(this.RapidTrains,BorderLayout.NORTH);
        this.panel2.add(this.label3,BorderLayout.NORTH);
        this.panel2.add(this.speed,BorderLayout.NORTH);

        this.panel2.add(this.panel3,BorderLayout.NORTH);
        this.panel2.add(this.start,BorderLayout.NORTH);
        this.panel2.add(this.stop,BorderLayout.NORTH);

        jFrame.add(this.panel1, BorderLayout.CENTER);
        jFrame.add(this.panel2, BorderLayout.EAST);

        jFrame.setSize(1000,840);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
    public void initThreads(){
        final Random r = new Random();

        int TrainsCount = this.Trains.getValue();
        this.TrainsArray = new Train[TrainsCount];
        this.TrainOverview = new TrainOverview(this.panel1);

        for(int i = 0; i < TrainsCount; i++){
            this.TrainsArray[i] = new Train(i, this);

            this.TrainsArray[i].setParam(
                    30 + r.nextInt(740),30 + r.nextInt(740), this.speed.getValue());
        }

        int RapidTrainsCount = this.RapidTrains.getValue();
        this.RapidTrainsArray = new RapidTrain[RapidTrainsCount];
        this.RapidTrainOverview = new RapidTrainOverview(this.panel1);

        for(int j = 0; j < RapidTrainsCount; j++){
            this.RapidTrainsArray[j] = new RapidTrain(j, this);

            this.RapidTrainsArray[j].setParam(
                    30 + r.nextInt(740),30 + r.nextInt(740), this.speed.getValue());
        }

        this.panel1.setParam(TrainsArray, RapidTrainsArray);
        this.panel1.repaint();
    }

    public void startAll(){
        this.start.setEnabled(false);
        this.stop.setEnabled(true);
        this.Trains.setEnabled(false);
        this.RapidTrains.setEnabled(false);
        this.speed.setEnabled(false);

        if(this.TrainOverview.getState() == Thread.State.TERMINATED && this.RapidTrainOverview.getState() == Thread.State.TERMINATED)
            initThreads();

        TrainRunnableCount = 0;
        RapidTrainRunnableCount = 0;

        this.TrainOverview.start();
        this.RapidTrainOverview.start();

        int TrainsCount = this.Trains.getValue();
        for(int i = 0; i < TrainsCount; i++){
            TrainsArray[i].start();
        }
        int RapidTrainsCount = this.RapidTrains.getValue();
        for(int j = 0; j < RapidTrainsCount; j++){
            RapidTrainsArray[j].start();
        }
    }

    public void stopAll(){
        this.start.setEnabled(true);
        this.stop.setEnabled(false);
        this.Trains.setEnabled(true);
        this.RapidTrains.setEnabled(true);
        this.speed.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == this.start)
            this.startAll();
        else{
            int TrainsCount = this.Trains.getValue();
            for (int i = 0; i < TrainsCount; i++){
                this.TrainsArray[i].end();
            }
            int RapidTrainsCount = this.RapidTrains.getValue();
            for (int i = 0; i < RapidTrainsCount; i++){
                this.RapidTrainsArray[i].end();
            }
        }
    }

    public synchronized void ThreadTrainEnd(){
        TrainRunnableCount++;

        if(TrainRunnableCount == this.Trains.getValue()){
            this.TrainOverview.end();
        }
    }

    public synchronized void ThreadRapidTrainEnd(){
        RapidTrainRunnableCount++;

        if(RapidTrainRunnableCount == this.RapidTrains.getValue()){
            this.RapidTrainOverview.end();
        }
    }

    public synchronized void ThreadEnd(){
        if(RapidTrainRunnableCount == this.RapidTrains.getValue() && RapidTrainRunnableCount == this.RapidTrains.getValue())
            stopAll();
    }

    @Override
    public void stateChanged(ChangeEvent ce){
        this.initThreads();
    }

    public static void main(String[] args) {
        MainFile jFrame = new MainFile();
        jFrame.launchFrame();
        jFrame.initThreads();
    }
}