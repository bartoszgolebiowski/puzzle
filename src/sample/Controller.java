package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;



public class Controller {
    @FXML
    private Button button;
    @FXML
    private GridPane grid;

    int[] original;
    int[] puzzle;
    BufferedImage imgs[];
    ArrayList<Integer> shuffle;
    ImageView[] node;
    public boolean flaga;
    int index1,index2, x1,y1,x2,y2;

    public Controller() {

        node = new ImageView[16];
        shuffle = new ArrayList<>();
        original = new int[16];
        puzzle = new int[16];
        for(int i=0;i<16;i++)
        {
            shuffle.add(i);
        }

    }
    @FXML
    void initialize(){

    }
    @FXML
    public void onActionButton(){
        try
        {
            splittingImage("C:\\Users\\Ituriello\\001puzzle\\src\\sample\\kolorowy.png");

        }
        catch(Exception e1)
        {
            System.out.println("Picture problem");
        }

    }
    public boolean check(int index) {
        if (index == -1)
        {
            return true;
        }
        return false;
    }
    @FXML
    public void click(MouseEvent e){

        if (check(whichOne(e.getTarget().hashCode())))
        {
            return;
        }
        if(!flaga) {

            index1 = whichOne(e.getTarget().hashCode());
            x1 = index1 % 4;
            y1 = index1 / 4 + 1;
            flaga=!flaga;
        }
        else
            {
            index2 = whichOne(e.getTarget().hashCode());
            x2 = index2 % 4;
            y2 = index2/ 4 + 1;

            Node add = new ImageView(
                    SwingFXUtils
                            .toFXImage(
                                    imgs[shuffle.get(index1)],
                                    null));

            grid.add(add,x2,y2);
            Integer hash = add.hashCode();

            add = new ImageView(
                    SwingFXUtils
                            .toFXImage(
                                    imgs[shuffle.get(index2)],
                                    null));

            grid.add(add,x1,y1);

            puzzle[index1]=add.hashCode();
            puzzle[index2]=hash;

            Integer pozycja = shuffle.get(index1);
            shuffle.set(index1,shuffle.get(index2));
            shuffle.set(index2,pozycja);
            flaga=!flaga;
        }

    }


    public int whichOne(int hashCode){

        for (int i=0;i<16;i++){
            if ( puzzle[i] == hashCode )

            return i;
        }
        return -1;
    }
    public void splittingImage(String path) {
        try
        {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            BufferedImage image = ImageIO.read(fis);
            int rows = 4;
            int cols = 4;
            int chunks = rows * cols;
            int chunkWidth = image.getWidth() / cols;
            int chunkHeight = image.getHeight() / rows;

            imgs = new BufferedImage[chunks];
            Collections.shuffle(shuffle);

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    imgs[x*4+y] = image.getSubimage(chunkWidth*x,chunkHeight*y,chunkWidth,chunkHeight);
                }
            }

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    node[x*4+y] = new ImageView(SwingFXUtils.toFXImage(imgs[shuffle.get(x*4+y)],null));
                    grid.add(node[x*4+y],y,x+1);
                    puzzle[x*4+y]=node[x*4+y].hashCode();
                }
            }

            fis.close();
        } catch (Exception e1) {
            System.out.println("Wrong path");
        }

    }

}


