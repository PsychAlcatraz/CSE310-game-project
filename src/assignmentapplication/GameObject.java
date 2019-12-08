/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package assignmentapplication;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

/**
 *
 * @author 18101264
 */
public class GameObject{       
    private Node view;
    private Point2D velocity = new Point2D(0, 0);
    private boolean alive = true;
    protected double accx;
    protected double accy;

    public GameObject(Node view) {
        this.view = view;
    }

    public void update() {
        velocity = velocity.add(accx,accy);
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }
    
    public void setAcceleration(double accx, double accy){
        this.accx=accx;
        this.accy=accy;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + 20);
//        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 20);
//        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(GameObject other) {
//        System.out.println(getView().getBoundsInLocal().intersects(other.getView().getBoundsInLocal()));
        Shape intx = Shape.intersect((Shape)this.getView(), (Shape)other.getView());
//        System.out.println(intx.getBoundsInLocal().getWidth());
//        System.out.println(intx.getBoundsInLocal().getHeight()); 
        return intx.getBoundsInLocal().getWidth() != -1;
    }
}
