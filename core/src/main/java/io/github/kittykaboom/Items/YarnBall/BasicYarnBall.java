package io.github.kittykaboom.Items.YarnBall;

public class BasicYarnBall extends YarnBall {
    //____________ COSNTRUCTOR ____________
    public BasicYarnBall(float x, float y) {
        super(x, y, "textures/basic_yarn_ball.png");
    }

    //____________ METHODS ____________
    @Override
    public void attack(){
        System.out.println("Basic YarnBall: causes a small explosion!");
        // -> Rajoutez la logique de l'effet de base là !!!!
    }
}