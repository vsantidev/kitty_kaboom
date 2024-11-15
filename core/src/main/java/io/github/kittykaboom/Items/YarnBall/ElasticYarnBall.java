package io.github.kittykaboom.Items.YarnBall;

public class ElasticYarnBall extends YarnBall {
    
    //____________ COSNTRUCTOR ____________
        public ElasticYarnBall(float x, float y) {
            super(x, y, "textures/elastic_yarn_ball.png"); // Texture spécifique
        }
    //____________ METHODS ____________
    @Override
    public void explode() {
        System.out.println("Elastic YarnBall: bounces off walls!");
        // Ajoutez la logique pour l'effet élastique ici !!!!!!
        
    }
}