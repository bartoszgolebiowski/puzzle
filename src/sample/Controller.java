package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.Node.*;


public class Controller {
    @FXML
    private Button button;
    @FXML
    private GridPane grid;

    int[] original = new int[16];
    int[] puzzle = new int[16];
    BufferedImage imgs[];
    ArrayList<Integer> shuffle;
    Node[] node;
    public boolean flaga;
    int index1,index2, x1,y1,x2,y2;
    public Controller() {
        node = new Node[16];
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
            index1=0;
            index2=0;
            x1=x2=y1=y2=0;
            flaga=false;
            return true;
        }
        return false;

    }
    @FXML
    public void click(MouseEvent e){
        if(!flaga) {
            System.out.println(e.getTarget().hashCode());
            index1 = whichOne(e.getTarget().hashCode());
            if (check(index1))
            {
                flaga=!flaga;
                return;
            }
            x1 = index1 % 4;
            y1 = index1 / 4 + 1;
            System.out.println(index1);
            System.out.println(x1);
            System.out.println(y1);
            flaga=!flaga;
        }
        else
            {
            index2 = whichOne(e.getTarget().hashCode());
            if (check(index2))
            {
                flaga=!flaga;
                return;
            }
            x2 = index2 % 4;
            y2 = index2/ 4 + 1;
            System.out.println(index2);
            System.out.println(x2);
            System.out.println(y2);




            Node add = new ImageView(
                    SwingFXUtils
                            .toFXImage(
                                    imgs[shuffle.get(index1)],
                                    null));
            grid.add(add,x2,y2);
            add = new ImageView(
                    SwingFXUtils
                            .toFXImage(
                                    imgs[shuffle.get(index2)],
                                    null));
            grid.add(add,x1,y1);
                Integer temp = shuffle.get(index1);
                shuffle.set(index1,shuffle.get(index2));
                shuffle.set(index2,temp);
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

            shuffle = new ArrayList<>();


            for(int i=0;i<16;i++)
            {
                shuffle.add(i);
            }
            Collections.shuffle(shuffle);

            imgs = new BufferedImage[chunks];
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    imgs[x*4+y] = image.getSubimage(chunkWidth*x,chunkHeight*y,chunkWidth,chunkHeight);
                    original[x*4+y]=imgs[x*4+y].hashCode();
                }
            }

            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    node[x*4+y] = new ImageView(SwingFXUtils.toFXImage(imgs[shuffle.get(x*4+y)],null));
                    grid.add(node[x*4+y],y,x+1);
                    puzzle[x*4+y]=node[x*4+y].hashCode();
                }
            }


            //for (int i = 0; i < imgs.length; i++) {
          //      ImageIO.write(imgs[i], "png", new File("src\\sample\\lena" + i + ".png"));
         //   }
            fis.close();
        } catch (Exception e1) {
            System.out.println("Wrong path");
        }

    }

}


