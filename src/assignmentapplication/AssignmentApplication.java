/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmentapplication;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

/**
 *
 * @author Mahir
 */

public class AssignmentApplication extends Application {
    //code for game
    AnimationTimer timer;
    private GameObject player;
    private GameObject ally;
    private GameObject ally2;
    private GameObject enemy;
    private Pane root;
    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(620, 620);
        player = new Player();
        ally = new Ally();
        ally2 = new Ally();
        enemy = new Enemy();
        System.out.println(ally);
        System.out.println(ally2);
        player.setVelocity(new Point2D(0, 0));
        player.setAcceleration(.05,.05);
        addGameObject(player, 300, 300);
        addGameObject(ally, 0, 0);
        addGameObject(enemy, 580, 480);
//        addGameObject(ally2,350,360);
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }
    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
                Circle temp = (Circle)player.getView();
                if (player.isColliding(enemy) || temp.getTranslateX() <= (root.getBoundsInLocal().getMinX()+temp.getRadius())|| temp.getTranslateX() >= (root.getBoundsInLocal().getMaxX()-temp.getRadius())|| temp.getTranslateY() <= (root.getBoundsInLocal().getMinY()+temp.getRadius())|| temp.getTranslateY() >= (root.getBoundsInLocal().getMaxY()-temp.getRadius())) {
                    enemy.setAlive(false);
                    player.setAlive(false);
                    ally.setAlive(false);
                    root.getChildren().removeAll(enemy.getView(), player.getView(),ally.getView());
                    timer.stop();
                }
                else if(player.isColliding(ally)){
                    double speedx=-player.getVelocity().getX();
                    double speedy=-player.getVelocity().getX();
                    player.setVelocity(new Point2D(speedx,speedy));
                    player.setAcceleration(-player.accx, -player.accy);
                }
                  player.update();
                
}
    //game code ends here
    StackPane s;
    StackPane s1;
    StackPane s2;
    Label swarning1;
    Label swarning2; 
    Label swarning3; 
    Label swarning4;
    Label ssuccess;
    Label fwarning;
    Label fwarning1;
    Label fwarning2;
    Label fwarning3;
    Label fsuccess;
    Label lwarning;
    Label lsuccess;
    protected boolean validcheck(String x){
        char [] a=x.toCharArray();
        boolean y=false;
        boolean z=false;
        for(int i=0;i<a.length;i++){
            if(Character.isUpperCase(a[i])){
                y=true;
            }
            if(Character.isDigit(a[i])){
                z=true;
            }
            if(y&&z){
                break;
            }
        
        }
        System.out.println(y&&z);
        return y&&z;
    }
    protected void accinsertdb(String n,String p) throws Exception{
        System.out.println(n+" "+p);
        boolean flag = true;
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:F:\\Fall 2019\\CSE310 project\\AssignmentApplication\\src\\assignmentapplication\\gamedb.db");
        Statement st = con.createStatement();
        Statement st2 = con.createStatement();
        Statement st3 = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT Name FROM playerinfo");
        while(rs.next()){
            if(n.equals(rs.getString("Name"))){
                swarning3.setVisible(true);
                flag=false;
                break;
            }
        }
        if(flag){
            swarning3.setVisible(false);
            ssuccess.setVisible(flag);
            String x=""+p.hashCode();
            st2.executeUpdate("INSERT INTO playerinfo VALUES ('"+n+"','"+x+"');");
            st3.executeUpdate("INSERT INTO passcheck VALUES ('"+n+"','"+x+"');");
        }
        rs.close();
        st.close();
        st2.close();
        st3.close();
        con.close();
    }
    protected void forgetpassdb(String n,String p) throws Exception{
        System.out.println(n+" "+p);
        boolean flag = true;
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:F:\\Fall 2019\\CSE310 project\\AssignmentApplication\\src\\assignmentapplication\\gamedb.db");
        Statement st = con.createStatement();
        Statement st2 = con.createStatement();
        Statement st3 = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM passcheck where Name = '"+n+"'");
        String x=""+p.hashCode();
        while(rs.next()){
            if(x.equals(rs.getString("Pass"))){
                fwarning1.setVisible(true);
                flag=false;
                break;
            }
        }
        if(flag){
            fwarning1.setVisible(false);
            fsuccess.setVisible(flag);
            st2.executeUpdate("UPDATE playerinfo SET Pass ='"+x+"' WHERE Name ='"+n+"';");
            System.out.println("flag");
            st3.executeUpdate("INSERT INTO passcheck VALUES ('"+n+"','"+x+"');");
            System.out.println("flag2");
        }
        rs.close();
        st.close();
        st2.close();
        st3.close();
        con.close();
    }
    protected boolean loginuserdb(String n,String p) throws Exception{
        boolean flag = true;
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:D:\\Fall 2019\\CSE310 project\\AssignmentApplication\\src\\assignmentapplication\\gamedb.db");
        Statement st = con.createStatement();
        Statement st2 = con.createStatement();
        Statement st3 = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM playerinfo");
        System.out.println(rs);
        String x=p.hashCode()+"";
        while(rs.next()){
            System.out.println("c1");
            if(n.equals(rs.getString("Name")) && x.equals(rs.getString("Pass"))){
                lsuccess.setVisible(true);
                flag=false;
                break;
            }
        }
        if(flag){
            lsuccess.setVisible(false);
            lwarning.setVisible(true);
        }
        rs.close();
        st.close();
        st2.close();
        st3.close();
        con.close();
        return !flag;
    }
    
    protected boolean usernameexists(String x)throws Exception{
        boolean flag = true;
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:F:\\Fall 2019\\CSE310 project\\AssignmentApplication\\src\\assignmentapplication\\gamedb.db");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT Name FROM playerinfo");
        while(rs.next()){
            if(x.equals(rs.getString("Name"))){
                flag=false;
                break;
            }
        }
        rs.close();
        st.close();
        con.close();
        return flag;
    }
    protected int signupmethod(String p,String cp){
        System.out.println(p +" "+cp);
        boolean x=validcheck(p);
        if(p.length()<8 || !(x)){
           int y=0;
           if(p.length()<8){
           y=1;
           }
           else if(!(x)){
           y=4;
           }
           return y;
        }
        else if(!(p.equals(cp))){
           return 2;
        }
        else{
           return 3;
        }
    }
    

    @Override
    public void start(Stage primaryStage) {
        
        VBox left = new VBox(10);
        Scene scene = new Scene(left, 300, 250);
        Label usern = new Label("Username:");
        TextField namef = new TextField();
        Label userp = new Label("Password:");
        PasswordField passf = new PasswordField();
        left.setPadding(new Insets(10));
        Button signin = new Button("Sign In");
        lwarning = new Label("Username and Password combination wrong please try again");
        lwarning.setVisible(false);
        lsuccess = new Label("Success!");
        lsuccess.setVisible(false);
        VBox gpage = new VBox(10);
        Scene mainpage = new Scene(gpage,300,300);
        Button ngame = new Button("New Game");
        ngame.setOnAction(e->{
            primaryStage.setScene(new Scene(createContent()));
        });
        
        Button lback = new Button("Go Back");
        lback.setOnAction(e -> primaryStage.setScene(scene));
        signin.setOnAction(
                e->{
                lwarning.setVisible(false);
                lsuccess.setVisible(false);
                    try {
            if(loginuserdb(namef.getText(),passf.getText())){
                lwarning.setVisible(false);
                lsuccess.setVisible(false);
                namef.clear();
                passf.clear();
                primaryStage.setScene(mainpage);
            }
            } catch (Exception ex) {
            }
});
        Label newuser = new Label("New here? Create an account:");
        Button signup = new Button("Signup");
        VBox supage = new VBox(10);
        Scene signuppage = new Scene(supage,400,400);
        Label luser = new Label("Enter your username");
        TextField suser = new TextField();
        Label lpass = new Label("Enter your pass, minimum 8 characters, and a capital letter and an integer");
        PasswordField spass = new PasswordField();
        Label confirmpass = new Label("Re-enter to confirm");
        PasswordField spass2 = new PasswordField();
        Button creatacc = new Button("Create your profile");
        swarning1 = new Label("Password must be of 8 characaters, a capital letter and an integer, please try again");
        swarning1.setVisible(false);
        swarning2 = new Label("Passwords don't match");
        swarning2.setVisible(false);
        swarning3 = new Label("User already exists please use a new name");
        swarning3.setVisible(false);
        swarning4 = new Label("Username cannot be empty");
        swarning4.setVisible(false);
        ssuccess = new Label("Success!");
        ssuccess.setVisible(false);
        s= new StackPane();
        creatacc.setOnAction(e -> {
            swarning1.setVisible(false);
            swarning2.setVisible(false);
            swarning3.setVisible(false);
            swarning4.setVisible(false);
            ssuccess.setVisible(false);
            if(suser.getText().length()==0){
                swarning4.setVisible(true);
            }
                else{
                    swarning4.setVisible(false);
                    int x=signupmethod(spass.getText(),spass2.getText());
                    if(x==1 || x==4){
                            swarning1.setVisible(true);
                            swarning2.setVisible(false);
                        }
                    else if(x==2){
                        swarning1.setVisible(false);
                        swarning2.setVisible(true);
                    }
                    else{
                        try {
                            swarning1.setVisible(false);
                            swarning2.setVisible(false);
                            accinsertdb(suser.getText(),spass.getText());
                        } catch (Exception ex) {
                            
                        }
                        
                    }
                }
        });
        Button back = new Button("Go back");
        signup.setOnAction(e->{primaryStage.setScene(signuppage);
                               lwarning.setVisible(false);
                               lsuccess.setVisible(false);
        });
        back.setOnAction(e->{primaryStage.setScene(scene);
                             swarning1.setVisible(false);
                             swarning2.setVisible(false);
                             swarning3.setVisible(false);
                             swarning4.setVisible(false);
                             ssuccess.setVisible(false);
                             suser.clear();
                             spass.clear();
                             spass2.clear();
        });
        Button forgetpass = new Button("Forgot Password?");
        supage.setPadding(new Insets(10));
        VBox fpass = new VBox(10);
        Scene fpscene = new Scene(fpass,300,300);
        forgetpass.setOnAction(e -> {primaryStage.setScene(fpscene);
                                     swarning1.setVisible(false);
                                     swarning2.setVisible(false);
                                     swarning3.setVisible(false);
                                     swarning4.setVisible(false);
                                     ssuccess.setVisible(false);
        });
        fpass.setPadding(new Insets(10));
        Label fpassu = new Label("Enter Username");
        TextField fpassue = new TextField();
        Label fpassp = new Label("Enter New Pass, minimum 8 characters, and a capital letter and an integer");
        PasswordField fpasspe = new PasswordField();
        Label fpasscp = new Label("Confirm New Pass");
        PasswordField fpasscpe = new PasswordField();
        Button confirm = new Button("Confirm");
        Button goback= new Button("Go Back");
        fwarning = new Label("User doesnot exist!");
        fwarning.setVisible(false);
        fwarning1 = new Label("Password already exists, try again.");
        fwarning1.setVisible(false);
        fwarning2 = new Label("Password must have minimum 8 characters, and a capital letter and an integer");
        fwarning2.setVisible(false);
        fwarning3 = new Label("Paswords don't match please try again");
        fwarning3.setVisible(false);
        fsuccess = new Label("Success!");
        fsuccess.setVisible(false);
        confirm.setOnAction(e ->{
            fwarning.setVisible(false);
            fwarning1.setVisible(false);           
            fwarning2.setVisible(false);
            fwarning3.setVisible(false);
            fsuccess.setVisible(false);
            try {
                if(usernameexists(fpassue.getText())){
                    fwarning.setVisible(true);
                }
                else{
                    fwarning.setVisible(false);
                    int x=signupmethod(fpasspe.getText(),fpasscpe.getText());
                    System.out.println(x);
                 if(x==1 || x==4){
                    fwarning1.setVisible(false);
                    fwarning2.setVisible(true);
                    fwarning3.setVisible(false);
                    fsuccess.setVisible(false);
                }
                 else if(x==2){
                    fwarning1.setVisible(false);
                    fwarning2.setVisible(false);
                    fwarning3.setVisible(true);
                    fsuccess.setVisible(false);   
                 }
                 else{
            try {   
                    fwarning1.setVisible(false);
                    fwarning2.setVisible(false);
                    fwarning3.setVisible(false);
                    fsuccess.setVisible(false);                    
                    forgetpassdb(fpassue.getText(),fpasspe.getText());
                        } catch (Exception ex) {
                            
                        }
                        
                    }
                }
            } catch (Exception ex) {   
            }
});
        
        s.getChildren().addAll(swarning1,swarning2,swarning3,ssuccess,swarning4);
        s1=new StackPane();
        s1.getChildren().addAll(fwarning,fwarning1,fwarning2,fwarning3,fsuccess);
        s2 = new StackPane();
        s2.getChildren().addAll(lwarning,lsuccess);
        goback.setOnAction(e->{primaryStage.setScene(signuppage);
                               fwarning.setVisible(false);
                               fwarning1.setVisible(false);
                               fwarning2.setVisible(false);
                               fwarning3.setVisible(false);
                               fsuccess.setVisible(false);
                               fpassue.clear();
                               fpasspe.clear();
                               fpasscpe.clear();
                                       
        });
        fpass.getChildren().addAll(fpassu,fpassue,fpassp,fpasspe,fpasscp,fpasscpe,s1,confirm,goback);
        supage.getChildren().addAll(luser,suser,lpass,spass,confirmpass,spass2,creatacc,s,back,forgetpass);
        left.getChildren().addAll(usern,namef,userp,passf,signin,s2,newuser,signup);
        gpage.getChildren().addAll(ngame,lback);
        primaryStage.setTitle("ProjectAPP");
        Scene gscene = new Scene(createContent());
        primaryStage.setScene(gscene);
        ally.getView().setOnMousePressed(e->{ally.getView().setOnMouseDragged(f->{
            ally.getView().setTranslateX(f.getSceneX()-20);
            ally.getView().setTranslateY(f.getSceneY()-10);
        });
        }); 
        VBox pbox = new VBox(10);
        Button rbutton = new Button("Resume");
        rbutton.setOnAction(e->{
            primaryStage.setScene(gscene);
            timer.start();
        });
        pbox.getChildren().addAll(rbutton);
        Scene pscene = new Scene(pbox,300,300);
        primaryStage.getScene().setOnKeyPressed(mscene->{
                if(mscene.getCode()==KeyCode.LEFT){
                    ally.rotateLeft();
                }
                else if(mscene.getCode()==KeyCode.RIGHT){ 
                    ally.rotateRight();
                }
                else if(mscene.getCode()==KeyCode.ESCAPE){
                timer.stop();
                primaryStage.setScene(pscene);
                }
            });
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private static class Enemy extends GameObject {
        Enemy() {
            super(new Rectangle(40, 20, Color.RED));
        }
    }

    private static class Player extends GameObject {
        Player() {
            super(new Circle(15, Color.BLUE));
        }
    }
    private static class Ally extends GameObject {
        
        Ally() {
            super(new Rectangle(40, 20, Color.YELLOW));
        }
//        @Override
//        public Node getView() {
//            return super.getView(); //To change body of generated methods, choose Tools | Templates.
//        }
//        public void setView(){
//            r.setFill(Color.GREEN);
//        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
