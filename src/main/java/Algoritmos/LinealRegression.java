package Algoritmos;

import Estructura.Table;

import java.util.List;

public class LinealRegression implements Algorithm<Table, Double, Double>{
    private Double alpha;
    private Double beta;

    public LinealRegression() {
        this.alpha = 0.0;
        this.beta = 0.0;
    }

    public void train(Table data){
        List<Double> x = data.getClumAt(0);
        List<Double> y = data.getClumAt(1);
        Double sumax= 0.0;
        Double sumay= 0.0;

        for (int i = 0; i<x.size(); i++){
            sumax += x.get(i);
            sumay += y.get(i);
        }
        Double mediax= sumax/x.size();
        Double mediay= sumay/y.size();
        Double numerador = 0.0;
        Double denominador = 0.0;
        for (int i = 0; i<x.size(); i++){
            numerador += ((x.get(i)-mediax)*(y.get(i)-mediay));
            denominador += Math.pow((x.get(i)-mediax), 2);
        }
        alpha = numerador/denominador;
        beta = mediay-(alpha*mediax);
    }
    public Double estimate(Double sample){
        return (alpha * sample)+beta;
    }

    public Double getAlpha() {
        return alpha;
    }

    public Double getBeta() {
        return beta;
    }
}
