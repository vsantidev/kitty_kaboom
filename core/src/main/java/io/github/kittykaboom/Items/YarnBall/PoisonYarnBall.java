package io.github.kittykaboom.Items.YarnBall;

public class PoisonYarnBall extends YarnBall{
    //____________ COSNTRUCTOR ____________
        public PoisonYarnBall(float x, float y) {
            super(x, y, "textures/poison_yarn_ball.png"); // Texture spécifique
        }
    
        //____________ METHODS ____________
        @Override
        public void explode() {
            System.out.println("Poison YarnBall: leaves a toxic residue!");
            // Ajoutez la logique pour l'effet de poison ici
        }
}